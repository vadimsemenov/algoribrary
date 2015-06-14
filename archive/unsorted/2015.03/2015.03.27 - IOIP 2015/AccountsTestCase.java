package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class AccountsTestCase {
    @TestCase
    public Collection<Test> createTests() {
        final Collection<Test> ret = new ArrayList<>();
        final int limit = 2 * 100_000;
        final int accounts = 10_000;
        final int hints = 10_000;
        final Random peresadin = new Random(2539);
        final int[] permutation = new int[accounts];
        for (int i = 0; i < accounts; ++i) {
            permutation[i] = i;
        }
        long[] b = new long[accounts];
        long[] days = new long[hints];

        final int justRandomTests = 100;
        for (int testCase = 0; testCase < justRandomTests; ++testCase) {
            for (int i = 0; i < b.length; ++i) {
                b[i] = peresadin.nextInt(1_000_000_000);
            }
            for (int i = 0; i < days.length; ++i) {
                days[i] = peresadin.nextInt(1_000_000_000) + 1;
            }
            StringBuilder input = new StringBuilder();
            input.append(accounts);
            input.append(' ');
            input.append(hints);
            input.append('\n');
            int alreadyUsed = 0;
            for (int i = 0; i < hints; ++i) {
                int cnt = Math.max(Math.min(accounts, peresadin.nextInt(limit - alreadyUsed) + 1), 10);
                alreadyUsed = Math.min(limit, alreadyUsed + cnt);
                input.append(cnt);
                input.append('\n');
                for (int j = 0; j < cnt; ++j) {
                    int id = peresadin.nextInt(permutation.length - j);
                    input.append(permutation[id] + 1);
                    input.append(' ');
                    input.append(days[i] * b[permutation[id]]);
                    if (j + 1 < cnt) input.append(' ');
                    int tmp = permutation[id];
                    permutation[id] = permutation[permutation.length - 1 - j];
                    permutation[permutation.length - 1 - j] = tmp;
                }
                input.append('\n');
            }
            ret.add(new Test(input.toString()));
        }

//        final int trickyRandomTests = 0;

        return ret;
    }
}
