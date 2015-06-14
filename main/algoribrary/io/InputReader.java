package algoribrary.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by vadim on 15-03-2014.
 */
public class InputReader {
    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    public InputReader(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    public int[] nextIntArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; ++i) {
            array[i] = nextInt();
        }
        return array;
    }

    public long[] nextLongArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; ++i) {
            array[i] = nextLong();
        }
        return array;
    }

    public double[] nextDoubleArray(int size) {
        double[] array = new double[size];
        for (int i = 0; i < size; ++i) {
            array[i] = nextDouble();
        }
        return array;
    }

    public String[] nextStringArray(int size) {
        String[] array = new String[size];
        for (int i = 0; i < size; ++i) {
            array[i] = next();
        }
        return array;
    }

    public boolean[][] nextBooleanTable(int rows, int columns, char trueCharacter) {
        boolean[][] table = new boolean[rows][columns];
        for (int i = 0; i < rows; ++i) {
            String row = next();
            assert row.length() == columns;
            for (int j = 0; j < columns; ++j) {
                table[i][j] = (row.charAt(j) == trueCharacter);
            }
        }
        return table;
    }

    public char[][] nextCharTable(int rows, int columns) {
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            table[i] = next().toCharArray();
            assert table[i].length == columns;
        }
        return table;
    }

    public int[][] nextIntTable(int rows, int columns) {
        int[][] table = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                table[i][j] = nextInt();
            }
        }
        return table;
    }

    public long[][] nextLongTable(int rows, int columns) {
        long[][] table = new long[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                table[i][j] = nextLong();
            }
        }
        return table;
    }

    public double[][] nextDoubleTable(int rows, int columns) {
        double[][] table = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                table[i][j] = nextDouble();
            }
        }
        return table;
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public BigInteger nextBigInteger() {
        return new BigInteger(next());
    }

    public boolean hasNext() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = readLine();
            if (line == null) {
                return false;
            }
            tokenizer = new StringTokenizer(line);
        }
        return true;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(readLine());
        }
        return tokenizer.nextToken();
    }

    public String readLine() {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }
}
