package app.puno.backend.service;

import app.puno.backend.model.Profile;
import app.puno.backend.model.authentication.RefreshToken;
import app.puno.backend.repository.ProfileRepository;
import app.puno.backend.repository.RefreshTokenRepository;
import app.puno.backend.util.JwtUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	public static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MINUTES = 60 * 24;

	private final ProfileRepository profileRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationService(
			final @NonNull ProfileRepository profileRepository,
			final @NonNull RefreshTokenRepository refreshTokenRepository,
			final @NonNull JwtUtils jwtUtils,
			final @NonNull PasswordEncoder passwordEncoder
	) {
		this.profileRepository = profileRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtUtils = jwtUtils;
		this.passwordEncoder = passwordEncoder;
	}


	public LoginResult createTokens(final @NonNull Profile profile) {
		String jwtToken = jwtUtils.createToken(profile);
		UUID refreshTokenId = refreshTokenRepository.save(new RefreshToken(profile)).getId();
		return new LoginResult(jwtToken, refreshTokenId);
	}

	@Nullable
	public LoginResult login(final @NonNull String usernameOrEmail, final @NonNull String password) {
		Profile profile = profileRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		if (profile == null) {
			return null;
		}
		if (!passwordEncoder.matches(password, profile.getPassword())) {
			return null;
		}

		return createTokens(profile);
	}

	@Nullable
	public String refresh(final @NonNull UUID refreshTokenId) {
		RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenId).orElse(null);
		if (refreshToken == null) {
			return null;
		}
		if (refreshToken.getCreateTimestamp().plus(REFRESH_TOKEN_EXPIRE_TIME_IN_MINUTES, ChronoUnit.MINUTES)
				.isBefore(LocalDateTime.now(refreshToken.getZoneId()))) {
			refreshTokenRepository.delete(refreshToken);
			return null;
		}
		return jwtUtils.createToken(refreshToken.getProfile());
	}

	public void logout(final @NonNull Profile authenticatedProfile) {
		refreshTokenRepository.deleteByProfileId(authenticatedProfile.getId());
	}


	public record LoginResult(String token, UUID refreshToken) {

	}

}
