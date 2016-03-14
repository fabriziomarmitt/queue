package queue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GeradorRequisicoes {
    private int ID = 1;
    private ArrayList<Fonte> fontes = new ArrayList<>();

    public class Fonte {
        private TipoRequisicao tipo;
        private IDistribuicaoRequisicao distribuicao;

        public TipoRequisicao getTipo() {
            return tipo;
        }

        public void setTipo(TipoRequisicao tipo) {
            this.tipo = tipo;
        }

        public IDistribuicaoRequisicao getDistribuicao() {
            return distribuicao;
        }

        public void setDistribuicao(IDistribuicaoRequisicao distribuicao) {
            this.distribuicao = distribuicao;
        }
    }

    public GeradorRequisicoes adicionarFonte(Fonte fonte) {
        this.fontes.add(fonte);
        return this;
    }

    public boolean temNovaRequisicao(int limit) {
        return ID <= limit;
    }

    public Requisicao proximaRequisicao() {
        Map<Double, TipoRequisicao> sorteio = new HashMap<>();
        fontes.forEach(fonte -> sorteio.put(fonte.getDistribuicao().gerar(), fonte.getTipo()));
        Map.Entry<Double, TipoRequisicao> sorteada = sorteio.entrySet().stream().min(Comparator.comparingDouble(value -> value.getKey())).get();
        double tempo = LinhaDoTempo.retornaInstancia().retornaTempo() + sorteada.getKey();
        LinhaDoTempo.retornaInstancia().setTempo(tempo);
        return new Requisicao() {{
            setID(ID++);
            setTipo(sorteada.getValue());
        }};
    }

    public static void main(String[] args) throws IOException {
        GeradorRequisicoes geradorRequisicoes = new GeradorRequisicoes(){{
            adicionarFonte(new Fonte(){{
                setDistribuicao(new Exponencial(0.1));
                setTipo(TipoRequisicao.SIMPLES);
            }});
        }};

        while(geradorRequisicoes.temNovaRequisicao(100)){
            geradorRequisicoes.proximaRequisicao();
            System.out.println(new Exponencial(10).gerar());
            //System.out.println(LinhaDoTempo.retornaInstancia().retornaTempo());
        }

    }
}