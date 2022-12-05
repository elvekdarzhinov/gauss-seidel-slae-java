package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static double epsilon = 1e-10;
    static int MATRIX_SIZE = 5;

    static double[][] a = new double[][]{{2, 1, 1}, {1, -1, 0}, {3, -1, 2}};
    static double[] b = new double[]{2, -2, 2};

    public static void main(String[] args) {
//        randomAB();
//        test1();
        test2();
//        test3();
    }

    static void test3() {
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(new double[][]{b}).transpose();

    }

    static void test2() {
        // C x = d  -- normal system

        Matrix A = new Matrix(a);
        Matrix B = new Matrix(new double[][]{b}).transpose();
        Matrix AT = A.transpose();
        Matrix C = AT.multiply(A);
        Matrix d = AT.multiply(B);

        var x = GaussSeidelSLAE.solve(C.asArray(), d.transpose().asArray()[0], epsilon);

        Matrix newB = A.multiply(new Matrix(new double[][]{x}).transpose());

        for (int i = 0; i < 3; i++) {
            System.out.format("%f  %f\n", B.get(i, 0), newB.get(i, 0));
        }
    }

    private static void test1() {
        var x = GaussSeidelSLAE.solve(a, b, epsilon);

        System.out.println(Arrays.toString(x));
    }

    static void randomAB() {
        Random random = ThreadLocalRandom.current();

        a = new double[MATRIX_SIZE][MATRIX_SIZE];
        b = new double[MATRIX_SIZE];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = random.nextInt(-100, 100);
            }
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = random.nextInt(-100, 100);
        }
    }

    static void fancyPrint(Matrix A, Matrix B, double[] x) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < MATRIX_SIZE; i++) {
            double a0 = A.get(i, 0);

            if ((a0 >= 0 && x[0] >= 0) || (a0 < 0 && x[0] < 0)) {
                sb.append(String.format("%.0f * %f ", Math.abs(a0), Math.abs(x[0])));
            } else {
                sb.append(String.format("-%.0f * %f ", Math.abs(a0), Math.abs(x[0])));
            }

            for (int j = 1; j < MATRIX_SIZE; j++) {
                double a = A.get(i, j);

                if ((a >= 0 && x[j] >= 0) || (a < 0 && x[j] < 0)) {
                    sb.append(String.format("+ %.0f * %f ", Math.abs(a), Math.abs(x[j])));
                } else {
                    sb.append(String.format("- %.0f * %f ", Math.abs(a), Math.abs(x[j])));
                }
            }
            sb.append(String.format("= %.0f\n", B.get(i, 0)));
        }
        System.out.println(sb);
    }

}