package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class F {
    private static final int BUBEN = 283;
    private int[] ad;
    private int[][] blocks;
    private int[][] sortedBlocks;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int queries = in.nextInt();
        if (counter == 0 && queries == 0) throw new UnknownError();
        int[] array = new int[counter];
        for (int i = 0; i < counter; ++i) array[i] = in.nextInt();
        initialize(array);
        Arrays.sort(array);
        int median = array[counter / 2];
        for (int q = 0; q < queries; ++q) {
            int left = in.nextInt() - 1;
            int right = in.nextInt() - 1;
            int ll = left / BUBEN;
            int rr = right / BUBEN;
            if (ll == rr) {
                update(ll, left % BUBEN, right % BUBEN + 1);
            } else {
                if (left % BUBEN != 0) {
                    update(ll, left % BUBEN, blocks[ll].length);
                    ll++;
                }
                if (right % BUBEN + 1 != blocks[rr].length) {
                    update(rr, 0, right % BUBEN + 1);
                    rr--;
                }
                for (int i = ll; i <= rr; ++i) {
                    update(i);
                }
            }
            if (calculate(median) < (counter + 1) / 2) median++;
            out.println(median);
        }
    }

    private void update(int id, int from, int to) {
        for (int i = from; i < to; ++i) {
            blocks[id][i]++;
        }
        System.arraycopy(blocks[id], 0, sortedBlocks[id], 0, blocks[id].length);
        Arrays.sort(sortedBlocks[id]);
    }

    private void update(int id) {
        ad[id]++;
    }

    private int calculate(int key) {
        int notGreater = 0;
        for (int i = 0; i < blocks.length; ++i) {
            int left = -1;
            int right = blocks[i].length;
            while (right - left > 1) {
                int mid = (left + right) >> 1;
                if (sortedBlocks[i][mid] <= key - ad[i]) {
                    left = mid;
                } else {
                    right = mid;
                }
            }
            notGreater += right;
        }
        return notGreater;
    }

    private void initialize(int[] array) {
        int size = array.length;
        int blockCounter = (size + BUBEN - 1) / BUBEN;
        blocks = new int[blockCounter][BUBEN];
        sortedBlocks = new int[blockCounter][BUBEN];
        blocks[blockCounter - 1] = new int[size % BUBEN];
        sortedBlocks[blockCounter - 1] = new int[size % BUBEN];
        ad = new int[blockCounter];
        for (int i = 0; i < size; ++i) {
            blocks[i / BUBEN][i % BUBEN] = array[i];
        }
        for (int i = 0; i < blocks.length; ++i) {
            sortedBlocks[i] = Arrays.copyOf(blocks[i], blocks[i].length);
            Arrays.sort(sortedBlocks[i]);
        }
    }
}
