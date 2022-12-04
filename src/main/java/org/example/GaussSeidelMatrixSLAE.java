package org.example;

public class GaussSeidelMatrixSLAE {

    private static final int MAX_ITERS = 1_000_000;

    private GaussSeidelMatrixSLAE() {
    }

    public static Matrix solve(Matrix a, Matrix b, double epsilon) {
        if (!checkInput(a, b)) {
            throw new IllegalArgumentException("Incorrect coefficient arrays.");
        }

        int numOfEquations = b.getRows();

        Matrix x = new Matrix(numOfEquations, 1);

        int itersCount = 0;

        while (itersCount < MAX_ITERS) {
            Matrix prevX = new Matrix(x);

            for (int i = 0; i < numOfEquations; i++) {
                double sum1 = 0;
                double sum2 = 0;

                for (int j = 0; j < i; j++) {
                    sum1 += a.get(i, j) * x.get(j, 0);
                }

                for (int j = i + 1; j < numOfEquations; j++) {
                    sum1 += a.get(i, j) * x.get(j, 0);
                }

                x.set(i, 0, (b.get(i, 0) - sum1 - sum2) / a.get(i, i));
            }

            if (isComplete(prevX, x, epsilon)) {
                break;
            }

            itersCount++;
        }

        System.out.println("Number of iterations: " + itersCount);

        return x;
    }

    private static boolean checkInput(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            System.out.println("Incompatible array sizes.");
            return false;
        }
        for (int i = 0; i < a.getRows(); i++) {
            if (a.get(i, i) == 0) {
                System.out.println("Zeroes aren't allowed on a main diagonal.");
                return false;
            }
        }
        return true;
    }

    private static boolean isComplete(Matrix prevX, Matrix x, double epsilon) {
        double sum = 0;

        for (int i = 0; i < x.getRows(); i++) {
            sum += Math.pow(x.get(i, 0) - prevX.get(i, 0), 2);
        }

        return Math.sqrt(sum) < epsilon;
    }

}

