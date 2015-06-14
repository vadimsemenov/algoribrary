package tasks;



import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int valuesCounter = in.nextInt();
        int canGive = in.nextInt();
        int[] values = new int[valuesCounter];
        for (int i = 0; i < valuesCounter; ++i) {
            values[i] = in.nextInt();
        }
        int[][] boom = new int[valuesCounter * canGive][2];
        int ptr = 0;
        for (int val : values) {
            for (int give = 0; give < canGive; ++give) {
                boom[ptr][0] = val * (give + 1);
                boom[ptr][1] = give + 1;
                ptr++;
            }
        }
        Arrays.sort(boom, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int res = Integer.compare(o1[0], o2[0]);
                if (res == 0) res = Integer.compare(o1[1], o2[1]);
                return res;
            }
        });
        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            int need = in.nextInt();
            int answer = Integer.MAX_VALUE;
            for (int i = 0; i < boom.length; ++i) {
                int[] aBoom = boom[i];
                if (aBoom[0] == need) {
                    answer = Math.min(answer, aBoom[1]);
                    continue;
                }
                int have = aBoom[0];
                int left = -1;
                int right = boom.length;
                while (right - left > 1) {
                    int middle = (left + right) >>> 1;
                    if (have + boom[middle][0] < need) {
                        left = middle;
                    } else {
                        right = middle;
                    }
                }
                if (i != right && right < boom.length && boom[i][1] + boom[right][1] <= canGive) {
                    answer = Math.min(answer, aBoom[1] + boom[right][1]);
                }
            }
            out.println(answer == Integer.MAX_VALUE ? -1 : answer);
        }
    }
}
