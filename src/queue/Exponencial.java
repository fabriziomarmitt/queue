package queue;

import edu.princeton.cs.introcs.StdRandom;

public class Exponencial implements IDistribuicaoRequisicao {
    private double lambda;

    public Exponencial(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double gerar() {
        return StdRandom.exp(lambda);
    }

}
