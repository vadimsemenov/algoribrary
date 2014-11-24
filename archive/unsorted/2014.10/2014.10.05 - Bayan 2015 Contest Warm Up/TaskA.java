package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int k = in.nextInt();
        char[][] result = new char[6][];
        result[0] = result[5] = "+------------------------+".toCharArray();
        result[1] = "|#.#.#.#.#.#.#.#.#.#.#.|D|)".toCharArray();
        result[2] = "|#.#.#.#.#.#.#.#.#.#.#.|.|".toCharArray();
        result[3] = "|#.......................|".toCharArray();
        result[4] = "|#.#.#.#.#.#.#.#.#.#.#.|.|)".toCharArray();
        int pos = 1;
        int row = 1;
        for (int i = 0; i < k; ++i) {
            result[pos][row] = 'O';
            pos++;
            if (pos == 5) {
                pos = 1;
                row += 2;
            } else if (pos == 3 && row > 1) {
                pos++;
            }
        }
        for (int i = 0; i < 6; ++i) {
            out.println(String.valueOf(result[i]));
        }
    }
}

/*
+------------------------+
|O.O.O.O.O.O.O.#.#.#.#.|D|)
|O.O.O.O.O.O.#.#.#.#.#.|.|)
|O.......................|
|O.O.O.O.O.O.#.#.#.#.#.|.|)
+------------------------+
 */