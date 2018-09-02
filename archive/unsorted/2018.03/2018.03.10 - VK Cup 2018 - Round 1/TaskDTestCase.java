package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class TaskDTestCase implements TestProvider {
    private static final int MAX_LENGTH = 16;

    private static List<Integer>[] graph = precalc(1 << MAX_LENGTH);

    private static List<Integer>[] precalc(int maxLength) {
        List<Integer>[] graph = new List[maxLength];
        for (int i = 0; i < graph.length; ++i) {
            graph[i] = new ArrayList<>();
        }
        return graph;
    }

    private Random rng = new Random(58);
    private double aProb = 0.5;

    @Override
    public Collection<Test> createTests() {
        int l1 = rng.nextInt(7) + 5;
        int l2 = rng.nextInt(7) + 5;
        int[] fst = generate(l1);
        int[] snd = generate(l2);

        return Collections.emptyList();
    }

    private int[] generate(int length) {
        int[] string = new int[length];
        for (int i = 0; i < length; ++i) {
            string[i] = rng.nextDouble() < aProb ? 0 : 1;
        }
        return string;
    }



    private static int encode(int[] string, int from, int to) {
        int code = 0;
        for (int i = from; i < to; ++i) {
            code = code * 4 + string[i] + 1;
        }
        return code;
    }
}
