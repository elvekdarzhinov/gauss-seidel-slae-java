package org.example;

public class Matrix {

    private final double[][] matrix;
    private final int rows;
    private final int columns;

    public Matrix(double[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException();
        }

        this.rows = matrix.length;
        this.columns = matrix[0].length;

        this.matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, columns);
        }
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

    public Matrix(Matrix m) {
        this.rows = m.rows;
        this.columns = m.columns;

        this.matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(m.matrix[i], 0, this.matrix[i], 0, columns);
        }
    }

    public Matrix transpose() {
        Matrix result = new Matrix(columns, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.set(j, i, this.get(i, j));
            }
        }

        return result;
    }

    public Matrix multiply(Matrix m) {
        if (this.columns != m.rows) {
            throw new IllegalArgumentException("Incorrect matrix sizes.");
        }

        Matrix result = new Matrix(this.rows, m.columns);

        for (int i = 0; i < result.rows; i++) {
            for (int k = 0; k < this.columns; k++) {
                for (int j = 0; j < result.columns; j++) {
                    result.set(i, j, result.get(i, j) + this.get(i, k) * m.get(k, j));
                }
            }
        }

        return result;
    }

    public Matrix add(Matrix m) {
        if (this.rows != m.rows || this.columns != m.columns) {
            throw new IllegalArgumentException("Matrices should be the same size.");
        }

        Matrix result = new Matrix(rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.set(i, j, this.get(i, j) + m.get(i, j));
            }
        }

        return result;
    }

    public double[][] asArray() {
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, result[i], 0, columns);
        }
        return result;
    }

    public void set(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public double get(int row, int column) {
        return matrix[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var row : matrix) {
            for (var elem : row) {
                sb.append(elem).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
