package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
//        new Thread(null, new Solution(in, out), "stacksize!!!", 1 << 20).start();
        new Solution(in, out).run();
    }

    static class Solution implements Runnable {
        final InputReader in;
        final PrintWriter out;

        Solution(InputReader in, PrintWriter out) {
            this.in = in;
            this.out = out;
        }

        char[] answer;
        List<Integer>[] clauses, where;
        boolean[] ok;

        @Override
        public void run() {
            int counter = in.nextInt() + 1;
            int variables = in.nextInt() + 1;
            answer = new char[variables];
            clauses = new List[counter];
            where = new List[variables];
            for (int i = 1; i < variables; ++i) {
                where[i] = new ArrayList<>(2);
            }

            for (int clause = 1; clause < counter; ++clause) {
                int cnt = in.nextInt();
                clauses[clause] = new ArrayList<>(cnt);
                for (int i = 0; i < cnt; ++i) {
                    int id = in.nextInt();
                    clauses[clause].add(id);
                    if (id < 0) {
                        where[-id].add(-clause);
                    } else {
                        where[id].add(clause);
                    }
                }
            }

            ok = new boolean[counter];
            for (int i = 1; i < counter; ++i) {
                if (clauses[i].size() == 1) {
                    int id = clauses[i].get(0);
                    assign(Math.abs(id), id > 0);
                }
            }

            for (int i = 1; i < variables; ++i) {
                if (where[i].size() == 1 && answer[i] == 0) {
                    int cl = where[i].get(0);
                    assign(i, cl > 0);
                }
            }

            for (int i = 1; i < counter; ++i) if (!ok[i]) {
                for (int _id : clauses[i]) {
                    int id = Math.abs(_id);
                    if (answer[id] == 0) {
                        assign(id, _id > 0);
                    }
                }
                if (!ok[i]) {
                    out.println("NO");
                    return;
                }
            }

            if (ABORT) {
                out.println("NO");
                return;
            }

            out.println("YES");
            for (int i = 1; i < variables; ++i) {
                out.print(answer[i] == 0 ? '0' : answer[i]);
            }
        }

        boolean ABORT = false;

        private void assign(int id, boolean what) {
            if (ABORT) return;
            if (answer[id] == (what ? '1' : '0')) return;
            if (answer[id] != 0) abort();
            answer[id] = (what ? '1' : '0');
            for (int _cl : where[id]) {
                int cl = Math.abs(_cl);
                if (!ok[cl] && !(what ^ (_cl > 0))) {
                    ok[cl] = true;
                    for (int _i : clauses[cl]) {
                        int i = Math.abs(_i);
                        if (answer[i] == 0) {
                            if (where[i].size() == 2) {
                                int j = 0;
                                if (Math.abs(where[i].get(j)) == cl) {
                                    j = 1;
                                }
                                int _other = where[i].get(j);
                                int other = Math.abs(_other);
                                if (!ok[other]) {
                                    assign(i, _other > 0);
                                }
                            } else {
                                answer[i] = '0';
                            }
                        }
                    }
                }
            }
        }

        private void abort() {
            ABORT = true;
//            out.println("NO");
//            out.close();
//            System.exit(0);
        }
    }
}
