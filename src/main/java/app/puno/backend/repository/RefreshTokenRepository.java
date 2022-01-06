package app.puno.backend.repository;

import app.puno.backend.model.authentication.RefreshToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

	@Nullable
	@Query("select r from RefreshToken r where r.id = ?1 and r.profile.id = ?2")
	RefreshToken findToken(UUID id, UUID profileId);

	@Transactional
	@Modifying
	@Query("delete from RefreshToken r where r.profile.id = ?1")
	void deleteByProfileId(UUID id);

}