package app.puno.backend.model.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record TagDto(UUID id, String name) {

}
