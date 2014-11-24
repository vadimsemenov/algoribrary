package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class BayanHealthBracelet {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        out.println("Case #" + testNumber + ":");
        int temperature = in.nextInt();
        int heartbeat = in.nextInt();
        int movement = in.nextInt();
        int sleepDuration = in.nextInt();
        if (sleepDuration > 8 * 60 && (temperature < 35 || heartbeat < 40)) {
            System.err.println(testNumber + ": " + temperature + " " + heartbeat + " " + movement + " " + sleepDuration);
        }
        if (movement >= 10) {
            out.println("NOTHING");
        } else if (temperature < 35 || heartbeat < 40) {
            out.println("EMERGENCY");
        } else if (heartbeat > 60) {
            out.println("NIGHTMARE");
        } else if (sleepDuration > 8 * 60 * 60) {
            out.println("WAKE-UP");
        } else {
            out.println("NOTHING");
        }
    }
}
