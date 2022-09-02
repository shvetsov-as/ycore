package com.shvetsov.ycore.arraysort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        ArraySort arraySort = new ArraySort();

        int[] array = new int[]
                {5, 6, 3, 2, 5, 1, 4, 9};

        arraySort.sort(array);
        System.out.println(Arrays.toString(array));
    }
}

class ArraySort {

    public void sort(int[] array) {

        int minIndex = 0;
        int maxIndex = array.length - 1;

        sort(array, minIndex, maxIndex);
    }

    private void sort(int[] array, int minIndex, int maxIndex) {

        // array is too short
        if (minIndex >= maxIndex) {
            return;
        }
        //select random barrier element by getting random index
        int randomIndex = randomIndexSelection(minIndex, maxIndex);
        int barrier = array[randomIndex];//and swap last element and barrier []...[barrier] ->[]...[]->[arr.length] (*)
        swapElements(array, randomIndex, maxIndex);//[]...[arr.length] ->[]...[]->[barrier]

        int left = minIndex;
        int right = maxIndex;

        //split the array into two parts [left][barrier][right]
        while (left < right) {

            //moving left index to the right
            while (array[left] <= barrier && left < right) {
                left++;
            }
            //moving right index to the left
            while (array[right] >= barrier && left < right) {
                right--;
            }
            //swap lower to the left and greater to the right [left][][right]
            swapElements(array, left, right);
        }
        //when [left] == [right] & [right] is greater than barrier
        //swap barrier [left][barrier][right]
        swapElements(array, left, maxIndex); //max index is barrier (*)
        //split end
        //sort parts
        sort(array, minIndex, left - 1); // -1 because left is pointing at barrier now, and we need upper bound of [left]
        sort(array, left + 1, maxIndex); // +1 because left is pointing at barrier now, and we need lower bound of [right]
    }

    private int randomIndexSelection (int minIndex, int maxIndex) {
        return new Random().nextInt(maxIndex - minIndex) + minIndex;
    }

    private void swapElements(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}

class ArraySortTest {

    ArraySort arraySort;

    int[] test1 = new int[]
                    {1, 2, 3, 4, 5, 6, 7, 8};
    int[] test2 = new int[]
                    {1, 2, 0, 4, 0, 0, 7, 8};
    int[] test3 = new int[]
                    {1, 0, 1, 0, 1, 0, 1, 0};
    int[] test4 = new int[]
                    {1, 2, 3, 4, -5, -6, -7, -8};
    int[] test5 = new int[5];
    int[] test6 = new int[]
                    {5, 6, 3, 2, 5, 1, 4, 9};

    @BeforeEach
    void setUp() {
        arraySort = new ArraySort();
    }

    @Test
    void shouldReturnSortedArray1() {
        arraySort.sort(test1);
        assertThat(test1).containsExactly(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    void shouldReturnSortedArray2() {
        arraySort.sort(test2);
        assertThat(test2).containsExactly(0, 0, 0, 1, 2, 4, 7, 8);
    }

    @Test
    void shouldReturnSortedArray3() {
        arraySort.sort(test3);
        assertThat(test3).containsExactly(0, 0, 0, 0, 1, 1, 1, 1);
    }

    @Test
    void shouldReturnSortedArray4() {
        arraySort.sort(test4);
        assertThat(test4).containsExactly(-8, -7, -6, -5, 1, 2, 3, 4);
    }

    @Test
    void shouldReturnSortedArray5() {
        arraySort.sort(test5);
        assertThat(test5).containsExactly(0, 0, 0, 0, 0);
    }

    @Test
    void shouldReturnSortedArray6() {
        arraySort.sort(test6);
        assertThat(test6).containsExactly(1, 2, 3, 4, 5, 5, 6, 9);
    }
}
