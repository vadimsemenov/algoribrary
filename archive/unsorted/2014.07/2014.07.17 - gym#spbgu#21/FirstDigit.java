package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class FirstDigit {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        while (in.hasNext()) {
            int number = in.nextInt();
            int length = (int) (number * Math.log10(number)) + 1;
            double lg = number * Math.log10(number) - length + 1;
            out.println((int) Math.pow(10, lg));
        }
    }
}
