package app.puno.backend.model.chat;

import app.puno.backend.model.Job;
import app.puno.backend.model.Model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chat extends Model {

	@OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
	private List<ChatMessage> messages = new ArrayList<>();

	@OneToOne
	private Job job;

	public boolean isJobChat() {
		return job != null;
	}

}
