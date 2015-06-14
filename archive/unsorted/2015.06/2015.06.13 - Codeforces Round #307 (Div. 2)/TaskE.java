package tasks;


import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class TaskE {
    private static final int INF = 1_000_000_000 + 100500;
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {int counter = in.nextInt();
        int queries = in.nextInt();
        int[] array = in.nextIntArray(counter);
        final int SIZE_OF_BLOCK = 1111;
        final int BLOCKS = counter / SIZE_OF_BLOCK + 1;
        int[][] blocks = new int[BLOCKS][SIZE_OF_BLOCK];
        Set<Integer>[] elements = new Set[BLOCKS];
        for (int i = 0; i < elements.length; ++i) elements[i] = new HashSet<>();
        int[] ad = new int[BLOCKS];
        int[] size = new int[BLOCKS];
        int block = 0;
        for (int i = 0; i < counter; ++i) {
            blocks[block][size[block]] = array[i];
            ++size[block];
            if (size[block] == SIZE_OF_BLOCK || i == counter - 1) {
                resort(elements[block], 0, size[block], blocks[block]);
                ++block;
            }
        }
        for (int query = 0; query < queries; ++query) {
            final int type = in.nextInt();
            if (type == 1) {
                int from = in.nextInt() - 1;
                int to = in.nextInt() - 1;
                int delta = in.nextInt();
                int fromBlock = from / SIZE_OF_BLOCK;
                int toBlock = to / SIZE_OF_BLOCK;
                if (fromBlock == toBlock) {
                    block = fromBlock;
                    if (ad[block] != 0) {
                        for (int i = 0; i < size[block]; ++i) {
                            blocks[block][i] = Math.min(blocks[block][i] + ad[block], INF);
                        }
                        ad[block] = 0;
                    }
                    for (int i = from % SIZE_OF_BLOCK; i <= to % SIZE_OF_BLOCK; ++i) {
                        blocks[block][i] = Math.min(blocks[block][i] + delta, INF);
                    }
                    resort(elements[block], 0, size[block], blocks[block]);
                } else {
                    {
                        block = fromBlock;
                        for (int i = 0; i < size[block]; ++i) {
                            blocks[block][i] = Math.min(blocks[block][i] + ad[block], INF);
                        }
                        ad[block] = 0;
                        for (int i = from % SIZE_OF_BLOCK; i < size[block]; ++i) {
                            blocks[block][i] = Math.min(blocks[block][i] + delta, INF);
                        }
                        resort(elements[block], 0, size[block], blocks[block]);
                    }
                    {
                        block = toBlock;
                        for (int i = 0; i < size[block]; ++i) {
                            blocks[block][i] = Math.min(blocks[block][i] + ad[block], INF);
                        }
                        ad[block] = 0;
                        for (int i = 0; i <= to % SIZE_OF_BLOCK; ++i) {
                            blocks[block][i] = Math.min(blocks[block][i] + delta, INF);
                        }
                        resort(elements[block], 0, size[block], blocks[block]);
                    }
                    for (block = fromBlock + 1; block < toBlock; ++block) {
                        ad[block] = Math.min(ad[block] + delta, INF);
                    }
                }
            } else if (type == 2) {
                final int target = in.nextInt();
                int min = -1;
                int max = -2;
                outer: for (block = 0; block < BLOCKS; ++block) if (ad[block] < INF && elements[block].contains(target - ad[block])) {
                    for (int i = 0; i < size[block]; ++i) {
                        if (blocks[block][i] + ad[block] == target) {
                            min = i + block * SIZE_OF_BLOCK;
                            break outer;
                        }
                    }
                }
                if (min == -1) {
                    out.println(-1);
                    continue;
                }
                outer: for (block = BLOCKS - 1; block >= 0; --block) if (ad[block] < INF && elements[block].contains(target - ad[block])) {
                    for (int i = size[block] - 1; i >= 0; --i) {
                        if (blocks[block][i] + ad[block] == target) {
                            max = i + block * SIZE_OF_BLOCK;
                            break outer;
                        }
                    }
                }
                out.println(max - min);
            } else throw new AssertionError(type);
        }
    }

    private static void resort(final Set<Integer> elements, final int from, final int to, final int[] block) {
        elements.clear();
        for (int i = from; i < to; ++i) if (block[i] < INF) {
            elements.add(block[i]);
        }
    }
}
