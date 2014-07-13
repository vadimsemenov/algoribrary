package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] home = new int[counter];
        int[] away = new int[counter];
        for (int i = 0; i < counter; i++) {
            home[i] = in.nextInt();
            away[i] = in.nextInt();
        }
        int[] number = new int[100_001];
        for (int i = 0; i < counter; i++) number[home[i]]++;
        for (int i = 0; i < counter; i++)
            out.println((counter - 1 + number[away[i]]) + " " + (counter - 1 - number[away[i]]));
    }
}
