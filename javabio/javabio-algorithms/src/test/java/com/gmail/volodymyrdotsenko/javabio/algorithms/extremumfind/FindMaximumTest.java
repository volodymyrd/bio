package com.gmail.volodymyrdotsenko.javabio.algorithms.extremumfind;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Volodymyr_Dotsenko on 9/28/2016.
 */
public class FindMaximumTest {

	private final int size = 10000000;
	private final Random random = new Random();

	@Test
	public void shouldFindMaximumAmongInteger() {
		Integer[] integers = new Integer[size];
		for (int i = 0; i < size; i++) {
			integers[i] = (random.nextInt());
		}
		Integer max = FindMaximum.max(integers);
		assertEquals(Collections.max(Arrays.asList(integers)), max);
	}

	@Test
	public void shouldFindMaximumAmongIntegerUsingForkJoinFramework() {
		Integer[] integers = new Integer[size];
		for (int i = 0; i < size; i++) {
			integers[i] = (random.nextInt());
		}
		Integer max = FindMaximum.maxWithForkJoin(integers);
		assertEquals(Collections.max(Arrays.asList(integers)), max);
	}
}