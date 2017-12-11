package com.sombra.algorithms;

import java.util.*;

import static java.util.Arrays.fill;

public class Dijkstra {
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
        boolean[] used = new boolean[nodeCount];
        int[] dist = new int[nodeCount];

        fill(dist, INF);
        dist[start] = 0;
        result.put(start, new LinkedList<>(Arrays.asList(start)));

        for (; ; ) {
            int currentNode = -1;
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
                if (!used[nodeIndex] && dist[nodeIndex] < INF && (currentNode == -1 || dist[currentNode] > dist[nodeIndex])) {
                    currentNode = nodeIndex;
                }
            }
            if (currentNode == -1) {
                break;
            }
            used[currentNode] = true;
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
                if (!used[nodeIndex] && graph[currentNode][nodeIndex] != null && graph[currentNode][nodeIndex] < INF) {
                    if ( dist[currentNode] + graph[currentNode][nodeIndex] < dist[nodeIndex]) {
                        List<Integer> path = new LinkedList<>(result.get(currentNode));
                        path.add(nodeIndex);
                        result.put(nodeIndex, path);
                        dist[nodeIndex] = dist[currentNode] + graph[currentNode][nodeIndex];
                    } else {
                        dist[nodeIndex] = dist[nodeIndex];
                    }
                }
            }
        }
        return result;
    }
}
