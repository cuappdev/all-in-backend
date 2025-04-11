package com.appdev.allin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseInitialization {

  @Bean
  FirebaseApp initialization() {
    try {
      if (FirebaseApp.getApps().isEmpty()) {
        FileInputStream serviceAccount = new FileInputStream("./firebaseServiceAccount.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        return FirebaseApp.initializeApp(options);
      } else {
        return FirebaseApp.getInstance();
      }
    } catch (Exception error) {
      error.printStackTrace();
      throw new RuntimeException("Failed to initialize Firebase app", error);
    }
  }
}