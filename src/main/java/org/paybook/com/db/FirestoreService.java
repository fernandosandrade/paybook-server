package org.paybook.com.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.jbosslog.JBossLog;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

@JBossLog
@Singleton
public class FirestoreService {

    private static final String PROJECT_ID = "plink";

    private static final String FIREBASE_CREDENTIALS_FILE = "plink-2b309-firebase-adminsdk-m2nbr-c17700bc21.json";

    private static final String FIREBASE_CREDENTIALS_PATH = Path.of("META-INF", "resources",
                    FIREBASE_CREDENTIALS_FILE)
            .toString();

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
        log.infof("reading firebase credentials from %s", FIREBASE_CREDENTIALS_PATH);

        Optional.ofNullable(System.getenv("FIRESTORE_EMULATOR_HOST"))
                .ifPresent(firestoreEmulatorHost -> log.infof("firestore running on emulator environment [%s]",
                        firestoreEmulatorHost));

        Optional.ofNullable(System.getenv("FIREBASE_AUTH_EMULATOR_HOST"))
                .ifPresent(authEmulatorHost -> log.infof("auth running on emulator environment [%s]",
                        authEmulatorHost));

        try (InputStream inputStream =
                     getClass().getClassLoader()
                             .getResourceAsStream(FIREBASE_CREDENTIALS_PATH)) {
            assert inputStream != null;
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
