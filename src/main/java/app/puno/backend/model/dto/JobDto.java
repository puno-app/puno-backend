package app.puno.backend.model.dto;

import app.puno.backend.model.Job.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record JobDto(UUID id, LocalDateTime createTimestamp, String title, Status status) {

}
