package tasks;

public class LongWordsDiv2 {
    public String find(String word) {
        int[] cnt = new int[256];
        for (int i = 0; i < word.length(); i++) cnt[word.charAt(i)]++;
        for (int i = 0; i < cnt.length; i++) if (cnt[i] > 3) return "Dislikes";
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < i; j++) {
                boolean[] was = new boolean[256];
                if (word.charAt(i) == word.charAt(j)) {
                    for (int k = j + 1; k < i; k++) was[word.charAt(k)] = true;
                    for (int k = i + 1; k < word.length(); k++) if (was[word.charAt(k)]) return "Dislikes";
                }
            }
        }
        return "Likes";
    }
}
