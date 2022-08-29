package com.shvetsov.ycore.arrayservice;

import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) {

        ArrayService arrService = new ArrayService(new RandomGen());

        int[][] arr = arrService.getArray(6, 6);
        arrService.print(arr);
        arrService.calcMinMaxAvg(arr);
    }
}

class ArrayService {

    private final RandomGen random;

    ArrayService(RandomGen randomGen) {
        this.random = randomGen;
    }

    public int[][] getArray(int dimX, int dimY) {
        int[][] result = new int[dimX][dimY];
        for (int i = 0; i < result[i].length - 1; i++) {
            for (int j = 0; j < result[j].length - 1; j++) {
                result[i][j] = (int) random.generate();
            }
        }
        return result;
    }

//    private int[] getOneDimArray(int dimY) {
//        int[] result = new int[dimY];
//        for (int i = 0; i < result.length; i++) {
//            result[i] = (int) random.generate();
//        }
//        return result;
//    }

    public void print(int[][] array) {

        for (int i = 0; i < array[i].length - 1; i++) {
            System.out.println();
            System.out.print("| ");
            for (int j = 0; j < array[j].length - 1; j++) {
                System.out.print(array[i][j] + " | ");
            }
            System.out.println();
        }
//        for (int[] number : array) {
//            System.out.println();
//            System.out.print("| ");
//            for (int i = 0; i < array.length; i++) {
//                System.out.print(number[i] + " | ");
//            }
//            System.out.println();
//        }
    }

    public void calcMinMaxAvg(int[][] array) {

        double avg;
        int max = array[0][0];
        int min = array[0][0];
        double sum = 0.0D;
        double count = 0.0D;

        for (int i = 0; i < array[i].length - 1; i++) {
            for (int j = 0; j < array[j].length - 1; j++) {
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
//        for (int[] numbers : array) {
//            for (int i = 0; i < array.length; i++) {
//                sum = sum + numbers[i];
//                count++;
//
//                if (numbers[i] > max) {
//                    max = numbers[i];
//                }
//                if (numbers[i] < min) {
//                    min = numbers[i];
//                }
//            }
//        }
        avg = sum / count;
        System.out.println(MessageFormat.format("Min value = {0}", min));
        System.out.println(MessageFormat.format("Max value = {0}", max));
        System.out.println(MessageFormat.format("Avg value = {0}", avg));
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
