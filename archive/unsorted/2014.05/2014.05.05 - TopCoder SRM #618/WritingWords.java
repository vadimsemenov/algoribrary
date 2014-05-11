package tasks;

public class WritingWords {
    public int write(String word) {
        int sum = 0;
        for (int i = 0; i < word.length(); i++) sum += word.charAt(i) - 'A' + 1;
        return sum;
    }
}
