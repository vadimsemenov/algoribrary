package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scarecrow {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int length = in.nextInt();
        List<Integer>[] array = new List[length];
        for (int i = 0; i < length; ++i) array[i] = new ArrayList<>();
        for (int i = 0; i < counter; ++i) {
            array[i % length].add(in.nextInt());
        }
        for (List<Integer> list : array) Collections.sort(list);
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < counter; ++i) {
            int cur = array[i % length].get(i / length);
            if (cur < prev) {
                out.println("NO");
                return;
            }
            prev = cur;
        }
        out.println("YES");
    }
}
