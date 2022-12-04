package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static double[][] a = new double[][]{{3, -2},
                                         {5, 1}};
    static double[] b = new double[]{-6, 3};

    public static void main(String[] args) {
        test();
//        test1();
    }

    static void test() {
        // C x = d  -- normal system
        double epsilon = 1e-10;

        Random random = ThreadLocalRandom.current();

        a = new double[100][100];
        b = new double[100];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = random.nextInt(-100, 100);
            }
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = random.nextInt(-100, 100);
        }

        Matrix A = new Matrix(a);
        Matrix B = new Matrix(new double[][]{b}).transpose();
        Matrix AT = A.transpose();
        Matrix C = AT.multiply(A);
        Matrix d = AT.multiply(B);

        var x = GaussSeidelMatrixSLAE.solve(C, d, epsilon);

        System.out.println(x);

//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < A.getRows(); i++) {
//            for (int j = 0; j < A.getColumns(); j++) {
//                sb.append(String.format("%.2f", A.get(i, j)))
//                  .append(" * ")
//                  .append(String.format("%.2f", x.get(j, 0)))
//                  .append(" + ");
//            }
//            sb.append("= ")
//              .append(String.format("%.2f", B.get(i, 0)))
//              .append("\n");
//        }
//        System.out.println(sb);
    }

    private static void test1() {
        double epsilon = 1e-10;
        var x = GaussSeidelSLAE.solve(a, b, epsilon);

        System.out.println(Arrays.toString(x));
    }

}