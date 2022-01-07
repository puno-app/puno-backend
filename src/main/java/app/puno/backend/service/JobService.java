package app.puno.backend.service;

import app.puno.backend.messaging.MessagingService;
import app.puno.backend.model.Job;
import app.puno.backend.model.Job.Status;
import app.puno.backend.model.Profile;
import app.puno.backend.model.chat.Chat;
import app.puno.backend.repository.ChatRepository;
import app.puno.backend.repository.JobRepository;
import app.puno.backend.repository.TagRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class JobService {

	private static final ExecutorService NOTIFICATION_DISPATCHER_EXECUTOR = Executors.newFixedThreadPool(5);

	private final JobRepository jobRepository;
	private final ChatRepository chatRepository;
	private final TagRepository tagRepository;
	private final MessagingService messagingService;

	public JobService(JobRepository jobRepository, ChatRepository chatRepository,
			TagRepository tagRepository,
			MessagingService messagingService) {
		this.jobRepository = jobRepository;
		this.chatRepository = chatRepository;
		this.tagRepository = tagRepository;
		this.messagingService = messagingService;
	}

	@NonNull
	public Job createJob(List<UUID> tags, String title, String description, Profile employer) {
		Job job = new Job();
		job.setTitle(title);
		job.setDescription(description);
		job.setEmployer(employer);
		job.setStatus(Status.STARTED);

		Chat chat = new Chat();
		chatRepository.save(chat);

		job.setChat(chat);

		tagRepository.findAllById(tags).forEach(tag -> job.getTags().add(tag));
		jobRepository.save(job);

		// Offload to another thread
		NOTIFICATION_DISPATCHER_EXECUTOR.submit(() -> messagingService.sendJobListingNotification(job));
		return job;
	}

}
