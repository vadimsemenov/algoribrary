package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int base = in.nextInt();
        int counter = in.nextInt();
        StringTokenizer input;
        String line = in.readLine();
        while (line.trim().length() == 0) {
            line = in.readLine();
        }
        input = new StringTokenizer(line);
        List<String> expression = new ArrayList<>();
        while (input.hasMoreElements()) {
            expression.add(input.nextToken());
        }
        stack = new int[expression.size()];
        int[] answer = new int[base];
        Arrays.fill(answer, -1);
        for (int i = 0; i < counter; ++i) {
            int x = -1;
            String token = in.next();
            char last = token.charAt(token.length() - 1);
            if ('A' <= last && last <= 'Z') {
                x = last - 'A' + 10;
            } else if ('0' <= last && last <= '9') {
                x = last - '0';
            } else throw new AssertionError(last);
            if (answer[x] == -1) {
                answer[x] = calculate(expression, x, base);
//                System.err.println(x + " " + answer[x]);
            }
            if (answer[x] < 10) {
                out.println(answer[x]);
            } else {
                out.println((char) (answer[x] - 10 + 'A'));
            }
        }
    }

    int[] stack;
    private int calculate(List<String> expression, int x, int base) {
        int ptr = 0;
        for (String token : expression) {
            if (token.equals("*")) {
                int a = stack[--ptr];
                int b = stack[--ptr];
                stack[ptr++] = (a * b) % base;
            } else if (token.equals("+")) {
                int a = stack[--ptr];
                int b = stack[--ptr];
                stack[ptr++] = (a + b) % base;
            } else if (token.equals("-")) {
                int a = stack[--ptr];
                int b = stack[--ptr];
                stack[ptr++] = (b + base - a) % base;
            } else if (token.equals("x")) {
                stack[ptr++] = x;
            } else {
                char last = token.charAt(token.length() - 1);
                if ('A' <= last && last <= 'Z') {
                    stack[ptr++] = last - 'A' + 10;
                } else if ('0' <= last && last <= '9') {
                    stack[ptr++] = last - '0';
                } else throw new AssertionError(last);
            }
        }
        if (ptr != 1) throw new AssertionError(ptr);
        return stack[0];
    }
}
