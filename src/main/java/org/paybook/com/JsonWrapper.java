package org.paybook.com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.db.DocumentRepositoryModel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@JBossLog
public class JsonWrapper {

    private JsonWrapper() {
    }

    /**
     * Converte o DocumentReference passado em uma instancia de clazz, populando os atributos e o DocumentReference
     *
     * @param firestoreDoc documento do firestore
     * @param clazz        classe que extende {@link DocumentRepositoryModel}
     * @param <T>
     * @return
     */
    public static <T extends DocumentRepositoryModel> T fromFirestoreToObject(DocumentReference firestoreDoc,
                                                                              Class<T> clazz) {
        try {
            T firestoreModel = toObject(firestoreDoc.get()
                    .get()
                    .getData(), clazz);
            //firestoreModel.setDocumentReference(firestoreDoc);
            return firestoreModel;
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
            log.error(e);
        } catch (ExecutionException e) {
            log.error(e);
        }
        return null;
    }

    public static <T> T toObject(DocumentSnapshot firestoreDoc, Class<T> clazz) {
        return toObject(firestoreDoc.getData(), clazz);
    }

    public static <T> T toObject(Map<String, Object> json, Class<T> clazz) {
        Objects.requireNonNull(json, "map json nao pode ser nulo");
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .build()
                .convertValue(json, clazz);
    }

    public static <T extends DocumentRepositoryModel> Map<String, Object> fromObject(T documentModel) {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .build()
                .convertValue(documentModel, new TypeReference<Map<String, Object>>() {
                });
    }

}
