package app.puno.backend.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "tag", indexes = {
		@Index(name = "idx_tag_name_unq", columnList = "name", unique = true)
})
public class Tag extends Model {

	public Tag() {

	}

	public Tag(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
