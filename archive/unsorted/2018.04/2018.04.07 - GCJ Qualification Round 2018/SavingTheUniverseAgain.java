package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class SavingTheUniverseAgain {
    public void solve(int testCase, InputReader in, PrintWriter out) {
        int shield = in.nextInt();
        char[] program = in.next().toCharArray();
        List<Integer> charges = new ArrayList<>();
        int[] damages = new int[program.length];
        int damage = 1;
        int totalDamage = 0;
        for (int i = 0; i < program.length; i++) {
            if (program[i] == 'C') {
                charges.add(i);
                damage *= 2;
            } else if (program[i] == 'S') {
                damages[i] = damage;
                totalDamage += damage;
            } else {
                throw new IllegalStateException("Unknown action: '" + program[i] + "'");
            }
        }
        if (totalDamage <= shield) {
            print(testCase, out, 0);
            return;
        }
        int answer = 0;
        for (int i = charges.size() - 1; i >= 0; --i) {
            for (int j = charges.get(i) + 1; j < damages.length; ++j) {
                if (damages[j] > 0) {
                    damages[j - 1] = damages[j] / 2;
                    damages[j] = 0;
                    totalDamage -= damages[j - 1];
                    ++answer;
                    if (totalDamage <= shield) {
                        print(testCase, out, answer);
                        return;
                    }
                }
            }
        }
        print(testCase, out, "IMPOSSIBLE");
    }

    private void print(int testCase, PrintWriter out, int answer) {
        print(testCase, out, "" + answer);
    }

    private void print(int testCase, PrintWriter out, String answer) {
        out.println("Case #" + testCase + ": " + answer);
    }
}
