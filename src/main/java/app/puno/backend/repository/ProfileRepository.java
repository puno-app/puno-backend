package app.puno.backend.repository;

import app.puno.backend.model.Profile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

	@Nullable Profile findByUsername(@NonNull String username);

	@Nullable Profile findByUsernameOrEmail(@NonNull String username, @NonNull String email);

}