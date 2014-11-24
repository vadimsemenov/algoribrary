package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        boolean odd = false;
        for (char ch : in.next().toCharArray()) {
            System.err.println(ch);
            if (ch == 'a' || ch == 'o' || ch == 'e' || ch == 'i' || ch == 'u') {
                odd = !odd;
            }
            System.err.println(odd);
        }
        out.println(odd ? "PESAR" : "DOKHTAR");
    }
}
