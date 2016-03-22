package queue;

import edu.princeton.cs.introcs.StdRandom;

import java.util.HashMap;

public class Servidor {
    private String nome;
    private double tempoServico = 0;
    private double saida = 0;
    private HashMap<TipoRequisicao, Double> taxasDeServico = new HashMap<>();

    public Servidor(String nome) {
        this.nome = nome;
    }

    public Servidor adicionaTaxaDeServico(TipoRequisicao tipoRequisicao, double taxaDeServico) {
        this.taxasDeServico.put(tipoRequisicao, taxaDeServico);
        return this;
    }

    public Requisicao atender(Requisicao requisicao) {
        double taxaDeServico = this.taxasDeServico.get(requisicao.getTipo()).doubleValue();
        tempoServico = new Exponencial(taxaDeServico).gerar();
        saida = requisicao.getChegada() + requisicao.getTempoEspera() + tempoServico;
        return requisicao
                .setTempoServico(tempoServico)
                .setSaida(saida);
    }

    public boolean estaOcupado(Requisicao requisicao) {
        return requisicao.getChegada() < saida;
    }

    public double tempoEspera(Requisicao requisicao) {
        return saida - requisicao.getChegada();
    }

    public String getNome() {
        return nome;
    }
}