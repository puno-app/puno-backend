package app.puno.backend.model;

import app.puno.backend.model.chat.Chat;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Job extends Model {

	private String title;

	@Column(length = 500)
	private String description;

	@ManyToOne(optional = false)
	private Profile employer;

	@ManyToOne
	private Profile employee;

	@OneToOne
	private Chat chat;

	@ManyToMany
	@JoinTable(name = "job_tags",
			joinColumns = @JoinColumn(name = "job_id"),
			inverseJoinColumns = @JoinColumn(name = "tags_id"))
	private Set<Tag> tags = new LinkedHashSet<>();

	private Status status = Status.STARTED;

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public Profile getEmployer() {
		return this.employer;
	}

	public Profile getEmployee() {
		return this.employee;
	}

	public Chat getChat() {
		return this.chat;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmployer(Profile employer) {
		this.employer = employer;
	}

	public void setEmployee(Profile employee) {
		this.employee = employee;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		STARTED,
		CLOSED,
		LOCKED,
		COMPLETED
	}

}
