package algoribrary.graph;

import algoribrary.misc.ArrayUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by vadim on 23/10/14.
 */
public class Graph {
    protected int verticesCounter;
    protected int edgesCounter;

    /*package private*/ int[] begin;
    /*package private*/ int[] end;
    /*package private*/ int[] firstOutgoing;
    /*package private*/ int[] nextOutgoing;
    /*package private*/ int[] firstIncoming;
    /*package private*/ int[] nextIncoming;
    /*package private*/ int[] reversedEdge;

    /*package private*/ byte[] flags;
    /*package private*/ long[] weights;
    /*package private*/ long[] capacities;
    /*package private*/ long[] flows;

    private Graph(int verticesCounter, int edgesCapacity) {
        this.verticesCounter = verticesCounter;
        begin = new int[edgesCapacity];
        end = new int[edgesCapacity];
        firstOutgoing = new int[verticesCounter];
        Arrays.fill(firstOutgoing, -1);
        nextOutgoing = new int[edgesCapacity];
        Arrays.fill(nextOutgoing, -1);
    }

    private Graph(int verticesCounter, int edgesCounter, int[] begin, int[] end, int[] firstOutgoing,
                  int[] nextOutgoing, int[] firstIncoming, int[] nextIncoming, int[] reversedEdge,
                  byte[] flags, long[] weights, long[] capacities, long[] flows) {
        this.verticesCounter = verticesCounter;
        this.edgesCounter = edgesCounter;
        this.begin = begin.clone();
        this.end = end.clone();
        this.firstOutgoing = firstOutgoing.clone();
        this.nextOutgoing = nextOutgoing.clone();
        if (firstIncoming != null) {
            this.firstIncoming = firstIncoming.clone();
        }
        if (nextIncoming != null) {
            this.nextIncoming = nextIncoming.clone();
        }
        if (reversedEdge != null) {
            this.reversedEdge = reversedEdge.clone();
        }
        if (flags != null) {
            this.flags = flags.clone();
        }
        if (weights != null) {
            this.weights = weights.clone();
        }
        if (capacities != null) {
            this.capacities = capacities.clone();
        }
        if (flows != null) {
            this.flows = flows.clone();
        }
    }

    public static Graph createSimpleGraph(int verticesCounter, int edgesCapacity) {
        return new Graph(verticesCounter, edgesCapacity);
    }

    public static Graph createWightedGraph(int verticesCounter, int edgesCapacity) {
        Graph returned = new Graph(verticesCounter, edgesCapacity);
        returned.initializeWeights();
        return returned;
    }

    public static Graph createFlowGraph(int verticesCounter, int edgesCapacity) {
        Graph returned = new Graph(verticesCounter, edgesCapacity);
        returned.initializeFlows();
        returned.initializeCapacities();
        returned.initializeReversed();
        return returned;
    }

    public static Graph createFlowWeightedGraph(int verticesCounter, int edgesCapacity) {
        Graph returned = createFlowGraph(verticesCounter, edgesCapacity);
        returned.initializeWeights();
        return returned;
    }

    public Graph clone() {
        return new Graph(verticesCounter, edgesCounter, begin, end, firstOutgoing, nextOutgoing, firstIncoming,
                nextIncoming, reversedEdge, flags, weights, capacities, flows);
    }

    public int addEdge(int beginID, int endID) {
        throw new UnsupportedOperationException();
    }

    public int addFlowEdge(int beginID, int endID, long capacity) {
        ensureCapacity(edgesCounter + 1);
        int id = edgesCounter;
        edgesCounter++;
        begin[id] = beginID;
        end[id] = endID;
        capacities[id] = capacity;
        flows[id] = 0;
        reversedEdge[id] = addFlowEdge(endID, beginID, 0, id);
        nextOutgoing[id] = firstOutgoing[beginID];
        firstOutgoing[beginID] = id;
        return id;
    }

    public int addFlowEdge(int beginID, int endID, long capacity, int reversedID) {
        ensureCapacity(edgesCounter + 1);
        int id = edgesCounter;
        edgesCounter++;
        begin[id] = beginID;
        end[id] = endID;
        capacities[id] = capacity;
        flows[id] = 0;
        reversedEdge[id] = reversedID;
        nextOutgoing[id] = firstOutgoing[beginID];
        firstOutgoing[beginID] = id;
        return id;
    }

    public int addWeightedEdge(int beginID, int endID, long weight) {
        throw new UnsupportedOperationException();
    }

    public int addWeightedEdge(int beginID, int endID, long weight, int reversedID) {
        throw new UnsupportedOperationException();
    }

    public int addFlowWeightedEdge(int beginID, int endID, long capacity, long weight) {
        throw new UnsupportedOperationException();
    }

