package queue;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdStats;

public class Histograma {
    private final double[] freq;   // freq[i] = # occurences of value i
    private double max;            // max frequency of any value

    public Histograma(int N) {
        freq = new double[N];
    }

    public void addDataPoint(int i) {
        freq[i]++;
        if (freq[i] > max) max = freq[i];
    }

    public void draw() {
        StdDraw.setYscale(-1, max + 1);  // to leave a little border
        StdStats.plotBars(freq);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);   // number of coins
        int T = Integer.parseInt(args[1]);   // number of trials

        Histograma histogram = new Histograma(N+1);
        for (int t = 0; t < T; t++) {
            histogram.addDataPoint(Bernoulli.binomial(N));
        }

        StdDraw.setCanvasSize(500, 100);
        histogram.draw();
    }
}
