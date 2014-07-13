package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String input = in.next();
        int[] prefix = prefixFunction(input);
        int[] counter = new int[input.length() + 1];
        for (int i : prefix) counter[i]++;
        for (int i = input.length() - 1; i > 0; i--) counter[prefix[i - 1]] += counter[i];
        for (int i = 0; i < counter.length; i++) counter[i]++;
        List<int[]> result = new ArrayList<>();
        result.add(new int[]{input.length(), 1});
        int current = input.length() - 1;
        while (prefix[current] > 0) {
            result.add(new int[]{prefix[current], counter[prefix[current]]});
            current = prefix[current] - 1;
        }
        Collections.reverse(result);
        out.println(result.size());
        for (int[] i : result) out.println(i[0] + " " + i[1]);
    }

    private int[] prefixFunction(String text) {
        int[] result = new int[text.length()];
        for (int i = 1; i < text.length(); i++) {
            int j = result[i - 1];
            while (j > 0 && text.charAt(i) != text.charAt(j)) j = result[j - 1];
            if (text.charAt(i) == text.charAt(j)) j++;
            result[i] = j;
        }
        return result;
    }
}
