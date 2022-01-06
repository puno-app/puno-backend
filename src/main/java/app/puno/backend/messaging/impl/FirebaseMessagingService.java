package app.puno.backend.messaging.impl;

import app.puno.backend.messaging.MessagingService;
import app.puno.backend.model.Job;
import app.puno.backend.model.Tag;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService implements MessagingService {

	private final FirebaseMessaging messaging;
	private final Logger logger;

	public FirebaseMessagingService(FirebaseMessaging messaging, Logger logger) {
		this.messaging = messaging;
		this.logger = logger;
	}

	private String buildCondition(List<Tag> tags) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tags.size(); i++) {
			Tag tag = tags.get(i);
			builder.append("'").append(tag.getId().toString()).append("' in topics");
			if (i == 0 || i == tags.size() - 1) {
				continue;
			}
			builder.append(" or");
		}
		return builder.toString();
	}

	@Override
	public void sendJobListingNotification(Job job) {
		Notification notification = Notification.builder()
				.setTitle("Puno.")
				.setBody("Një shpallje e re është deklaruar, jeni të interesuar?")
				.build();

		Message message = Message.builder()
				.putData("id", job.getId().toString())
				.putData("title", job.getTitle())
				.setCondition(buildCondition(job.getTags()))
				.setNotification(notification)
				.build();

		try {
			messaging.send(message);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			logger.error(String.format("Something went wrong when sending firebase message for job %s.", job.getId()), e);
		}
	}

}
