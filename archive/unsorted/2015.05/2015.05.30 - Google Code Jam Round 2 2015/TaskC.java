package tasks;

import algoribrary.graph.Flow;
import algoribrary.graph.Graph;
import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
//        stress();
        int counter = in.nextInt();
        String[][] sentences = new String[counter][];
        for (int i = 0; i < counter; ++i) {
            StringTokenizer tokenizer = new StringTokenizer(in.readLine());
            sentences[i] = new String[tokenizer.countTokens()];
            int ptr = 0;
            while (tokenizer.hasMoreElements()) {
                sentences[i][ptr++] = tokenizer.nextToken();
            }
        }

//        int answer = solveSmall(sentences);
        int answer = solveLarge(sentences);
               out.println("Case #" + testNumber + ": " + answer);
        System.err.println("Case #" + testNumber + ": " + answer);
    }

    private void stress() {
        final Random rng = new Random(58);
        final int sentencesQty = 20;
        final int largeSize = 1000;
        final int smallSize = 10;
        final int wordLength = 3;
        final int letters = 2;
        for (int iteration = 1; ; ++iteration) {
            if (iteration % 100 == 0) System.err.println(iteration + " iterations done");
            String[][] sentences = new String[sentencesQty][];
            for (int i = 0; i < 2; ++i) {
                sentences[i] = new String[largeSize];
            }
            for (int i = 2; i < sentences.length; ++i) {
                sentences[i] = new String[smallSize];
            }
            for (String[] sentence : sentences) {
                for (int i = 0; i < sentence.length; ++i) {
                    sentence[i] = "";
                    for (int letter = 0; letter < wordLength; ++letter) {
                        sentence[i] += (char) ('a' + rng.nextInt(letters));
                    }
                }
            }
            int expected = solveSmall(sentences);
            int found = solveLarge(sentences);
            if (expected != found) {
                System.err.println("File :(");
                System.err.println(Arrays.deepToString(sentences));
                System.err.println("Expected: " + expected);
                System.err.println("Found: " + found);
                System.exit(42);
            }
        }
    }

    private int solveLarge(String[][] sentences) {
        Map<String, Integer> id = new HashMap<>();
        int nextId = sentences.length;
        int edges = 0;
        for (String[] sentence : sentences) {
            for (String word : sentence) {
                ++edges;
                if (!id.containsKey(word)) {
                    id.put(word, nextId++);
                }
            }
        }
        Graph graph = Graph.createFlowGraph(sentences.length + 2 * id.size(), 2 * edges + id.size());
        final int source = 0;
        final int sink = 1;
        int[] degree = new int[id.size()];
        for (int sentenceId = 0; sentenceId < sentences.length; ++sentenceId) {
            final int finalSentenceId = sentenceId;
            Arrays.stream(sentences[sentenceId]).distinct().forEach(new Consumer<String>() {
                @Override
                public void accept(String word) {
                    int wordId = id.get(word);
                    ++degree[wordId - sentences.length];
                    graph.addFlowEdge(finalSentenceId, wordId, 1);
                    graph.addFlowEdge(id.size() + wordId, finalSentenceId, 1);
                }
            });
        }
        for (int wordId = sentences.length; wordId < sentences.length + id.size(); ++wordId) {
            graph.addFlowEdge(wordId, id.size() + wordId, degree[wordId - sentences.length]);
        }
        int answer = 0;
        boolean[] cut = Flow.getMinCut(graph, source, sink);
        for (int wordId = sentences.length; wordId < sentences.length + id.size(); ++wordId) {
            boolean english = false;
            boolean french = false;
            for (int edgeId : graph.getOutgoingEdges(wordId)) {
                english |= cut[graph.end(edgeId)];
                french |= !cut[graph.end(edgeId)];
            }
            for (int edgeId : graph.getOutgoingEdges(id.size() + wordId)) {
                english |= cut[graph.end(edgeId)];
                french |= !cut[graph.end(edgeId)];
            }
            if (english && french) {
                ++answer;
            }
        }
        return answer;
    }

    private int solveSmall(String[][] sentences) {
        Map<String, List<Integer>> entering = new HashMap<>();
        int no = 0;
        for (String[] sentence : sentences) {
            for (String word : sentence) {
                List<Integer> list = entering.get(word);
                if (list == null) {
                    list = new ArrayList<>();
                    entering.put(word, list);
                }
                list.add(no);
            }
            ++no;
        }
        int answer = entering.size();
        for (int mask = 0; mask < (1 << sentences.length); ++mask) {
            final int[] current = {0};
            final int finalMask = mask;
            entering.forEach(new BiConsumer<String, List<Integer>>() {
                @Override
                public void accept(String word, List<Integer> list) {
                    boolean english = false;
                    boolean french = false;
                    for (int sentence : list) {
                        english |= sentence == 0;
                        french |= sentence == 1;
                        english |= (finalMask >>> sentence & 1) == 1;
                        french |= (finalMask >>> sentence & 1) == 0;
                    }
                    if (english && french) {
                        ++current[0];
                    }
                }
            });
            answer = Math.min(answer, current[0]);
        }
        return answer;
    }
}
