package app.puno.backend.controller.authentication;

import static app.puno.backend.util.ResponseUtils.fail;

import app.puno.backend.controller.authentication.request.RefreshRequest;
import app.puno.backend.controller.authentication.request.RegisterRequest;
import app.puno.backend.controller.authentication.response.LoginRequest;
import app.puno.backend.controller.authentication.response.LoginResponse;
import app.puno.backend.controller.authentication.response.RefreshResponse;
import app.puno.backend.model.Profile;
import app.puno.backend.service.AuthenticationService;
import app.puno.backend.service.AuthenticationService.LoginResult;
import app.puno.backend.service.ProfileService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final ProfileService profileService;
	private final AuthenticationService authenticationService;

	public AuthenticationController(ProfileService profileService,
			AuthenticationService authenticationService) {
		this.profileService = profileService;
		this.authenticationService = authenticationService;
	}

	@PreAuthorize("isAnonymous()")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
		if (profileService.doesProfileExist(registerRequest.username(), registerRequest.password())) {
			return fail("Username/E-Mail already exists.", HttpStatus.CONFLICT);
		}
		Profile profile = profileService.createProfile(registerRequest.username(), registerRequest.email(),
				registerRequest.password());

		LoginResult result = authenticationService.createTokens(profile);
		return ResponseEntity.ok(new LoginResponse(result.refreshToken(), result.token()));
	}

	@PreAuthorize("isAnonymous()")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
		LoginResult result = authenticationService.login(loginRequest.identifier(), loginRequest.password());
		if (result == null) {
			return fail("Invalid username or password.", HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(new LoginResponse(result.refreshToken(), result.token()));
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		authenticationService.logout(Profile.getAuthenticatedProfile());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
		String result = authenticationService.refresh(request.refreshToken());
		if (result == null) {
			return fail("Invalid refresh token provided.", HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(new RefreshResponse(result));
	}

}
