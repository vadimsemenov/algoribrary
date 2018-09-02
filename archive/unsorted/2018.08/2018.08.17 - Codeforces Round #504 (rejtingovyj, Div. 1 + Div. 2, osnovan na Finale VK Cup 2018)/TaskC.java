package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int length = in.nextInt();
        String sequence = in.next();
        int opened = 0;
        int closed = 0;
        for (int i = 0; i < sequence.length(); ++i) {
            if (sequence.charAt(i) == '(') {
                if (opened * 2 < length) {
                    out.print('(');
                    ++opened;
                }
            } else if (sequence.charAt(i) == ')') {
                if (opened > closed) {
                    out.print(')');
                    closed++;
                }
            } else throw new IllegalStateException("POLUNDRA");
        }
        if (opened != closed) throw new IllegalStateException("WTF");
        out.println();
    }
}
