package app.puno.backend.repository;

import app.puno.backend.model.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

	@Nullable Tag findByNameIgnoreCase(String name);

	@Query("select t from Tag t where upper(t.name) = upper(?1)")
	List<Tag> findManyByNameIgnoreCase(String name, Pageable pageable);

	@Modifying
	@Transactional
	@Query("delete from Tag t where t.id = ?1")
	int delete(UUID uuid);

}