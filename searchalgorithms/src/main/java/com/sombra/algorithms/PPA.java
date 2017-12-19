package com.sombra.algorithms;

import com.sombra.Edge;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by bogdan on 19-Dec-17.
 */
public class PPA {

    private int nodeCount;
    private Integer[][] graph;
    private Set<Edge> allEdges = new HashSet<>();
    private Set<Edge> usedEdges = new HashSet<>();
    private Set<Edge> unusedEdges = new HashSet<>();
    private Map<Integer, List<Integer>> dijkstraResult;

    public Integer[][] getGraph() {
        return graph;
    }

    public void setGraph(Integer[][] graph) {
        this.graph = graph;
    }

    public Set<Edge> getAllEdges() {
        return allEdges;
    }

    public void setAllEdges(Set<Edge> allEdges) {
        this.allEdges = allEdges;
    }

    public Set<Edge> getUsedEdges() {
        return usedEdges;
    }

    public void setUsedEdges(Set<Edge> usedEdges) {
        this.usedEdges = usedEdges;
    }

    public Set<Edge> getUnusedEdges() {
        return unusedEdges;
    }

    public void setUnusedEdges(Set<Edge> unusedEdges) {
        this.unusedEdges = unusedEdges;
    }

    public PPA(int nodeCount, Integer[][] graph) {
        this.nodeCount = nodeCount;
        this.graph = graph;
    }

    private int getDistance(List<Integer> path) {
        int result = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            result += graph[path.get(i)][path.get(i + 1)];
        }
        return result;
    }

    private void calculateWeight(int start) {
        allEdges.stream().filter(usedEdges::contains).forEach(edge -> {
            Integer[][] pairSearch = graph.clone();
            for (int i = 0; i < nodeCount; i++) {
                pairSearch[i] = graph[i].clone();
            }
            pairSearch[edge.getStart()][edge.getEnd()] = null;
            pairSearch[edge.getEnd()][edge.getStart()] = null;
            Dijkstra alg = new Dijkstra(nodeCount, pairSearch);
            Map<Integer, List<Integer>> result = alg.calculate(start);
            int startDistance = getDistance(result.get(edge.getStart()));
            int endDistance = getDistance(result.get(edge.getEnd()));
            int end = startDistance > endDistance ? edge.getStart() : edge.getEnd();
            List<Integer> newPath = result.get(end);
            if (newPath.size() > 1) {
                int pairEdgeStart = newPath.get(newPath.size() - 2);
                int pairEdgeEnd = newPath.get(newPath.size() - 1);
                Edge pairEdge = new Edge(pairEdgeStart, pairEdgeEnd, graph[pairEdgeStart][pairEdgeEnd]);
                edge.setPairEdge(pairEdge);
                int distanceDiff = alg.getDistance(newPath) - getDistance(dijkstraResult.get(edge.getEnd()));
                allEdges.forEach(item -> {
                    if (item.equals(pairEdge)) {
                        item.setWeight(graph[pairEdge.getStart()][pairEdge.getEnd()] - distanceDiff);
                    }
                });
                edge.setWeight(graph[edge.getStart()][edge.getEnd()] + distanceDiff);
            }
        });
    }

    public Set<Edge> calculate(int start) {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.setNodeCount(nodeCount);
        dijkstra.setGraph(graph);
        dijkstraResult = dijkstra.calculate(0);
        for (int i = 0; i < nodeCount; i++) {
            for (int j = i + 1; j < nodeCount; j++) {
                if (i != j && graph[i][j] != null) {
                    allEdges.add(new Edge(i , j, graph[i][j]));
                }
            }
        }
        dijkstraResult.forEach((key, path) -> {
            for (int i = 0; i < path.size() - 1; i++) {
                usedEdges.add(new Edge(path.get(i), path.get(i + 1), graph[i][i + 1]));
            }
        });
        allEdges.forEach(edge -> {
            if (!usedEdges.contains(edge)) {
                unusedEdges.add(edge);
            }
        });
        calculateWeight(start);
        return allEdges;
    }

    private Edge getEdge(int start, int end) {
        Optional<Edge> first = this.allEdges.stream().filter(edge -> edge.equals(new Edge(start, end, null))).findFirst();
        return first.orElse(null);
    }

    private List<Edge> getListOfParent (Edge edge) {
        return this.allEdges.stream().filter(item -> item.getPairEdge() != null && item.getPairEdge().equals(edge)).collect(Collectors.toList());
    }

    public Set<Edge> addChange(int startVertex, int endVertex, int newWeight) {
        Edge edge = getEdge(startVertex, endVertex);
        if (usedEdges.contains(edge) && newWeight <= edge.getWeight() && newWeight >= graph[startVertex][endVertex]) {
            graph[startVertex][endVertex] = newWeight;
            graph[endVertex][startVertex] = newWeight;
            if (edge.getPairEdge() != null) {
                Edge pairEdge = getEdge(edge.getPairEdge().getStart(), edge.getPairEdge().getEnd());
                pairEdge.setWeight(pairEdge.getWeight() + (newWeight - edge.getValue()));
            }
        } else {
            if (unusedEdges.contains(edge) && newWeight >= edge.getWeight() && newWeight <= graph[startVertex][endVertex]) {
                graph[startVertex][endVertex] = newWeight;
                graph[endVertex][startVertex] = newWeight;
                getListOfParent(edge).forEach(item -> item.setWeight(item.getWeight() + (edge.getWeight() - newWeight)));
            } else {
                graph[startVertex][endVertex] = newWeight;
                graph[endVertex][startVertex] = newWeight;
                this.allEdges.clear();
                this.usedEdges.clear();
                this.unusedEdges.clear();
                this.calculate(0);
            }
        }
        return allEdges;
    }
}
