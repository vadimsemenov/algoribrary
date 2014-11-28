package algoribrary.misc;

import java.util.Random;

/**
 * Created by vadim on 27/11/14.
 */
public class ArrayUtils {
    private static final long SEED = System.nanoTime();
    private static final Random RANDOM = new Random(SEED);

    private ArrayUtils() {
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

    public static byte[] resize(byte[] array, int size) {
        byte[] newArray = new byte[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static short[] resize(short[] array, int size) {
        short[] newArray = new short[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static double[] resize(double[] array, int size) {
        double[] newArray = new double[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static float[] resize(float[] array, int size) {
        float[] newArray = new float[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }
}
