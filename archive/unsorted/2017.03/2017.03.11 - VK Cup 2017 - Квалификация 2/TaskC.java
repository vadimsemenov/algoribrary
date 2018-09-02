package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskC {

    @SuppressWarnings("unchecked")
    public void solve(int __, InputReader in, PrintWriter out) {
        int courses = in.nextInt();
        int mainCourses = in.nextInt();
        int[] necessary = in.nextIntArray(mainCourses);
        List<Integer>[] dependencies = new List[courses];
        for (int i = 0; i < courses; ++i) {
            dependencies[i] = new ArrayList<>();
            int qty = in.nextInt();
            while (qty --> 0) {
                dependencies[i].add(in.nextInt() - 1);
            }
        }
        int[] color = new int[courses];
        List<Integer> order = new ArrayList<>();
        for (int course : necessary) {
            if (!dfs(course - 1, dependencies, color, order)) {
                out.println(-1);
                return;
            }
        }
        out.println(order.size());
        for (int i = 0; i < order.size(); ++i) {
            if (i > 0) out.print(' ');
            out.print(order.get(i) + 1);
        }
    }

    private boolean dfs(int course, List<Integer>[] dependencies, int[] color, List<Integer> order) {
        if (color[course] != 0) {
            return color[course] != 1; // cycle
        }
        ++color[course];
        for (int pred : dependencies[course]) {
            if (!dfs(pred, dependencies, color, order)) {
                return false;
            }
        }
        order.add(course);
        ++color[course];
        return true;
    }
}
