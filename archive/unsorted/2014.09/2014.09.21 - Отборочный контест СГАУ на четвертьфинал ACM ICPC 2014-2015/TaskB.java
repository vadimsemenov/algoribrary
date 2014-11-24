package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    private int[] permutation;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        permutation = new int[length + 1];
        mem = new int[length + 1][length + 1][];
        mem[1][length] = new int[length];
        for (int i = 0; i < length; ++i) {
            mem[1][length][i] = i + 1;
        }
        solve(1, length, in, out);
        out.print('A');
        for (int i = 1; i <= length; ++i) {
            out.print(' ');
            out.print(permutation[i]);
        }
        out.println();
    }

    private int[][][] mem;

    private void solve(int from, int to, InputReader in, PrintWriter out) {
        int len = to - from + 1;
        if (len == 1) {
            out.println("Q " + from + " " + to);
            permutation[from] = in.nextInt();
        } else if(len == 3) {
            out.println("Q " + from + " " + (to - 1));
            int a = in.nextInt();
            int b = in.nextInt();
            permutation[from] = a ^ b;
            out.println("Q " + (from + 1) + " " + to);
            int c = in.nextInt();
            int d = in.nextInt();
            permutation[to] = c ^ d;
            if (c == a || c == b) {
                permutation[from + 1] = c;
            } else {
                permutation[from + 1] = d;
            }
            permutation[from] ^= permutation[from + 1];
            permutation[to] ^= permutation[from + 1];
        } else if (len % 2 == 1) {
            int mid = from + len / 2;
            mem[from][mid] = new int[mid - from + 1];
            mem[mid + 1][to] = new int[to - (mid + 1) + 1];
            mem[from + 1][mid] = new int[mid - (from + 1) + 1];

            out.println("Q " + from + " " + mid);
            for (int i = 0; i < mid - from + 1; ++i) {
                mem[from][mid][i] = in.nextInt();
            }

            out.println("Q " + (from + 1) + " " + to);
            mem[from + 1][to] = new int[to - (from + 1) + 1];
            for (int i = 0; i < to - (from + 1) + 1; ++i) {
                mem[from + 1][to][i] = in.nextInt();
            }

            boolean[] was = new boolean[mid - from + 1];
            int ptr = 0;
            for (int i = 0; i < to - (from + 1) + 1; ++i) {
                boolean contains = false;
                for (int j = 0; j < mid - from + 1; ++j) {
                    contains |= mem[from][mid][j] == mem[from + 1][to][i];
                    was[j] |= mem[from][mid][j] == mem[from + 1][to][i];
                }
                if (!contains) {
                    mem[mid + 1][to][ptr] = mem[from + 1][to][i];
                    ptr++;
                }
            }
            ptr = 0;
            for (int i = 0; i < mid - from + 1; ++i) {
                if (!was[i]) {
                    permutation[from] = mem[from][mid][i];
                } else {
                    mem[from + 1][mid][ptr] = mem[from][mid][i];
                    ptr++;
                }
            }

            int l = from + 2;
            int r = to - 1;
            while (r - l + 1 >= 2) {
                out.println("Q " + l + " " + r);
                int[] buf = new int[r - l + 1];
                for (int i = 0; i < buf.length; ++i) {
                    buf[i] = in.nextInt();
                }
                for (int i : mem[l - 1][mid]) {
                    boolean contains = false;
                    for (int j = 0; j < buf.length; ++j) {
                        contains |= buf[j] == i;
                    }
                    if (!contains) {
                        permutation[l - 1] = i;
                        if (r - l + 1 != 2) break;
                    } else if (r - l + 1 == 2) {
                        permutation[l] = i;
                    }
                }
                for (int i : mem[mid + 1][r + 1]) {
                    boolean contains = false;
                    for (int j = 0; j < buf.length; ++j) {
                        contains |= buf[j] == i;
                    }
                    if (!contains) {
                        permutation[r + 1] = i;
                        if (r - l + 1 != 2) break;
                    } else if (r - l + 1 == 2) {
                        permutation[r] = i;
                    }
                }

                mem[l][mid] = new int[mid - l + 1];
                ptr = 0;
                for (int i = 0; i < mem[l - 1][mid].length; ++i) {
                    if (permutation[l - 1] != mem[l - 1][mid][i]) {
                        mem[l][mid][ptr] = mem[l - 1][mid][i];
                        ptr++;
                    }
                }
                mem[mid + 1][r] = new int[r - (mid + 1) + 1];
                ptr = 0;
                for (int i = 0; i < mem[mid + 1][r + 1].length; ++i) {
                    if (permutation[r + 1] != mem[mid + 1][r + 1][i]) {
                        mem[mid + 1][r][ptr] = mem[mid + 1][r + 1][i];
                        ptr++;
                    }
                }

                r--;
                l++;
            }
        } else if (len % 2 == 0) {
            solve(from, to - 1, in, out);
            for (int i = 0; i < mem[from][to].length; ++i) {
                boolean was = false;
                for (int j = from; j < to; ++j) {
                    was |= mem[from][to][i] == permutation[j];
                }
                if (!was) {
                    permutation[to] = mem[from][to][i];
                    break;
                }
            }
        }
    }
}
