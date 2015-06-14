package algoribrary.experemental;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class TarjanFB {
    static class Edge {
        int begin, end;
        int weight;

        public Edge(int begin, int end, int weight) {
            this.begin = begin;
            this.end = end;
            this.weight = weight;
        }
    }

    public long[] process(List<Edge>[] graph, int source) {
        long[] distance = new long[graph.length];
        Arrays.fill(distance, Long.MAX_VALUE / 3);
        distance[source] = 0;
        int[] firstChild = new int[graph.length];
        int[] nextSibling = new int[graph.length];
        Arrays.fill(firstChild, -1);
        Arrays.fill(nextSibling, -1);
        int[] queue = new int[graph.length];
        int head = 0;
        int tail = 0;
        queue[tail++] = source;
        while (head < tail) {
            int current = queue[head++];
            for (Edge edge : graph[current]) {
                int next = current ^ edge.begin ^ edge.end;
                long newDistance = distance[current] + edge.weight;
                if (newDistance < distance[next]) {

                }
            }
        }
        return distance;
    }
}
