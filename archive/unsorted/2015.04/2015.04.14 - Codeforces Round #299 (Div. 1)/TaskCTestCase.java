package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TaskCTestCase {
    @TestCase
    public Collection<Test> createTests() {
        final int tests = 100;
        final int counter = 10000;
        final int max = 100;
        final Random rng = new Random(58);
        List<Test> retval = new ArrayList<>();
        for (int test = 0; test < tests; ++test) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(counter);
            stringBuilder.append('\n');
            for (int i = 0; i < counter; ++i) {
                stringBuilder.append(rng.nextInt(max) + 1);
                stringBuilder.append(' ');
                stringBuilder.append(rng.nextInt(max) + 1);
                stringBuilder.append('\n');
            }
            retval.add(new Test(stringBuilder.toString()));
        }
        return retval;
    }
}
