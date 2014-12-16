package tasks;

import algoribrary.graph.Flow;
import algoribrary.graph.Graph;
import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int locations = in.nextInt();
        int sourceCity = in.nextInt() - 1;
        int people = in.nextInt();
        int haveTime = in.nextInt() + 1;
        int sink = haveTime * locations;
        int totalVertices = sink + 1;
        Graph graph = Graph.createFlowGraph(totalVertices, totalVertices);
        for (int time = 0; time + 1 < haveTime; ++time) {
            graph.addFlowEdge(hash(sourceCity, time, haveTime), hash(sourceCity, time + 1, haveTime), people);
        }
        int medicalFacilities = in.nextInt();
        for (int i = 0; i < medicalFacilities; ++i) {
            int facility = in.nextInt() - 1;
            for (int time = 0; time < haveTime; ++time) {
                graph.addFlowEdge(hash(facility, time, haveTime), sink, people);
            }
        }
        int roads = in.nextInt();
        for (int i = 0; i < roads; ++i) {
            int begin = in.nextInt() - 1;
            int end = in.nextInt() - 1;
            int capacity = in.nextInt();
            int timesteps = in.nextInt();
            for (int time = 0; time + timesteps < haveTime; ++time) {
                graph.addFlowEdge(hash(begin, time, haveTime), hash(end, time + timesteps, haveTime), capacity);
            }
        }
        out.println(Math.min(people, Flow.dinic(graph, hash(sourceCity, 0, haveTime), sink)));
    }

    private int hash(int location, int time, int totalTime) {
        return location * totalTime + time;
    }
}
