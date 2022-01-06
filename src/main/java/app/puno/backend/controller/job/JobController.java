package app.puno.backend.controller.job;

import app.puno.backend.controller.job.request.JobCreateRequest;
import app.puno.backend.dto.JobDto;
import app.puno.backend.model.Job;
import app.puno.backend.model.Profile;
import app.puno.backend.service.JobService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/job")
public class JobController {

	private final JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid JobCreateRequest request) {
		Job job = jobService.createJob(request.tags(), request.title(), request.description(), Profile.getAuthenticatedProfile());
		return ResponseEntity.ok(new JobDto(job.getId(), job.getCreateTimestamp(), job.getTitle(), job.getStatus()));
	}

}
