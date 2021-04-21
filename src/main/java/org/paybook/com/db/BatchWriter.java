package org.paybook.com.db;

import com.google.cloud.firestore.WriteBatch;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class BatchWriter {

    @Inject
    FirestoreService firestoreService;

    public WriteBatch getBatchWriter() {
        return this.firestoreService.get().batch();
    }
}
