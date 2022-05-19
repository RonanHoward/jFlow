package lib;

import java.util.HashSet;

import jflow.Dataset;

import java.awt.Color;

public class PointSets {

    // ------------POINT SETS--------------------

    public static HashSet<Point> bullseye(int innerPoints, int outerPoints) {
        HashSet<Point> output = new HashSet<>();

        // editorials
        final double innerRadius = 2;
        final double outerStart  = 3;
        final double outerEnd    = 4;

        // plot inner circle
        for (int i = 0; i < innerPoints; i++) {
            double r = innerRadius * Math.sqrt(Math.random());
            double theta = Math.random() * 2.0 * Math.PI;
            output.add(new Point(r * Math.cos(theta), r * Math.sin(theta), Color.ORANGE));
        }

        // plot outer ring
        for (int i = 0; i < outerPoints; i++) {
            double r = (Math.random() * (outerEnd - outerStart)) + outerStart;
            double theta = Math.random() * 2.0 * Math.PI;
            output.add(new Point(r * Math.cos(theta), r * Math.sin(theta), Color.BLUE));
        }

        return output;
    }

    public static HashSet<Point> diagonalSeparation(int pointsPerGroup) {
        HashSet<Point> output = new HashSet<>();

        // editorials
        final double[] offset = {2, 2}; // [x, y] will be mirrored over y=x
        final double noise = 3;
        
        for (int i = 0; i < pointsPerGroup; i++) {
            double theta = Math.random() * 2.0 * Math.PI;
            double r = noise * Math.random();
            double[] noiseOffset = {r * Math.cos(theta), r * Math.sin(theta)};
            output.add(new Point(offset[0] + noiseOffset[0], offset[1] + noiseOffset[1], Color.ORANGE));
        }

        for (int i = 0; i < pointsPerGroup; i++) {
            double theta = Math.random() * 2.0 * Math.PI;
            double r = noise * Math.random();
            double[] noiseOffset = {r * Math.cos(theta), r * Math.sin(theta)};
            output.add(new Point(-offset[0] + noiseOffset[0], -offset[1] + noiseOffset[1], Color.BLUE));
        }

        return output;
    }

    public static HashSet<Point> checkeredSquare(int pointsPerGroup) {
        HashSet<Point> output = new HashSet<>();

        final double squareRadius = 4;

        // for each quadrant
        int[] foo = {-1, 1};
        int[] bar = {-1, 1};
        for (int cx : foo) {
            for (int cy : bar) {
                for (int i = 0; i < pointsPerGroup; i++) {
                    double r = (squareRadius / 2.0) * Math.sqrt(Math.random());
                    double theta = Math.random() * 2.0 * Math.PI;
                    double offX = (squareRadius / 2.0) * cx;
                    double offY = (squareRadius / 2.0) * cy;
                    Color c;
                    if (cx != cy) {
                        c = Color.ORANGE;
                    } else {
                        c = Color.BLUE;
                    }
                    output.add(new Point(
                        (r * Math.cos(theta)) + offX,
                        (r * Math.sin(theta)) + offY,
                        c
                    ));
                }
            }
        }

        return output;
    }


    // ------------PREPROCESSING-----------------

    public static Dataset preprocessPointSet(HashSet<Point> pointSet) {
        Point[] points = pointSet.toArray(new Point[pointSet.size()]);

        double[][] train_data   = new double[pointSet.size()][2];
        double[][] train_labels = new double[pointSet.size()][1];
        for (int i = 0; i < points.length; i++) {
            train_data[i][0] = points[i].x;
            train_data[i][1] = points[i].y;
            if (points[i].color.equals(Color.BLUE)) {
                train_labels[i][0] = 1.0;
            } else {
                train_labels[i][0] = -1.0;
            }
        }

        return new Dataset(train_data, train_labels);
    }

}
