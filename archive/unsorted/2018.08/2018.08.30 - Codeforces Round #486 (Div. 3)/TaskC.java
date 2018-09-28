package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        Map<Long, Node> have = new HashMap<>();
        for (int seqId = 0; seqId < counter; ++seqId) {
            int size = in.nextInt();
            int[] sequence = in.nextIntArray(size);
            long sum = 0;
            for (int num : sequence) sum += num;
            List<Node> nodes = new ArrayList<>(size);
            for (int elemId = 0; elemId < size; ++elemId) {
                long currentSum = sum - sequence[elemId];
                Node current = new Node(seqId + 1, elemId + 1, currentSum);
                Node prev = have.get(currentSum);
                if (prev != null) {
                    out.println("YES");
                    out.println(prev.sequenceId + " " + prev.elementId);
                    out.println(current.sequenceId + " " + current.elementId);
                    return;
                }
                nodes.add(current);
            }
            for (Node node : nodes) {
                have.put(node.sum, node);
            }
        }
        out.println("NO");
    }

    static class Node {
        final int sequenceId, elementId;
        final long sum;

        Node(int sequenceId, int elementId, long sum) {
            this.sequenceId = sequenceId;
            this.elementId = elementId;
            this.sum = sum;
        }
    }
}
