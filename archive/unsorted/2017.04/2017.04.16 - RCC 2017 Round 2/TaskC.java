package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] array = new int[counter + 1];
        int[] where = new int[counter + 1];
        for (int i = 1; i <= counter; ++i) {
            array[i] = in.nextInt();
            where[array[i]] = i;
        }
        int firstFree = 1;
        while (firstFree <= counter && where[firstFree] != 0) ++firstFree;
        if (firstFree <= counter) {
            int nextFree = firstFree + 1;
            int ptr = firstFree;
            while (true) {
                if (array[ptr] == 0) {
                    while (nextFree <= counter && where[nextFree] != 0) ++nextFree;
                    if (nextFree > counter) {
                        array[ptr] = firstFree;
                        break;
                    } else {
                        array[ptr] = nextFree;
                    }
                }
                where[array[ptr]] = ptr;
                ptr = array[ptr];
            }
        }
        out.println(counter - countCycles(array));
        for (int i = 1; i <= counter; ++i) {
            out.print(array[i]);
            out.print(' ');
        }
        out.println();
    }

    private int countCycles(int[] array) {
        boolean[] visited = new boolean[array.length];
        int cycles = 0;
        for (int i = 1; i < visited.length; ++i) {
            if (!visited[i]) {
                ++cycles;
                int ptr = i;
                while (!visited[ptr]) {
                    visited[ptr] = true;
                    ptr = array[ptr];
                }
            }
        }
        return cycles;
    }
}
