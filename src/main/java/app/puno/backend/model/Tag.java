package app.puno.backend.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tag", indexes = {
		@Index(name = "idx_tag_name_unq", columnList = "name", unique = true)
})
@Getter
@Setter
public class Tag extends Model {

	private String name;

}
