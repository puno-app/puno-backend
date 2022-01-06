package app.puno.backend.messaging;

import app.puno.backend.model.Job;
import org.springframework.lang.NonNull;

public interface MessagingService {

	void sendJobListingNotification(Job job);

}
