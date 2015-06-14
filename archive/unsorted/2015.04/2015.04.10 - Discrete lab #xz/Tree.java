package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.SuffixTree;

import java.io.PrintWriter;

public class Tree {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        String text = in.next();
        SuffixTree suffixTree = new SuffixTree(text.toCharArray(), 'z' - 'a' + 1, 'a');
        out.println(suffixTree.vertices + " " + (suffixTree.vertices - 1));
        for (int v = 0; v < suffixTree.vertices; ++v) {
            for (int edge = 0; edge < suffixTree.outgoing[v].length; ++edge) {
                if (suffixTree.outgoing[v][edge] != 0) {
                    int parent = v + 1;
                    int child = suffixTree.outgoing[v][edge];
                    int begin = suffixTree.begin[child] + 1;
                    int end = Math.min(suffixTree.begin[child] + suffixTree.length[child], text.length());
                    ++child;
                    out.println(parent + " " + child + " " + begin + " " + end);
                }
            }
        }
    }
}
