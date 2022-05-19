package jflow.activations;

public class ReLU implements Activation {
    public double eval(double x) {
        return Math.max(0, x);
    }
    public double[] eval(double[] x) {
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.max(0, x[i]);
        }
        return x;
    }

    public double deval(double x) {
        if (x < 0) {
            return 0;
        }
        return 1;
    }
    public double[] deval(double[] x) {
        double[] output = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            if (x[i] < 0) {
                output[i] = 0;
            } else {
                output[i] = 1;
            }
        }
        return output;
    }
}
