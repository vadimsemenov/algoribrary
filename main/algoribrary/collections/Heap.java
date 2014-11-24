package algoribrary.collections;

import algoribrary.comparators.IntComparator;

import java.util.Arrays;

/**
 * Created by vadim on 23-03-2014.
 */
public class Heap {
    private static final int MAX_ELEMENT = Integer.MAX_VALUE - 1;

    private IntComparator comparator;
    private int[] elements;
    private int[] position;
    private int size;

    public Heap(int capacity, int maxElement, IntComparator comparator) {
        if (maxElement > MAX_ELEMENT) {
            throw new IllegalArgumentException("maxElement must be less or equal " + MAX_ELEMENT);
        }
        elements = new int[capacity];
        position = new int[maxElement + 1];
        this.comparator = comparator;
        Arrays.fill(elements, -1);
        Arrays.fill(position, -1);
    }

    public void add(int element) {
        if (element >= position.length) {
            throw new IllegalArgumentException(element + " > " + (position.length - 1));
        }
        if (size == elements.length) {
            throw new IndexOutOfBoundsException("tried to add new element in full heap");
        }
        elements[size] = element;
        position[element] = size;
        size++;
        siftUp(elements[size - 1]);
    }

    public int peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return elements[0];
    }

    public int pop() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        int result = elements[0];
        size--;
        swap(0, size);
        position[elements[size]] = -1;
        elements[size] = -1;
        if (size > 0) {
            siftDown(elements[0]);
        }
        return result;
    }

    public boolean contains(int element) {
        if (element >= position.length) {
            throw new IllegalArgumentException(element + " > " + (position.length - 1));
        }
        return position[element] != -1;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void siftUp(int element) {
        int at = position[element];
        while (at > 0) {
            int parent = (at - 1) >> 1;
            if (comparator.compare(elements[parent], elements[at]) > 0) {
                swap(at, parent);
                at = parent;
            } else {
                break;
            }
        }
    }

    public void siftDown(int element) {
        int at = position[element];
        while (true) {
            int next = at;
            int left = (at << 1) + 1;
            int right = (at << 1) + 2;
            if (left < size &&
                    comparator.compare(elements[next], elements[left]) > 0) {
                next = left;
            }
            if (right < size &&
                    comparator.compare(elements[next], elements[right]) > 0) {
                next = right;
            }
            if (next == at) {
                break;
            }
            swap(at, next);
            at = next;
        }
    }

    private void swap(int first, int second) {
        int temp = elements[first];
        elements[first] = elements[second];
        elements[second] = temp;
        position[elements[first]] = first;
        position[elements[second]] = second;
    }
}
