package algoribrary.collections.map;

import algoribrary.collections.State;
import algoribrary.collections.set.StringHashSet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by vadim on 22-04-2014.
 */
public class StringMultiHashMap {
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
    private String[] keys;
    private StringHashSet[] values;
    private State[] states;

    public StringMultiHashMap() {
        this(8);
    }

    public StringMultiHashMap(int initialCapacity) {
        if (initialCapacity > MAX_CAPACITY) {
            throw new OutOfMemoryError("To big capacity: " + initialCapacity);
        }
        int capacity = Integer.highestOneBit(initialCapacity);
        if (capacity < initialCapacity) {
            capacity <<= 1;
        }
        keys = new String[capacity];
        values = new StringHashSet[capacity];
        states = new State[capacity];
        Arrays.fill(states, State.EMPTY);
    }

    public void put(String key, String value) {
        ensureCapacity((size + 1) * RATIO);
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        int insertPosition = -1;
        while (true) {
            if (keys[index] != null && keys[index].equals(key)) {
                if (values[index] == null) {
                    values[index] = new StringHashSet();
                }
                values[index].add(value);
                return;
            }
            if (states[index] != State.ACTUAL && insertPosition == -1) {
                insertPosition = index;
            }
            if (states[index] == State.EMPTY) {
                break;
            }
            index = (index + step) & (keys.length - 1);
        }
        keys[insertPosition] = key;
        values[insertPosition] = new StringHashSet();
        values[insertPosition].add(value);
        states[insertPosition] = State.ACTUAL;
        size++;
    }

    public void putAll(String key, StringHashSet set) {
        ensureCapacity((size + 1) * RATIO);
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        int insertPosition = -1;
        while (true) {
            if (keys[index] != null && keys[index].equals(key)) {
                values[index].addAll(set);
                return;
            }
            if (states[index] != State.ACTUAL && insertPosition == -1) {
                insertPosition = index;
            }
            if (states[index] == State.EMPTY) {
                break;
            }
            index = (index + step) & (keys.length - 1);
        }
        keys[insertPosition] = key;
        values[insertPosition] = set;
        states[insertPosition] = State.ACTUAL;
        size++;
    }

    public void delete(String key, String value) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                values[index].delete(value);
                if (values[index].size() == 0) {
                    keys[index] = null;
                    values[index] = null;
                    states[index] = State.DELETED;
                    size--;
                }
                return;
            }
            index = (index + step) & (keys.length - 1);
        }
//        throw new NoSuchElementException(key);
    }

    public void delete(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                keys[index] = null;
                values[index] = null;
                states[index] = State.DELETED;
                size--;
                return;
            }
            index = (index + step) & (keys.length - 1);
        }
//        throw new NoSuchElementException(key);
    }

    public StringHashSet get(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                return values[index];
            }
            index = (index + step) & (keys.length - 1);
        }
        return null;
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
        if (keys.length < capacity) {
            if (capacity > MAX_CAPACITY) {
                throw new OutOfMemoryError("To big capacity: " + capacity);
            }
            int newCapacity = Integer.highestOneBit(capacity);
            if (newCapacity < capacity) {
                newCapacity <<= 1;
            }
            String[] oldKeys = keys;
            StringHashSet[] oldValues = values;
            State[] oldStates = states;
            keys = new String[newCapacity];
            values = new StringHashSet[newCapacity];
            states = new State[newCapacity];
            size = 0;
            Arrays.fill(states, State.EMPTY);
            for (int i = 0; i < oldKeys.length; i++) {
                if (oldStates[i] == State.ACTUAL) {
                    putAll(oldKeys[i], oldValues[i]);
                }
            }
        }
    }
}
