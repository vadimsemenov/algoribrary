package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    private static final int MAGIC = 333;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[][] elements = new int[MAGIC][MAGIC];
        int[] head = new int[MAGIC];
        int[] tail = new int[MAGIC];
        Arrays.fill(tail, -1);
        int[][] cnt = new int[MAGIC][counter + 1];
        int currentBlock = 0;
        for (int i = 0; i < counter; ++i) {
            int x = in.nextInt();
            if (tail[currentBlock] + 1 == MAGIC) {
                currentBlock++;
            }
            elements[currentBlock][++tail[currentBlock]] = x;
            cnt[currentBlock][x]++;
        }
        int queryCounter = in.nextInt();
        int lastAns = 0;
        for (int query = 0; query < queryCounter; ++query) {
            int type = in.nextInt();
            if (type == 1) {
                int li = (in.nextInt() + lastAns - 1) % counter;
                int ri = (in.nextInt() + lastAns - 1) % counter;
                if (li > ri) {
                    int tmp = li; li = ri; ri = tmp;
                }
                int first = li / MAGIC;
                int fpos = li % MAGIC;
                int second = ri / MAGIC;
                int spos = ri % MAGIC;

                if (first == second) {
                    int tmp = elements[first][(head[first] + spos) % MAGIC];
                    for (int i = (head[first] + spos) % MAGIC; i != (head[first] + fpos) % MAGIC; i = pred(i)) {
                        elements[first][i] = elements[first][pred(i)];
                    }
                    elements[first][(head[first] + fpos) % MAGIC] = tmp;
                } else {
                    int tmp = elements[first][tail[first]];
                    cnt[first][tmp]--;
                    for (int i = tail[first]; i != (head[first] + fpos) % MAGIC; i = pred(i)) {
                        elements[first][i] = elements[first][pred(i)];
                    }
                    for (int block = first + 1; block < second; ++block) {
                        // removeLast
                        int tmp2 = elements[block][tail[block]];
                        tail[block] = pred(tail[block]);
                        cnt[block][tmp2]--;
                        // addFirst
                        head[block] = pred(head[block]);
                        elements[block][head[block]] = tmp;
                        cnt[block][tmp]++;
                        //
                        tmp = tmp2;
                    }
                    int tmp2 = elements[second][(head[second] + spos) % MAGIC];
                    cnt[second][tmp2]--;
                    elements[first][(head[first] + fpos) % MAGIC] = tmp2;
                    cnt[first][tmp2]++;
                    for (int i = (head[second] + spos) % MAGIC; i != head[second]; i = pred(i)) {
                        elements[second][i] = elements[second][pred(i)];
                    }
                    elements[second][head[second]] = tmp;
                    cnt[second][tmp]++;
                }
            } else if (type == 2) {
                int li = (in.nextInt() + lastAns - 1) % counter;
                int ri = (in.nextInt() + lastAns - 1) % counter;
                if (li > ri) {
                    int tmp = li; li = ri; ri = tmp;
                }
                int ki = (in.nextInt() + lastAns - 1) % counter + 1;
                int first = li / MAGIC;
                int fpos = li % MAGIC;
                int second = ri / MAGIC;
                int spos = ri % MAGIC;
                lastAns = 0;

                if (first == second) {
                    for (int i = (head[first] + fpos) % MAGIC; i != (head[first] + spos) % MAGIC; i = succ(i)) {
                        if (elements[first][i] == ki) lastAns++;
                    }
                    if (elements[first][(head[first] + spos) % MAGIC] == ki) lastAns++;
                } else {
                    for (int i = (head[first] + fpos) % MAGIC; i != tail[first]; i = succ(i)) {
                        if (elements[first][i] == ki) lastAns++;
                    }
                    if (elements[first][tail[first]] == ki) lastAns++;
                    for (int block = first + 1; block < second; ++block) {
                        lastAns += cnt[block][ki];
                    }
                    for (int i = head[second]; i != (head[second] + spos) % MAGIC; i = succ(i)) {
                        if (elements[second][i] == ki) lastAns++;
                    }
                    if (elements[second][(head[second] + spos) % MAGIC] == ki) lastAns++;
                }
                out.println(lastAns);
            } else throw new AssertionError(type);
        }
    }

    private static final int succ(int i) {
        return (i + 1) % MAGIC;
    }

    private static final int pred(int i) {
        return (i - 1 + MAGIC) % MAGIC;
    }
}
