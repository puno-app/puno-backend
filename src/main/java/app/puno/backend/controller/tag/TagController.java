package app.puno.backend.controller.tag;

import static app.puno.backend.util.ResponseUtils.fail;
import static app.puno.backend.util.ResponseUtils.ok;

import app.puno.backend.controller.tag.request.TagCreateRequest;
import app.puno.backend.controller.tag.response.TagListResponse;
import app.puno.backend.model.Tag;
import app.puno.backend.model.dto.TagDto;
import app.puno.backend.service.TagService;
import java.util.ArrayList;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
public class TagController {

	private final TagService tagService;

	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping
	public ResponseEntity<?> createTag(@Valid @RequestBody TagCreateRequest request) {
		Tag tag = tagService.findOrCreateTag(request.tag());
		return ResponseEntity.ok(new TagDto(tag.getId(), tag.getName()));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{tagId}")
	public ResponseEntity<?> deleteTag(@PathVariable UUID tagId) {
		boolean deleted = tagService.deleteTag(tagId);
		return deleted ? ok("Tag deleted.") : fail("Tag not found.", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{name}")
	public ResponseEntity<?> searchTags(@PathVariable String name, @RequestParam(defaultValue = "1") int page) {
		return ResponseEntity.ok(new TagListResponse(new ArrayList<>(tagService.searchTags(name, page, 10))));
	}

}
