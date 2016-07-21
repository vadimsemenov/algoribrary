package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class I18n {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        Map<Integer, Integer> foo = new HashMap<>();
        Set<String> was = new HashSet<>();
        while (in.hasNext()) {
            String current = in.next();
            int bar = hash(current);
            if (current.length() < 4) {
                out.print(current);
                out.print(' ');
            } else if (was.contains(current) && foo.get(bar) == 1) {
                out.print(current.charAt(0));
                out.print(current.length() - 2);
                out.print(current.charAt(current.length() - 1));
                out.print(' ');
//                foo.put(bar, foo.get(bar) + 1);
            } else {
                out.print(current);
                out.print(' ');
                was.add(current);
                Integer foobar = foo.get(bar);
                if (foobar == null) {
                    foobar = 0;
                }
                foo.put(bar, foobar + 1);
            }
        }
        out.println();
    }

    int hash(String s) {
        return ((s.charAt(0) - 'a') * 26 + s.charAt(s.length() - 1) - 'a') * 100_000 + s.length() - 2;
    }
}
