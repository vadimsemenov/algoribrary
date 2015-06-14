package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    private static final int MODULO = 1_000_000_000 + 23;
    private static final int BUBEN = 1_490_280;

    private static final int[] MODULOS = new int[]{3, 121, 61, 45161};
    private static final int[] BUBENS  = new int[]{8, 110, 60, 45160};
//    private static final int[] MODULOS = new int[]{22143, 45161};
//    private static final int[] BUBENS  = new int[]{1320, 45160};

/*
1
6723810479506575607 8951403739315253998
Expected output:
934853157
Execution result:
601519816
*/
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long counter = in.nextLong();
        long power = in.nextLong();
        int[] result = new int[BUBENS.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = solve(counter, power, BUBENS[i], MODULOS[i]);
        }
        System.err.println(Arrays.toString(result));

        /* crt here:

        x === a1 mod p1
        ...
        x === a4 mod p4

        x = a1 * p2 * rev[2][1] * p3 * rev[3][1] * p4 * rev[4][1] +
            a2 * p1 * rev[1][2] * p3 * rev[3][1] * p4 * rev[4][1] +
            a3 * p1 * ...
         */

        long answer = 0;
//        for (int i = 0; i < result.length; ++i) {
//            long ad = result[i];
//            for (int j = 0; j < result.length; ++j) if (i != j) {
//                ad = (ad * MODULOS[j] * inverse(MODULOS[j], MODULOS[i])) % MODULO;
//            }
//            answer += ad;
//        }

        long[] x = new long[result.length];
        long mul = 1;
        for (int i = 0; i < result.length; ++i) {
            x[i] = result[i];
            for (int j = 0; j < i; ++j) {
                x[i] = ((x[i] + MODULO - x[j]) * inverse(MODULOS[j], MODULOS[i])) % MODULO;
            }
            answer += x[i] * mul;
            answer %= MODULO;
            mul *= MODULOS[i];
        }
        out.println(answer % MODULO);
    }

    private int inverse(int base, int modulo) {
        return pow(base % modulo, phi(modulo) - 1, modulo);
    }

    private int phi(int number) {
        int phi = number;
        for (int d = 2; d * d <= number; ++d) {
            if (number % d == 0) {
                while (number % d == 0) {
                    number /= d;
                }
                phi -= phi / d;
            }
        }
        if (number > 1) {
            phi -= phi / number;
        }
        return phi;
    }

    private int solve(long counter, long _power, int buben, int modulo) {
        int power = (int) (_power % phi(modulo));

        int f1 = 1;
        int f2 = 1;
        long sum = 2;
        for (int no = 3; no <= counter % buben; ++no) {
            int f = f1 + f2;
            if (f >= modulo) f -= modulo;
            sum += pow(f, power, modulo);
            f2 = f1;
            f1 = f;
        }
        int answer;
        if ((counter % buben) < 3) {
            answer = (int) (counter % buben);
        } else {
            answer = (int) (sum % modulo);
        }
        if (counter / buben > 0) {
            for (int no = Math.max(2, (int) (counter % buben)) + 1; no <= buben; ++no) {
                int f = f1 + f2;
                if (f >= modulo) f -= modulo;
                sum += pow(f, power, modulo);
                f2 = f1;
                f1 = f;
            }
        }
        answer = (int) ((answer + (sum % modulo) * (counter / buben % modulo)) % modulo);
        return answer;
    }

    public final int pow(int base, int power, int modulo) {
        int result = 1;
        while (power > 0) {
            if ((power & 1) == 1) {
                result = (result * base) % modulo;
            }
            base = (base * base) % modulo;
            power >>>= 1;
        }
        return result;
    }
}
