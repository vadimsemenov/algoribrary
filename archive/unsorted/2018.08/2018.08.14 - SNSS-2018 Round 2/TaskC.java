package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        String addressString = in.next();
        char[] address = addressString.toCharArray();
        for (char ch : address) {
            if (ch < '0' || ch > '9') {
                throw new AssertionError("WTF?! " + addressString);
            }
        }
        for (int a = 1; a < 4; ++a) {
            for (int b = 1; b < 4; ++b) {
                for (int c = 1; c < 4; ++c) {
                    for (int d = 1; d < 4; ++d) {
                        if (address.length != a + b + c + d) continue;
                        int p1 = parse(address, 0, a);
                        int p2 = parse(address, a, b);
                        int p3 = parse(address, a + b, c);
                        int p4 = parse(address, a + b + c, d);
                        if ((p1 | p2 | p3 | p4) >= 0) {
                            if (!addressString.equals("" + p1 + p2 + p3 + p4)) {
                                throw new IllegalStateException("WTF?!! " + addressString + "->" + p1 + "." + p2 + "." + p3 + "." + p4);
                            }
                            out.println(p1 + "." + p2 + "." + p3 + "." + p4);
                        }
                    }
                }
            }
        }
    }

    private int parse(char[] address, int offset, int length) {
        if (address[offset] == '0' && length > 1) return -1;
        int result = 0;
        for (int i = offset; i < offset + length; ++i) {
            result = result * 10 + address[i] - '0';
        }
        return 0 <= result && result < 256 ? result : -1;
    }

    public void solve1(int __, InputReader in, PrintWriter out) {
        char[] address = in.next().toCharArray();
        if (address.length < 4) {
            return;
        }
        int[] positions = new int[]{1, 2, 3, address.length};
        do {
            if (valid(address, positions)) {
                print(address, positions, out);
            }
        } while (next(positions));
    }

    private boolean valid(char[] address, int[] positions) {
        int ptr = 0;
        for (int position : positions) {
            if (address[ptr] == '0') {
                return false;
            }
            long current = 0;
            while (ptr < position) {
                current = 10 * current + address[ptr++] - '0';
            }
            if (current >= 256 || current == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean next(int[] positions) {
        for (int i = 0; i < positions.length - 1; ++i) {
            if (positions[i] + 1 < positions[i + 1]) {
                positions[i]++;
                for (int j = 0; j < i; ++j) {
                    positions[j] = j + 1;
                }
                return true;
            }
        }
        return false;
    }

    private void print(char[] address, int[] positions, PrintWriter out) {
        int ptr = 0;
        for (int position : positions) {
            while (ptr < position) {
                out.print(address[ptr++]);
            }
            out.print(position < address.length ? '.' : '\n');
        }
    }
}
