package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskE {
    private InputReader in;
    private PrintWriter out;
    private int size;

    public void solve(int __, InputReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        size = in.nextInt();
        char[] path = new char[2 * size - 2];
        int[] fst = firstPart(path);
        int[] snd = secondPart(path);
        if (!Arrays.equals(fst, snd)) throw new RuntimeException("POLUNDRA");
        out.println("! " + String.valueOf(path));
        out.flush();
    }

    private int[] firstPart(char[] path) {
        int ptr = 0;
        int x = 1;
        int y = 1;
        while (2 * size - x - y >= size) {
            if (x < size && checkToFinish(x + 1, y)) {
                x = x + 1;
                path[ptr++] = 'D';
            } else if (y < size && checkToFinish(x, y + 1)) {
                y = y + 1;
                path[ptr++] = 'R';
            }
        }
        assert ptr == size - 1;
        return new int[]{x, y};
    }

    private int[] secondPart(char[] path) {
        int ptr = path.length - 1;
        int x = size;
        int y = size;
        while (x + y - 2 >= size) {
            if (y > 1 && checkFromStart(x, y - 1)) {
                y = y - 1;
                path[ptr--] = 'R';
            } else if (x > 1 && checkFromStart(x - 1, y)) {
                x = x - 1;
                path[ptr--] = 'D';
            }
        }
        assert ptr == size - 2;
        return new int[]{x, y};
    }

    private boolean checkFromStart(int x, int y) {
        return query(1, 1, x, y);
    }

    private boolean checkToFinish(int x, int y) {
        return query(x, y, size, size);
    }

    private boolean query(int x1, int y1, int x2, int y2) {
        if (x1 > x2 || y1 > y2 || (x2 - x1) + (y2 - y1) < size - 1) {
            throw new IllegalArgumentException("illegal query");
        }
        out.println("? " + x1 + " " + y1 + " " + x2 + " " + y2);
        out.flush();
        return check();
    }

    private boolean check() {
        String result = in.next();
        if (result.equals("YES")) return true;
        if (result.equals("NO")) return false;
        throw new IllegalStateException(result);
    }
}
