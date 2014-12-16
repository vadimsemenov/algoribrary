package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int segments = in.nextInt();
        int[] initial = new int[counter];
        for (int i = 0; i < counter; ++i) {
            initial[i] = in.nextInt();
        }
        segments++;
        int[] left = new int[segments];
        int[] right = new int[segments];
        double[] probability = new double[segments];
        left[0] = 0; right[0] = counter - 1; probability[0] = 0;
        for (int i = 1; i < segments; ++i) {
            left[i] = in.nextInt() - 1;
            right[i] = in.nextInt() - 1;
            probability[i] = in.nextDouble();
            for (int j = 0; j < i; ++j) {
                if (left[i] < left[j] || (left[i] == left[j] && right[j] < right[i])) {
                    int tmp = left[j]; left[j] = left[i]; left[i] = tmp;
                    tmp = right[j]; right[j] = right[i]; right[i] = tmp;
                    double dtmp = probability[j]; probability[j] = probability[i]; probability[i] = dtmp;
                }
            }
        }
        List<Integer>[] graph = new List[segments];
        for (int i = 0; i < graph.length; ++i) {
            graph[i] = new ArrayList<>();
        }
        int[] max = new int[segments];
        int ptr = 0;
        int[] stack = new int[segments];
        int top = 0;
        for (int i = 0; i < counter; ++i) {
            while (ptr < segments && left[ptr] == i) {
                if (top > 0) graph[stack[top - 1]].add(ptr);
                stack[top++] = ptr;
                max[ptr] = initial[i];
                ptr++;
            }
            // top > 0 cause fictive segment
            max[stack[top - 1]] = Math.max(max[stack[top - 1]], initial[i]);
            while (top > 0 && right[stack[top - 1]] == i) {
                top--;
                if (top > 0 && max[stack[top - 1]] < max[stack[top]]) {
                    max[stack[top - 1]] = max[stack[top]];
                }
            }
        }
        // dp[i][j] -- probability that max in i-th segment <= max[i] + j;
        double[][] dp = new double[segments][segments + 1];
        for (double[] d : dp) Arrays.fill(d, 1);
        for (int i = 0; i < segments; ++i) {
            dp[i][0] = 1 - probability[i];
            dp[i][1] = probability[i];
        }
        dfs(0, graph, dp, max);
        double answer = dp[0][0] * max[0];
        for (int i = 1; i < dp[0].length; ++i) {
            answer += (max[0] + i) * (dp[0][i] - dp[0][i - 1]);
        }
        out.println(answer);
    }

    private void dfs(int current, List<Integer>[] graph, double[][] probability, int[] max) {
        for (int child : graph[current]) {
            dfs(child, graph, probability, max);
        }
        double withoutCurrent = probability[current][0];
        double withCurrent = probability[current][1];
        for (int i = 0; i < probability[current].length; ++i) {
            double prob = 1;
            for (int child : graph[current]) {
                prob *= (i + max[current] - max[child] < probability[child].length)
                        ? probability[child][i + max[current] - max[child]] : 1;
            }
            probability[current][i] = withoutCurrent * prob;
            if (i == 0) continue;
            prob = 1;
            for (int child : graph[current]) {
                prob *= (i + max[current] - max[child] - 1 < probability[child].length)
                        ? probability[child][i + max[current] - max[child] - 1] : 1;
            }
            probability[current][i] += withCurrent * prob;
        }
    }
}
