package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskH {
    static class Edge {
        int begin, end, lenght;

        public Edge(int begin, int end, int lenght) {
            this.begin = begin;
            this.end = end;
            this.lenght = lenght;
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int start = in.nextInt() - 1;
        int finish = in.nextInt() - 1;
        List<Edge> list = new ArrayList<>();
        for (int e = 0; e < edges; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            int w = in.nextInt();
            list.add(new Edge(u, v, w));
            list.add(new Edge(v, u, w));
        }
        long goodCounter = in.nextInt();
        boolean[] good = new boolean[vertices];
        for (int i = 0; i < goodCounter; ++i) {
            good[in.nextInt() - 1] = true;
        }
        long[][][] distances = bellmanFord(start, vertices, list, good);
        long answer = solve(start, finish, distances[0][finish], distances[1][finish]);
        if (answer == Long.MAX_VALUE) {
            out.println("Infinity");
        } else if (answer == -1) {
            out.println("Impossible");
        } else {
            out.println(answer);
        }
    }

    private long solve(int start, int finish, long[] good, long[] bad) {
        int firstGood = -1;
        int firstBad = -1;
        for (int i = 0; i < good.length; ++i) {
            if (firstGood == -1 && good[i] != -1) {
                firstGood = i;
            }
            if (firstBad == -1 && bad[i] != -1) {
                firstBad = i;
            }
        }
        if (firstGood == -1) {
            return -1;
        } // now firstGood != -1
        if (firstBad == -1 || firstGood < firstBad || (firstGood == firstBad && good[firstGood] < bad[firstBad])) {
            return Long.MAX_VALUE;
        }
        long answer = -1;
        for (int i = firstGood; i < good.length; ++i) {
            if (good[i] == -1) continue;
            if (bad[i] != -1 && good[i] >= bad[i]) continue;
            long upper = Long.MAX_VALUE;
            boolean passed = true;
            for (int j = firstBad; j < i; ++j) {
                if (bad[j] == -1) continue;
                if (bad[j] <= good[i]) {
                    passed = false;
                    break;
                } // now bad[j] > good[i] && i > j
                // ad * i + good[i] < ad * j + bad[j]
                // ad < (bad[j] - good[i]) / (i - j)
                long ad = (bad[j] - good[i]) / (i - j);
                if ((bad[j] - good[i]) % (i - j) == 0) ad--;
                upper = Math.min(upper, ad);
            }
            long lower = 0;
            for (int j = i + 1; j < bad.length; ++j) {
                if (bad[j] == -1) continue;
                if (good[i] <= bad[j]) continue;
                // now good[i] > bad[j] && i < j
                // ad * i + good[i] < ad * j + bad[j]
                // ad > (good[i] - bad[j]) / (j - i)
                long ad = (good[i] - bad[j]) / (j - i);
                if ((good[i] - bad[j]) % (j - i) == 0) ad++;
                lower = Math.max(lower, ad);
            }
            if (passed && lower <= upper && upper > answer) {
                if (upper == Long.MAX_VALUE) throw new AssertionError(answer);
                answer = upper;
            }
        }
        return answer;
    }

    private long[][][] bellmanFord(int start, int vertices, List<Edge> list, boolean[] good) {
        long[][][] distances = new long[2][vertices][vertices];
        for (long[][] dd : distances) for (long[] d : dd) Arrays.fill(d, -1);
        distances[0][start][0] = 0;
        for (int it = 1; it < vertices; ++it) {
            for (Edge edge : list) {
                if (distances[0][edge.begin][it - 1] != -1) {
                    long newDistance = distances[0][edge.begin][it - 1] + edge.lenght;
                    if (good[edge.begin] && good[edge.end]) {
                        if (distances[0][edge.end][it] == -1 || distances[0][edge.end][it] > newDistance) {
                            distances[0][edge.end][it] = newDistance;
                        }
                    } else {
                        if (distances[1][edge.end][it] == -1 || distances[1][edge.end][it] > newDistance) {
                            distances[1][edge.end][it] = newDistance;
                        }
                    }
                }
                if (distances[1][edge.begin][it - 1] != -1) {
                    long newDistance = distances[1][edge.begin][it - 1] + edge.lenght;
                    if (distances[1][edge.end][it] == -1 || distances[1][edge.end][it] > newDistance){
                        distances[1][edge.end][it] = newDistance;
                    }
                }
            }
        }
        return distances;
    }
}
