package org.paybook.com;

public enum EnumTipoCobrancaTest implements ITipoCobranca {

    /** cobranca especifica para teste */
    C_TESTE(001);

    private int valor;

    EnumTipoCobrancaTest(int status) {
        valor = status;
    }

    public int getValor() {
        return valor;
    }

    public String getValorAsString() {
        return String.valueOf(valor);
    }

    public static EnumTipoCobrancaTest from(int codTipoBook) {
        for (EnumTipoCobrancaTest tipoBook : EnumTipoCobrancaTest.values()) {
            if (tipoBook.valor == codTipoBook)
                return tipoBook;
        }
        throw new IllegalArgumentException(String.format("codigo tipo de book invalido [%s]", codTipoBook));
    }

}
