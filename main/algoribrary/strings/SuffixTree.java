package algoribrary.strings;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class SuffixTree {
    public static final int root = 0;

    public final int[][] outgoing;
    public final int[] suffixLink;
    public final int[] begin;
    public final int[] length;

    public int vertices;

    private final int offset;
    private final int INF;

    private int[] text;
    private int textLength;

    private int activeVertex, remainder;

    public SuffixTree(final int capacity, final int alphabetSize, final int offset) {
        this.INF = Integer.MAX_VALUE / 2;
        this.offset = offset;
        this.outgoing = new int[capacity][alphabetSize];
        this.suffixLink = new int[capacity];
        this.begin = new int[capacity];
        this.length = new int[capacity];
        this.text = new int[capacity];
        this.length[root] = INF;
        this.vertices = 1;
    }

    public SuffixTree(final char[] text, final int alphabetSize, final int offset) {
        this(2 * text.length + 1, alphabetSize, offset);
        for (char letter : text) {
            append(letter);
        }
    }

    public SuffixTree(final int[] text, final int alphabetSize) {
        this(2 * text.length + 1, alphabetSize, 0);
        for (int letter : text) {
            append(letter);
        }
    }

    public void append(final char letter) {
        append(letter - offset);
    }

    public void append(final int letter) {
        text[textLength++] = letter;
        remainder++;
        // there's no suffixLink for root, so suffixLink[root] can be used as a fictive sate
        int indigentSuffixLink = root;
        while (remainder > 0) {
            walkDown();
            int edge = text[textLength - remainder];
            if (outgoing[activeVertex][edge] == 0) {
                outgoing[activeVertex][edge] = createVertex(textLength - remainder, INF);
                suffixLink[indigentSuffixLink] = activeVertex;
                indigentSuffixLink = root;
            } else if (text[begin[outgoing[activeVertex][edge]] + remainder - 1] == letter) {
                // there's such suffix in Suffix Tree
                suffixLink[indigentSuffixLink] = activeVertex;
                return;
            } else {
                // [activeVertex]-edge...-[child] --> [activeVertex]-edge...-[newVertex]-...-[child]
                int child = outgoing[activeVertex][edge];
                int newVertex = createVertex(begin[child], remainder - 1);
                outgoing[newVertex][letter] = createVertex(textLength - 1, INF);
                outgoing[newVertex][text[begin[child] + remainder - 1]] = child;
                begin[child] += remainder - 1;
                length[child] -= remainder - 1;
                outgoing[activeVertex][edge] = newVertex;
                suffixLink[indigentSuffixLink] = newVertex;
                indigentSuffixLink = newVertex;
            }
            if (activeVertex == root) {
                remainder--;
            } else {
                activeVertex = suffixLink[activeVertex];
            }
        }
    }

    private int createVertex(int begin, int length) {
        this.begin[vertices] = begin;
        this.length[vertices] = length;
        return vertices++;
    }

    private void walkDown() {
        int next = outgoing[activeVertex][text[textLength - remainder]];
        while (remainder > length[next]) {
            activeVertex = next;
            remainder -= length[next];
            next = outgoing[activeVertex][text[textLength - remainder]];
        }
    }
}
