package org.paybook.com.db;

import com.google.cloud.firestore.DocumentSnapshot;
import lombok.Value;
import lombok.experimental.Accessors;
import org.paybook.com.JsonWrapper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a repository document.
 * <p>
 * Store the document data (as a {@code Map<String,Object>}) and its reference
 * </p>
 */
@Value
@Accessors(fluent = true)
public class DBDocument {

    /**
     * map of document.
     */
    private Map<String, Object> data;

    /**
     * Remote reference to that document.
     * <p>
     * Composed of collection_path + document_id. ex:
     * </p>
     * <pre>
     * documentReference:   cobrancas/101/111/O5OUe1ZKbmz5VNM9R7z4
     * collection:          cobrancas/101/111/
     * id:                  O5OUe1ZKbmz5VNM9R7z4
     * </pre>
     * <p>
     * In firestore, a document reference also is a collection reference (documents can store collections)
     * </p>
     */
    private String documentReference;

    public static DBDocument from(DocumentSnapshot documentSnapshot) {
        Objects.requireNonNull(documentSnapshot);
        return new DBDocument(documentSnapshot.getData(), documentSnapshot.getReference().getPath());
    }

    public static DBDocument from(Map<String, Object> data,
                                  @Nullable String collectionPath,
                                  String documentID) {
        Objects.requireNonNull(data);
        Map<String, Object> collect = data.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(DocumentRepositoryModel.DOCUMENT_ID_FIELD))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        data.remove(DocumentRepositoryModel.DOCUMENT_ID_FIELD);
        return new DBDocument(collect, DocumentReferencePath.of(collectionPath, documentID).getPath());
    }

    /**
     * Converte este documento em um objeto
     *
     * @param classeCobranca
     * @param <T>
     * @return
     */
    public <T extends DocumentRepositoryModel> T toObject(Class<T> classeCobranca) {
        this.data.put(DocumentRepositoryModel.DOCUMENT_ID_FIELD, this.id());
        return JsonWrapper.toObject(this.data, classeCobranca);
    }

    /**
     * Returns the corresponding ID of this document reference.
     *
     * @return
     */
    public String id() {
        return this.documentReference.substring(this.documentReference.lastIndexOf('/') + 1);
    }

    /**
     * Returns de corresponding collection of this document reference.
     *
     * @return
     */
    public String collection() {
        return this.documentReference.substring(0, this.documentReference.lastIndexOf('/'));
    }
}
