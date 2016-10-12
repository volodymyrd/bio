package com.gmail.volodymyrdotsenko.javabio.algorithms.matrix;

public class Matrix<T> {

    private class Cell {
        private final T value;

        public Cell(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private final int rows;
    private final int columns;
    private final Object[][] cells;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Object[rows][columns];
    }

    public Matrix<T> addCell(int row, int column, T value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns)
            throw new IllegalArgumentException();

        cells[row][column] = new Cell(value);

        return this;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> transpose() {
        Matrix<T> transpose = new Matrix<>(this.columns, this.rows);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < columns; c++) {
                transpose.addCell(c, r, (T) cells[r][c]);
            }

        return transpose;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> rotate90Rigt() {
        Matrix<T> rotate90Rigt = new Matrix<>(this.columns, this.rows);
        rotate90Rigt = transpose();
        for (int r = 0; r < rotate90Rigt.rows; r++) {
            reverse((T[]) rotate90Rigt.cells[r]);
        }

        return rotate90Rigt;
    }

    private void reverse(T[] a) {
        for (int i = 0, j = a.length - 1; i < a.length / 2; i++, j--) {
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            sb.append("|");
            for (int c = 0; c < columns; c++) {
                sb.append(cells[r][c]);
                sb.append("|");
            }
            sb.append("\r\n");
        }

        return sb.toString();
    }
}
