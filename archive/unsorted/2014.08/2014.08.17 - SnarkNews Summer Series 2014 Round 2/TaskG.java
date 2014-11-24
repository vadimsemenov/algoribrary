package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskG {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] path = in.next().toCharArray();
        int[] stack = new int[path.length];
        int[] code = new int[256];
        code['u'] = 1;
        code['d'] = -1;
        code['r'] = 2;
        code['l'] = -2;
        int top = 0;
        for (char current : path) {
            if (top > 0 && stack[top - 1] == code[current]) top--;
            else stack[top++] = -code[current];
        }
        out.println(top == 0 ? "Yes" : "No");
    }
}
