package org.paybook.com.services.cobranca;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import lombok.Value;
import org.paybook.com.JsonWrapper;
import org.paybook.com.services.cobranca.dao.CobrancaBaseModel;

import java.util.Map;

@Value // immutable data
public class CobrancaDocument {

    /** map contendo o documento */
    private Map<String, Object> data;

    private DocumentReference reference;

    private String id;

    public static CobrancaDocument from(DocumentSnapshot document) {
        return new CobrancaDocument(document.getData(), document.getReference(), document.getId());
    }

    public static CobrancaDocument from(Map<String, Object> data) {
        return new CobrancaDocument(data, null, null);
    }

    /**
     * Converte este documento de cobranca em uma classe do tipo {@link CobrancaBaseModel}
     *
     * @param classeCobranca
     * @param <T>
     * @return
     */
    public <T extends CobrancaBaseModel> T toCobranca(Class<T> classeCobranca) {
        return JsonWrapper.toObject(this.data, classeCobranca);
    }

}
