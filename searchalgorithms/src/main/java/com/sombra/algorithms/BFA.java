package com.sombra.algorithms;

import java.util.*;

import static java.util.Arrays.fill;


public class BFA {
    private int INF = Integer.MAX_VALUE / 2;
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

    public Map<Integer, List<Integer>> calculate(int start) {
        Map<Integer, List<Integer>> result =  new HashMap<>();
        int[] dist = new int[nodeCount];
        fill(dist, INF);
        dist[start] = 0;
        result.put(start, new LinkedList<>(Arrays.asList(start)));
        for (int step = 1; step < nodeCount; step++) {
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
                if (dist[nodeIndex] < INF) {
                    for (int j = 0; j < nodeCount; j++) {
                        if (graph[nodeIndex][j] != null &&  dist[nodeIndex] + graph[nodeIndex][j] < dist[j]) {
                            List<Integer> path = new LinkedList<>(result.get(nodeIndex));
                            path.add(j);
                            result.put(j, path);
                            dist[j] = dist[nodeIndex] + graph[nodeIndex][j];
                        }
                    }
                }
            }
        }
        return result;
    }
}
