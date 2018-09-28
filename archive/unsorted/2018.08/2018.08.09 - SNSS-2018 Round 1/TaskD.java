package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int[] parents = new int[vertices];
        int[] character = new int[vertices];
        parents[0] = -1;
        int[] children = new int[vertices];
        for (int i = 1; i < vertices; ++i) {
            parents[i] = in.nextInt() - 1;
            character[i] = in.next().charAt(0);
            children[parents[i]]++;
        }
        int[][] candidates = new int[vertices][];
        for (int i = 0; i < vertices; ++i) {
            candidates[i] = new int[children[i]];
        }
        int[] candidatesPtr = new int[vertices];
        int[] next = new int[vertices];
        int[] depth = new int[vertices];
        int[] finish = new int[vertices];
        int[] queue = new int[vertices];
        int head = 0;
        int tail = 0;
        for (int i = 0; i < vertices; ++i) {
            if (children[i] == 0) {
                queue[tail++] = i;
            }
        }
        int[] child = new int[vertices];
        while (head < tail) {
            int vertex = queue[head++];
            if (candidatesPtr[vertex] == 0) {
                finish[vertex] = vertex;
            } else {
                int[] layer = candidates[vertex];
                int qty = candidatesPtr[vertex];
                System.arraycopy(layer, 0, child, 0, qty);
                int d = depth[vertex];
                while (qty > 1 && d --> 0) {
                    int max = Integer.MIN_VALUE;
                    for (int i = 0; i < qty; ++i) {
                        max = Math.max(max, character[layer[i]]);
                    }
                    int ptr = 0;
                    for (int i = 0; i < qty; ++i) {
                        if (character[layer[i]] == max) {
                            layer[ptr] = layer[i];
                            child[ptr] = child[i];
                            ptr++;
                        }
                    }
                    qty = ptr;
                    for (int i = 0; i < qty; ++i) {
                        layer[i] = next[layer[i]];
                    }
                }
                next[vertex] = child[0];
                if (qty > 1) {
                    for (int i = 1; i < qty; ++i) {
                        if (finish[next[vertex]] > finish[child[i]]) {
                            next[vertex] = child[i];
                        }
                    }
                }
                finish[vertex] = finish[next[vertex]];
            }

            int parent = parents[vertex];
            if (parent == -1) break; // root
            int parentDepth = depth[vertex] + 1;
            if (depth[parent] < parentDepth) {
                candidatesPtr[parent] = 0;
                depth[parent] = parentDepth;
            }
            if (depth[parent] <= parentDepth) {
                candidates[parent][candidatesPtr[parent]++] = vertex;
            }
            children[parent]--;
            if (children[parent] == 0) {
                queue[tail++] = parent;
            }
        }
        for (int v = 0; v < vertices; ++v) {
            out.println(finish[v] != v ? finish[v] + 1 : 0);
        }
    }
}
