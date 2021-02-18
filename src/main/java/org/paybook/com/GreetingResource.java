package org.paybook.com;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.paybook.com.db.FirestoreService;

@Path("/hello")
public class GreetingResource {
  private static final Logger LOGGER = Logger.getLogger("ListenerBean");

  static final String BOOKS_COLLECTION = "books";

  @Inject
  FirestoreService firestoreService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response hello() throws InterruptedException, ExecutionException {
    CollectionReference collectionReference = firestoreService
      .get()
      .collection(BOOKS_COLLECTION);
    ApiFuture<QuerySnapshot> apiFuture = collectionReference
      .whereEqualTo("id_book", "wcbdb5ks0zz3086s")
      .get();

    List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();
    for (DocumentSnapshot document : documents) {
      LOGGER.info(document.getData());
    }

    return Response.ok(documents.get(0).getData()).build();
  }
}
