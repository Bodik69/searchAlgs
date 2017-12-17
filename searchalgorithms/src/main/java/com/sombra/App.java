package com.sombra;
import com.sombra.algorithms.BFA;
import com.sombra.algorithms.BFS;
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
        Map<Integer, List<Integer>> dijkstraResult = dijkstra.calculate(0);
        //dijkstraResult.keySet().forEach(key -> GraphDisplay.displayPath("Path to " + key, countOfNodes, graphData, dijkstraResult.get(key)));


        BFA bfa = new BFA();
        bfa.setNodeCount(countOfNodes);
        bfa.setGraph(graphData);
        Map<Integer, List<Integer>> BFAResult = bfa.calculate(0);
        //BFAResult.keySet().forEach(key -> GraphDisplay.displayPath("Path to " + key, countOfNodes, graphData, BFAResult.get(key)));


        BFS bfs = new BFS();
        bfs.setNodeCount(countOfNodes);
        bfs.setGraph(graphData);
        List<Integer> BFSResult = bfs.calculate(0, 5);
        GraphDisplay.displayPath("Path to " + 5, countOfNodes, graphData, BFSResult);

    }
}
