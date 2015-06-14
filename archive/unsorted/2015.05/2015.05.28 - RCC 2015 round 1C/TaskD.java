package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int length = in.nextInt();
        int counter = in.nextInt();
        char[] target = in.next().toCharArray();
        boolean[] turnOn = new boolean[counter];
        char[][] mask = new char[counter][];
        for (int i = 0; i < counter; ++i) {
            turnOn[i] = (1 == in.nextInt());
            mask[i] = in.next().toCharArray();
        }
        int[] badBits = new int[counter];
        List<Integer>[] badHere = new List[length];
        for (int i = 0; i < length; ++i) {
            badHere[i] = new ArrayList<>();
        }
        int[] queue = new int[counter];
        int head = 0;
        int tail = 0;
        for (int i = 0; i < counter; ++i) {
            char foo = turnOn[i] ? '0' : '1';
            for (int bit = 0; bit < length; ++bit) {
                if (mask[i][bit] == '1' && target[bit] == foo) {
                    badBits[i]++;
                    badHere[bit].add(i);
                }
            }
            if (badBits[i] == 0) {
                queue[tail++] = i;
            }
        }
        while (head < tail) {
            int current = queue[head++];
            char foo = turnOn[current] ? '1' : '0';
            char bar = turnOn[current] ? '0' : '1';
            for (int bit = 0; bit < length; ++bit) {
                if (mask[current][bit] == '1' && target[bit] == foo) {
                    target[bit] = '?';
                    for (int id : badHere[bit]) {
                        badBits[id]--;
                        mask[id][bit] = '?';
                        if (badBits[id] == 0) {
                            queue[tail++] = id;
                        }
                    }
                    badHere[bit].clear();
                } else if (mask[current][bit] == '1' && target[bit] == bar) {
                    throw new AssertionError("PALUNDRA!");
                }
            }
        }
        for (int bit = 0; bit < length; ++bit) {
            if (target[bit] == '1') {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
        out.println(tail);
        for (int i = tail - 1; i >= 0; --i) {
            if (i < tail - 1) out.print(' ');
            out.print(queue[i] + 1);
        }
        out.println();
    }
}
