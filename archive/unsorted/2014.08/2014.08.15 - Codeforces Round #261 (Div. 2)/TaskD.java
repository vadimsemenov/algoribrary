
package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.*;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] array = new int[counter];
        for (int i = 0; i < counter; ++i) {
            array[i] = in.nextInt();
        }
        int[] fst = new int[counter];

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < counter; ++i) {
            Integer val = map.get(array[i]);
            if (val == null) val = 0;
            fst[i] = val + 1;
            map.put(array[i], fst[i]);
        }
        BIT tree = new BIT(counter + 1);
        for (int i = 0; i < counter; ++i) {
            tree.update(map.get(array[i]) - fst[i] + 1, 1);
        }

        long result = 0;
        for (int i = 0; i < counter; ++i) {
            tree.update(map.get(array[i]) - fst[i] + 1, -1);
            result += tree.query(fst[i] - 1);
        }
        out.println(result);
    }

    static class BIT {
        int[] data;

        BIT(int size) {
            this.data = new int[size];
        }

        void update(int at, int by) {
            while (at < data.length) {
                data[at] += by;
                at |= at + 1;
            }
        }

        long query(int at) {
            long result = 0;
            while (at >= 0) {
                result += data[at];
                at = (at & at + 1) - 1;
            }
            return result;
        }
    }

}
