package app.puno.backend.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Model {

	@Id
	@GeneratedValue
	private UUID id;

	@Transient
	private ZoneId zoneId = ZoneId.of("UTC");

	private LocalDateTime createTimestamp;
	private LocalDateTime updateTimestamp;

	@PrePersist
	public void onPersist() {
		createTimestamp = LocalDateTime.now(zoneId);
	}

	@PreUpdate
	private void onUpdate() {
		updateTimestamp = LocalDateTime.now(zoneId);
	}

}
