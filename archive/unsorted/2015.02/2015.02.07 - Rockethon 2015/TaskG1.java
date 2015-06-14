package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class TaskG1 {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int counter = in.nextInt();
        List<Integer> perm = new ArrayList<>();
        for (int i = 0; i < length; ++i) {
            perm.add(in.nextInt());
        }
        Map<List<Integer>, Double> layer = new HashMap<>();
        layer.put(perm, 1.0);

        for (int it = 0; it < counter; ++it) {
            final Map<List<Integer>, Double> next = new LinkedHashMap<>();
            layer.forEach(new BiConsumer<List<Integer>, Double>() {
                @Override
                public void accept(List<Integer> permutation, Double probability) {
                    for (int l = 0; l < permutation.size(); ++l) {
                        for (int r = l; r < permutation.size(); ++r) {
                            List<Integer> tmp = new ArrayList<>();
                            for (int i = 0; i < permutation.size(); ++i) {
                                tmp.add(permutation.get(i));
                            }
                            for (int i = 0; l + i < r - i; ++i) {
                                int a = tmp.get(l + i);
                                int b = tmp.get(r - i);
                                tmp.set(l + i, b);
                                tmp.set(r - i, a);
                            }
                            Double prob = next.get(tmp);
                            if (prob == null) prob = 0.0;
                            prob += probability / (length * (length + 1) / 2);
                            next.put(tmp, prob);
                        }
                    }
                }
            });
            layer = next;
        }

        double[] answer = new double[1];
        layer.forEach(new BiConsumer<List<Integer>, Double>() {
            @Override
            public void accept(List<Integer> permutation, Double aDouble) {
                int invs = 0;
                for (int i = 0; i < permutation.size(); ++i) {
                    for (int j = i + 1; j < permutation.size(); ++j) {
                        if (permutation.get(i) > permutation.get(j)) {
                            invs++;
                        }
                    }
                }
                answer[0] += invs * aDouble;
            }
        });
        out.println(answer[0]);
    }
}
