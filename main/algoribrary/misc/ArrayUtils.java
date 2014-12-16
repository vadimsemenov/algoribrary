package algoribrary.misc;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by vadim on 27/11/14.
 */
public class ArrayUtils {
    private static final long SEED = System.nanoTime();
    private static final Random RANDOM = new Random(SEED);

    private ArrayUtils() {
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
