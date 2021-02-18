package org.paybook.com.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.firestore.DocumentReference;

import javax.annotation.Nullable;

/**
 * Interface a ser extendida por todos objetos que representam um documento do repositorio de documentos.
 */
public interface DocumentRepositoryModel {

    @JsonIgnore
    @Nullable
    DocumentReference documentReference();

}
