package app.puno.backend.repository;

import app.puno.backend.model.Job;
import app.puno.backend.model.Job.Status;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {

	@Query("select j from Job j where j.status = ?1 order by j.createTimestamp")
	Page<Job> findJobByStatus(Status status, Pageable pageable);

	@Query("select j from Job j where j.employer.id = ?1")
	Page<Job> findByEmployerId(UUID id, Pageable pageable);

	@Query("select j from Job j where j.employee.id = ?1")
	Page<Job> findByEmployeeId(UUID id, Pageable pageable);



}