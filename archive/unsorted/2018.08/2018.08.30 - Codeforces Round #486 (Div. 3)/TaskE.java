package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        String number = in.next();
        if (number.length() == 1) {
            out.println(-1);
        } else {
            int answer = Integer.MAX_VALUE;
            for (int last = 0; last < 100; last += 25) {
                String suffix = String.format("%02d", last);
                answer = Math.min(answer, makeSuffix(number, suffix));
            }
            out.println(answer == Integer.MAX_VALUE ? -1 : answer);
        }
    }

    private int makeSuffix(String number, String suffix) {
        assert suffix.length() == 2;
        if (number.length() == 3 && suffix.charAt(0) == '0') return Integer.MAX_VALUE;
        int[] lastPosition = new int[suffix.length()];
        Arrays.fill(lastPosition, -1);
        for (int i = number.length(); i --> 0; ) {
            for (int j = suffix.length(); j --> 0; ) {
                if (suffix.charAt(j) == number.charAt(i) && lastPosition[suffix.length() - 1 - j] == -1) {
                    lastPosition[suffix.length() - 1 - j] = i;
                    break;
                }
            }
        }
        for (int i = 0; i < suffix.length(); ++i) {
            if (lastPosition[i] == -1) {
                return Integer.MAX_VALUE;
            }
        }
        int answer = 0;
        for (int i = 0; i < lastPosition.length; ++i) {
            answer += number.length() - lastPosition[i] - 1;
            for (int j = 0; j < i; ++j) {
                if (lastPosition[j] > lastPosition[i]) {
                    answer--;
                }
            }
        }
        int next = 0;
        while (next < number.length() && contains(lastPosition, next)) {
            next++;
        }
        if (next == number.length()) return answer;
        int zeros = 0;
        while (next < number.length() && (number.charAt(next) == '0' || contains(lastPosition, next))) {
            if (number.charAt(next) == '0' && !contains(lastPosition, next)) {
                zeros++;
            }
            next++;
        }
        return zeros > 0 && next == number.length() ? Integer.MAX_VALUE : (answer + zeros);
    }

    private boolean contains(int[] set, int item) {
        for (int elem : set) {
            if (elem == item) {
                return true;
            }
        }
        return false;
    }
}
