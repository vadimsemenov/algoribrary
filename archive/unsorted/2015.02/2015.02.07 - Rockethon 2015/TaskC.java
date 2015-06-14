package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] min = new int[counter];
        int[] max = new int[counter];
        int MAX = -1;
        for (int i = 0; i < counter; ++i) {
            min[i] = in.nextInt();
            max[i] = in.nextInt();
            MAX = Math.max(MAX, max[i]);
        }
        double answer = 0;
        for (int cost = 1; cost <= MAX; ++cost) {
            double prob = 0.0;
            for (int mask = 1; mask < (1 << counter); ++mask) {
                boolean was = false;
                for (int winner = 0; winner < counter; ++winner) {
                    if (max[winner] < cost) continue;
                    double currentProb = 1.0;
                    for (int i = 0; i < counter; ++i) {
                        if (((mask >> i) & 1) == 1) {
                            if (max[i] < cost || min[i] > cost) {
                                currentProb = 0;
                                break;
                            }
                            currentProb *= 1.0 / (max[i] - min[i] + 1);
                        } else {
                            if (i == winner) {
                                currentProb *= (max[i] - Math.max(min[i], cost + 1) + 1) * 1.0 / (max[i] - min[i] + 1);
                            } else {
                                if (min[i] >= cost) {
                                    currentProb = 0;
                                    break;
                                }
                                currentProb *= (Math.min(max[i], cost - 1) - min[i] + 1) * 1.0 / (max[i] - min[i] + 1);
                            }
                        }
                    }
                    if (((mask >> winner) & 1) == 1 && Integer.bitCount(mask) == 1) currentProb = 0;
                    if (((mask >> winner) & 1) == 1 && was) currentProb = 0;
                    was |= ((mask >> winner) & 1) == 1;
                    prob += currentProb;
                }
            }
            answer += prob * cost;
        }
        out.println(answer);
    }
}
