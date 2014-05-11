package algoribrary.comparators;

/**
 * Created by vadim on 23-03-2014.
 */
public interface IntComparator {
    public static final IntComparator DEFAULT = new IntComparator() {
        @Override
        public int compare(int first, int second) {
            if (first > second) return 1;
            if (first < second) return -1;
            return 0;
        }
    };

    public int compare(int first, int second);
}
