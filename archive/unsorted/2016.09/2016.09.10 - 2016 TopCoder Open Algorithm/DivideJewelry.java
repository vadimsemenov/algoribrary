package tasks;

import java.util.*;

public class DivideJewelry {
    public int[] divide(int[] x) {
        int[] result = new int[x.length];
        int max = 1_000_000;
        int[] id = new int[max + 1];
        Arrays.fill(id, -1);
        NavigableSet<Node> known = new TreeSet<>();
        for (int i = 0; i < x.length; ++i) {
            if (id[x[i]] == -1) {
                id[x[i]] = i;
            } else {
                result[i] = 1;
                result[id[x[i]]] = -1;
                return result;
            }
        }
        Integer[] order = new Integer[x.length];
        for (int i = 0; i < order.length; ++i) order[i] = i;
        Arrays.sort(order, (Integer fst, Integer snd) -> Integer.compare(x[fst], x[snd]));
        List<Node> toAdd = new ArrayList<>(known.size());
        for (int i : order) {
            Iterator<Node> it = known.iterator();
            toAdd.clear();
            toAdd.add(new Node(x[i], i));
            while (it.hasNext()) {
                Node node = it.next();
                if (node.sum == x[i]) {
                    return found(x, result, known, new Node(x[i], i), node);
                }
                Node nextNode = new Node(node.sum + x[i], i);
                Node floor = known.floor(nextNode);
                if (floor != null && floor.sum == nextNode.sum) {
                    // found
                    return found(x, result, known, nextNode, floor);
                }
                toAdd.add(nextNode);
            }
            known.addAll(toAdd);
        }
        return new int[0];
    }

    private int[] found(int[] x, int[] result, NavigableSet<Node> known, Node nextNode, Node floor) {
        Node current = nextNode;
        Node fake = new Node(current.sum, -1);
        while (fake.sum > 0) {
            fake.sum -= x[current.prev];
            result[current.prev] += 1;
            current = known.floor(fake);
            assert current == null || current.sum == fake.sum;
        }
        current = floor;
        fake = new Node(current.sum, -1);
        while (fake.sum > 0) {
            fake.sum -= x[current.prev];
            result[current.prev] -= 1;
            current = known.floor(fake);
            assert current == null || current.sum == fake.sum;
        }
        return result;
    }

    static class Node implements Comparable <Node> {
        int sum;
        final int prev;

        Node(int sum, int prev) {
            this.sum = sum;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            Node node = (Node) o;

            return sum == node.sum;

        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(sum, o.sum);
        }
    }

    public static void main(String[] args) {
        int max = 1_000_000;
        int qty = 1000;
        DivideJewelry solver = new DivideJewelry();
        while (true) {
            int[] xs = generate(max, qty);
            long start = System.currentTimeMillis();
            int[] res = solver.divide(xs);
            long millis = System.currentTimeMillis() - start;
            if (res.length == 0) {
                System.out.println("not found in " + millis + "ms.");
            } else {
                int fst = 0;
                int snd = 0;
                for (int i = 0; i < qty; ++i) {
                    if (res[i] == -1) {
                        fst += xs[i];
                    } else if (res[i] == 1) {
                        snd += xs[i];
                    } else if (res[i] != 0) {
                        System.out.println(Arrays.toString(xs));
                        throw new RuntimeException("oO" + res[i]);
                    }
                }
                if (fst != snd || fst == 0) {
                    System.out.println(Arrays.toString(xs));
                    throw new RuntimeException("POLUNDRA: " + fst + " " + snd);
                } else {
                    System.out.println("ok " + fst + " in " + millis + "ms.");
                }
            }
        }
    }

    static final Random rng = new Random(58);
    private static int[] generate(int max, int qty) {
        int[] xs = new int[qty];
        int min = max - qty * 20;
        HashSet<Integer> was = new HashSet<>();
        for (int i = 0; i < qty; ++i) {
            int x;
            do {
                x = rng.nextInt(max - min) + 1 + min;
            } while (was.contains(x));
            was.add(x);
            xs[i] = x;
        }
        return xs;
    }
}
