package queue;

import java.util.ArrayList;

public class SubFluxo {
    private String nome;
    private double probabilidade;
    private ArrayList<Passo> passos = new ArrayList<>();

    public SubFluxo(String nome, double probabilidade) {
        this.nome = nome;
        this.probabilidade = probabilidade;
    }

    public SubFluxo adicionarPasso(Passo passo) {
        passos.add(passo);
        return this;
    }

    public ArrayList<Passo> retornaPassos() {
        return passos;
    }

    public String getNome() {
        return nome;
    }

    public SubFluxo setProbabilidade(double probabilidade) {
        this.probabilidade = probabilidade;
        return this;
    }

    public double getProbabilidade() {
        return probabilidade;
    }
}
