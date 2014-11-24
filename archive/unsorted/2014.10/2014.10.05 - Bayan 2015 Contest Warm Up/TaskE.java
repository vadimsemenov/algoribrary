package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskE {
    List<Set<Vertex>> graph;
    Vertex[] dots;
    int vertices, edges;
    int nextId;

    static class Vertex {
        int id;
        int plus;
        int cnt;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            Vertex vertex = (Vertex) o;

            if (cnt != vertex.cnt) {
                return false;
            }
            if (id != vertex.id) {
                return false;
            }
            if (plus != vertex.plus) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + plus;
            result = 31 * result + cnt;
            return result;
        }

        Vertex(int id, int plus, int cnt) {
            this.id = id;
            this.plus = plus;
            this.cnt = cnt;
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        vertices = in.nextInt();
        edges = in.nextInt();
        graph = new ArrayList<>(vertices);
        dots = new Vertex[vertices];
        for (int i = 0; i < vertices; ++i) {
            dots[i] = new Vertex(nextId++, 1, 1);
            graph.set(i, new HashSet<Vertex>());
        }

        int[] degree = new int[vertices];
        for (int i = 0; i < edges; ++i) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;

            graph.get(v).add(dots[u]);
            graph.get(u).add(dots[v]);
            degree[v]++;
            degree[u]++;
        }
        int[] cycle  = findCycle();
        Vertex compressed = new Vertex(nextId++, 0, 0);
        for (int i : cycle) {
            compressed.cnt += dots[i].cnt;
            compressed.plus += dots[i].plus;
        }
        for (int i = 0; i < cycle.length; ++i) {
            for (int j = i + 1; j < cycle.length; ++j) {
                compressed.plus += dots[i].cnt * dots[j].cnt;
            }
        }
        for (int i = 0; i < cycle.length; ++i) {

        }
    }

    private int[] findCycle() {

        return null;
    }
}
