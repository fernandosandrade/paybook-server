package org.paybook.com;

public enum EnumTipoBook implements ITipoBook {

    B_101(101),
    B_201(101),
    B_301(101),
    B_401(401);

    private int valor;

    EnumTipoBook(int status) {
        valor = status;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String getValorAsString() {
        return String.valueOf(valor);
    }

    public static EnumTipoBook from(int codTipoCobranca) {
        for (EnumTipoBook tipoCobranca : EnumTipoBook.values()) {
            if (tipoCobranca.valor == codTipoCobranca)
                return tipoCobranca;
        }
        throw new IllegalArgumentException(String.format("codigo tipo de cobranca invalido [%s]", codTipoCobranca));
    }

}
