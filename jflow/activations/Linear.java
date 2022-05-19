package jflow.activations;

import java.util.Arrays;

public class Linear implements Activation {
    public double eval(double x) {
        return x;
    }
    public double[] eval(double[] x) {
        return x;
    }

    public double deval(double x) {
        return 1;
    }
    public double[] deval(double[] x) {
        double[] output = new double[x.length];
        Arrays.fill(output, 1);
        return output;
    }
}
