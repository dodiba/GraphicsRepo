package Week02;

public class Matrix extends MatrixAbstract {

    public Matrix(int rows, int cols) {
        m = new double[rows][cols];
    }

    public Matrix(double[][] values) {
        m = new double[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            System.arraycopy(values[i], 0, m[i], 0, values[i].length);
        }
    }

    @Override
    public MatrixAbstract mult(MatrixAbstract other) throws IllegalArgumentException {
        double[][] otherMatrix = other.getMatrix();
        if (this.m[0].length != otherMatrix.length) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        double[][] result = new double[this.m.length][otherMatrix[0].length];
        for (int i = 0; i < this.m.length; i++) {
            for (int j = 0; j < otherMatrix[0].length; j++) {
                for (int k = 0; k < this.m[0].length; k++) {
                    result[i][j] += this.m[i][k] * otherMatrix[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    @Override
    public MatrixAbstract invert() throws ArithmeticException, IllegalArgumentException {
        int n = m.length;
        if (n != m[0].length) {
            throw new IllegalArgumentException("Matrix is not square.");
        }

        double[][] identity = new double[n][n];
        double[][] copy = new double[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(m[i], 0, copy[i], 0, n);
            identity[i][i] = 1;
        }

        for (int i = 0; i < n; i++) {
            double pivot = copy[i][i];
            if (Math.abs(pivot) < 1e-10) {
                throw new ArithmeticException("Matrix is singular.");
            }

            for (int j = 0; j < n; j++) {
                copy[i][j] /= pivot;
                identity[i][j] /= pivot;
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = copy[k][i];
                    for (int j = 0; j < n; j++) {
                        copy[k][j] -= factor * copy[i][j];
                        identity[k][j] -= factor * identity[i][j];
                    }
                }
            }
        }
        return new Matrix(identity);
    }

    @Override
    public MatrixAbstract transpose() {
        double[][] result = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                result[j][i] = m[i][j];
            }
        }
        return new Matrix(result);
    }

    @Override
    public MatrixAbstract scale(double s) {
        double[][] result = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                result[i][j] = m[i][j] * s;
            }
        }
        return new Matrix(result);
    }
}
