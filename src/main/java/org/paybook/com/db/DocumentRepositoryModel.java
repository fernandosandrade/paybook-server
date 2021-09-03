package org.paybook.com.db;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Interface a ser extendida por todos objetos que representam um documento do repositorio de documentos.
 */
public interface DocumentRepositoryModel {

    final static String DOCUMENT_ID_FIELD = "document_id";

    @JsonProperty(value = DOCUMENT_ID_FIELD)
    String documentID();

}
