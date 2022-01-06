package app.puno.backend.model;

import app.puno.backend.model.chat.Chat;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Job extends Model {

	private String title;

	private String description;

	@ManyToOne(optional = false)
	private Profile employer;

	@ManyToOne
	private Profile employee;

	@OneToOne
	private Chat chat;

	@OneToMany
	private List<Tag> tags;

	private Status status;

	public enum Status {
		STARTED,
		CLOSED,
		LOCKED,
		COMPLETED
	}

}
