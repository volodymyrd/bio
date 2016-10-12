package com.gmail.volodymyrdotsenko.javabio.algorithms.matrix;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

    private Matrix<Integer> matrix_1_2 = new Matrix<>(1, 2);
    private Matrix<Integer> matrix_2_2 = new Matrix<>(2, 2);
    private Matrix<Integer> matrix_3_2 = new Matrix<>(3, 2);
    private Matrix<Integer> matrix_3_4 = new Matrix<>(3, 4);

    @Before
    public void setUp() {
        matrix_1_2.addCell(0, 0, 1).addCell(0, 1, 2);

        matrix_2_2.addCell(0, 0, 1).addCell(0, 1, 2).addCell(1, 0, 3).addCell(1, 1, 4);

        matrix_3_2.addCell(0, 0, 1).addCell(0, 1, 2).addCell(1, 0, 3).addCell(1, 1, 4)
                .addCell(2, 0, 5).addCell(2, 1, 6);

        matrix_3_4.addCell(0, 0, 1).addCell(0, 1, 2).addCell(0, 2, 3).addCell(0, 3, 4)
                .addCell(1, 0, 5).addCell(1, 1, 6).addCell(1, 2, 7).addCell(1, 3, 8)
                .addCell(2, 0, 9).addCell(2, 1, 10).addCell(2, 2, 11).addCell(2, 3, 12);
    }

    @Test
    public void shouldCreateMatrixWithInteger3x4() {
        System.out.println(matrix_1_2);
        System.out.println(matrix_2_2);
        System.out.println(matrix_3_2);
        System.out.println(matrix_3_4);
    }

    @Test
    public void shouldTransposeMatrix() {
        System.out.println(matrix_1_2);
        System.out.println(matrix_1_2.transpose());

        System.out.println(matrix_2_2);
        System.out.println(matrix_2_2.transpose());

        System.out.println(matrix_3_2);
        System.out.println(matrix_3_2.transpose());

        System.out.println(matrix_3_4);
        System.out.println(matrix_3_4.transpose());
    }
    
    @Test
    public void shouldRotate90RigtMatrix() {
        System.out.println(matrix_1_2);
        System.out.println(matrix_1_2.rotate90Rigt());

        System.out.println(matrix_2_2);
        System.out.println(matrix_2_2.rotate90Rigt());

        System.out.println(matrix_3_2);
        System.out.println(matrix_3_2.rotate90Rigt());

        System.out.println(matrix_3_4);
        System.out.println(matrix_3_4.rotate90Rigt());
    }
}
