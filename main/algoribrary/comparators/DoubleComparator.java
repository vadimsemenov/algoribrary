package algoribrary.comparators;

/**
 * @author Vadim Semenov
 */
public interface DoubleComparator {
    public DoubleComparator DEFAULT = new DoubleComparator() {
        public static final double EPS = 1e-9;

        @Override
        public int compare(double first, double second) {
            if (first + EPS < second) return -1;
            if (first > second + EPS) return 1;
            return 0;
        }
    };

    public int compare(double first, double second);
}
