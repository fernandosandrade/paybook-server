package org.paybook.com;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumChargeType {

    C_111(111),
    C_112(112),
    C_211(211),
    C_311(311),
    C_411(411);

    private int valor;

    EnumChargeType(int status) {
        this.valor = status;
    }

    @JsonValue
    public int getValor() {
        return this.valor;
    }

    @JsonCreator
    public static EnumChargeType from(int codTipoBook) {
        for (EnumChargeType tipoBook : EnumChargeType.values()) {
            if (tipoBook.valor == codTipoBook)
                return tipoBook;
        }
        throw new IllegalArgumentException(String.format("codigo tipo de book invalido [%s]", codTipoBook));
    }

}
