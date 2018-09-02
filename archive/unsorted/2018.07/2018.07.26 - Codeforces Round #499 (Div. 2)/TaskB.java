package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int expeditors = in.nextInt();
        int types = in.nextInt();
        int[] counts = new int[101];
        for (int i = 0; i < types; ++i) {
            counts[in.nextInt()]++;
        }
        int max = Arrays.stream(counts).max().getAsInt();
        int days;
        for (days = max; days > 0; --days) {
            int count = 0;
            for (int c : counts) {
                count += c / days;
            }
            if (count >= expeditors) {
                break;
            }
        }
        out.println(days);
    }
}
