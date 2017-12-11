package com.sombra.algorithms;

import java.util.Arrays;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

/**
 * Created by bogdan on 05-Dec-17.
 */
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

    public void calculate(int start) {
        boolean[] used = new boolean[nodeCount];
        int[] dist = new int[nodeCount];

        fill(dist, INF);
        dist[start] = 0;

        for (; ; ) {
            int v = -1;
            for (int nv = 0; nv < nodeCount; nv++) {
                if (!used[nv] && dist[nv] < INF && (v == -1 || dist[v] > dist[nv])) {
                    v = nv;
                }
            }
            if (v == -1) {
                break;
            }
            used[v] = true;
            for (int nv = 0; nv < nodeCount; nv++) {
                if (!used[nv] && graph[v][nv] != null && graph[v][nv] < INF) {
                    dist[nv] = min(dist[nv], dist[v] + graph[v][nv]);
                }
            }
        }
        System.out.println(Arrays.asList(dist));
    }
}
