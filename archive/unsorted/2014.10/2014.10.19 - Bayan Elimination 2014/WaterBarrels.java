package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class WaterBarrels {
    private List<Integer>[] graph;
    private int[] x, y, h;
    private long[] answer;
    private boolean[] visited;
    private long currentTime;


    public void solve(int testNumber, InputReader in, PrintWriter out) {
        currentTime = 0;
        int barrels = in.nextInt();
        int pipes = in.nextInt();
        graph = new List[barrels];
        for (int i = 0; i < barrels; ++i) {
            graph[i] = new ArrayList<>();
        }
        x = new int[pipes];
        y = new int[pipes];
        h = new int[pipes];
        for (int i = 0; i < pipes; ++i) {
            x[i] = in.nextInt() - 1;
            y[i] = in.nextInt() - 1;
            h[i] = in.nextInt();
        }
        Integer[] order = new Integer[pipes];
        for (int i = 0; i < pipes; ++i) {
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return h[o1] - h[o2];
            }
        });
        toAdd = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return h[o1] - h[o2];
            }
        });
        visited = new boolean[barrels];
        visited[0] = true;
        answer = new long[barrels];
        Arrays.fill(answer, -1);
        answer[0] = 0;
        int all = 1;
        int prevHeight = 0;
        for (int i : order) {
            if (!toAdd.isEmpty()) {
                throw new AssertionError(testNumber + " toAdd must be empty!");
            }
            graph[x[i]].add(i);
            graph[y[i]].add(i);
            currentTime += all * (h[i] - prevHeight);
            // if (currentTime < 0) throw new AssertionError(testNumber + ":1 " + currentTime + " " + all + " " + h[i] + " " + prevHeight);
            prevHeight = h[i];
            if (visited[x[i]] && visited[y[i]]) continue;
            if (visited[x[i]]) {
                all += go(y[i], i);
            } else if (visited[y[i]]) {
                all += go(x[i], i);
            }
        }
        System.err.println(testNumber + " " + Arrays.toString(answer));
        out.println("Case #" + testNumber + ":");
        for (long i : answer) {
            if (i == -1) {
                throw new AssertionError(testNumber + " " + Arrays.toString(answer));
            }
            out.print(i);
            out.print(' ');
        }
        out.println();
    }

    private TreeSet<Integer> toAdd;
    private int go(int current, int inID) {
        answer[current] = currentTime;
        visited[current] = true;
        int subTree = 1;
        int prevHeight = 0;
        for (int edge : graph[current]) {  // h[i] < h[i + 1]!!!

            if (h[edge] > h[inID]) {
                toAdd.add(edge);
                continue;
            }

            while (!toAdd.isEmpty() && h[edge] > h[toAdd.first()]) {
                int e = toAdd.first();
                toAdd.remove(e);
                if (visited[x[e]] && visited[y[e]]) continue;
                int to;
                if (visited[x[e]]) {
                    to = y[e];
                } else if (visited[y[e]]) {
                    to = x[e];
                } else {
                    throw new AssertionError(e + " was added incorrect");
                }
                currentTime += subTree * (h[e] - prevHeight);
                // if (currentTime < 0) throw new AssertionError(":2 " + currentTime + " " + subTree + " " + h[e] + " " + prevHeight + " " + h[edge]);
                prevHeight = h[e];
                subTree += go(to, e);
            }

            if (edge == inID) continue;


            int to = current ^ x[edge] ^ y[edge];
            if (visited[to]) continue;
            currentTime += subTree * (h[edge] - prevHeight);
            // if (currentTime < 0) throw new AssertionError(":3 " + currentTime + " " + subTree + " " + h[edge] + " " + prevHeight);

            prevHeight = h[edge];
            subTree += go(to, edge);
        }
        currentTime += subTree * (h[inID] - prevHeight);
        return subTree;
    }
}
