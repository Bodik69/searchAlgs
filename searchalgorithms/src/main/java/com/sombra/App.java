package com.sombra;
import com.sombra.algorithms.BFA;
import com.sombra.algorithms.BFS;
import com.sombra.algorithms.Dijkstra;
import com.sombra.algorithms.PPA;

import java.util.*;

public class App
{
    public static void main( String[] args )
    {
        int countOfNodes = 8;
        Integer[][] graphData = new Integer[][] {
                {0, 3, 3, 2, null, null, null, null},
                {3, 0, null, 7, 5, null, null, null, null},
                {3, null, 0, null, null, 6, null, null},
                {2, 7, null, 0, 4, 6, 8, null},
                {null, 5, null, 4, 0, null, 5, 9},
                {null, null, 6, 6, null, 0, null, 5},
                {null, null, null, 8, 5, null, 0, 2},
                {null, null, null, null, 9, 5, 2, 0},
        };
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.setNodeCount(countOfNodes);
        dijkstra.setGraph(graphData);
        Map<Integer, List<Integer>> dijkstraResult = dijkstra.calculate(0);
        ///dijkstraResult.keySet().forEach(key -> GraphDisplay.displayPath("Path to " + key, countOfNodes, graphData, dijkstraResult.get(key)));

        PPA ppa = new PPA(countOfNodes, graphData);
        Set<Edge> result = ppa.calculate(0);
        GraphDisplay.displayPPA("PPA", countOfNodes, graphData, result, ppa.getUsedEdges());
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose option:");
            System.out.println("Add change (1):");
            System.out.println("Exit (2):");
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Start vertex:");
                    int start = in.nextInt();
                    System.out.println("End vertex:");
                    int end = in.nextInt();
                    System.out.println("New weight:");
                    int newWeight = in.nextInt();
                    Set<Edge> newResult = ppa.addChange(start, end, newWeight);
                    GraphDisplay.displayPPA("PPA", countOfNodes, ppa.getGraph(), newResult, ppa.getUsedEdges());
                    break;
                }
                case 2: {
                    exit = true;
                    break;
                }
                default: {
                    System.out.println("incorrect option");
                }
            }
        }

        BFA bfa = new BFA();
        bfa.setNodeCount(countOfNodes);
        bfa.setGraph(graphData);
        Map<Integer, List<Integer>> BFAResult = bfa.calculate(0);
        //BFAResult.keySet().forEach(key -> GraphDisplay.displayPath("Path to " + key, countOfNodes, graphData, BFAResult.get(key)));


        BFS bfs = new BFS();
        bfs.setNodeCount(countOfNodes);
        bfs.setGraph(graphData);
        List<Integer> BFSResult = bfs.calculate(0, 5);
        //GraphDisplay.displayPath("Path to " + 5, countOfNodes, graphData, BFSResult);

    }
}
