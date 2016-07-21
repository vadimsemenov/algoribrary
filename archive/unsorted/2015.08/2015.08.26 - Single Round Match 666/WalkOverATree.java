package tasks;

public class WalkOverATree {
    public int maxNodesVisited(int[] _parent, int L) {
        int[] parent = new int[_parent.length + 1];
        System.arraycopy(_parent, 0, parent, 1, _parent.length);
        parent[0] = -1;
        int[] longest = new int[parent.length];
        for (int v = parent.length - 1; v > 0; --v) {
            longest[parent[v]] = Math.max(longest[parent[v]], 1 + longest[v]);
        }
        int x = longest[0];
        if (x >= L) {
            return L + 1;
        }
        return 1 + x + Math.min(parent.length - x - 1, (L - x) / 2);
    }
}
