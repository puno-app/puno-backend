package app.puno.backend.model.authentication;

import app.puno.backend.model.Model;
import app.puno.backend.model.Profile;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RefreshToken extends Model {

	@ManyToOne(optional = false)
	private Profile profile;

	public RefreshToken() {

	}

	public RefreshToken(Profile profile) {
		this.profile = profile;
	}

	public Profile getProfile() {
		return this.profile;
	}

}
