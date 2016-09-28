package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Volodymyr_Dotsenko on 9/28/2016.
 */
public class HelperTest {

    @Test
    public void shouldMerge1() throws Exception {
        List<Integer> list2 = Stream.of(1, 4, 7).collect(Collectors.toList());
        List<Integer> list1 = Stream.of(2, 3, 5, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 4, 5, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldMerge2() throws Exception {
        List<Integer> list1 = Stream.of(1, 4, 7).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(2, 3, 5, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 4, 5, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldMerge3() throws Exception {
        List<Integer> list1 = Stream.of(1, 2, 3).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(5, 6, 7, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 5, 6, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldMerge4() throws Exception {
        List<Integer> list2 = Stream.of(1, 2, 3).collect(Collectors.toList());
        List<Integer> list1 = Stream.of(5, 6, 7, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 5, 6, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }
}
