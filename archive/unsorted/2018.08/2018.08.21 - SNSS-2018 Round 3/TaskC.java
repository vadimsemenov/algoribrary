package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        String input = in.next();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == '9') continue;
            for (int j = 0; j < input.length(); ++j) {
                if (input.charAt(j) == '0' || i == j) continue;
                for (int ii = 0; ii < 2; ++ii) {
                    if (input.charAt(i) != '0' && ii == 0) continue;
                    for (int jj = 0; jj < 2; ++jj) {
                        if (input.charAt(j) != '9' && jj == 0) continue;
                        if (input.charAt(i) + ii <= '9' && input.charAt(j) - jj >= '0') {
                            StringBuilder number = new StringBuilder(input);
                            number.setCharAt(i, (char) (input.charAt(j) - jj));
                            number.setCharAt(j, (char) (input.charAt(i) + ii));
                            if (eq(encode(number, i, j), input)) {
                                number.setCharAt(i, (char) (input.charAt(j) - jj));
                                number.setCharAt(j, (char) (input.charAt(i) + ii));
                                result.add(number.toString());
                            }
                        }
                    }
                }
            }
        }
        if (result.isEmpty()) {
            out.println("-1");
        } else {
            Collections.sort(result);
            for (String res : result) {
                out.println(res);
            }
        }
    }

    private boolean eq(CharSequence fst, CharSequence snd) {
        if (fst.length() != snd.length()) return false;
        for (int i = 0; i < fst.length(); ++i) {
            if (fst.charAt(i) != snd.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private CharSequence encode(StringBuilder origin, int x, int y) {
        int min = 0;
        int max = origin.length() - 1;
        for (int i = 0; i < origin.length(); ++i) {
            if (origin.charAt(i) < origin.charAt(min)) {
                min = i;
            }
            if (origin.charAt(i) >= origin.charAt(max)) {
                max = i;
            }
        }
        if (x != min || y != max) return "";
        char minChar = origin.charAt(min);
        char maxChar = origin.charAt(max);
        if (maxChar > '0') maxChar--;
        if (minChar < '9') minChar++;
        origin.setCharAt(min, maxChar);
        origin.setCharAt(max, minChar);
        return origin;
    }
}
