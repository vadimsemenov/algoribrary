package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Numbersrebmun {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int tests = in.nextInt();
        outer:
        for (int test = 0; test < tests; test++) {
            String name = in.next().toLowerCase();
            int[] number = new int[name.length()];
            for (int i = 0; i < name.length(); i++) {
                switch (name.charAt(i)) {
                    case 'a':
                    case 'b':
                    case 'c':
                        number[i] = 2;
                        break;
                    case 'd':
                    case 'e':
                    case 'f':
                        number[i] = 3;
                        break;
                    case 'g':
                    case 'h':
                    case 'i':
                        number[i] = 4;
                        break;
                    case 'j':
                    case 'k':
                    case 'l':
                        number[i] = 5;
                        break;
                    case 'm':
                    case 'n':
                    case 'o':
                        number[i] = 6;
                        break;
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                        number[i] = 7;
                        break;
                    case 't':
                    case 'u':
                    case 'v':
                        number[i] = 8;
                        break;
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                        number[i] = 9;
                        break;
                    default:
                        throw new AssertionError(i);
                }
            }
            for (int i = 0; i < number.length; i++) {
                if (number[i] != number[number.length - i - 1]) {
                    out.println("NO");
                    continue outer;
                }
            }
            out.println("YES");
        }
    }
}
