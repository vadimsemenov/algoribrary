package algoribrary.collections;

import algoribrary.comparators.IntComparator;

import java.util.Arrays;

/**
 * Created by vadim on 23-03-2014.
 */
public class Heap {
    private static final int MAX_ELEMENT = Integer.MAX_VALUE - 10;

    private IntComparator comparator;
    private int[] elements;
    private int[] position;
    private int size;

    public Heap() {
        this(10);
    }

    public Heap(IntComparator comparator) {
        this(10, comparator);
    }

    public Heap(int maxElement) {
        this(maxElement, IntComparator.DEFAULT);
    }

    public Heap(int maxElement, IntComparator comparator) {
        this(10, maxElement, comparator);
    }

    public Heap(int capacity, int maxElement, IntComparator comparator) {
        elements = new int[capacity];
        position = new int[maxElement];
        this.comparator = comparator;
        Arrays.fill(position, -1);
    }

    public void add(int element) {
        ensureCapacity(size + 1);
        ensureMaxElement(element);
        elements[size] = element;
        position[element] = size;
        size++;
        siftUp(element);
    }

    public int peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return elements[0];
    }

    public int extract() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        int result = elements[0];
        position[result] = -1;
        size--;
        elements[0] = elements[size];
        position[elements[0]] = 0;
        siftDown(elements[0]);
        return result;
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

    private void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            int[] oldElements = elements;
            elements = new int[oldElements.length << 1];
            System.arraycopy(oldElements, 0, elements, 0, size);
        }
    }

    private void ensureMaxElement(int maxElement) {
        if (maxElement >= position.length) {
            int[] oldPosition = position;
            int newMaxElement = maxElement + 1;
            if (MAX_ELEMENT >> 1 > newMaxElement &&
                    newMaxElement < oldPosition.length << 1) {
                newMaxElement = oldPosition.length << 1;
            } else if (newMaxElement > MAX_ELEMENT) {
                throw new OutOfMemoryError();
            }
            position = new int[newMaxElement];
            Arrays.fill(position, -1);
            System.arraycopy(oldPosition, 0, position, 0, oldPosition.length);
        }
    }
}
