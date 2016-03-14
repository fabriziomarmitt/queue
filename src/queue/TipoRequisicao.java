package queue;

import edu.princeton.cs.introcs.StdRandom;

/**
 * Created by Fabrizio on 09/03/2016.
 */
public enum TipoRequisicao {
    SIMPLES, REGULAR, COMPLEXA;

    public static TipoRequisicao retornaTipoRequisicaoAleatorio(double lambda) {
        double rnd = StdRandom.exp(lambda);
        return TipoRequisicao.values()[rnd >= TipoRequisicao.values().length - 1 ? TipoRequisicao.values().length - 1 : (int) rnd];
    }

}
