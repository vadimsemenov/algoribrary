package tasks;

import java.util.ArrayList;
import java.util.List;

public class CloneGauntlet2 {
    public int minClones(int[] parent, int[] connection) {
        int vertices = connection.length;
        List<Integer>[] tree = new List[vertices];
        for (int i = 0; i < vertices; ++i) tree[i] = new ArrayList<>();
        for (int i = 0; i < parent.length; ++i) {
            tree[parent[i]].add(i + 1);
        }
        boolean[] was = new boolean[vertices];
        boolean[] available = new boolean[vertices];
        was[0] = available[0] = true;
        int[] queue = new int[vertices];
        int head = 0; int tail = 0;
        queue[tail++] = 0;
        while (head < tail) {
            int cur = queue[head++];
            for (int child : tree[cur]) {
                available[child] = true;
            }
            int next = connection[cur];
            if (!available[next] || was[next]) {
                return -1;
            }
            was[next] = true;
            if (next == vertices - 1) {
                break;
            }
            queue[tail++] = next;
        }
        int answer = tail;
        int cur = vertices - 1;
        while (cur != 0) {
            int next = parent[cur - 1];
            if (connection[next] != cur) {
                answer--;
            }
            cur = next;
        }
        return answer;
    }
}
