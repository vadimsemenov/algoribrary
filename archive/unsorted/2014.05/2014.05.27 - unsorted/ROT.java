package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class ROT {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        Set<String> words = new HashSet<>(counter);
        Set<String> encodedWords = new HashSet<>(counter);

        for (int i = 0; i < counter; i++) {
            String word = in.next();
            words.add(word);
            encodedWords.add(rot13(word));
        }
        int answer = 0;
        for (String s : encodedWords) if (words.contains(s)) answer++;
        out.println(answer);
    }

    private StringBuilder result = new StringBuilder();
    private String rot13(String word) {
        result.delete(0, result.length());
        for (int i = 0; i < word.length(); i++)
            result.append((char) (((word.charAt(i) - 'a' + 13) % 26) + 'a'));
        return result.toString();
    }
}
