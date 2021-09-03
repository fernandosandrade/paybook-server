package org.paybook.com.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
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

    private static final String PROJECT_ID = "plink";

    private Firestore firestore;

    private FirebaseApp app;

    @PostConstruct
    public void init() throws IOException {
        log.info("initializing FirebaseApp...");
        try {
            this.app = FirebaseApp.getInstance(PROJECT_ID);
//            this.app.delete();
//            this.app = FirebaseApp.initializeApp(PROJECT_ID);
        } catch (IllegalStateException e) {
            // IllegalStateException – if the FirebaseApp was not initialized, either via
            // initializeApp(FirebaseOptions, String) or getApps().
            this.app = initialize();
        }
        this.firestore = FirestoreClient.getFirestore(this.app);
//        this.firestore = test();
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

            return FirebaseApp.initializeApp(options, PROJECT_ID);
        }
    }

    private Firestore test() {
        log.info("setting firestore to emulator environment");
        return FirestoreOptions.getDefaultInstance()
//                .toBuilder()
//                .setProjectId(PROJECT_ID)
//                .setHost("127.0.0.1:9090")
//                .setChannelProvider(
//                        InstantiatingGrpcChannelProvider.newBuilder()
//                                .setEndpoint("127.0.0.1:9090")
//                                .setChannelConfigurator(input -> {
//                                    input.usePlaintext();
//                                    return input;
//                                }).build())
//                .setCredentialsProvider(FixedCredentialsProvider.create(new FakeCredentials()))
//                .setCredentials(new FakeCredentials())
//                .setHeaderProvider(() -> ImmutableMap.of("Authorization", "Bearer owner"))
//                .build()
                .getService();
    }

}
