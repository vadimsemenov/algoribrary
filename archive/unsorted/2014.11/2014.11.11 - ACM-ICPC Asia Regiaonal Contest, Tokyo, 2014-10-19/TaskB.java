package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] expression = in.next().toCharArray();
        int multiplicationFirstResult = 0;
        for (int i = 0; i < expression.length; i += 2) {
            int ad = expression[i] - '0';
            for (int j = i + 1; j < expression.length; j += 2) {
                if (expression[j] == '*') {
                    i = j + 1;
                    ad *= expression[i] - '0';
                } else {
                    break;
                }
            }
            multiplicationFirstResult += ad;
        }
        int leftToRight = expression[0] - '0';
        for (int i = 1; i < expression.length; i += 2) {
            if (expression[i] == '*') {
                leftToRight *= expression[i + 1] - '0';
            } else {
                leftToRight += expression[i + 1] - '0';
            }
        }
        int bobsResult = in.nextInt();
        if (bobsResult == multiplicationFirstResult && bobsResult == leftToRight) {
            out.println("U");
        } else if (bobsResult == multiplicationFirstResult) {
            out.println("M");
        } else if (bobsResult == leftToRight) {
            out.println("L");
        } else {
            out.println("I");
        }
    }
}
