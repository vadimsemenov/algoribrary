package algoribrary.graph;

import java.util.Arrays;

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

    /*package private*/ int[] flags;
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

    public void addEdge(int beginID, int endID, int weight, int capacity, int reversedEdgeID) {
        throw new UnsupportedOperationException();
    }

    public int addFlowEdge(int beginID, int endID, int capacity) {
        ensureCapacity(edgesCounter + 1);
        int id = edgesCounter;
        edgesCounter++;
        begin[id] = beginID;
        end[id] = endID;
        capacities[id] = capacity;
        reversedEdge[id] = addFlowEdge(endID, beginID, 0, id);
        return id;
    }

    public int addFlowEdge(int beginID, int endID, int capacity, int reversedId) {
        ensureCapacity(edgesCounter + 1);
        int id = edgesCounter;
        edgesCounter++;
        begin[id] = beginID;
        end[id] = endID;
        capacities[id] = capacity;
        reversedEdge[id] = reversedId;
        return id;
    }

    public void addWeightedEdge(int beginID, int endID, int weight) {
        throw new UnsupportedOperationException();
    }

    public void addFlowWeightedEdge(int beginID, int endID, int capacity, int weight) {
        throw new UnsupportedOperationException();
    }

    public void pushFlow(int edgeId, long pushed) {
        flows[edgeId] += pushed;
        flows[reversedEdge[edgeId]] -= pushed;
    }

    private void ensureCapacity(int capacity) {
        throw new UnsupportedOperationException();
    }

    private void initializeIncoming() {
        throw new UnsupportedOperationException();
    }

    private void initializeWeights() {
        throw new UnsupportedOperationException();
    }

    private void initializeCapacities() {
        throw new UnsupportedOperationException();
    }

    private void initializeFlows() {
        throw new UnsupportedOperationException();
    }

    private void initializeFlags() {
        throw new UnsupportedOperationException();
    }

    private void initializeReversed() {
        throw new UnsupportedOperationException();
    }
}

// TODO: fabrics
// TODO: Edge class
// TODO: Iterable (incoming, outgoing)
// TODO: getter/setter