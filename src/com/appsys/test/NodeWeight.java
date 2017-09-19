package com.appsys.test;

public class NodeWeight implements Comparable<NodeWeight> {
    int nodeNo;
    int weight;

    NodeWeight(int n, int w) {
        nodeNo = n;
        weight = w;
    }

    @Override
    public int compareTo(NodeWeight other) {
        if (weight < other.weight) {
            return -1;
        } else if (weight > other.weight) {
            return 1;
        }

        return 0;
    }
}
