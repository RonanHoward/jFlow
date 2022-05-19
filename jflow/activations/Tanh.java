package jflow.activations;

public class Tanh implements Activation {
    public double eval(double x) {
        return Math.tanh(x);
    }
    public double[] eval(double[] x) {
        double[] output = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            output[i] = Math.tanh(x[i]);
        }
        return output;
    }

    public double deval(double x) {
        return 1 - Math.pow(Math.tanh(x), 2);
    }
    public double[] deval(double[] x) {
        double[] output = new double[x.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = this.deval(x[i]);
        }
        return output;
    }
}
