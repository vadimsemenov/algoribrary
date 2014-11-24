package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Code {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int blocks = in.nextInt();
        Block[] list = new Block[blocks];
        for (int i = 0; i < blocks; ++i) {
            list[i] = new Block(in.next(), i);
        }
        Arrays.sort(list);
        int sum = 0;
        for (int i = 0; i < blocks; ++i) {
            sum += list[i].balance;
            if (sum < 0) {
                out.println("NO");
                return;
            }
        }
        if (sum != 0) {
            out.println("NO");
        } else {
            out.println("YES");
            for (Block block : list) {
                out.print(block.idx + 1);
                out.print(' ');
            }
            out.println();
        }
    }

    static class Block implements Comparable<Block> {
        int balance, minBalance, idx;

        Block(String block, int idx) {
            this.idx = idx;
            balance = 0;
            minBalance = 0;
            for (int i = 0; i < block.length(); ++i) {
                if (block.charAt(i) == ')') balance--;
                else if (block.charAt(i) == '(') balance++;
                else throw new IllegalArgumentException(block);
                minBalance = Math.min(minBalance, balance);
            }
        }

        // mb = 0 b > 0 | mb < 0 b > 0 | mb < 0 b < 0
        @Override
        public int compareTo(Block other) {
            if (this.balance < 0 && other.balance < 0) {
                return -Integer.compare(this.minBalance, other.minBalance);
            }
            if (this.balance < 0) {
                return 1;
            }
            if (other.balance < 0) {
                return -1;
            }
            return -Integer.compare(this.balance, other.balance);
        }
    }
}
