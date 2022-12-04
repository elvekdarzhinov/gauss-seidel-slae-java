package org.example;

import java.util.Arrays;

public class Main {

    static double[][] a = new double[][]{{5, 2},
                                         {7, 3}};
    static double[] b = new double[]{3, 2};

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        double epsilon = 1e-10;
        var x = GaussSeidelSLAE.solve(a, b, epsilon);

        System.out.println(Arrays.toString(x));
    }

}