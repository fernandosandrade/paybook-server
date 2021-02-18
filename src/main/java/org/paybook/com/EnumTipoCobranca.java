package org.paybook.com;

public enum EnumTipoCobranca implements ITipoCobranca {

    C_111(111),
    C_112(112),
    C_211(211),
    C_311(311),
    C_411(411);

    private int valor;

    EnumTipoCobranca(int status) {
        valor = status;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String getValorAsString() {
        return String.valueOf(valor);
    }

    public static EnumTipoCobranca from(int codTipoBook) {
        for (EnumTipoCobranca tipoBook : EnumTipoCobranca.values()) {
            if (tipoBook.valor == codTipoBook)
                return tipoBook;
        }
        throw new IllegalArgumentException(String.format("codigo tipo de book invalido [%s]", codTipoBook));
    }

}
