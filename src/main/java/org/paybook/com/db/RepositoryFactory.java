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

    /**
     * Retorna acesso a coleção de documentos existente no path passado.
     *
     * @param
     * @return
     */
    public IDocumentRepository collection(CollectionPath collectionPath) {
        return FirestoreCollection.of(this.firestoreService,
                this.firestoreService.get().collection(collectionPath.getPath()));
    }

    /**
     * Retorna acesso a uma collection group.
     *
     * @param collectionGroupName nome da collection group
     * @return
     */
    public IDocumentRepository collectionGroup(String collectionGroupName) {
        return FirestoreCollectionGroup.of(this.firestoreService, collectionGroupName);
//                this.firestoreService.get().collectionGroup(groupName));
    }

}
