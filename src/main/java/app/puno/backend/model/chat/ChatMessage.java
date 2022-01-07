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

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
