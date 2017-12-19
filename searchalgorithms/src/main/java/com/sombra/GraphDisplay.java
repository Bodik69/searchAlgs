package com.sombra;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GraphDisplay {

    private static Graph displayInitialGraph(String name, int countOfNodes, Integer[][] graphData) {
        Graph graph = new SingleGraph(name);
        graph.addAttribute("ui.stylesheet", "edge.marked {" +
                "        fill-color: red;" +
                "    }");

        for (int i = 0; i < countOfNodes; i++) {
            Node n = graph.addNode(String.valueOf(i));
            n.addAttribute("ui.label", String.valueOf(i));
        }
        for (int i = 0; i < countOfNodes; i++) {
            for (int j = i + 1; j < countOfNodes; j++) {
                if (i != j && graphData[i][j] != null) {
                    Edge edge = graph.addEdge(i + "_" + j, String.valueOf(i), String.valueOf(j), false);
                    edge.addAttribute("ui.label", String.valueOf(graphData[i][j]));
                }
            }
        }
        return graph;
    }

    public static void displayPath(String name, int countOfNodes, Integer[][] graphData, List<Integer> path) {
        Graph graph =  displayInitialGraph(name, countOfNodes, graphData);
        for (int i = 0; i < path.size() - 1; i++) {
            graph.getEdge(path.get(i) + "_" + path.get(i + 1)).setAttribute("ui.class", "marked");
        }
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }

    public static void displayPPA(String name, int countOfNodes, Integer[][] graphData, Collection<com.sombra.Edge> edges, Collection<com.sombra.Edge> userEdges) {
        List<com.sombra.Edge> resultEdges = new LinkedList<>(edges);
        List<com.sombra.Edge> resultUsedEdges = new LinkedList<>(userEdges);
        Graph graph = new SingleGraph(name);
        graph.addAttribute("ui.stylesheet", "edge.marked {" +
                "        fill-color: red;" +
                "    }");

        for (int i = 0; i < countOfNodes; i++) {
            Node n = graph.addNode(String.valueOf(i));
            n.addAttribute("ui.label", String.valueOf(i));
        }
        for (int i = 0; i < countOfNodes; i++) {
            for (int j = i + 1; j < countOfNodes; j++) {
                if (i != j && graphData[i][j] != null) {
                    Edge edge = graph.addEdge(i + "_" + j, String.valueOf(i), String.valueOf(j), false);
                    edge.addAttribute("ui.label", String.valueOf(graphData[i][j]) + "(" + resultEdges.get(resultEdges.indexOf(new com.sombra.Edge(i, j, null))).getWeight() + ")");
                }
            }
        }
        for (com.sombra.Edge resultUsedEdge : resultUsedEdges) {
            graph.getEdge(resultUsedEdge.getStart() + "_" + resultUsedEdge.getEnd()).setAttribute("ui.class", "marked");
        }
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }
}
