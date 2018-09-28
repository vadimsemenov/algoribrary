package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.function.Predicate;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        String password = in.next();
        if (!ok(password)) {
            outer:
            for (int len = 1; len < 4; ++len) {
                for (int i = 0; i + len <= password.length(); ++i) {
                    for (int mask = 0; mask < (1 << 3); ++mask) {
                        if (Integer.bitCount(mask) != len) continue;
                        String tmp = password.substring(0, i) + generate(mask) + password.substring(i + len);
                        if (ok(tmp)) {
                            password = tmp;
                            break outer;
                        }
                    }
                }
            }
        }
        out.println(password);
    }

    private boolean ok(String password) {
        return contains(password, Character::isLowerCase) && contains(password, Character::isUpperCase) && contains(password, Character::isDigit);
    }

    private String generate(int mask) {
        StringBuilder result = new StringBuilder(Integer.bitCount(mask));
        if ((mask & 1) != 0) {
            result.append('a');
        }
        if (((mask >>> 1) & 1) != 0) {
            result.append('A');
        }
        if (((mask >>> 2) & 1) != 0) {
            result.append('1');
        }
        return result.toString();
    }

    private boolean contains(String s, Predicate<Character> predicate) {
        for (int i = 0; i < s.length(); ++i) {
            if (predicate.test(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
