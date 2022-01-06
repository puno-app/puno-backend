package app.puno.backend.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import app.puno.backend.model.Profile;
import app.puno.backend.model.Profile.Role;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtUtilsTest {

	private JwtUtils jwtUtils;
	private Profile profile;

	@BeforeEach
	void setUp() {
		jwtUtils = new JwtUtils("jwt_secret_key_puno_app");
		profile = new Profile() {{
			setId(UUID.randomUUID());
			setUsername("bob");
			setPassword("password");
		}};
	}

	@Test
	@Order(1)
	@DisplayName("Expect JWT token to be created.")
	void createToken() {
		assertDoesNotThrow(() -> {
			String token = jwtUtils.createToken(profile);
			assertFalse(token.isBlank());
		});
	}

	@Test
	@Order(2)
	@DisplayName("Expect a valid JWT token to be provided and parsed correctly")
	void parseCreatedToken() {
		String token = jwtUtils.createToken(profile);
		assertSame(jwtUtils.getRole(token), Role.DEFAULT);
		assertEquals(jwtUtils.getProfileId(token), profile.getId());
	}

}