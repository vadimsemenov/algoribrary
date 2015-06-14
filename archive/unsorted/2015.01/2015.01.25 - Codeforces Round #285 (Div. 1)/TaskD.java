package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

public class TaskD {
    private static final int MAX_BIT = 2000;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        BigInteger[] numbers = new BigInteger[counter];
        for (int i = 0; i < counter; ++i) {
            numbers[i] = in.nextBigInteger();
        }
        int[] responsible = new int[MAX_BIT];
        BitSet[] representation = new BitSet[MAX_BIT];
        Arrays.fill(responsible, -1);
        for (int i = 0; i < counter; ++i) {
            representation[i] = new BitSet(counter);
            boolean representable = true;
            for (int bit = 0; bit < MAX_BIT; ++bit) {
                if (numbers[i].testBit(bit)) {
                    if (responsible[bit] == -1) {
                        representable = false;
                        responsible[bit] = i;
                        break;
                    } else {
                        numbers[i] = numbers[i].xor(numbers[responsible[bit]]);
                        representation[i].xor(representation[responsible[bit]]);
                    }
                }
            }
            if (representable) {
                out.print(representation[i].cardinality());
                for (int j = 0; j < counter; ++j) {
                    if (representation[i].get(j)) {
                        out.print(' ');
                        out.print(j);
                    }
                }
                out.println();
            } else {
                out.println(0);
            }
            representation[i].set(i);
        }
    }
}
