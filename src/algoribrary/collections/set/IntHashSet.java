package algoribrary.collections.set;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by vadim on 19-04-2014.
 */
public class IntHashSet {
    private enum State  { EMPTY, ACTUAL, DELETED };

    private static final Random RANDOM = new Random();

    private static final int MAX_CAPACITY = 1 << 30;
    private static final int RATIO = 2;
    private static final int[] SHIFTS;
    private static final int[] ANOTHER_SHIFTS;

    static {
        SHIFTS = new int[4];
        for (int i = 0; i < SHIFTS.length; i++) {
            SHIFTS[i] = RANDOM.nextInt(31) + 1;
        }
        ANOTHER_SHIFTS = new int[4];
        for (int i = 0; i < ANOTHER_SHIFTS.length; i++) {
            ANOTHER_SHIFTS[i] = RANDOM.nextInt(31) + 1;
        }
    }

    private int[] elements;
    private State[] states;
    private int size;

    public IntHashSet() {
        this(2);
    }

    public IntHashSet(int capacity) {
        capacity *= RATIO;
        int newCapacity = Integer.highestOneBit(capacity);
        if (newCapacity < capacity) {
            newCapacity <<= 1;
        }
        elements = new int[newCapacity];
        states = new State[newCapacity];
        Arrays.fill(states, State.EMPTY);
    }

    public void add(int value) {
        if (contains(value)) {
            return;
        }
        ensureCapacity((size + 1) * RATIO);
        int hash = getHash(value);
        int step = getAnotherHash(value);
        int index = hash & (elements.length - 1);
        for (int i = 0; i < elements.length; i++) {
            if (states[index] != State.ACTUAL) {
                size++;
                elements[index] = value;
                states[index] = State.ACTUAL;
                return;
            }
            index = (index + step) & (elements.length - 1);
        }
    }

    public void delete(int value) {
        int hash = getHash(value);
        int step = getAnotherHash(value);
        int index = hash & (elements.length - 1);
        while (true) {
            if (states[index] == State.EMPTY) {
                break;
//                throw new NoSuchElementException(value);
            } else if (elements[index] == value) {
                if (states[index] == State.DELETED) {
                    break;
//                throw new NoSuchElementException(value);
                } else {
                    states[index] = State.DELETED;
                    size--;
                    break;
                }
            }
            index = (index + step) & (elements.length - 1);
        }
    }

    public boolean contains(int value) {
        int hash = getHash(value);
        int step = getAnotherHash(value);
        int index = hash & (elements.length - 1);
        while (true) {
            if (states[index] == State.EMPTY) {
                break;
            } else if (elements[index] == value) {
                return states[index] == State.ACTUAL;
            }
            index = (index + step) & (elements.length - 1);
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getHash(int value) {
        int hash = value;
        for (int shift : SHIFTS) {
            hash ^= value >>> shift;
        }
        return hash;
    }

    private int getAnotherHash(int value) {
        int hash = value;
        for (int shift : ANOTHER_SHIFTS) {
            hash ^= value >>> shift;
        }
        return hash | 1;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            if (elements.length == MAX_CAPACITY) {
                throw new OutOfMemoryError("To big capacity: " + capacity);
            }
            rebuilt(elements.length << 1);
        }
    }

    private void rebuilt(int capacity) {
        int[] oldElements = elements;
        State[] oldStates = states;
        elements = new int[capacity];
        states = new State[capacity];
        Arrays.fill(states, State.EMPTY);
        size = 0;
        for (int i = 0; i < oldElements.length; i++) {
            if (oldStates[i] == State.ACTUAL) {
                add(oldElements[i]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("set.in")));
        PrintWriter writer = new PrintWriter(new FileOutputStream("set.out"));
        String currentLine = reader.readLine();
        IntHashSet set = new IntHashSet();
        StringTokenizer tokenizer;
        while (currentLine != null) {
            tokenizer = new StringTokenizer(currentLine);
            String arg = tokenizer.nextToken();
            int value = Integer.parseInt(tokenizer.nextToken());
            switch (arg) {
                case "insert":
                    set.add(value);
                    break;
                case "delete":
                    set.delete(value);
                    break;
                case "exists":
                    writer.println(set.contains(value));
                    break;
                default:
                    throw new IllegalArgumentException(currentLine);
            }
            currentLine = reader.readLine();
        }
        reader.close();
        writer.close();
        System.err.println(System.currentTimeMillis() - startTime + ".ms");
    }
}
