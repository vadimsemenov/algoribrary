package tasks;

public class BalancedSubstrings {
    public int countSubstrings(String _s) {
        int answer = 0;
        char[] s = _s.toCharArray();
        int n = s.length;
        for (int i = 0; i < n; ++i) {
            int center = i;
            int ptr = i + 1;
            int balance = 0;
            int cntLeft = 0;
            int cntRight = 0;
            while (ptr < n) {
                if (s[ptr] == '1' && center < ptr) {
                    ++cntRight;
                    balance -= ptr - center;
                }
                ++ptr;
                while (balance < 0) {
                    if (s[center] == '1') ++cntLeft;
                    ++center;
                    balance += cntRight;
                    if (center < ptr && s[center] == '1') --cntRight;
                    balance += cntLeft;
                }
                if (balance == 0 && center < ptr) {
                    ++answer;
                }
            }
        }
        return answer + n;
    }
}
