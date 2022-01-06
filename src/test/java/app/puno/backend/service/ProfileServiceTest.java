package app.puno.backend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import app.puno.backend.model.Profile;
import app.puno.backend.model.Profile.Role;
import app.puno.backend.repository.ProfileRepository;
import app.puno.backend.service.ProfileService.ProfileUserDetails;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileServiceTest {

	private final ProfileService profileService;

	public ProfileServiceTest() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

		when(profileRepository.save(any(Profile.class))).thenAnswer((Answer<Profile>) invocation -> {
			Profile profile = invocation.getArgument(0, Profile.class);
			profile.setId(UUID.randomUUID());
			return profile;
		});

		this.profileService = new ProfileService(profileRepository, passwordEncoder);

	}

	@Test
	@Order(1)
	@DisplayName("When a profile is created, make sure it is not null and assign it a unique id")
	void createProfile() {
		Profile result = profileService.createProfile("test", "test@test.com", "password");
		assertNotNull(result);
		assertNotNull(result.getId());
	}

	@Test
	@Order(2)
	@DisplayName("Create a ProfileUserDetails from a profile and assert the role is being provided properly.")
	void wrapProfile() {
		Profile result = profileService.createProfile("test", "test@test.com", "password");
		ProfileUserDetails profileUserDetails = ProfileService.wrapProfile(result);
		assertSame(profileUserDetails.getProfile(), result);
		assertTrue(profileUserDetails.getAuthorities().stream().anyMatch(
				authority -> authority instanceof SimpleGrantedAuthority simpleGrantedAuthority
						&& simpleGrantedAuthority.getAuthority().equals(
						Role.DEFAULT.name())));
	}

}