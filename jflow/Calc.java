package jflow;

import java.util.Arrays;
import java.awt.Color;

/**
 * Any variable of type double[] will be considered a vector
 * Any variable of type double[][] will be considered a matrix
 */

public class Calc {
    
    //---------------------------------Matrix & Vector math----------------------------------------
    /**
     * Transposes a matrix
     * @param matrix
     * @return
     */
    public static double[][] transpose(double[][] matrix) {
        double[][] output = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                output[j][i] = matrix[i][j];
            }
        }
        return output;
    }
    /**
     * Transposes a n length vector to a (1 x n) matrix
     * @param vector
     * @return
     */
    public static double[][] transpose(double[] vector) {
        double[][] output = {vector};
        return output;
    }
    /**
     * Multiplies 2 Matricies
     * @param m1
     * @param m2
     * @return
     */
    public static double[][] dot(double[][] m1, double[][] m2) {
        Shape $m1 = new Shape(m1);
        Shape $m2 = new Shape(m2);

        // verify shapes
        if ($m1.x != $m2.y) {
            throw new Error(
                "Matricies of shape "+$m1.toString()+" and "+$m2.toString()+" cannot compute a dot product."
            );
        }

        double[][] output = new double[$m1.y][$m2.x];

        // for each row of m1 and each column of m2
        for (int row = 0; row < $m1.y; row++) {
            for (int col = 0; col < $m2.x; col++) {
                // get a summation of each number in row multiplied by each number in column
                double sum = 0.0;
                for (double rowNum : m1[row]) {
                    for (int i = 0; i < $m2.y; i++) {
                        sum += rowNum * m2[i][col];
                    }
                }
                output[row][col] = sum; // add to output matrix
            }
        }

        return output;
    }
    /**
     * Multiplies a matrix by a vector
     * @param m
     * @param v
     * @return 
     */
    public static double[] dot(double[][] m, double[] v) {
        Shape $m = new Shape(m);
        Shape $v = new Shape(v);

        // verify shapes
        if ($m.x != $v.y) {
            throw new Error(
                "Matrix of shape "+$m.toString()+" and vector of shape "+$v.toString()+" cannot compute a dot product."
            );
        }

        double[] output = new double[m.length];
        Arrays.fill(output, 0.0);

        // for each row in matrix
        for (int row = 0; row < m.length; row++) {
            // for each number in that matrix row and vector column
            for (int num = 0; num < m[0].length; num++) {
                // add the product those 2 values to the output vector
                output[row] += m[row][num] * v[num];
            }
        }

        return output;
    }
    /**
     * Computes hadamard product for 2 vectors
     * @param a
     * @param b
     * @return
     */
    public static double[] hadamard(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new Error(
                "Vectors of size "+a.length+" and "+b.length+" cannot compute a Hadamard product"
            );
        }
        double[] output = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            output[i] = a[i] * b[i];
        }
        return output;
    }
    /**
     * Adds 2 matricies of the same shape
     * @param a
     * @param b
     * @return
     */
    public static double[][] add(double[][] a, double[][] b) {
        Shape $a = new Shape(a);
        Shape $b = new Shape(b);
        if (!$a.equals($b)) {
            throw new Error("Cannot add matricies of shape "+$a.toString()+" and "+$b.toString());
        }

        double[][] output = new double[$a.y][$a.x];
        for (int i = 0; i < $a.y; i++) {
            for (int j = 0; j < $a.x; j++) {
                output[i][j] = a[i][j] + b[i][j];
            }
        }
        return output;
    }
    /**
     * Adds 2 vectors of the same size
     * @param a
     * @param b
     * @return
     */
    public static double[] add(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new Error(
                "Cannot add vectors of size "+a.length+" and "+b.length
            );
        }
        double[] output = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            output[i] = a[i] + b[i];
        }
        return output;
    }
    /**
     * Subtracts 2 vectors of the same size
     * @param a
     * @param b
     * @return
     */
    public static double[] subtract(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new Error(
                "Cannot subtract vectors of size "+a.length+" and "+b.length
            );
        }
        double[] output = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            output[i] = a[i] - b[i];
        }
        return output;
    }
    /**
     * Multiplies vector by a scalar
     * @param vector
     * @param scalar
     * @return
     */
    public static double[] scalar(double[] vector, double scalar) {
        double[] output = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            output[i] = vector[i] * scalar;
        }
        return output;
    }
    /**
     * Multiplies a matrix by a scalar
     * @param matrix
     * @param scalar
     * @return
     */
    public static double[][] scalar(double[][] matrix, double scalar) {
        double[][] output = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                output[i][j] = matrix[i][j] * scalar;
            }
        }
        return output;
    }




    public static Color assignColor(double x, double min, double max) {
        if ( x >= min && x <= max) {
            x = x - min;
            x = x / (max - min);
            int value = (int)(255 * x);
            return new Color(value, value, value);
        } else {
            if (x < min) {
                return new Color(0, 0, 0);
            }
            return new Color(255, 255, 255);
        }
    }


    // DEBUGGING FUNCTIONS
    public static void log(double[][] matrix) {
        System.out.print('[');
        for (int i = 0; i < matrix.length; i++) {
            if (i != 0) { System.out.print(' '); }
            System.out.print(Arrays.toString(matrix[i]));
            if (i != matrix.length - 1) { System.out.println(','); }
        }
        System.out.println(']');
    }
    public static void log(double[] vector) {
        System.out.println(Arrays.toString(vector));
    }
}
