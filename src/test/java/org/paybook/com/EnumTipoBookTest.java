package org.paybook.com;

/**
 * Enum especifico para testes
 */
public enum EnumTipoBookTest implements ITipoBook {

    /** book especifico para testes */
    B_TESTE(000);

    private int valor;

    EnumTipoBookTest(int status) {
        valor = status;
    }

    public int getValor() {
        return valor;
    }

    public String getValorAsString() {
        return String.valueOf(valor);
    }

    public static EnumTipoBookTest from(int codTipoCobranca) {
        for (EnumTipoBookTest tipoCobranca : EnumTipoBookTest.values()) {
            if (tipoCobranca.valor == codTipoCobranca)
                return tipoCobranca;
        }
        throw new IllegalArgumentException(String.format("codigo tipo de cobranca invalido [%s]", codTipoCobranca));
    }

}
