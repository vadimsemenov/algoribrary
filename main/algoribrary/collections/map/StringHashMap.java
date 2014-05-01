package algoribrary.collections.map;

import algoribrary.collections.State;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by vadim on 21-04-2014.
 */
public class StringHashMap {
    private static final int MAX_CAPACITY = 1 << 30;
    private static final int RATIO = 2;
    private static final Random RANDOM = new Random();
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

    private String[] keys;
    private String[] values;
    private State[] states;
    private int size;

    public StringHashMap() {
        this(8);
    }

    public StringHashMap(int capacity) {
        capacity *= RATIO;
        int newCapacity = Integer.highestOneBit(capacity);
        if (newCapacity < capacity) {
            newCapacity <<= 1;
        }
        keys = new String[newCapacity];
        values = new String[newCapacity];
        states = new State[newCapacity];
        Arrays.fill(states, State.EMPTY);
    }

    public void put(String key, String value) {
        ensureCapacity((size + 1) * RATIO);
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int insertPosition = -1;
        int index = hash & (keys.length - 1);
        while (true) {
            if (keys[index] != null && keys[index].equals(key)) {
                values[index] = value;
                states[index] = State.ACTUAL;
                return;
            } else if (states[index] != State.ACTUAL && insertPosition == -1) {
                insertPosition = index;
            }
            if (states[index] == State.EMPTY) {
                break;
            }
            index = (index + step) & (keys.length - 1);
        }
        keys[insertPosition] = key;
        values[insertPosition] = value;
        states[insertPosition] = State.ACTUAL;
        size++;
    }

    public void delete(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                size--;
                keys[index] = null;
                values[index] = null;
                states[index] = State.DELETED;
                return;
            }
            index = (index + step) & (keys.length - 1);
        }
    }

    public String get(String key) {
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
        return ((int) result ^ (int) (result >>> 32));
    }

    private int getAnotherHash(String value) {
        long result = 0;
        for (int i = 0; i < value.length(); i++) {
            result = (result * SECOND_BASE + value.charAt(i)) % SECOND_MODULO;
        }
        return ((int) result ^ (int) (result >>> 32)) | 1;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > keys.length) {
            if (keys.length == MAX_CAPACITY || capacity > MAX_CAPACITY) {
                throw new OutOfMemoryError("To big capacity: " + capacity);
            }
            int newCapacity = keys.length << 1;
            while (newCapacity < capacity && newCapacity < MAX_CAPACITY) {
                newCapacity <<= 1;
            }
            String[] oldKeys = keys;
            String[] oldValues = values;
            State[] oldStates = states;
            size = 0;
            keys = new String[newCapacity];
            values = new String[newCapacity];
            states = new State[newCapacity];
            Arrays.fill(states, State.EMPTY);
            for (int i = 0; i < oldKeys.length; i++) {
                if (oldStates[i] == State.ACTUAL) {
                    put(oldKeys[i], oldValues[i]);
                }
            }
        }
    }
}
