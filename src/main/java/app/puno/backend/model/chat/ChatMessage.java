package app.puno.backend.model.chat;

import app.puno.backend.model.Model;
import app.puno.backend.model.Profile;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ChatMessage extends Model {

	@ManyToOne(optional = false)
	private Chat chat;

	@ManyToOne(optional = false)
	private Profile profile;

	private String content;

}
