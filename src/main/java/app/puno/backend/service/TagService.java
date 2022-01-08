package app.puno.backend.service;

import app.puno.backend.model.Tag;
import app.puno.backend.model.dto.TagDto;
import app.puno.backend.repository.TagRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class TagService {

	private final SetOperations<String, String> setOperations;
	private final RedisTemplate<String, String> redisTemplate;
	private final TagRepository tagRepository;

	public TagService(RedisTemplate<String, String> redisTemplate, TagRepository tagRepository) {
		this.tagRepository = tagRepository;
		this.redisTemplate = redisTemplate;
		this.setOperations = redisTemplate.opsForSet();
	}

	private String buildKey(String name, int page, int size) {
		return String.format("tag:search:%s:%d:%d", name.toLowerCase(), page, size);
	}

	@NonNull
	public Tag findOrCreateTag(String name) {
		Tag found = tagRepository.findByNameIgnoreCase(name);
		if (found != null) {
			return found;
		}
		found = tagRepository.save(new Tag(name));
		return found;
	}

	public boolean deleteTag(UUID id) {
		Tag tag = tagRepository.findById(id).orElse(null);
		if (tag != null) {
			tagRepository.delete(tag.getId());
			setOperations.remove(String.format("tag:search:%s:*", tag.getName().toLowerCase()));
		}
		return tag != null;
	}

	@Nullable
	private TagDto parseDto(String value) {
		String[] split = value.split(":");
		if (split.length < 2) {
			return null;
		}

		String name = split[0];
		UUID id = UUID.fromString(split[1]);

		return new TagDto(id, name);
	}

	private String tagToString(Tag tag) {
		return tag.getName() + ":" + tag.getId().toString();
	}

	@NonNull
	public Collection<TagDto> searchTags(String name, int page, int size) {
		String key = buildKey(name, page, size);

		Set<String> members = setOperations.members(key);
		if (members != null && members.size() > 0) {
			return members.stream().map(this::parseDto).filter(Objects::nonNull).collect(Collectors.toList());
		}

		List<Tag> tags = tagRepository.findManyByNameIgnoreCase(name, Pageable.ofSize(size).withPage(page - 1));
		if (tags.isEmpty()) {
			return new ArrayList<>();
		}

		setOperations.add(key, tags.stream().map(this::tagToString).toArray(String[]::new));
		redisTemplate.expire(key, 2, TimeUnit.MINUTES);

		return tags.stream().map(tag -> new TagDto(tag.getId(), tag.getName())).collect(Collectors.toList());
	}

}
