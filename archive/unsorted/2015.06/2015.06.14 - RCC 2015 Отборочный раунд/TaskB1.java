package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskB1 {
    private static class Component {
        List<Integer> boys = new ArrayList<>();
        int leftSize, rightSize, leftColor;
    }

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int[] desiredTeam = in.nextIntArray(vertices);
        List<Integer>[][] graph = new List[2][vertices];
        for (int it = 0; it < 2; ++it) for (int i = 0; i < vertices; ++i) {
            graph[it][i] = new ArrayList<>();
        }
        for (int e = 0; e < edges; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            int type = in.nextInt();
            graph[type][u].add(v);
            graph[type][v].add(u);
        }
        int[] team = new int[vertices];
        int[] queue = new int[vertices];
        List<Component> components = new ArrayList<>();
        for (int i = 0; i < vertices; ++i) {
            if (team[i] != 0) continue;
            Component component = new Component();
            int head = 0;
            int tail = 0;
            queue[tail++] = i;
            team[i] = 1;
            while (head < tail) {
                int current = queue[head++];
                component.boys.add(current);
                if (team[current] == 1) ++component.leftSize; else
                if (team[current] == 2) ++component.rightSize; else
                    throw new AssertionError(team[current]);
                if (desiredTeam[current] != 0) {
                    if (component.leftColor == 0) component.leftColor = team[current] == 1 ? desiredTeam[current] : 3 - desiredTeam[current]; else
                    if ((team[current] == 1 && component.leftColor != desiredTeam[current]) ||
                            (team[current] == 2 && component.leftColor == desiredTeam[current])) {
                        out.println("NO");
                        return;
                    }
                }
                for (List<Integer>[] part : graph) for (int he : part[current]) {
                    int hisTeam = (part == graph[0]) ? team[current] : 3 - team[current];
                    if (team[he] == 0) {
                        team[he] = hisTeam;
                        queue[tail++] = he;
                    } else if (team[he] != hisTeam) {
                        out.println("NO");
                        return;
                    }
                }
            }
            components.add(component);
        }
        final int ZERO = (vertices + 1) / 2;
        int[][] dp = new int[components.size() + 1][2 * ZERO + 1];
        dp[0][ZERO] = 1;
        for (int i = 0; i < components.size(); ++i) {
            Component component = components.get(i);
            for (int w = 0; w < dp[i].length; ++w) if (dp[i][w] != 0) {
                for (int mul = -1; mul < 2; mul += 2) {
                    if ((component.leftColor == 1 && mul == -1) || (component.leftColor == 2 && mul == 1)) continue;
                    int ww = w + mul * (component.leftSize - component.rightSize);
                    if (0 <= ww && ww < dp[i].length) dp[i + 1][ww] = mul;
                }
            }
        }
        if (dp[components.size()][ZERO] == 0) {
            out.println("NO");
            return;
        }
        int cur = ZERO;
        for (int i = components.size(); i > 0; --i) {
            if (dp[i][cur] == -1) for (int id : components.get(i - 1).boys) team[id] = 3 - team[id];
            else if (dp[i][cur] != 1) throw new AssertionError("WTF?!");
            cur -= dp[i][cur] * (components.get(i - 1).leftSize - components.get(i - 1).rightSize);
        }
        out.println("YES");
        for (int no : team) out.print(no + " ");
        out.println();
    }
}
