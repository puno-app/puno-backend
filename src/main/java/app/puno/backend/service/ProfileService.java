package app.puno.backend.service;

import app.puno.backend.model.Profile;
import app.puno.backend.repository.ProfileRepository;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final PasswordEncoder passwordEncoder;

	public ProfileService(
			@NonNull ProfileRepository profileRepository,
			@NonNull PasswordEncoder passwordEncoder
	) {
		this.profileRepository = profileRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@NonNull
	public static ProfileUserDetails wrapProfile(@NonNull Profile profile) {
		return new ProfileUserDetails(profile);
	}

	public boolean doesProfileExist(final @NonNull String username, final @NonNull String email) {
		return profileRepository.findByUsernameOrEmail(username, email) != null;
	}

	@NonNull
	public Profile createProfile(final @NonNull String username, final @NonNull String email,
			final @NonNull String password) {
		Profile profile = new Profile();
		profile.setUsername(username);
		profile.setEmail(email);
		profile.setPassword(passwordEncoder.encode(password));
		profile.setVerified(false);
		profileRepository.save(profile);
		return profile;
	}

	@Getter
	public static class ProfileUserDetails implements UserDetails {

		private final Profile profile;
		private final Set<? extends GrantedAuthority> authorities;

		public ProfileUserDetails(Profile profile) {
			this.profile = profile;
			this.authorities = Set.of(new SimpleGrantedAuthority(profile.getRole().name()));
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		@Override
		public String getPassword() {
			return profile.getPassword();
		}

		@Override
		public String getUsername() {
			return profile.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}
