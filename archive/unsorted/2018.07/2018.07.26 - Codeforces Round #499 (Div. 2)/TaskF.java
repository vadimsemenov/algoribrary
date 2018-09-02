package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskF {
    int[][] tree;
    int[] parent;
    int[] values;
    int[][] answer;
    Type[] types;

    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        tree = new int[vertices][2];
        parent = new int[vertices];
        Arrays.fill(parent, -1);
        values = new int[vertices];
        Arrays.fill(values, -1);
        answer = new int[vertices][2];
        for (int i = 0; i < vertices; ++i) {
            Arrays.fill(answer[i], -1);
        }
        types = new Type[vertices];
        for (int vertex = 0; vertex < vertices; ++vertex) {
            types[vertex] = Type.parseType(in.next());
            if (types[vertex] == Type.IN) {
                values[vertex] = in.nextInt();
            } else if (types[vertex] == Type.NOT) {
                int input = in.nextInt() - 1;
                parent[input] = vertex;
                tree[vertex] = new int[]{input};
            } else {
                tree[vertex] = new int[2];
                for (int i = 0; i < 2; ++i) {
                    tree[vertex][i] = in.nextInt() - 1;
                    parent[tree[vertex][i]] = vertex;
                }
            }
        }
        int output = dfs(0);
        for (int vertex = 0; vertex < vertices; ++vertex) {
            if (values[vertex] == -1) continue;
            answer[vertex][values[vertex]] = output;
        }
        for (int vertex = 0; vertex < vertices; ++vertex) {
            if (types[vertex] == Type.IN) {
                out.print(findAnswer(parent[vertex], vertex, 1 - values[vertex]));
            }
        }
    }

    private int findAnswer(int vertex, int child, int childValue) {
        if (vertex == -1) {
            return childValue;
        }
        if (types[vertex] == Type.IN) {
            throw new IllegalStateException();
        }
        int vertexValue;
        if (types[vertex] == Type.NOT) {
            vertexValue = 1 - childValue;
        } else {
            int anotherChild = tree[vertex][0] ^ tree[vertex][1] ^ child;
            int anotherValue = values[anotherChild];
            vertexValue = types[vertex].eval(childValue, anotherValue);
        }
        if (answer[vertex][vertexValue] == -1) {
            answer[vertex][vertexValue] = findAnswer(parent[vertex], vertex, vertexValue);
        }
        return answer[vertex][vertexValue];
    }

    private int dfs(int vertex) {
        if (values[vertex] != -1) {
            return values[vertex];
        }
        if (types[vertex] == Type.IN) {
            throw new IllegalStateException();
        }
        if (types[vertex] == Type.NOT) {
            return values[vertex] = 1 - dfs(tree[vertex][0]);
        }
        int fst = dfs(tree[vertex][0]);
        int snd = dfs(tree[vertex][1]);
        return values[vertex] = types[vertex].eval(fst, snd);
    }

    enum Type {
        AND,
        OR,
        XOR,
        NOT,
        IN;

        static Type parseType(String type) {
            switch (type) {
                case "AND": return AND;
                case "OR": return OR;
                case "XOR": return XOR;
                case "NOT": return NOT;
                case "IN": return IN;
            }
            throw new IllegalArgumentException(type);
        }

        int eval(int fst, int snd) {
            switch (this) {
                case AND: return fst & snd;
                case OR: return fst | snd;
                case XOR: return fst ^ snd;
            }
            throw new IllegalStateException();
        }
    }
}
