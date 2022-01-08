package app.puno.backend.model.chat;

import app.puno.backend.model.Job;
import app.puno.backend.model.Model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Chat extends Model {

	@OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
	private Set<ChatMessage> messages = new HashSet<>();

	@OneToOne
	private Job job;

	public boolean isJobChat() {
		return job != null;
	}

	public Set<ChatMessage> getMessages() {
		return this.messages;
	}

	public Job getJob() {
		return this.job;
	}

	public void setMessages(Set<ChatMessage> messages) {
		this.messages = messages;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}
