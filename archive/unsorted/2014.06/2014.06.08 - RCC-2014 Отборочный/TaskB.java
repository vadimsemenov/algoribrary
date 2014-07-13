package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        if (a > b || b > counter - 1) {
            out.println("IMPOSSIBLE");
        } else {
            int all = counter;
            counter = Math.min(counter, a + b);
                int inner = a + b - counter;
                List<Integer>[] result = new List[counter];
                for (int i = 0; i < counter; i++) result[i] = new ArrayList<>();
                for (int i = 0; i < inner; i++) {
                    result[i].add(i + 1);
                }
                for (int i = inner + 1; i < a; i++) {
                    result[i].add(i + (a - inner - 1));
                }
                for (int i = a + (a - inner - 1); i < counter; i++) {
                    result[inner].add(i);
                }
                for (List<Integer> list : result) {
                    out.print(list.size());
                    for (int i : list) out.print(" " + (i + 1));
                    out.println();
                }
            for (int i = counter; i < all; i++) out.println(0);
        }
    }
}
