package org.paybook.com.db;

import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Fornece objeto de acesso ao repositorio de documentos
 */
@JBossLog
@ApplicationScoped
public class RepositoryFactory {

    @Inject
    FirestoreService firestoreService;

    public IDocumentRepository collection(String... path) {
        return new FirestoreCollection(this.firestoreService,
                this.firestoreService.get().collection(String.join("/", path)));
    }

    public IDocumentRepository collectionGroup(String groupName) {
        return new FirestoreCollectionGroup(this.firestoreService,
                this.firestoreService.get().collectionGroup(groupName));
    }

    public IReactiveDocumentRepository reactiveCollection(String... path) {
        return new FirestoreCollectionReactive(this.firestoreService,
                this.firestoreService.get().collection(String.join("/", path)));
    }


}
