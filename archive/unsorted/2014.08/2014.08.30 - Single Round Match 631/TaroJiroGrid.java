package tasks;

import java.util.Arrays;

public class TaroJiroGrid {
    public int getNumber(String[] grid) {
        if (check(grid)) return 0;
        for (int i = 0; i < grid.length; ++i) {
            if (check(invert(grid, i, 'W'))) return 1;
            if (check(invert(grid, i, 'B'))) return 1;
        }
        return 2;
    }

    private String[] invert(String[] grid, int row, char b) {
        String[] newGrid = new String[grid.length];
        for (int r = 0; r < grid.length; ++r) {
            newGrid[r] = grid[r];
            if (r == row) {
                char[] rr = new char[grid.length];
                Arrays.fill(rr, b);
                newGrid[r] = new String(rr);
            }
        }
        return newGrid;
    }

    private boolean check(String[] grid) {
        for (int col = 0; col < grid.length; ++col) {
            int cnt = 0;
            char prev = 0;
            for (int row = 0; row < grid.length; ++row) {
                if (grid[row].charAt(col) == prev) {
                    cnt++;
                } else {
                    cnt = 1;
                    prev = grid[row].charAt(col);
                }
                if (cnt > grid.length / 2) return false;
            }
        }
        return true;
    }
}
