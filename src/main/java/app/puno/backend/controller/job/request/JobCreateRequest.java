package app.puno.backend.controller.job.request;

import app.puno.backend.validation.ListSize;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.Size;

public record JobCreateRequest(
		@Size(min = 3, max = 255) String title,
		@Size(min = 50, max = 500) String description,
		@ListSize(max = 10) List<UUID> tags
) {

}
