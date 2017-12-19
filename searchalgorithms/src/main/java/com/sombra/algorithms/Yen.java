package com.sombra.algorithms;

import com.sombra.Edge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by bogdan on 19-Dec-17.
 */
public class Yen {

    private int nodeCount;
    private Integer[][] graph;

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Integer[][] getGraph() {
        return graph;
    }

    public void setGraph(Integer[][] graph) {
        this.graph = graph;
    }

    private List<Integer> getAdjacentVertex(Integer vertex) {
        final List<Integer> result = new LinkedList<>();
        for (int i = 0; i < nodeCount; i++) {
            if (graph[vertex][i] != null && vertex != i) {
                result.add(i);
            }
        }
        return result;
    }

    public List<List<Integer>> calculate(int start, int end, int k) {
        Integer[][] calculateGraph = graph.clone();
        for (int j = 0; j < nodeCount; j++) {
            calculateGraph[j] = graph[j].clone();
        }
        List<List<Integer>> paths = new LinkedList<>();
        Dijkstra dijkstra = new Dijkstra(nodeCount, calculateGraph);
        Map<Integer, List<Integer>> calculate = dijkstra.calculate(start);
        List<Integer> path = calculate.get(end);
        paths.add(path);
        for (int i = 0; i < k - 1; i++) {
            Integer[][] pairSearch = calculateGraph.clone();
            for (int j = 0; j < nodeCount; j++) {
                pairSearch[j] = calculateGraph[j].clone();
            }
            List<Integer> lastPath = paths.get(paths.size() - 1);
            Map<Edge, List<Integer>> possiblePaths = new HashMap<>();
            for (int j = 0; j < lastPath.size() - 1; j++) {
                pairSearch[lastPath.get(j)][lastPath.get(j + 1)] = null;
                pairSearch[lastPath.get(j + 1)][lastPath.get(j)] = null;
                Dijkstra alg = new Dijkstra(nodeCount, pairSearch);
                Map<Integer, List<Integer>> result = alg.calculate(start);
                List<Integer> newPath = result.get(end);
                possiblePaths.put(new Edge(lastPath.get(j), lastPath.get(j + 1), null), newPath);
                pairSearch[lastPath.get(j)][lastPath.get(j + 1)] = graph[lastPath.get(j)][lastPath.get(j + 1)];
                pairSearch[lastPath.get(j + 1)][lastPath.get(j)] = pairSearch[lastPath.get(j)][lastPath.get(j + 1)];
            }
            List<Integer> minPath = new LinkedList<>();
            Edge resultEdge = null;
            Integer distance = Integer.MAX_VALUE;
            for (Map.Entry<Edge, List<Integer>> pair: possiblePaths.entrySet()) {
                int d = dijkstra.getDistance(pair.getValue());
                if (d < distance) {
                    distance = d;
                    resultEdge = pair.getKey();
                    minPath = pair.getValue();
                }
            }
            if (minPath.size() > 0 && resultEdge != null) {
                paths.add(minPath);
                calculateGraph[resultEdge.getStart()][resultEdge.getEnd()] = null;
                calculateGraph[resultEdge.getEnd()][resultEdge.getStart()] = null;
            }
        }
        return paths;
    }
}
