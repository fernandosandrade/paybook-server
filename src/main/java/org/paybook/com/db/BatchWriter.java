package org.paybook.com.db;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteBatch;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Map;

/**
 * Wrapp the {@link WriteBatch}.
 */
@RequestScoped
public class BatchWriter {

    private final Firestore firestore;

    /**
     * delegate
     */
    private WriteBatch batch;

    @Inject
    BatchWriter(FirestoreService firestoreService) {
        this.firestore = firestoreService.get();
        this.batch = this.firestore.batch();
    }

    /**
     * Creates a new document on the collection. If already exists, will be updated.
     * <p>
     * The update operation occures by merge, fields not especified on map do not change
     * </p>
     *
     * @param path
     * @param data
     */
    public void set(String path, Map<String, Object> data) {
        DocumentReference document = this.firestore.document(path);
        this.batch.set(document, data, SetOptions.merge());
    }

    /**
     * Effective batch operations.
     */
    public void commit() {
        this.batch.commit();
    }

    /**
     * Clear this batch operations.
     */
    public void clear() {
        this.batch = this.firestore.batch();
    }

}
