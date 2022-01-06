package app.puno.backend.repository;

import app.puno.backend.model.chat.Chat;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

	@Query("select c from Chat c where c.job.id=?1")
	@Nullable Chat findChatByJobId(UUID jobId);

}