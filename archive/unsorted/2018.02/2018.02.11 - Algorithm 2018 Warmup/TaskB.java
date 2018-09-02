package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        String input = in.next();
        String answer = null;
        for (int i = 0; i < input.length() - 1; ++i) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                String palindrome = input.substring(i, i + 2);
                if (answer == null || answer.compareTo(palindrome) > 0) {
                    answer = palindrome;
                }
            }
        }
        if (answer == null) {
            for (int i = 1; i < input.length() - 1; ++i) {
                if (input.charAt(i - 1) == input.charAt(i + 1)) {
                    String palindrome = input.substring(i - 1, i + 2);
                    if (answer == null || answer.compareTo(palindrome) > 0) {
                        answer = palindrome;
                    }
                }
            }
        }
        out.println(answer == null ? -1 : answer);
    }
}
