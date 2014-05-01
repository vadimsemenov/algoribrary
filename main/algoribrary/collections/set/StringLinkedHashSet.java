package algoribrary.collections.set;

import algoribrary.collections.State;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by vadim on 21-04-2014.
 */
public class StringLinkedHashSet implements Iterable<String> {
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

    private String[] keys;
    private State[] states;
    private int[] pred;
    private int[] succ;
    private int lastIndex;
    private int size;

    public StringLinkedHashSet() {
        this(8);
    }

    public StringLinkedHashSet(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new OutOfMemoryError("To big capacity: " + capacity);
        }
        int newCapacity = Integer.highestOneBit(capacity);
        if (newCapacity < capacity) {
            newCapacity <<= 1;
        }
        lastIndex = -1;
        keys = new String[newCapacity];
        states = new State[newCapacity];
        Arrays.fill(states, State.EMPTY);
        pred = new int[newCapacity];
        Arrays.fill(pred, -1);
        succ = new int[newCapacity];
        Arrays.fill(succ, -1);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int pointer = lastIndex;

            @Override
            public boolean hasNext() {
                return pointer != -1;
            }

            @Override
            public String next() {
                String result = keys[pointer];
                pointer = pred[pointer];
                return result;
            }

            @Override
            public void remove() {
                throw new NoSuchMethodError("remove");
            }
        };
    }

    public void addAll(StringLinkedHashSet set) {
        for (String element : set) {
            add(element);
        }
    }

    public void add(String key) {
        if (contains(key)) {
            return;
        }
        ensureCapacity((size + 1) * RATIO);
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] == State.ACTUAL) {
            index = (index + step) & (keys.length - 1);
        }
        {
            pred[index] = lastIndex;
            if (lastIndex != -1) {
                succ[lastIndex] = index;
            }
            lastIndex = index;
        }
        size++;
        keys[index] = key;
        states[index] = State.ACTUAL;
    }

    private boolean contains(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                return true;
            }
            index = (index + step) & (keys.length - 1);
        }
        return false;
    }

    public void delete(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                {
                    if (pred[index] != -1) {
                        succ[pred[index]] = succ[index];
                    }
                    if (succ[index] != -1) {
                        pred[succ[index]] = pred[index];
                    }
                    if (lastIndex == index) {
                        lastIndex = pred[index];
                    }
                }
                size--;
                keys[index] = null;
                pred[index] = succ[index] = -1;
                states[index] = State.DELETED;
                return;
            }
            index = (index + step) & (keys.length - 1);
        }
    }

    public String pred(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                return pred[index] == -1 ? null : keys[pred[index]];
            }
            index = (index + step) & (keys.length - 1);
        }
        return null;
    }

    public String succ(String key) {
        int hash = getHash(key);
        int step = getAnotherHash(key);
        int index = hash & (keys.length - 1);
        while (states[index] != State.EMPTY) {
            if (keys[index] != null && keys[index].equals(key)) {
                return succ[index] == -1 ? null : keys[succ[index]];
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
            if (keys.length == MAX_CAPACITY || capacity > MAX_CAPACITY) {
                throw new OutOfMemoryError("To big capacity: " + capacity);
            }
            int newCapacity = keys.length << 1;
            while (newCapacity < capacity && newCapacity < MAX_CAPACITY) {
                newCapacity <<= 1;
            }
            String[] oldKeys = keys;
            int[] temp = new int[size];
            for (int pointer = temp.length - 1; pointer >= 0; pointer--) {
                temp[pointer] = lastIndex;
                lastIndex = pred[lastIndex];
            }
            size = 0;
            keys = new String[newCapacity];
            states = new State[newCapacity];
            Arrays.fill(states, State.EMPTY);
            pred = new int[newCapacity];
            Arrays.fill(pred, -1);
            succ = new int[newCapacity];
            Arrays.fill(succ, -1);
            for (int index : temp) {
                add(oldKeys[index]);
            }
        }
    }
}