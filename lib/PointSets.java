package lib;

import java.util.HashSet;
import java.awt.Color;

public class PointSets {

    public static HashSet<Point> bullseye(int innerPoints, int outerPoints) {
        HashSet<Point> output = new HashSet<>();

        // editorials
        final double innerRadius = 2;
        final double outerStart  = 3;
        final double outerEnd    = 4;

        // plot inner circle
        for (int i = 0; i < innerPoints; i++) {
            double r = innerRadius * Math.sqrt(Math.random());
            double theta = Math.random() * 2 * Math.PI;
            output.add(new Point(r * Math.cos(theta), r * Math.sin(theta), Color.ORANGE));
        }

        // plot outer ring
        for (int i = 0; i < outerPoints; i++) {
            double r = (Math.random() * (outerEnd - outerStart)) + outerStart;
            double theta = Math.random() * 2 * Math.PI;
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
            double theta = Math.random() * 2 * Math.PI;
            double r = noise * Math.random();
            double[] noiseOffset = {r * Math.cos(theta), r * Math.sin(theta)};
            output.add(new Point(offset[0] + noiseOffset[0], offset[1] + noiseOffset[1], Color.ORANGE));
        }

        for (int i = 0; i < pointsPerGroup; i++) {
            double theta = Math.random() * 2 * Math.PI;
            double r = noise * Math.random();
            double[] noiseOffset = {r * Math.cos(theta), r * Math.sin(theta)};
            output.add(new Point(-offset[0] + noiseOffset[0], -offset[1] + noiseOffset[1], Color.BLUE));
        }

        return output;
    }
}
