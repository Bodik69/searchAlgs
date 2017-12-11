package com.sombra;
import com.sombra.algorithms.Dijkstra;

import java.util.List;
import java.util.Map;

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
        Map<Integer, List<Integer>> result = dijkstra.calculate(0);
        result.keySet().forEach(key -> GraphDisplay.displayPath("Path to " + key, countOfNodes, graphData, result.get(key)));
    }
}
