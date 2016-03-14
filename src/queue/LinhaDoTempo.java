package queue;

/**
 * Created by Fabrizio on 12/03/2016.
 */
public class LinhaDoTempo {
    private double linhaDoTempo = 0;
    private static LinhaDoTempo instancia;

    public static synchronized LinhaDoTempo retornaInstancia() {
        if (instancia == null) {
            instancia = new LinhaDoTempo();
        }
        return instancia;
    }

    public synchronized LinhaDoTempo setTempo(double tempo) {
        linhaDoTempo = tempo;
        return this;
    }

    public synchronized LinhaDoTempo adicionaTempo(double tempo) {
        linhaDoTempo += tempo;
        return this;
    }

    public synchronized double retornaTempo() {
        return linhaDoTempo;
    }
}
