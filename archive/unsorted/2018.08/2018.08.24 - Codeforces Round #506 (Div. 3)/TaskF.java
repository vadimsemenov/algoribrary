package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        long a = in.nextLong();
        long b = in.nextLong();
        List<Long> as = getDivisors(a);
        List<Long> bs = getDivisors(b);
        List<Long> ss = getDivisors(a + b);
        out.println(2 * Math.min(solve(a + b, a, ss, as), solve(a + b, b, ss, bs)));
    }

    private long solve(long sum, long num, List<Long> sumDivs, List<Long> numDivs) {
        long answer = sum + 1;
        for (long div : sumDivs) {
            long anotherDiv = sum / div;
            assert anotherDiv >= div;
            int idx = Collections.binarySearch(numDivs, div);
            if (idx < 0) idx = -(idx + 2);
            assert idx < 0 || numDivs.get(idx) <= div;
            if (idx >= 0 && num / numDivs.get(idx) <= anotherDiv) {
                answer = Math.min(answer, div + anotherDiv);
            }
        }
        return answer;
    }

    private List<Long> getDivisors(long number) {
        List<Long> result = new ArrayList<>();
        for (long div = 1; div * div <= number; ++div) {
            if (number % div == 0) {
                result.add(div);
            }
        }
        return result;
    }
}
