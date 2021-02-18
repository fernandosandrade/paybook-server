package org.paybook.com.db;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import lombok.Value;
import org.paybook.com.JsonWrapper;

import java.util.Map;

@Value
public class DBDocument {

    /**
     * map contendo o documento
     */
    private Map<String, Object> data;

    /**
     * referencia ao documento no repositorio remoto
     */
    private DocumentReference reference;

    public static DBDocument from(DocumentSnapshot documentSnapshot) {
        return new DBDocument(documentSnapshot.getData(), documentSnapshot.getReference());
    }

    public static DBDocument from(Map<String, Object> data, DocumentReference documentReference) {
        return new DBDocument(data, documentReference);
    }

    /**
     * Converte este documento em um objeto
     *
     * @param classeCobranca
     * @param <T>
     * @return
     */
    public <T> T toObject(Class<T> classeCobranca) {
        return JsonWrapper.toObject(this.data, classeCobranca);
    }
}
