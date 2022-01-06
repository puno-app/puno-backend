package app.puno.backend.service;

import app.puno.backend.messaging.MessagingService;
import app.puno.backend.model.Job;
import app.puno.backend.model.Profile;
import app.puno.backend.model.chat.Chat;
import app.puno.backend.repository.JobRepository;
import app.puno.backend.repository.TagRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class JobService {

	private final JobRepository jobRepository;
	private final TagRepository tagRepository;
	private final MessagingService messagingService;

	public JobService(JobRepository jobRepository, TagRepository tagRepository,
			MessagingService messagingService) {
		this.jobRepository = jobRepository;
		this.tagRepository = tagRepository;
		this.messagingService = messagingService;
	}

	@NonNull
	public Job createJob(List<UUID> tags, String title, String description, Profile employer) {
		Job job = new Job();
		job.setTitle(title);
		job.setDescription(description);
		job.setEmployer(employer);
		job.setChat(new Chat());

		tagRepository.findAllById(tags).forEach(tag -> job.getTags().add(tag));
		jobRepository.save(job);

		messagingService.sendJobListingNotification(job);
		return job;
	}

}
