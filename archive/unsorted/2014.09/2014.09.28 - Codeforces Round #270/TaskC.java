package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        String[] firstName = new String[counter];
        String[] secondName = new String[counter];
        for (int i = 0; i < counter; ++i) {
            firstName[i] = in.next();
            secondName[i] = in.next();
        }
        int[] permutation = new int[counter];
        for (int i = 0; i < counter; ++i) {
            permutation[i] = in.nextInt() - 1;
        }
        boolean canFirst = true;
        boolean canSecond = true;
        for (int i = 1; i < permutation.length; ++i) {
            int idx = permutation[i];
            int pre = permutation[i - 1];
            boolean nf = (canFirst && firstName[idx].compareTo(firstName[pre]) > 0) || (canSecond && firstName[idx].compareTo(secondName[pre]) > 0);
            boolean ns = (canFirst && secondName[idx].compareTo(firstName[pre]) > 0) || (canSecond && secondName[idx].compareTo(secondName[pre]) > 0);
            canFirst = nf;
            canSecond = ns;
            if (!canFirst && !canSecond) break;
        }
        out.println((canFirst || canSecond) ? "YES" : "NO");
    }
}
