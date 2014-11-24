package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class DependencyProblem {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        Map<String, Integer> map = new HashMap<>();
        List<List<Integer>> graph = new ArrayList<>();
        List<Set<Integer>> dependencies = new ArrayList<>();
        List<Integer> queue = new ArrayList<>();
        Set<Integer> available = new HashSet<>();
        int cnt = 0;
        while (in.hasNext()) {
            String currentPackage = in.next();
            Integer currentKey = map.get(currentPackage);
            if (currentKey == null) {
                currentKey = cnt++;
                map.put(currentPackage, currentKey);
                graph.add(new ArrayList<Integer>());
                dependencies.add(new HashSet<Integer>());
            }
            String need = in.next();
            while (!need.equals("0")) {
                Integer key = map.get(need);
                if (key == null) {
                    key = cnt++;
                    map.put(need, key);
                    graph.add(new ArrayList<Integer>());
                    dependencies.add(new HashSet<Integer>());
                }
                graph.get(key).add(currentKey);
                dependencies.get(currentKey).add(key);
                need = in.next();
            }
            if (dependencies.get(currentKey).size() == 0) {
                available.add(currentKey);
                queue.add(currentKey);
            }
        }
        for (int head = 0; head < queue.size(); ++head) {
            int current = queue.get(head);
            for (int next : graph.get(current)) {
                dependencies.get(next).remove(current);
                if (dependencies.get(next).size() == 0) {
                    queue.add(next);
                    available.add(next);
                }
            }
        }
        out.println(available.size());
    }
}
