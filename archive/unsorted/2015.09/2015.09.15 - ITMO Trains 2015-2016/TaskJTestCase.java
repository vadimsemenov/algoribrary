package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TaskJTestCase {
    @TestCase
    public Collection<Test> createTests() {
        final int tests = 100;
        final Random rng = new Random(58);
        List<Test> ret = new ArrayList<>();
        for (int test = 0; test < tests; ++test) {
            final int cnt = 5;
            StringBuilder input = new StringBuilder();
            input.append(cnt);
            input.append('\n');
            for (int i = 0; i < cnt; ++i) {
                int curLengt = 3 + rng.nextInt(5);
                for (int j = 0; j < curLengt; ++j) {
                    if (rng.nextBoolean()) {
                        input.append('a');
                    } else {
                        input.append('b');
                    }
                }
                input.append('\n');
            }
            ret.add(new Test(input.toString(), bf(input.toString())));
        }
        return ret;
    }

    private String bf(String input) {
        Scanner in = new Scanner(input);

        int counter = in.nextInt();
        String[] toponyms = new String[counter];
        in.nextLine();
        for (int i = 0; i < counter; ++i) toponyms[i] = in.nextLine();
        Node root = new Node();
        long answer = 0;
        for (String toponym : toponyms) {
            Node current = root;
            ++current.cnt;
            for (char ch : toponym.toCharArray()) {
                int id = code(ch);
                if (current.children[id] == null) {
                    current.children[id] = new Node();
                    current.children[id].length = current.length + 1;
                }
                current = current.children[id];
                ++current.cnt;
                answer = Math.max(answer, current.cnt * 1L * current.length);
            }
        }
        return "" + answer;
    }

    private int code(char ch) {
        if ('a' <= ch && ch <= 'z') return ch - 'a';
        if ('A' <= ch && ch <= 'Z') return ch - 'A' + 26;
        return 26 + 26;
    }

    static class Node {
        static final int ALPHABET = 26 + 26 + 2;
        Node[] children = new Node[ALPHABET];
        int cnt = 0;
        int length = 0;
    }
}
