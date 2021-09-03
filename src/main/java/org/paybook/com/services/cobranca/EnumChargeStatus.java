package org.paybook.com.services.cobranca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

/**
 * Indica o status atual de uma COBRANCA.
 * <ul>
 * <li>ao criar uma nova cobranca, ela inicia com o status {@link #CHARGE_OPEN}.
 * <li>quando um link de pagamento é vinculado a cobranca, altera seu status para {@link #WAITING_PAYMENT}
 * <li>quando todos os pagamentos vinculados a cobranca são efetuados e o valor total do pagamentos corresponder ao total da cobranca, o status é {@link #CHARGE_PAID}.
 * <li>caso a cobrança seja finalizada precocemente, sem que os pagamentos efetuados correspondam ao total da cobrança (TOTAL_PAGO < TOTAL_COBRANCA), seu status é {@link #CHARGE_CANCELED}
 * </ul>
 */
public enum EnumChargeStatus {

    /** cobranca ativa e em aberto */
    CHARGE_OPEN(0),

    /** cobranca em aberto aguardado algum pagamento */
    WAITING_PAYMENT(5),

    /** cobranca concluida */
    CHARGE_PAID(10),

    /** cobranca cancelada */
    CHARGE_CANCELED(20);

    private int status;

    EnumChargeStatus(int status) {
        this.status = status;
    }

    @JsonValue
    public int getStatus() {
        return this.status;
    }

    @JsonCreator
    static EnumChargeStatus fromStatus(int status) {
        return Stream.of(EnumChargeStatus.values())
                .filter(enumStatusCobranca -> enumStatusCobranca.status == status)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid EnumStatusCobranca status code: " + status));
    }

}
