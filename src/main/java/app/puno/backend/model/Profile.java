package app.puno.backend.model;

import app.puno.backend.model.authentication.RefreshToken;
import app.puno.backend.model.chat.ChatMessage;
import app.puno.backend.service.ProfileService.ProfileUserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "profile", indexes = {
		@Index(name = "idx_profile_username_email_unq", columnList = "email, username", unique = true)
})
@Getter
@Setter
public class Profile extends Model {

	private String username;
	private String password;
	private String email;

	private Role role = Role.DEFAULT;

	private boolean verified;

	@OneToMany(mappedBy = "employer", cascade = CascadeType.DETACH)
	private List<Job> createdJobs = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.DETACH)
	private List<Job> employedJobs = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.DETACH)
	private List<ChatMessage> messages = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
	private List<RefreshToken> refreshTokens = new ArrayList<>();

	@OneToMany
	private List<Tag> tags;

	@NonNull
	public static Profile getAuthenticatedProfile() {
		Authentication authentication = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication(),
				"Couldn't find authenticated profile(is this being called on a secure endpoint?)");
		if (!(authentication.getPrincipal() instanceof ProfileUserDetails profileDetails)) {
			throw new IllegalStateException("Invalid authentication principal found (is this a profile?)");
		}
		return profileDetails.getProfile();
	}

	public enum Role {
		ADMINISTRATOR,
		DEFAULT,
	}

}
