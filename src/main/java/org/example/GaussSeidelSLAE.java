package org.example;

import java.util.Arrays;

public class GaussSeidelSLAE {

    private static final int MAX_ITERS = 1_000_000;

    private GaussSeidelSLAE() {
    }

    public static double[] solve(double[][] a, double[] b, double epsilon) {
        if (!checkInput(a, b)) {
            throw new IllegalArgumentException("Incorrect input.");
        }

        int numOfEquations = b.length;

        double[] x = new double[numOfEquations];
        double[] prevX = Arrays.copyOf(x, x.length);

        int itersCount = 0;

        do {
            System.arraycopy(x, 0, prevX, 0, x.length);

            for (int i = 0; i < numOfEquations; i++) {
                double sum = 0;

                for (int j = 0; j < i; j++) {
                    sum += a[i][j] * x[j];
                }

                for (int j = i + 1; j < numOfEquations; j++) {
                    sum += a[i][j] * x[j];
                }

                x[i] = (b[i] - sum) / a[i][i];
            }

            itersCount++;

        } while (!converged(prevX, x, epsilon) && itersCount < MAX_ITERS);

        System.out.println("Number of iterations: " + itersCount);

        return x;
    }

    private static boolean checkInput(double[][] a, double[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != b.length) {
                System.out.println("Incompatible array sizes.");
                return false;
            }
            if (a[i][i] == 0) {
                System.out.println("Zeroes aren't allowed on a main diagonal.");
                return false;
            }
        }
        return true;
    }

    private static boolean converged(double[] prevX, double[] x, double epsilon) {
        double sumOfSquares = 0;

        for (int i = 0; i < prevX.length; i++) {
            sumOfSquares += Math.pow(x[i] - prevX[i], 2);
        }

        return Math.sqrt(sumOfSquares) < epsilon;
    }

}

