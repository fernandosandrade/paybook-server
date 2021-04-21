package org.paybook.com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.firestore.DocumentSnapshot;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.controller.dto.DtoObject;
import org.paybook.com.db.DocumentRepositoryModel;
import org.paybook.com.db.RepositoryMap;

import java.util.Map;
import java.util.Objects;

@JBossLog
public class JsonWrapper {

    private JsonWrapper() {
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

    public static <T extends DtoObject> Map<String, Object> fromObject(T dto) {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .build()
                .convertValue(dto, new TypeReference<Map<String, Object>>() {
                });
    }

    public static <T extends RepositoryMap> Map<String, Object> fromObject(T map) {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .build()
                .convertValue(map, new TypeReference<Map<String, Object>>() {
                });
    }


}
