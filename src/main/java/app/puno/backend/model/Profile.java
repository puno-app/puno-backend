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
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "profile", indexes = {
		@Index(name = "idx_profile_username_email_unq", columnList = "email, username", unique = true)
})
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

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public Role getRole() {
		return this.role;
	}

	public boolean isVerified() {
		return this.verified;
	}

	public List<Job> getCreatedJobs() {
		return this.createdJobs;
	}

	public List<Job> getEmployedJobs() {
		return this.employedJobs;
	}

	public List<ChatMessage> getMessages() {
		return this.messages;
	}

	public List<RefreshToken> getRefreshTokens() {
		return this.refreshTokens;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setCreatedJobs(List<Job> createdJobs) {
		this.createdJobs = createdJobs;
	}

	public void setEmployedJobs(List<Job> employedJobs) {
		this.employedJobs = employedJobs;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}

	public void setRefreshTokens(List<RefreshToken> refreshTokens) {
		this.refreshTokens = refreshTokens;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public enum Role {
		ADMINISTRATOR,
		DEFAULT,
	}

}
