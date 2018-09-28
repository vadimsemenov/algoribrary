package algoribrary.misc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class ArrayUtils {
    private static final Random RANDOM = ThreadLocalRandom.current();

    private ArrayUtils() {
    }

    public static boolean nextPermutation(int[] permutation) {
        final int length = permutation.length;
        int idx = length - 2;
        while (idx >= 0 && permutation[idx] >= permutation[idx + 1]) {
            --idx;
        }
        if (idx < 0) {
            Arrays.sort(permutation);
            return false;
        }
        int toSwap = idx + 1;
        for (int i = idx + 2; i < length; ++i) {
            if (permutation[i] > permutation[idx] && permutation[i] < permutation[toSwap]) {
                toSwap = i;
            }
        }
        swap(permutation, idx, toSwap);
        Arrays.sort(permutation, idx + 1, length);
        return true;
    }

    public static boolean nextPermutation(long[] permutation) {
        final int length = permutation.length;
        int idx = length - 2;
        while (idx >= 0 && permutation[idx] >= permutation[idx + 1]) {
            --idx;
        }
        if (idx < 0) {
            Arrays.sort(permutation);
            return false;
        }
        int toSwap = idx + 1;
        for (int i = idx + 2; i < length; ++i) {
            if (permutation[i] > permutation[idx] && permutation[i] < permutation[toSwap]) {
                toSwap = i;
            }
        }
        swap(permutation, idx, toSwap);
        Arrays.sort(permutation, idx + 1, length);
        return true;
    }

    public static <T extends Comparable<T>> boolean nextPermutation(T[] permutation) {
        final int length = permutation.length;
        int idx = length - 2;
        while (idx >= 0 && permutation[idx].compareTo(permutation[idx + 1]) >= 0) {
            --idx;
        }
        if (idx < 0) {
            Arrays.sort(permutation);
            return false;
        }
        int toSwap = idx + 1;
        for (int i = idx + 2; i < length; ++i) {
            if (permutation[i].compareTo(permutation[idx]) > 0 &&
                    permutation[i].compareTo(permutation[toSwap]) < 0) {
                toSwap = i;
            }
        }
        swap(permutation, idx, toSwap);
        Arrays.sort(permutation, idx + 1, length);
        return true;
    }

    public static <T> boolean nextPermutation(T[] permutation, Comparator<T> comparator) {
        final int length = permutation.length;
        int idx = length - 2;
        while (idx >= 0 && comparator.compare(permutation[idx], permutation[idx + 1]) >= 0) {
            --idx;
        }
        if (idx < 0) {
            Arrays.sort(permutation, comparator);
            return false;
        }
        int toSwap = idx + 1;
        for (int i = idx + 2; i < length; ++i) {
            if (comparator.compare(permutation[i], permutation[idx]) > 0 &&
                    comparator.compare(permutation[i], permutation[toSwap]) < 0) {
                toSwap = i;
            }
        }
        swap(permutation, idx, toSwap);
        Arrays.sort(permutation, idx + 1, length, comparator);
        return true;
    }

    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void swap(long[] array, int i, int j) {
        long tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void swap(double[] array, int i, int j) {
        double tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void shuffle(int[] array) {
        for (int i = 1; i < array.length; ++i) {
            int j = RANDOM.nextInt(i + 1);
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static void shuffle(long[] array) {
        for (int i = 1; i < array.length; ++i) {
            int j = RANDOM.nextInt(i + 1);
            long tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static void shuffle(double[] array) {
        for (int i = 1; i < array.length; ++i) {
            int j = RANDOM.nextInt(i + 1);
            double tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static <T> void shuffle(T[] array) {
        for (int i = 1; i < array.length; ++i) {
            int j = RANDOM.nextInt(i + 1);
            T tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static void sort(int[] array) {
        shuffle(array);
        Arrays.sort(array);
    }

    public static void sort(long[] array) {
        shuffle(array);
        Arrays.sort(array);
    }

    public static void sort(double[] array) {
        shuffle(array);
        Arrays.sort(array);
    }

    public static void sort(Comparable[] array) {
        shuffle(array);
        Arrays.sort(array);
    }

    public static <T> void sort(T[] array, Comparator<T> comparator) {
        shuffle(array);
        Arrays.sort(array, comparator);
    }

    public static int[] resize(int[] array, int size) {
        int[] newArray = new int[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static long[] resize(long[] array, int size) {
        long[] newArray = new long[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static double[] resize(double[] array, int size) {
        double[] newArray = new double[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static byte[] resize(byte[] array, int size) {
        byte[] newArray = new byte[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }
}
