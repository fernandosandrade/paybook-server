package org.paybook.com.services.cobranca;

/**
 * Indica o status atual de uma COBRANCA.
 * <ul>
 * <li>ao criar uma nova cobranca, ela inicia com o status {@link #CHARGE_OPEN}.
 * <li>quando um link de pagamento é vinculado a cobranca, altera seu status para {@link #WAITING_PAYMENT}
 * <li>quando todos os pagamentos vinculados a cobranca são efetuados e o valor total do pagamentos corresponder ao total da cobranca, o status é {@link #CHARGE_PAID}.
 * <li>caso a cobrança seja finalizada precocemente, sem que os pagamentos efetuados correspondam ao total da cobrança (TOTAL_PAGO < TOTAL_COBRANCA), seu status é {@link #CHARGE_CANCELED}
 * </ul>
 */
public enum EnumStatusCobranca {
    
    /** cobranca ativa e em aberto */
    CHARGE_OPEN(0),

    /** cobranca em aberto aguardado algum pagamento */
    WAITING_PAYMENT(5),

    /** cobranca concluida */
    CHARGE_PAID(10),

    /** cobranca cancelada */
    CHARGE_CANCELED(20);

    private int valor;

    EnumStatusCobranca(int status) {
        valor = status;
    }

    public int getValor() {
        return valor;
    }
}
