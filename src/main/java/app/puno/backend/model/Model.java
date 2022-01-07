package app.puno.backend.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

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

	public UUID getId() {
		return this.id;
	}

	public ZoneId getZoneId() {
		return this.zoneId;
	}

	public LocalDateTime getCreateTimestamp() {
		return this.createTimestamp;
	}

	public LocalDateTime getUpdateTimestamp() {
		return this.updateTimestamp;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setZoneId(ZoneId zoneId) {
		this.zoneId = zoneId;
	}

	public void setCreateTimestamp(LocalDateTime createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

}
