package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskE {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int need = in.nextInt();
        List<Integer> operators = new ArrayList<>();
        List<Integer> assistant = new ArrayList<>();
        List<Integer> both = new ArrayList<>();
        int[] cost = new int[counter];
        int[] experience = new int[counter];
        for (int i = 0; i < counter; ++i) {
            int type = in.nextInt();
            experience[i] = in.nextInt();
            cost[i] = in.nextInt();
            if (type == 1) {
                operators.add(i);
            } else if (type == 2) {
                assistant.add(i);
            } else if (type == 3) {
                both.add(i);
            } else throw new AssertionError("WTF? " + type);
        }
        Comparator<Integer> byCost = (first, second) -> Integer.compare(cost[first], cost[second]);
        Collections.sort(operators, byCost);
        Collections.sort(assistant, byCost);
        Collections.sort(both, byCost);
        
        int verticesQty = 0;
        int edgesQty = 0;
        int source = verticesQty++;
        int auxSource = verticesQty++;
        ++edgesQty;
        verticesQty += 2 * counter;
    }
}
