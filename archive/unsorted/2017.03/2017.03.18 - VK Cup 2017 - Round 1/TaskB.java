package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int window = in.nextInt();
        String[] names = new String[length];
        int qty = 1;
        for (int i = 0; i < window - 1; ++i) {
            names[i] = getName(qty++);
        }
        for (int i = window - 1; i < length; ++i) {
            String input = in.next();
            if (input.equals("YES")) {
                names[i] = getName(qty++);
            } else if (input.equals("NO")) {
                names[i] = names[i - window + 1];
            }
        }
        for (String name : names) {
            out.print(name + " ");
        }
    }

    private String getName(int hash) {
        StringBuilder builder = new StringBuilder();
        int x = 'A';
        while (hash > 0) {
            builder.append((char) (x + hash % 26));
            x = 'a';
            hash /= 26;
        }
        return builder.toString();
    }
}
