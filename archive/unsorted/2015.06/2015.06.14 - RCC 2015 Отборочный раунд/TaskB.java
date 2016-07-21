package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int[] desiredTeam = in.nextIntArray(vertices);
        DSU friends = new DSU(vertices);
        List<Integer>[] enemies = new List[vertices];
        List<Integer>[] graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            enemies[i] = new ArrayList<>();
            graph[i] = new ArrayList<>();
        }
        for (int e = 0; e < edges; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            if (in.nextInt() == 0) {
                friends.unite(u, v);
            } else {
                enemies[u].add(v);
                enemies[v].add(u);
            }
        }
        int[] color = new int[vertices];
        for (int boy = 0; boy < vertices; ++boy) {
            int team = friends.get(boy);
            for (int enemy : enemies[boy]) {
                graph[team].add(friends.get(enemy));
            }
            color[team] |= desiredTeam[boy];
            if (color[team] > 2) {
                out.println("NO");
                return;
            }
        }
        int[] part = new int[vertices];
        int[] queue = new int[vertices];
        List<int[]> undefined = new ArrayList<>();
        List<List<Integer>> components = new ArrayList<>();
        int firstTeamSize = 0;
        int secondTeamSize = 0;
        for (int i = 0; i < vertices; ++i) {
            int team = friends.get(i);
            if (part[team] != 0) {
                continue;
            }
            List<Integer> component = new ArrayList<>();
            int inLeft = 0;
            int inRight = 0;
            int head = 0;
            int tail = 0;
            queue[tail++] = team;
            part[team] = 1;
            int foo = 0;
            while (head < tail) {
                int current = queue[head++];
                component.add(current);
                if (part[current] == 1) {
                    inLeft += friends.size[current];
                } else if (part[current] == 2) {
                    inRight += friends.size[current];
                } else throw new AssertionError(part[current]);
                if (color[current] != 0) {
                    if (foo == 0) {
                        foo = part[current] == 1 ? color[current] : 3 - color[current];
                    } else {
                        if ((part[current] == 1 && foo != color[current]) ||
                                (part[current] == 2 && foo == color[current])) {
                            out.println("NO");
                            return;
                        }
                    }
                }
                for (int to : graph[current]) {
                    if (part[to] == 0) {
                        part[to] = 3 - part[current];
                        queue[tail++] = to;
                    } else if (part[to] == part[current]) {
                        out.println("NO");
                        return;
                    }
                }
            }
            components.add(component);
            if (foo == 0) {
                undefined.add(new int[]{inLeft, inRight, components.size() - 1});
            } else {
                if (foo == 1) {
                    firstTeamSize += inLeft;
                    secondTeamSize += inRight;
                } else if (foo == 2) {
                    firstTeamSize += inRight;
                    secondTeamSize += inLeft;
                    for (int id : component) {
                        part[id] = 3 - part[id];
                    }
                } else throw new AssertionError(foo);
            }
        }
        int ZERO = Math.abs(firstTeamSize - secondTeamSize);
        for (int[] tuple : undefined) {
            ZERO += Math.abs(tuple[0] - tuple[1]);
        }
        int[][] dp = new int[undefined.size() + 1][2 * ZERO + 1];
        dp[0][ZERO + firstTeamSize - secondTeamSize] = 1;
        for (int i = 0; i < undefined.size(); ++i) {
            int fst = undefined.get(i)[0];
            int snd = undefined.get(i)[1];
            for (int w = 0; w < dp[i].length; ++w) if (dp[i][w] != 0) {
                int ww = w + fst - snd;
                if (ww >= 0 && ww < dp[i].length) {
                    dp[i + 1][ww] = 1;
                }
                ww = w - fst + snd;
                if (ww >= 0 && ww < dp[i].length) {
                    dp[i + 1][ww] = -1;
                }
            }
        }
        if (dp[undefined.size()][ZERO] == 0) {
            out.println("NO");
            return;
        }
        int cur = ZERO;
        for (int i = undefined.size(); i > 0; --i) {
            if (dp[i][cur] == 1) {
                firstTeamSize += undefined.get(i - 1)[0];
                secondTeamSize += undefined.get(i - 1)[1];
            } else if (dp[i][cur] == -1) {
                for (int id : components.get(undefined.get(i - 1)[2])) {
                    part[id] = 3 - part[id];
                }
                firstTeamSize += undefined.get(i - 1)[1];
                secondTeamSize += undefined.get(i - 1)[0];
            } else throw new AssertionError("WTF?!");
            cur -= dp[i][cur] * (undefined.get(i - 1)[0] - undefined.get(i - 1)[1]);
        }
        for (int v = 0; v < vertices; ++v) {
            part[v] = part[friends.get(v)];
        }
        if (firstTeamSize + secondTeamSize != vertices) {
            throw new AssertionError(firstTeamSize + " " + secondTeamSize + " " + vertices);
        }
        if (firstTeamSize != secondTeamSize) {
            out.println("NO");
            return;
        }
        out.println("YES");
        for (int team : part) {
            out.print(team);
            out.print(" ");
        }
        out.println();
    }

    static class DSU {
        int[] dad, size;

        DSU(int capacity) {
            dad = new int[capacity];
            size = new int[capacity];
            clear();
        }

        void clear() {
            for (int i = 0; i < dad.length; ++i) {
                dad[i] = i;
                size[i] = 1;
            }
        }

        int get(int v) {
            return v == dad[v] ? v : (dad[v] = get(dad[v]));
        }

        boolean unite(int u, int v) {
            u = get(u);
            v = get(v);
            if (u == v) return false;
            if (size[u] > size[v]) {
                int tmp = u; u = v; v = tmp;
            }
            size[v] += size[u];
            dad[u] = v;
            return true;
        }
    }
}
