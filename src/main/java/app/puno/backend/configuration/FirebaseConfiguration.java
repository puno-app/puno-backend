package app.puno.backend.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfiguration {

	@Bean
	public FirebaseApp firebaseApp() throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(
						GoogleCredentials.fromStream(new ClassPathResource("firebase-service-account.json").getInputStream())
				)
				.build();

		return FirebaseApp.initializeApp(options);
	}

	@Bean
	public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
		return FirebaseMessaging.getInstance(firebaseApp);
	}


}
