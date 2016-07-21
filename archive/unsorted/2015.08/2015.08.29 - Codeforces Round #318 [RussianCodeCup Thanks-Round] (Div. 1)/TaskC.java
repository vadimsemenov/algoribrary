package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        List<Integer>[] tree = readTree(in);
        tree = compress1(tree);
        tree = compress2(tree);
        int current = -1;
        for (int i = 0; i < tree.length; ++i) if (tree[i].size() > 1) {
            int degree = tree[i].size();
            for (int j : tree[i]) if (tree[j].size() == 1) {
                --degree;
            }
            if (degree == 1) {
                current = i;
                break;
            }
        }
        boolean[] visited = new boolean[tree.length];
        while (current != -1) {
            visited[current] = true;
            int next = -1;
            for (int v : tree[current]) if (!visited[v]) {
                if (tree[v].size() == 1) {
                    visited[v] = true;
                } else if (next == -1) {
                    next = v;
                } else {
                    out.println("No");
                    return;
                }
            }
            current = next;
        }
        out.println("Yes");
    }

    private List<Integer>[] compress2(List<Integer>[] oldTree) {
        int oldVertices = oldTree.length;
        boolean[] removed = new boolean[oldVertices];
        for (int i = 0; i < oldVertices; ++i) {
            if (oldTree[i].size() == 3) {
                int[] degrees = new int[3];
                for (int j = 0; j < 3; ++j) {
                    degrees[j] = oldTree[oldTree[i].get(j)].size();
                }
                Arrays.sort(degrees);
                if (degrees[0] == 1 && degrees[1] == 1) {
                    int cur = 0;
                    for (int it = 0; it < 2; ++it) {
                        while (oldTree[oldTree[i].get(cur)].size() != 1) {
                            ++cur;
                        }
                        int rem = oldTree[i].get(cur++);
                        removed[rem] = true;
                    }
                }
            }
        }
        int[] id = new int[oldVertices];
        Arrays.fill(id, -1);
        int newVertices = 0;
        for (int i = 0; i < oldVertices; ++i) if (!removed[i]) {
            id[i] = newVertices++;
        }
        List<Integer>[] tree = new List[newVertices];
        for (int i = 0; i < newVertices; ++i) {
            tree[i] = new ArrayList<>();
        }
        for (int i = 0; i < oldVertices; ++i) if (!removed[i]) {
            for (int j : oldTree[i]) if (!removed[j]) {
                tree[id[i]].add(id[j]);
            }
        }
        return tree;
    }

    boolean[] removed;
    int[] id;
    List<Integer>[] oldTree, newTree;
    int newVertices;
    private List<Integer>[] compress1(List<Integer>[] _oldTree) {
        oldTree = _oldTree;
        int oldVertices = oldTree.length;
        newTree = new List[oldVertices];
        for (int i = 0; i < newTree.length; ++i) {
            newTree[i] = new ArrayList<>();
        }
        removed = new boolean[oldVertices];
        id = new int[oldVertices];
        for (int i = 0; i < oldVertices; ++i) {
            if (oldTree[i].size() == 1) {
                go(i, -1);
                return Arrays.copyOfRange(newTree, 0, newVertices);
            }
        }
        throw new AssertionError();
    }

    private int go(int v, int parent) {
        if (oldTree[v].size() == 2) {
            removed[v] = true;
            int ret = oldTree[v].get(0) ^ parent ^ oldTree[v].get(1);
            return go(ret, v);
        }
        id[v] = newVertices++;
        for (int to : oldTree[v]) if (to != parent) {
            int x = go(to, v);
            newTree[id[v]].add(x);
            newTree[x].add(id[v]);
        }
        return id[v];
    }

    private List<Integer>[] readTree(InputReader in) {
        int vertices = in.nextInt();
        List<Integer>[] tree = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            tree[i] = new ArrayList<>();
        }
        for (int i = 1; i < vertices; ++i) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        return tree;
    }
}
