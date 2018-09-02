package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        int[] seq = in.nextIntArray(qty);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < seq.length; ++i) {
            seq[i] = map.computeIfAbsent(seq[i], k -> map.size());
        }
        int[] sa = StringUtils.buildSuffixArray(seq);
        int[] lcp = StringUtils.buildLCP(seq, sa);
        int answer = 0;
        for (int i = 0; i < qty - 1; ++i) {
            if (lcp[i] >= answer) {
                if (sa[i] + lcp[i] != qty && sa[i + 1] + lcp[i] != qty) {
                    answer = lcp[i] + 1;
                }
            }
        }
        out.println(answer);
    }
}