package org.paybook.com.services.link_pagamento;

/**
 * Define os possiveis status de um link de pagamento.
 * <br>
 * Ao criar um link, ele inicia com o status {@link #WAITING_PAIMENT}. Ao efetuar pagamento, o link altera para o status
 * {@link #LINK_PAID}. No dia apos a data de vencimento (D+1) do link, caso nao tenha sido pago, seu status passa a ser
 * {@link #LINK_EXPIRED}
 */
public enum EnumStatusLinkPagamento {

    /** link aguardando o pagamento */
    WAITING_PAIMENT(1),

    /** link pago */
    LINK_PAID(10),

    /** link cancelado */
    LINK_CANCELLED(20),

    /** link vencido */
    LINK_EXPIRED(90);

    private int valor;

    EnumStatusLinkPagamento(int status) {
        valor = status;
    }

    public int getValor() {
        return valor;
    }
}
