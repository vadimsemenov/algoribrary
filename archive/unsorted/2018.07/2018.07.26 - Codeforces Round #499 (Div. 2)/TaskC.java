package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int planets = in.nextInt();
        int weight = in.nextInt();
        int[] coef = new int[planets * 2];
        for (int i = 0; i < planets; ++i) {
            coef[i * 2] = in.nextInt();
        }
        coef[coef.length - 1] = in.nextInt();
        for (int i = 0; i < planets - 1; ++i) {
            coef[i * 2 + 1] = in.nextInt();
        }
        for (int c : coef) {
            if (c == 1) {
                out.println(-1);
                return;
            }
        }
        double left = 0;
        double right = 1e9 + 1e5;
        while ((right - left) > 1e-7) {
            double mid = (left + right) / 2;
            if (check(mid, weight, coef)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (right > 1e9) throw new RuntimeException(right + "");
        out.println(right);
    }

    private boolean check(double fuel, int weight, int[] coefs) {
        double mass = weight + fuel;
        for (int c : coefs) {
            double sub = mass * c;
            if (mass < weight + sub) {
                return false;
            }
            mass -= sub;
        }
        return true;
    }
}
