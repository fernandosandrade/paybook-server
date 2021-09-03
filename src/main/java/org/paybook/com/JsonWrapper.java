package org.paybook.com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.Timestamp;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.db.DocumentRepositoryModel;
import org.paybook.com.db.RepositoryMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@JBossLog
public class JsonWrapper {

    private static final JsonMapper jsonMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build();

    public static JsonMapper getDefaultMapper() {
        return jsonMapper;
    }

    public static <T extends DocumentRepositoryModel> Map<String, Object> fromObject(T documentModel) {
        Map<String, Object> jsonMap = jsonMapper.convertValue(documentModel,
                new TypeReference<Map<String, Object>>() {
                });
        parseDates(jsonMap);
        return jsonMap;
    }

    public static <T> T toObject(Map<String, Object> json, Class<T> clazz) {
        Objects.requireNonNull(json, "map json nao pode ser nulo");
        return jsonMapper.convertValue(json, clazz);
    }

    public static <T extends RepositoryMap> Map<String, Object> fromObject(T map) {
        return jsonMapper.convertValue(map, new TypeReference<Map<String, Object>>() {
        });
    }

    static void parseDates(Map jsonMap) {
        jsonMap.forEach((k, v) -> {
            if (v instanceof String) {
                try {
                    Timestamp timestamp = Timestamp.parseTimestamp((String) v);
                    jsonMap.replace(k, timestamp);
                } catch (Exception e) {
                }
            } else if (v instanceof List) {
                ((List<Map>) v).forEach(JsonWrapper::parseDates);
            }
        });
    }

//    public <T> T toObject(DocumentSnapshot firestoreDoc, Class<T> clazz) {
//        return toObject(firestoreDoc.getData(), clazz);
//    }

//    public <T> Map<String, Object> fromObject(T dto) {
//        return this.jsonMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {
//        });
//    }

}
