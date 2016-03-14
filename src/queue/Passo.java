package queue;

import java.util.ArrayList;

public class Passo {
    private String nome;
    private ArrayList<Servidor> servidores = new ArrayList<>();

    public Passo(String nome){
        this.nome = nome;
    }

    public Passo adicionarServidor(Servidor servidor) {
        servidores.add(servidor);
        return this;
    }

    public ArrayList<Servidor> retornaServidores() {
        return servidores;
    }

    public String getNome() {
        return nome;
    }
}