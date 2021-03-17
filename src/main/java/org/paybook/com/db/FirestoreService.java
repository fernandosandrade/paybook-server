package org.paybook.com.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.IOException;

@JBossLog
@Singleton
public class FirestoreService {

    private static final String APP_NAME = "plink";

    private Firestore firestore;

    @PostConstruct
    public void init() throws IOException {
        FirebaseApp plink;
        try {
            plink = FirebaseApp.getInstance(APP_NAME);
        } catch (IllegalStateException e) {
            // IllegalStateException – if the FirebaseApp was not initialized, either via
            // initializeApp(FirebaseOptions, String) or getApps().
            plink = initialize();
        }
        this.firestore = FirestoreClient.getFirestore(plink);
    }

    public Firestore get() {
        return this.firestore;
    }

    private FirebaseApp initialize() throws IOException {
        final String firebaseAdminJson = ConfigProvider.getConfig()
                .getValue("firebase.adminsdk.jsonpath", String.class);
        try (FileInputStream inputStream = new FileInputStream(firebaseAdminJson)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl("https://plink-2b309.firebaseio.com")
                    .build();

            return FirebaseApp.initializeApp(options, APP_NAME);
        }
    }
}
