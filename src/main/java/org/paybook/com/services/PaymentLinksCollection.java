package org.paybook.com.services;

import org.paybook.com.db.RepositoryCollection;

public class PaymentLinksCollection {

    static final String collectionName = "payment_links";

    private final String paymentLinkId;

    private final ChargesCollection chargesCollection;

    PaymentLinksCollection(String userId, String booksId, String chargesId, String paymentLinkId) {
        this.chargesCollection = new ChargesCollection(userId, booksId, chargesId);
        this.paymentLinkId = paymentLinkId;
    }

    private PaymentLinksCollection(ChargesCollection chargesCollection, String paymentLinkId) {
        this.chargesCollection = chargesCollection;
        this.paymentLinkId = paymentLinkId;
    }

    public static PaymentLinksCollection of(String userId, String booksId, String chargeId) {
        return new PaymentLinksCollection(userId, booksId, chargeId, null);
    }

    public static PaymentLinksCollection from(ChargesCollection chargesCollection, String chargeId) {
        return new PaymentLinksCollection(chargesCollection.withChargeId(chargeId), null);
    }

    public String getPath() {
        return this.paymentLinkId == null ? RepositoryCollection.of(this.chargesCollection.getPath(), collectionName)
                .getPath() :
                RepositoryCollection.of(this.chargesCollection.getPath(), collectionName, this.paymentLinkId).getPath();
    }
}
