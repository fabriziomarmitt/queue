package queue;

import java.util.ArrayList;

public class Requisicao {
    private int ID;
    private double chegada = 0;
    private double saida = 0;
    private double tempoEspera = 0;
    private double tempoServico = 0;
    private TipoRequisicao tipo;

    public double getChegada() {
        return chegada;
    }

    public Requisicao setChegada(double chegada) {
        this.chegada = chegada;
        return this;
    }

    public double getSaida() {
        return saida;
    }

    public Requisicao setSaida(double saida) {
        this.saida = saida;
        return this;
    }

    public double getTempoEspera() {
        return tempoEspera;
    }

    public Requisicao setTempoEspera(double tempoEspera) {
        this.tempoEspera = tempoEspera;
        return this;
    }

    public double getTempoServico() {
        return tempoServico;
    }

    public Requisicao setTempoServico(double tempoServico) {
        this.tempoServico = tempoServico;
        return this;
    }

    public TipoRequisicao getTipo() {
        return tipo;
    }

    public Requisicao setTipo(TipoRequisicao tipo) {
        this.tipo = tipo;
        return this;
    }

    public int getID() {
        return ID;
    }

    public Requisicao setID(int ID) {
        this.ID = ID;
        return this;
    }
}