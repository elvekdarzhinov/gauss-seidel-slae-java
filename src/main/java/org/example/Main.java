package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static double epsilon = 1e-10;

    static double[][] a = new double[][]{{2, 1, 1},
                                         {1, -1, 0},
                                         {3, -1, 2}};
    static double[] b = new double[]{2, -2, 2};

    public static void main(String[] args) {
        test1();
        test2();
    }

    static void test2() {
        // C x = d  -- normal system

        Matrix A = new Matrix(a);
        Matrix B = new Matrix(new double[][]{b}).transpose();
        Matrix AT = A.transpose();
        Matrix C = AT.multiply(A);
        Matrix d = AT.multiply(B);

        var x = GaussSeidelMatrixSLAE.solve(C, d, epsilon).transpose().asArray();

        System.out.println(Arrays.deepToString(x));
    }

    private static void test1() {
        var x = GaussSeidelSLAE.solve(a, b, epsilon);

        System.out.println(Arrays.toString(x));
    }

    static void randomAB() {
        Random random = ThreadLocalRandom.current();

        a = new double[10][10];
        b = new double[10];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = random.nextInt(-100, 100);
            }
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = random.nextInt(-100, 100);
        }
    }

}