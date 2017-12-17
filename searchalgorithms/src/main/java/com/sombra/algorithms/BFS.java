package com.sombra.algorithms;

import java.util.*;

/**
 * Created by bogdan on 18-Dec-17.
 */
public class BFS {

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

    public List<Integer> calculate(int start, int end) {
        final List<Integer> result =  new LinkedList<>();
        final Map<Integer, Integer> parentNodes = new HashMap<>();
        boolean visited[] = new boolean[nodeCount];
        final LinkedList<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        while (queue.size() != 0)
        {
            Integer vertex = queue.poll();

            for (Integer n :getAdjacentVertex(vertex)) {
                if ( n != null && !visited[n]) {
                    parentNodes.put(n, vertex);
                    visited[n] = true;
                    queue.add(n);
                }
                if (n != null && n == end) {
                    queue.clear();
                    break;
                }
            }
        }
        if (parentNodes.size() > 0) {
            Integer node = end;
            while(node != null) {
                result.add(node);
                node = parentNodes.get(node);
            }
            Collections.reverse(result);
        }
        return result;
    }
}
