package queue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Fluxo {
    private ArrayList<Passo> passos = new ArrayList<>();
    private ArrayList<Historico> historico = new ArrayList<>();
    private GeradorRequisicoes geradorRequisicoes;

    public class Historico {
        private Requisicao requisicao;
        private Passo passo;
        private Servidor servidor;

        public Requisicao getRequisicao() {
            return requisicao;
        }

        public void setRequisicao(Requisicao requisicao) {
            this.requisicao = requisicao;
        }

        public Passo getPasso() {
            return passo;
        }

        public void setPasso(Passo passo) {
            this.passo = passo;
        }

        public Servidor getServidor() {
            return servidor;
        }

        public void setServidor(Servidor servidor) {
            this.servidor = servidor;
        }
    }

    private void adicionaNoHistorico(Requisicao requisicao, Passo passo, Servidor servidor) {
        historico.add(new Historico() {{
            setRequisicao(new Requisicao() {{
                setID(requisicao.getID());
                setChegada(requisicao.getChegada());
                setTempoEspera(requisicao.getTempoEspera());
                setTempoServico(requisicao.getTempoServico());
                setSaida(requisicao.getSaida());
                setTipo(requisicao.getTipo());
            }});
            setPasso(new Passo(passo.getNome()));
            setServidor(new Servidor(servidor.getTaxaDeServico(), servidor.getNome()));
        }});
    }

    public ArrayList<Historico> retornaHistorico() {
        return historico;
    }

    public Fluxo adicionarGeradorRequisicoes(GeradorRequisicoes geradorRequisicoes) {
        this.geradorRequisicoes = geradorRequisicoes;
        return this;
    }

    public Fluxo adicionarPasso(Passo passo) {
        passos.add(passo);
        return this;
    }

    public void executar(int limit) {
        while (geradorRequisicoes.temNovaRequisicao(limit)) {
            Requisicao requisicao = geradorRequisicoes.proximaRequisicao();
            passos.forEach(passo -> {
                requisicao.setChegada(requisicao.getChegada() > 0
                        ? requisicao.getSaida()
                        : LinhaDoTempo.retornaInstancia().retornaTempo()
                );
                Optional<Servidor> servidorLivre = passo.retornaServidores()
                        .stream().filter(s -> !s.estaOcupado(requisicao)).findFirst();
                if (!servidorLivre.isPresent()) {
                    Servidor servidorMenorTempoEspera = passo.retornaServidores().stream()
                            .min(Comparator.comparing(s -> s.tempoEspera(requisicao))).get();
                    double tempoEspera = servidorMenorTempoEspera.tempoEspera(requisicao) + requisicao.getTempoEspera();
                    requisicao.setTempoEspera(tempoEspera);
                    adicionaNoHistorico(servidorMenorTempoEspera.atender(requisicao), passo, servidorMenorTempoEspera);
                } else {
                    adicionaNoHistorico(servidorLivre.get().atender(requisicao), passo, servidorLivre.get());
                }
            });
        }
    }

    public void imprimeEstatisticas() throws IOException {
        File file = new File("estatisticas.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        DecimalFormat df = new DecimalFormat("0.00000");
        df.setDecimalSeparatorAlwaysShown(true);
        df.setDecimalFormatSymbols(new DecimalFormatSymbols() {{
            setDecimalSeparator(',');
            setGroupingSeparator('.');
        }});
        PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
        writer.println("ID\tTIPO\tCHEGADA\tTEMPO_ESPERA\tTEMPO_SERVICO\tSAIDA\tPASSO\tSERVIDOR");
        for (Historico historico : this.retornaHistorico()) {
            writer.println(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    historico.getRequisicao().getID(),
                    historico.getRequisicao().getTipo(),
                    df.format(historico.getRequisicao().getChegada()),
                    df.format(historico.getRequisicao().getTempoEspera()),
                    df.format(historico.getRequisicao().getTempoServico()),
                    df.format(historico.getRequisicao().getSaida()),
                    historico.getPasso().getNome(),
                    historico.getServidor().getNome()
            ));
        }
        writer.flush();
        writer.close();
    }
}