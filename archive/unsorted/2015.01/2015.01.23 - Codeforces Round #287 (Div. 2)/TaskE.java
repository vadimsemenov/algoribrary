package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int[] v = new int[edges];
        int[] u = new int[edges];
        int[] worked = new int[edges];
        List<Integer>[] outgoing = new List[vertices];
        for (int i = 0; i < vertices; ++i) outgoing[i] = new ArrayList<>();
        int good = 0;
        for (int e = 0; e < edges; ++e) {
            u[e] = in.nextInt() - 1;
            v[e] = in.nextInt() - 1;
            worked[e] = in.nextInt();
            if (worked[e] == 1) good++;
            outgoing[v[e]].add(e);
            outgoing[u[e]].add(e);
        }
        int[] queue = new int[vertices];
        int head = 0;
        int tail = 0;
        queue[tail++] = 0;
        int[] distanceTo = new int[vertices];
        int[] bfsDistance = new int[vertices];
        int[] pred = new int[vertices];
        Arrays.fill(pred, -1);
        Arrays.fill(bfsDistance, Integer.MAX_VALUE);
        Arrays.fill(distanceTo, Integer.MAX_VALUE);
        pred[0] = -2;
        bfsDistance[0] = 0;
        distanceTo[0] = 0;
        while (head < tail) {
            int current = queue[head++];
            for (int e : outgoing[current]) {
                int next = current ^ v[e] ^ u[e];
                if (pred[next] == -1) {
                    bfsDistance[next] = 1 + bfsDistance[current];
                    distanceTo[next] =  distanceTo[current] + 1 - worked[e];
                    pred[next] = e;
                    queue[tail++] = next;
                } else if (1 + bfsDistance[current] == bfsDistance[next]) {
                    if (distanceTo[next] > distanceTo[current] + 1 - worked[e]) {
                        distanceTo[next] = distanceTo[current] + 1 - worked[e];
                        pred[next] = e;
                    }
                }
            }
        }
        boolean[] need = new boolean[edges];
        int current = vertices - 1;
        int reallyGood = 0;
        while (current != 0) {
            need[pred[current]] = true;
            if (worked[pred[current]] == 1) reallyGood++;
            current = current ^ v[pred[current]] ^ u[pred[current]];
        }
        out.println((bfsDistance[vertices - 1] - reallyGood) + (good - reallyGood));
        for (int i = 0; i < edges; ++i) {
            if ((need[i] && worked[i] == 0) || (!need[i] && worked[i] == 1)) {
                out.println((u[i] + 1) + " " + (v[i] + 1) + " " + (1 - worked[i]));
            }
        }
    }
}
