package tasks;

import java.util.*;

public class Egalitarianism3 {
    static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    List<Edge>[] tree;
    public int maxCities(int n, int[] a, int[] b, int[] len) {
        tree = new List[n];
        for (int i = 0; i < n; ++i) tree[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; ++i) {
            tree[a[i] - 1].add(new Edge(b[i] - 1, len[i]));
            tree[b[i] - 1].add(new Edge(a[i] - 1, len[i]));
        }
        int answer = Math.min(n, 2);
        for (int v = 0; v < n; ++v) {
            HashMap<Integer, Integer> result = new HashMap<>();
            for (Edge edge : tree[v]) {
                HashSet<Integer> set = dfs(edge.to, v);
                for (int i : set) {
                    Integer tmp = result.get(i + edge.weight);
                    if (tmp == null) tmp = 0;
                    result.put(i + edge.weight, tmp + 1);
                    answer = Math.max(answer, tmp + 1);
                }
            }
        }
        return answer;
    }

    private HashSet<Integer> dfs(int v, int parent) {
        HashSet<Integer> result = new HashSet<>();
        result.add(0);
        for (Edge e : tree[v]) if (e.to != parent) {
            HashSet<Integer> set = dfs(e.to, v);
            for (int key : set) {
                result.add(key + e.weight);
            }
        }
        return result;
    }
}
