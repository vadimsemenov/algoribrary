package task;

import algoribrary.io.InputReader;
import net.egork.chelper.tester.Interactor;
import net.egork.chelper.tester.Verdict;
import net.egork.chelper.tester.State;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class TaskGInteractor {
    private static final long MIN = 1;
    private static final long MAX = 10004205361450474L;

    public Verdict interact(InputStream input, InputStream solutionOutput, OutputStream solutionInput, State<Boolean> state) {
        long n = new InputReader(input).nextLong();
        InputReader in = new InputReader(solutionOutput);
        PrintWriter out = new PrintWriter(solutionInput, true);
        for (int queriesLeft = 5; queriesLeft > 0; --queriesLeft) {
            try {
                if (!in.hasNext()) return Verdict.WA;
            } catch (RuntimeException e) {
                return Verdict.WA;
            }
            int size = in.nextInt();
            long[] numbers = in.nextLongArray(size);
            long prev = MIN - 1;
            for (long number : numbers) {
                if (prev >= number) {
                    out.println(-2);
                    return Verdict.WA;
                }
                prev = number;
            }
            if (prev > MAX) {
                out.println(-2);
                return Verdict.WA;
            }

            prev = MIN - 1;
            int idx = 0;
            for (long number : numbers) {
                if (number == n) {
                    out.println(-1);
                    return Verdict.OK;
                }
                if (prev < n && n < number) {
                    break;
                }
                idx++;
            }
            out.println(idx);
        }
        return Verdict.WA;
    }
}
