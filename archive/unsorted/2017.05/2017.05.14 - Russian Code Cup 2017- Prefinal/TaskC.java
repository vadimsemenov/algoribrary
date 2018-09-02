package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        int totalQty = in.nextInt();
        List<Cell> cells = new ArrayList<>(totalQty);
        for (int i = 0; i < qty; ++i) cells.add(Cell.read(in));
        Set<Cell> set = new HashSet<>(cells);
        int minX = Integer.MAX_VALUE;
        for (Cell cell : cells) minX = Math.min(minX, cell.x);
        Set<Cell> visited = new HashSet<>();
        Cell[] queue = new Cell[totalQty];
        int head = 0;
        int tail = 0;
        for (Cell cell : cells) if (cell.x == minX) {
            queue[tail++] = cell;
            visited.add(cell);
        }
        while (cells.size() < totalQty) {
            Cell current = queue[head++];
            for (int d = 0; d < 4; ++d) {
                Cell next = current.move(d);
                if (set.contains(next) && !visited.contains(next)) {
                    queue[tail++] = next;
                    visited.add(next);
                }
            }
            cells.add(new Cell(2 * minX - current.x - 1, current.y));
        }
        out.println("L " + minX);
        for (Cell cell : cells) {
            cell.println(out);
        }
    }

    static class Cell {
        private static final int[] dx = {-1, 0, 1, 0};
        private static final int[] dy = {0, -1, 0, 1};

        final int x, y;

        private Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Cell move(int d) {
            return new Cell(x + dx[d], y + dy[d]);
        }

        @SuppressWarnings ("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;

            Cell cell = (Cell) o;

            return x == cell.x && y == cell.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        static Cell read(InputReader in) {
            return new Cell(in.nextInt(), in.nextInt());
        }

        public void println(PrintWriter out) {
            out.print(x);
            out.print(' ');
            out.println(y);
        }
    }
}
