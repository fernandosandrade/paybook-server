package org.paybook.com.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

/**
 * Interface a ser extendida por todos objetos que representam um documento do repositorio de documentos.
 */
public interface DocumentRepositoryModel {

    @JsonProperty(value = "document_id", access = JsonProperty.Access.WRITE_ONLY)
    @Nullable
    String documentID();

}
