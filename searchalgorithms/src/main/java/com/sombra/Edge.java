package com.sombra;

/**
 * Created by bogdan on 19-Dec-17.
 */
public class Edge {
    private int start;
    private int end;
    private Integer value;
    private int weight;
    private Edge pairEdge;

    public Edge(int start, int end, Integer value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public Edge(int start, int end, int weight, Edge pairEdge) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.pairEdge = pairEdge;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Edge getPairEdge() {
        return pairEdge;
    }

    public void setPairEdge(Edge pairEdge) {
        this.pairEdge = pairEdge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return (start == edge.start && end == edge.end) || ((start == edge.end && end == edge.start));
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        return result;
    }
}
