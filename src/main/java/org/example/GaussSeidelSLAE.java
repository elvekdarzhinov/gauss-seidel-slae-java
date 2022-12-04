package org.example;

import java.util.Arrays;

public class GaussSeidelSLAE {

    private static final int MAX_ITERS = 1000;

    private GaussSeidelSLAE() {
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

    private static boolean isComplete(double[] oldX, double[] newX, double epsilon) {
        double sum = 0;

        for (int i = 0; i < oldX.length; i++) {
            sum += Math.pow(newX[i] - oldX[i], 2);
        }

        return Math.sqrt(sum) < epsilon;
    }

    public static double[] solve(double[][] a, double[] b, double epsilon) {
        if (!checkInput(a, b)) {
            throw new IllegalArgumentException("Incorrect coefficient arrays.");
        }

        int numOfEquations = b.length;

        double[] x = new double[numOfEquations];
        Arrays.fill(x, 1);
        double[] prevX = Arrays.copyOf(x, x.length);

        int itersCount = 0;

        while (itersCount < MAX_ITERS) {
            System.arraycopy(x, 0, prevX, 0, x.length);

            for (int i = 0; i < numOfEquations; i++) {
                double sum1 = 0;
                double sum2 = 0;

                for (int j = 0; j < i; j++) {
                    sum1 += a[i][j] * x[j];
                }

                for (int j = i + 1; j < numOfEquations; j++) {
                    sum1 += a[i][j] * x[j];
                }

                x[i] = (b[i] - sum1 - sum2) / a[i][i];
            }

            if (isComplete(prevX, x, epsilon)) {
                break;
            }

            itersCount++;
        }

        System.out.println("Number of iterations: " + itersCount);

        return x;
    }

}

