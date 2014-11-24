package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int counter = in.nextInt();
        BigInteger P = BigInteger.valueOf(in.nextInt());
        BigInteger Q = BigInteger.valueOf(in.nextInt());
//        long P = in.nextInt();
//        long Q = in.nextInt();
        BIT tree = new BIT(rows + 1);
        int[][] initial = new int[rows][];
        for (int i = 0; i < rows; ++i) {
            tree.update(i, 2);
            initial[i] = new int[]{1, 1};
        }
        int[] array = new int[counter];
        long totalInconveniently = 0;
        for (int i = 0; i < counter; ++i) {
            String place = in.next();
            int row = Integer.parseInt(place.substring(0, place.length() - 1)) - 1;
            int seat = place.charAt(place.length() - 1) - 'A';
            if (seat == 2 || seat == 3) tree.update(row, -1);
            int toLeft = tree.query(0, row);
            int toRight = tree.query(row, rows);
            if (seat == 0) {
                toLeft += initial[row][0];
                toRight += initial[row][0];
            }
            if (seat == 5) {
                toLeft += initial[row][1];
                toRight += initial[row][1];
            }
            if (seat == 1) initial[row][0]--;
            if (seat == 4) initial[row][1]--;
            array[i] = toLeft - toRight;
            totalInconveniently += toRight;
        }
        Arrays.sort(array);

        BigInteger result = new BigInteger("1000000000000000000000000");
        for (int i = 0; i <= counter; ++i) {
            long inLeft = i;
            long inRight = counter - inLeft;
            BigInteger currentInconveniently = BigInteger.valueOf((inLeft - 1) * inLeft / 2 + (inRight - 1) * inRight / 2);
            currentInconveniently = currentInconveniently.multiply(Q);
            currentInconveniently = currentInconveniently.add(BigInteger.valueOf(totalInconveniently).multiply(P));
            if (result.compareTo(currentInconveniently) > 0) result = currentInconveniently;
            if (i < counter) totalInconveniently += array[i];
        }
        /*
        long result = Long.MAX_VALUE;
        for (int i = 0; i <= counter; ++i) {
            long inLeft = i;
            long inRight = counter - inLeft;
            long currentInconveniently = (inLeft - 1) * inLeft / 2 + (inRight - 1) * inRight / 2;
            currentInconveniently *= Q;
            currentInconveniently += totalInconveniently * P;
            result = Math.min(result, currentInconveniently);
            if (i < counter) totalInconveniently += array[i];
        }
        */
        out.println(result);
    }
}

class BIT {
    private int[] data;

    public BIT(int size) {
        data = new int[size];
    }

    public void update(int at, int by) {
        internalUpdate(at, by);
    }

    public int query(int from, int to) {
        return internalQuery(to) - internalQuery(from - 1);
    }

    private void internalUpdate(int at, int by) {
        if (at < 0) return;
        while (at < data.length) {
            data[at] += by;
            at |= at + 1;
        }
    }

    private int internalQuery(int at) {
        int result = 0;
        if (at < data.length) {
            while (at >= 0) {
                result += data[at];
                at = (at & at + 1) - 1;
            }
        }
        return result;
    }
}
