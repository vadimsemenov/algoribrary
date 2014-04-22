package algoribrary.collections.set;

import algoribrary.collections.State;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by vadim on 22-04-2014.
 */
public class StringHashSet implements Iterable<String> {
    private static final Random RANDOM = new Random();
    private static final int MAX_CAPACITY = 1 << 30;
    private static final int RATIO = 2;
    private static final long FIRST_BASE;
    private static final long FIRST_MODULO;
    private static final long SECOND_BASE;
    private static final long SECOND_MODULO;

    static {
        FIRST_BASE = BigInteger.probablePrime(RANDOM.nextInt(11) + 17, RANDOM).longValue();
        FIRST_MODULO = BigInteger.probablePrime(RANDOM.nextInt(11) + 17, RANDOM).longValue();
        SECOND_BASE = BigInteger.probablePrime(RANDOM.nextInt(11) + 17, RANDOM).longValue();
        SECOND_MODULO = BigInteger.probablePrime(RANDOM.nextInt(11) + 17, RANDOM).longValue();
    }

    private int size;
    private String[] values;
    private State[] states;

    public StringHashSet() {
        this(16);
    }

    public StringHashSet(int initialCapacity) {
        if (initialCapacity > MAX_CAPACITY) {
            throw new OutOfMemoryError("To big capacity: " + initialCapacity);
        }
        int capacity = Integer.highestOneBit(initialCapacity);
        if (capacity < initialCapacity) {
            capacity <<= 1;
        }
        values = new String[capacity];
        states = new State[capacity];
        Arrays.fill(states, State.EMPTY);
    }

    public void add(String value) {
        if (contains(value)) {
            return;
        }
        ensureCapacity((size + 1) * RATIO);
        int hash = getHash(value);
        int step = getAnotherHash(value);
        int index = hash & (values.length - 1);
        while (states[index] == State.ACTUAL) {
            index = (index + step) & (values.length - 1);
        }
        values[index] = value;
        states[index] = State.ACTUAL;
        size++;
    }

    public void addAll(StringHashSet set) {
        if (set == null) {
            return;
        }
        for (String string : set) {
            add(string);
        }
    }

    public boolean contains(String value) {
        int hash = getHash(value);
        int step = getAnotherHash(value);
        int index = hash & (values.length - 1);
        while (states[index] != State.EMPTY) {
            if (values[index] != null && values[index].equals(value)) {
                return true;
            }
            index = (index + step) & (values.length - 1);
        }
        return false;
    }

    public void delete(String value) {
        if (!contains(value)) {
            return;
//            throw new NoSuchElementException(value);
        }
        int hash = getHash(value);
        int step = getAnotherHash(value);
        int index = hash & (values.length - 1);
        while (states[index] != State.EMPTY) {
            if (values[index] != null && values[index].equals(value)) {
                values[index] = null;
                states[index] = State.DELETED;
                size--;
                if (values.length > 4 && size * RATIO < (values.length >> 2)) {
                    rehash(values.length >> 1);
                }
            }
            index = (index + step) & (values.length - 1);
        }
//        throw NoSuchElementException(value);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int pointer;
            private int was;

            @Override
            public boolean hasNext() {
                return pointer < values.length && was < size;
            }

            @Override
            public String next() {
                while (pointer < states.length && states[pointer] != State.ACTUAL) {
                    pointer++;
                }
                if (pointer == values.length) {
                    throw new NoSuchElementException();
                }
                was++;
                return values[pointer++];
            }

            @Override
            public void remove() {
                throw new NoSuchMethodError();
            }
        };
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getHash(String value) {
        long result = 0;
        for (int i = 0; i < value.length(); i++) {
            result = (result * FIRST_BASE + value.charAt(i)) % FIRST_MODULO;
        }
        return (int) result ^ (int) (result >>> 32);
    }

    private int getAnotherHash(String value) {
        long result = 0;
        for (int i = 0; i < value.length(); i++) {
            result = (result * SECOND_BASE + value.charAt(i)) % SECOND_MODULO;
        }
        return ((int) result ^ (int) (result >>> 32)) | 1;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > values.length) {
            if (capacity > MAX_CAPACITY) {
                throw new OutOfMemoryError("To big capacity: " + capacity);
            }
            int newCapacity = Integer.highestOneBit(capacity);
            if (newCapacity < capacity) {
                newCapacity <<= 1;
            }
            rehash(newCapacity);
        }
    }

    private void rehash(int capacity) {
        if (capacity < size) {
            throw new AssertionError("size > capacity");
        }
        String[] oldValues = values;
        State[] oldStates = states;
        values = new String[capacity];
        states = new State[capacity];
        size = 0;
        Arrays.fill(states, State.EMPTY);
        for (int i = 0; i < oldStates.length; i++) {
            if (oldStates[i] == State.ACTUAL) {
                add(oldValues[i]);
            }
        }
    }
}
