package app.puno.backend.controller.tag.response;

import app.puno.backend.model.dto.TagDto;
import java.util.List;

public record TagListResponse(List<TagDto> tags) {

}
