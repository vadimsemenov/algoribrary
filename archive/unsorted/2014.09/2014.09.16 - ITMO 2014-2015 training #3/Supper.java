package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class Supper {
    int counter, groups;
    int[] plus = new int[]{0, 3, 1, 4, 2};
    int[] order = new int[]{3, 1, 4, 2, 0};
    int currentAnswer = 0;
    int[] answer = new int[5];
    int[] rem = new int[5];

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        counter = in.nextInt();
        groups = in.nextInt();
        go(0);
        int sum = 5 * answer[0];
        for (int i = 1; i < 5; ++i) sum += i * answer[i];
        int lost = counter - sum;
        if (lost % 5 != 0 || lost < 0) throw new AssertionError("wtf?!0");
        boolean was = false;
        for (int i = 0; i < 5; ++i) {
            int c = i;
            if (c == 0) c = 5;
            for (int j = 0; j < answer[i]; ++j) {
                groups--;
                if (!was) {
                    counter -= lost + c;
                    out.print(lost + c);
                    was = true;
                }
                else {
                    counter -= c;
                    out.print(c);
                }
                out.print(' ');
            }
        }
        out.println();
        // System.err.println(counter + " " + groups);
        if (/*counter != 0 ||*/ groups != 0) throw new AssertionError();
    }

    int[] tmp = new int[5];
    private void go(int idx) {
        if (idx == 5) {
            int sum = 0;
            for (int i = 0; i < 5; ++i) sum += rem[i];
            if (sum > groups || sum % 5 != groups % 5) return;

            int lGroups = groups - sum; // lGroups % 5 == 0
            sum = rem[0] * 5;
            for (int i = 1; i < 5; ++i) sum += rem[i] * i;
            if (sum + lGroups > counter || sum % 5 != counter % 5) return;

            for (int i = 0; i < 5; ++i) tmp[i] = rem[i];
            tmp[3] += lGroups;
            sum += 3 * lGroups;
            if (sum > counter) {
                int much = (sum - counter + 1) / 2;
                tmp[3] -= 5 * much;
                if (tmp[3] < 0) throw new AssertionError("wtf?!");
                tmp[1] += 5 * much;
            }
            int cur = 0;
            for (int i = 0; i < 5; ++i) cur += plus[i] * tmp[i];
            if (cur > currentAnswer) {
                currentAnswer = cur;
                answer = tmp.clone();
            }
        } else {
            for (int cur = 0; cur < 5; ++cur) {
                rem[idx] = cur;
                go(idx + 1);
            }
        }
    }
}
