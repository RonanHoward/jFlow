package jflow.activations;

public interface Activation {
    public double eval(double x);
    public double[] eval(double[] x);

    // derivatives
    public double deval(double x);
    public double[] deval(double[] x);
}

