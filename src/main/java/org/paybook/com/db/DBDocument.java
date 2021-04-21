package org.paybook.com.db;

import com.google.cloud.firestore.DocumentSnapshot;
import lombok.Value;
import lombok.experimental.Accessors;
import org.paybook.com.JsonWrapper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

/**
 * Representa um documento generico do repositorio.
 * <p>
 * Armazena os atributos do documento e a referencia ao mesmo (caso ja exista na base)
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
                                  @Nullable CollectionPath collectionPath,
                                  String documentID) {
        Objects.requireNonNull(data);
        return new DBDocument(data, CollectionPath.of(collectionPath.getPath(), documentID).getPath());
    }

    /**
     * Converte este documento em um objeto
     *
     * @param classeCobranca
     * @param <T>
     * @return
     */
    public <T extends DocumentRepositoryModel> T toObject(Class<T> classeCobranca) {
        //this.data.put("document_reference", this.documentReference);
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
