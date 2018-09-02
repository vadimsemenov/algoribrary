package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        char[] number = in.next().toCharArray();
        out.println(solve(number));
    }

    private long solve(char[] number) {
        int ptr = 0;
        while (ptr < number.length && number[ptr] == '9') {
            ++ptr;
        }
        if (ptr < number.length) {
            if (number[ptr] == '0') {
                number[ptr - 1] -= 1; // no leading zero
                for (int i = ptr; i < number.length; ++i) {
                    number[i] = '9';
                }
            } else {
                int ad = 0;
                for (int i = ptr + 1; i < number.length; ++i) {
                    ad += '9' - number[i];
                }
                if (ptr == 0 && ad > 1) {
                    ++ptr;
                    while (number[ptr] == '9') { // ad > 0 => ptr < number.length - 1
                        ++ptr;
                    }
                    number[ptr - 1] -= 1;
                    for (int i = ptr; i < number.length; ++i) {
                        number[i] = '9';
                    }
                } else if (ptr > 0 && ad + '9' - number[ptr] > 1) {
                    number[ptr - 1]--; // was '9'
                    for (int i = ptr; i < number.length; ++i) {
                        number[i] = '9';
                    }
                }
            }
        }
        return Long.valueOf(String.valueOf(number));
    }

    public static void main(String[] args) {
        TaskB solver = new TaskB();
        long best = 0;
        int bestSum = 0;
        for (long input = 1; ; ++input) {
            if (input % 100_000_000 == 0) {
                System.out.println("checking... " + input);
            }
            int curSum = digitSum(input);
            if (curSum >= bestSum) {
                best = input;
                bestSum = curSum;
            }
            long answer = solver.solve(Long.toString(input).toCharArray());
            if (answer != best) {
                System.out.println("POLUNDRA");
                System.out.println(input);
                System.out.println(answer + " is worse than " + best);
                return;
            }
        }
    }

    private static int digitSum(long number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
