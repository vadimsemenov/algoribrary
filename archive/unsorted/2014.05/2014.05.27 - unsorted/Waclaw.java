package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Waclaw {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String number = in.next();
        if (number.charAt(number.length() - 1) == '4') {
            for (int i = 1; i < 4; i++)
                out.println(number.substring(0, number.length() - 1) + i);
            return;
        }
        int first = number.lastIndexOf('1');
        int second = number.lastIndexOf('2');
        int third = number.lastIndexOf('3');
        if (first != -1) out.println(number.substring(0, first) + '4');
        if (second != -1) out.println(number.substring(0, second) + '4');
        if (third != -1) out.println(number.substring(0, third) + '4');
    }
}
