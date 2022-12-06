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

    public double determinant() {
        if (!this.isSquare()) {
            throw new UnsupportedOperationException("Matrix needs to be square.");
        }
        if (this.getRows() == 1) {
            return this.get(0, 0);
        }
        if (this.getRows() == 2) {
            return (this.get(0, 0) * this.get(1, 1)) - (this.get(0, 1) * this.get(1, 0));
        }
        double sum = 0.0;
        for (int i = 0; i < this.getColumns(); i++) {
            sum += ((i % 2) == 0 ? 1 : -1) * this.get(0, i) * createSubMatrix(0, i).determinant();
        }

        return sum;
    }

    public Matrix createSubMatrix(int excluding_row, int excluding_col) {
        Matrix m = new Matrix(this.getRows() - 1, this.getColumns() - 1);

        int r = -1;
        for (int i = 0; i < this.getRows(); i++) {
            if (i == excluding_row) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < this.getColumns(); j++) {
                if (j == excluding_col) {
                    continue;
                }
                m.set(r, ++c, this.get(i, j));
            }
        }

        return m;
    }

    public Matrix inverse() {
        return cofactor().transpose().multiplyByConstant(1.0 / determinant());
    }

    public Matrix multiplyByConstant(double constant) {
        Matrix m = new Matrix(this.getRows(), this.getColumns());

        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                m.set(i, j, this.get(i, j) * constant);
            }
        }

        return m;
    }

    public Matrix cofactor() {
        Matrix m = new Matrix(this.getRows(), this.getColumns());

        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                m.set(i, j, ((i % 2) == 0 ? 1 : -1) * ((j % 2) == 0 ? 1 : -1) * createSubMatrix(i, j).determinant());
            }
        }

        return m;
    }

    public boolean isSquare() {
        return rows == columns;
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

    public double norm() {
        double sumOfSquares = 0;

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                sumOfSquares += Math.pow(Math.abs(get(i, j)), getRows());
            }
        }

        return Math.pow(sumOfSquares, 1.0 / getRows());
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
        sb.append("[");

        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < columns - 1; j++) {
                sb.append(matrix[i][j]).append(", ");
            }
            sb.append(matrix[i][columns - 1])
              .append("],\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");

        return sb.toString();
    }

}
