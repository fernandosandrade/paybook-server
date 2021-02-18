package org.paybook.com.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.IOException;

@JBossLog
@Singleton
public class FirestoreService {

    private final Firestore firestore;

    public FirestoreService() {
        try {
            initialize();
        } catch (IOException e) {
            log.error(e);
        }
        this.firestore = FirestoreClient.getFirestore();
    }

    public Firestore get() {
        return this.firestore;
    }

    private void initialize() throws IOException {
        final String firebaseAdminJson = ConfigProvider.getConfig()
                .getValue("firebase.adminsdk.jsonPath", String.class);
        try (FileInputStream inputStream = new FileInputStream(firebaseAdminJson)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl("https://plink-2b309.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
