package org.paybook.com;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumBookType {

    B_101(101),
    B_201(101),
    B_301(101),
    B_401(401);

    private int valor;

    EnumBookType(int status) {
        this.valor = status;
    }

    @JsonValue
    public int getValor() {
        return this.valor;
    }

    @JsonCreator
    public static EnumBookType from(int codTipoCobranca) {
        for (EnumBookType tipoCobranca : EnumBookType.values()) {
            if (tipoCobranca.valor == codTipoCobranca)
                return tipoCobranca;
        }
        throw new IllegalArgumentException(String.format("codigo tipo de cobranca invalido [%s]", codTipoCobranca));
    }

}
