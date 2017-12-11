package com.sombra;
import com.sombra.algorithms.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int countOfNodes = 6;
        Integer[][] graphData = new Integer[][] {
                {0, 4, 2, null, null, null},
                {null, 0, null, 5, null, null},
                {null, 1, 0, null, 10, null},
                {null, null, null, 0, 2, 6},
                {null, null, null, null, 0, 3},
                {null, null, null, null, null, 0}
        };
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.setNodeCount(countOfNodes);
        dijkstra.setGraph(graphData);
        dijkstra.calculate(0);
        Graph graph = new SingleGraph("Dijkstra");

        for (int i = 0; i < countOfNodes; i++) {
            Node n = graph.addNode(String.valueOf(i));
            n.addAttribute("ui.label", String.valueOf(i));
        }
        for (int i = 0; i < countOfNodes; i++) {
            for (int j = 0; j < countOfNodes; j++) {
                if (i != j && graphData[i][j] != null) {
                    Edge edge = graph.addEdge(i + "_" + j, String.valueOf(i), String.valueOf(j));
                    edge.addAttribute("ui.label", String.valueOf(graphData[i][j]));
                }
            }
        }

        graph.display();
    }
}