    public int addFlowWeightedEdge(int beginID, int endID, long capacity, long weight, int reversedID) {
        throw new UnsupportedOperationException();
    }

    public int begin(int edgeID) {
        return begin[edgeID];
    }

    public int end(int edgeID) {
        return end[edgeID];
    }

    public int nextOutgoing(int edgeID) {
        return nextOutgoing[edgeID];
    }

    public int nextIncomming(int edgeID) {
        initializeIncoming();
        return nextIncoming[edgeID];
    }

    public void pushFlow(int edgeID, long pushed) {
        flows[edgeID] += pushed;
        flows[reversedEdge[edgeID]] -= pushed;
    }

    public long weight(int edgeID) {
        return weights[edgeID];
    }

    public long residualCapacity(int edgeID) {
        return capacities[edgeID] - flows[edgeID];
    }

    public long initialCapacity(int edgeID) {
        return capacities[edgeID];
    }

    public long flow(int edgeID) {
        return flows[edgeID];
    }

    public void remove(int edgeID) {
        throw new UnsupportedOperationException();
    }

    public void setFlag(int edgeID, int flag) {
        initializeFlags();
        flags[edgeID] |= (1 << flag);
    }

    public void removeFlag(int edgeID, int flag) {
        initializeFlags();
        flags[edgeID] &= -1 ^ (1 << flag);
    }

    public boolean getFlag(int edgeID, int flag) {
        initializeFlags();
        return (flags[edgeID] >> flag & 1) == 1;
    }

    public Iterable<Integer> getOutgoingEdges(final int vertex) {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new EdgeIterator(firstOutgoing[vertex], nextOutgoing);
            }
        };
    }

    public Iterable<Integer> getIncomingEdges(final int vertex) {
        initializeIncoming();
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new EdgeIterator(firstIncoming[vertex], nextIncoming);
            }
        };
    }

    private void ensureCapacity(int capacity) {
        if (begin.length < capacity) {
            int oldSize = begin.length;
            int newSize = Math.max(capacity, 2 * begin.length);
            begin = ArrayUtils.resize(begin, newSize);
            end = ArrayUtils.resize(end, newSize);
            nextOutgoing = ArrayUtils.resize(nextOutgoing, newSize);
            Arrays.fill(nextOutgoing, oldSize, nextOutgoing.length, -1);
            if (nextIncoming != null) {
                nextIncoming = ArrayUtils.resize(nextIncoming, newSize);
            }
            if (reversedEdge != null) {
                reversedEdge = ArrayUtils.resize(reversedEdge, newSize);
            }
            if (flags != null) {
                flags = ArrayUtils.resize(flags, newSize);
            }
            if (weights != null) {
                weights = ArrayUtils.resize(weights, newSize);
            }
            if (capacities != null) {
                capacities = ArrayUtils.resize(capacities, newSize);
            }
            if (flows != null) {
                flows = ArrayUtils.resize(flows, newSize);
            }
        }
    }

    private void initializeIncoming() {
        if (firstIncoming == null) {
            firstIncoming = new int[firstOutgoing.length];
            Arrays.fill(firstIncoming, -1);
            nextIncoming = new int[nextOutgoing.length];
            Arrays.fill(nextIncoming, -1);
            for (int e = 0; e < edgesCounter; ++e) {
                nextIncoming[end[e]] = firstIncoming[end[e]];
                firstIncoming[end[e]] = e;
            }
        }
    }

    private void initializeWeights() {
        if (weights == null) {
            weights = new long[begin.length];
        }
    }

    private void initializeCapacities() {
        if (capacities == null) {
            capacities = new long[begin.length];
        }
    }

    private void initializeFlows() {
        if (flows == null) {
            flows = new long[begin.length];
        }
    }

    private void initializeFlags() {
        if (flags == null) {
            flags = new byte[begin.length];
        }
    }

    private void initializeReversed() {
        if (reversedEdge == null) {
            reversedEdge = new int[begin.length];
        }
    }

    public final class EdgeIterator implements Iterator<Integer> {
        private int currentID, nextID;
        private int[] nextEdge;

        public EdgeIterator(int nextID, int[] nextEdge) {
            this.currentID = -1;
            this.nextID = nextID;
            this.nextEdge = nextEdge;
        }

        @Override
        public boolean hasNext() {
            return nextID != -1;
        }

        @Override
        public Integer next() {
            if (nextID == -1) {
                throw new NoSuchElementException();
            }
            currentID = nextID;
            nextID = nextEdge[currentID];
            return currentID;
        }

        @Override
        public void remove() {
            if (currentID == -1) {
                throw new IllegalStateException();
            }
            Graph.this.remove(currentID);
        }
    }
}

// TODO: Edge class (?)