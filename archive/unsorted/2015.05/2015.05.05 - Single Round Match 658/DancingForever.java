package tasks;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DancingForever {
    List<Integer>[] graph;
    int[] matching;
    boolean[] visited;

    public int[] getStablePairs(String x) {
        int n = 1;
        while (n * n < x.length()) ++n;
        graph = new List[n];
        matching = new int[n];
        Arrays.fill(matching, -1);
        visited = new boolean[n];
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; ++i) {
            graph[i] = new ArrayList<>();
            order[i] = i;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (x.charAt(i * n + j) == 'Y') {
                    graph[i].add(j);
                }
            }
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(graph[o1].size(), graph[o2].size());
            }
        });
        for (int i : order) {
            if (!dfs(i)) {
                boolean[] took = new boolean[n];
                int[] bfs = new int[n];
                int head = 0;
                int tail = 0;
                bfs[tail++] = i;
                int cnt = 0;
                while (head < tail) {
                    int current = bfs[head++];
                    for (int to : graph[current]) {
                        if (!took[to]) {
                            cnt++;
                            took[to] = true;
                            bfs[tail++] = matching[to];
                        }
                    }
                }
                int[] answer = new int[2 * cnt];
                cnt = 0;
                for (int v = 0; v < n; ++v) {
                    if (took[v]) {
                        answer[cnt++] = matching[v];
                        answer[cnt++] = v;
                    }
                }
                check(answer);
                return answer;
            } else {
                Arrays.fill(visited, false);
            }
        }
        int[] answer = new int[2 * n];
        for (int i = 0; i < n; ++i) {
            answer[2 * i] = matching[i];
            answer[2 * i + 1] = i;
        }
        check(answer);
        return answer;
    }

    private boolean dfs(int vertex) {
        if (visited[vertex]) return false;
        visited[vertex] = true;
        for (int to : graph[vertex]) {
            if (matching[to] == -1) {
                matching[to] = vertex;
                return true;
            }
        }
        for (int to : graph[vertex]) {
            if (dfs(matching[to])) {
                matching[to] = vertex;
                return true;
            }
        }
        return false;
    }

    private void check(int[] answer) {
        boolean[] male = new boolean[graph.length];
        boolean[] female = new boolean[graph.length];
        for (int i = 0; i < answer.length; i += 2) {
            if (male[answer[i]] || female[answer[i + 1]]) {
                throw new AssertionError("WTF?!");
            }
            male[answer[i]] = true;
            female[answer[i + 1]] = true;
        }
        for (int m = 0; m < graph.length; ++m) {
            if (male[m]) for (int f : graph[m]) {
                if (!female[f]) {
                    throw new AssertionError(":(");
                }
            }
        }
    }

    public static void main(String[] args) {
        final Random rng = new Random(58);
        int iter = 0;
        while (true) {
            if (++iter % 1000 == 0) System.err.println(iter + " proceeded");
            int n = rng.nextInt(100) + 1;
            char[] input = new char[n * n];
            for (int i = 0; i < input.length; ++i) {
                input[i] = rng.nextBoolean() ? 'Y' : 'N';
            }
            String string = new String(input);
            DancingForever solver = new DancingForever();
            try {
                solver.getStablePairs(string);
            } catch (AssertionError e) {
                System.out.println("Failed on input: \"" + string + "\"");
                return;
            }
        }
    }
}
