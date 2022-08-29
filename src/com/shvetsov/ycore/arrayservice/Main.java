package com.shvetsov.ycore.arrayservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Main {
    public static void main(String[] args) {

        ArrayService arrService = new ArrayService(new RandomGen());

        int[][] arr = arrService.getArray(2, 6);
        arrService.print(arr);
        arrService.printMinMaxAvg(arrService.calculateMinMaxAvg(arr));
    }
}

class ArrayService {

    private final RandomGen random;

    ArrayService(RandomGen randomGen) {
        this.random = randomGen;
    }

    public int[][] getArray(int row, int column) {
        int[][] result = new int[row][column];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = (int) random.generate();
            }
        }
        return result;
    }

    public void print(int[][] array) {

        for (int i = 0; i < array.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " | ");
            }
            System.out.println();
        }

    }

    public Map<String, Double> calculateMinMaxAvg(int[][] array) {

        Map<String, Double> arrayCalculation = new HashMap<>();
        double avg;
        double sum = 0.0D;
        double count = 0.0D;
        double max = array[0][0];
        double min = array[0][0];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sum = sum + array[i][j];
                count++;

                if (array[i][j] > max) {
                    max = array[i][j];
                }
                if (array[i][j] < min) {
                    min = array[i][j];
                }
            }
        }
        avg = sum / count;

        arrayCalculation.put("Min", min);
        arrayCalculation.put("Max", max);
        arrayCalculation.put("Avg", avg);
        return arrayCalculation;
    }

    public void printMinMaxAvg(Map<String, Double> arrayCalculation) {
        System.out.println("------------------------------");
        System.out.println(MessageFormat.format("Min value = {0}", arrayCalculation.get("Min")));
        System.out.println(MessageFormat.format("Max value = {0}", arrayCalculation.get("Max")));
        System.out.println(MessageFormat.format("Avg value = {0}", arrayCalculation.get("Avg")));
        System.out.println("------------------------------");
    }

}

class RandomGen {

    private long seed = 1;

    public long generate() {

        long bound = 100;
        long a = (System.nanoTime() / 100) % 100;

        seed = ((seed * 0xfffffff + a)) & ((1L << 7) - 1);
        if (seed >= bound) {
            seed = seed - bound;
        }

        return seed;
    }
}

class RandomGenTest {

    RandomGen randomGen;

    @BeforeEach
    void setUp() {
        randomGen = new RandomGen();
    }

    @Test
    void shouldGenerateRandInBound() {
        for (int i = 0; i < 1000; i++) {
            assertThat(randomGen.generate()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(100);
        }
    }
}

class ArrayServiceTest {

    ArrayService arrayService;
    int[][] testArray1 = new int[][]
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[][] testArray2 = new int[][]
                        {{0}, {0}, {0}};
    int[][] testArray3 = new int[][]
            {{3, 3, 3}, {1, 1, 1}, {2, 2, 2}};

    @BeforeEach
    void setUp() {
        arrayService = new ArrayService(new RandomGen());
    }

    @Test
    void shouldReturnTwoDimArray() {
        assertThat(arrayService.getArray(10, 10)).hasDimensions(10, 10);
        assertThat(arrayService.getArray(5, 10)).hasDimensions(5, 10);
        assertThat(arrayService.getArray(10, 5)).hasDimensions(10, 5);
    }

    @Test
    void shouldReturnCorrectMinMaxAvg() {
        assertThat(arrayService.calculateMinMaxAvg(testArray1)).containsEntry("Min", 1.0);
        assertThat(arrayService.calculateMinMaxAvg(testArray1)).containsEntry("Max", 9.0);
        assertThat(arrayService.calculateMinMaxAvg(testArray1)).containsEntry("Avg", 5.0);
    }

    @Test
    void shouldReturnCorrectMinMaxAvg2() {
        assertThat(arrayService.calculateMinMaxAvg(testArray2)).containsEntry("Min", 0.0);
        assertThat(arrayService.calculateMinMaxAvg(testArray2)).containsEntry("Max", 0.0);
        assertThat(arrayService.calculateMinMaxAvg(testArray2)).containsEntry("Avg", 0.0);
    }

    @Test
    void shouldReturnCorrectMinMaxAvg3() {
        assertThat(arrayService.calculateMinMaxAvg(testArray3)).containsEntry("Min", 1.0);
        assertThat(arrayService.calculateMinMaxAvg(testArray3)).containsEntry("Max", 3.0);
        assertThat(arrayService.calculateMinMaxAvg(testArray3)).containsEntry("Avg", 2.0);
    }
}
