package queue;

import edu.princeton.cs.introcs.StdRandom;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.sql.*;

public class Fluxo {
    private String ID;
    private ArrayList<SubFluxo> subFluxos = new ArrayList<>();
    private ArrayList<Double> probSubFluxo = new ArrayList<>();
    private ArrayList<Historico> historico = new ArrayList<>();
    private GeradorRequisicoes geradorRequisicoes;

    public Fluxo(String id) {
        ID = id;
    }

    public class Historico {
        private Requisicao requisicao;
        private SubFluxo subFluxo;
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

        public SubFluxo getSubFluxo() {
            return subFluxo;
        }

        public void setSubFluxo(SubFluxo subFluxo) {
            this.subFluxo = subFluxo;
        }
    }

    private void adicionaNoHistorico(Requisicao requisicao, SubFluxo subFluxo, Passo passo, Servidor servidor) {
        historico.add(new Historico() {{
            setRequisicao(new Requisicao() {{
                setID(requisicao.getID());
                setChegada(requisicao.getChegada());
                setTempoEspera(requisicao.getTempoEspera());
                setTempoServico(requisicao.getTempoServico());
                setSaida(requisicao.getSaida());
                setTipo(requisicao.getTipo());
            }});
            setSubFluxo(new SubFluxo(subFluxo.getNome(), subFluxo.getProbabilidade()));
            setPasso(new Passo(passo.getNome()));
            setServidor(new Servidor(servidor.getNome()));
        }});
    }

    public ArrayList<Historico> retornaHistorico() {
        return historico;
    }

    public Fluxo adicionarGeradorRequisicoes(GeradorRequisicoes geradorRequisicoes) {
        this.geradorRequisicoes = geradorRequisicoes;
        return this;
    }

    public Fluxo adicionarSubFluxo(SubFluxo subFluxo) {
        probSubFluxo.add(subFluxo.getProbabilidade());
        subFluxos.add(subFluxo);
        return this;
    }

    public void executar(int limit) {
        while (geradorRequisicoes.temNovaRequisicao(limit)) {
            Requisicao requisicao = geradorRequisicoes.proximaRequisicao();
            int sorteio = StdRandom.discrete(probSubFluxo.stream().mapToDouble(Double::doubleValue).toArray());
            subFluxos.get(sorteio).retornaPassos().forEach(passo -> {
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
                    adicionaNoHistorico(servidorMenorTempoEspera.atender(requisicao), subFluxos.get(sorteio), passo, servidorMenorTempoEspera);
                } else {
                    adicionaNoHistorico(servidorLivre.get().atender(requisicao), subFluxos.get(sorteio), passo, servidorLivre.get());
                }
            });
        }
    }

    public void salvaEstatisticas() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/queue";
        Connection conexao = DriverManager.getConnection(url, "postgres", "Rb5!!!!!");
        String query;
        PreparedStatement pst;

        pst = conexao.prepareStatement("DELETE FROM estatisticas.estatisticas WHERE \"FLUXO_ID\" = ?");
        pst.setString(1, ID);
        pst.execute();

        for (Historico historico : this.retornaHistorico()) {
            query = "INSERT INTO estatisticas.estatisticas(" +
                    " \"FLUXO_ID\",\"REQUISICAO_ID\", \"TIPO\", \"CHEGADA\", \"TEMPO_ESPERA\", \"TEMPO_SERVICO\", \"SAIDA\", \"SUB_FLUXO\", \"PASSO\", \"SERVIDOR\")" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            pst = conexao.prepareStatement(query);
            pst.setString(1, ID);
            pst.setInt(2, historico.getRequisicao().getID());
            pst.setString(3, historico.getRequisicao().getTipo().toString());
            pst.setDouble(4, historico.getRequisicao().getChegada());
            pst.setDouble(5, historico.getRequisicao().getTempoEspera());
            pst.setDouble(6, historico.getRequisicao().getTempoServico());
            pst.setDouble(7, historico.getRequisicao().getSaida());
            pst.setString(8, historico.getSubFluxo().getNome());
            pst.setString(9, historico.getPasso().getNome());
            pst.setString(10, historico.getServidor().getNome());
            pst.executeUpdate();
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
            writer.println(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    historico.getRequisicao().getID(),
                    historico.getRequisicao().getTipo(),
                    df.format(historico.getRequisicao().getChegada()),
                    df.format(historico.getRequisicao().getTempoEspera()),
                    df.format(historico.getRequisicao().getTempoServico()),
                    df.format(historico.getRequisicao().getSaida()),
                    historico.getSubFluxo().getNome(),
                    historico.getPasso().getNome(),
                    historico.getServidor().getNome()
            ));
        }
        writer.flush();
        writer.close();
    }
}