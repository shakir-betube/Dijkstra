package com.appsys.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Dijkstra {

    private HashMap<Integer, HashMap<Integer, Integer>> mPaths;
    private int g_nodes = 0;
    private int[] g_from = new int[0];
    private int[] g_to = new int[0];
    private int[] g_weight = new int[0];


    void loadFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();

        String[] parts = line.split(" ");
        g_nodes = Integer.parseInt(parts[0]);
        int edges = Integer.parseInt(parts[1]);

        g_from = new int[edges];
        g_to = new int[edges];
        g_weight = new int[edges];

        int i = 0;
        while ((line = br.readLine()) != null) {

            parts = line.split(" ");
            g_from[i] = Integer.parseInt(parts[0]);
            g_to[i] = Integer.parseInt(parts[1]);
            g_weight[i] = Integer.parseInt(parts[2]);

            i++;
        }
    }

    int minCost() {
        computeCost();
        boolean[] visitedList = new boolean[g_nodes];
        for (int i = 0; i < g_nodes; i++) {
            visitedList[i] = false;
        }

        PriorityQueue<NodeWeight> queue = new PriorityQueue<>();
        queue.add(new NodeWeight(1, 0));

        NodeWeight qm;

        while ((qm = queue.poll()) != null) {
            System.out.println(qm.nodeNo + ": " + qm.weight);
            if (qm.nodeNo == g_nodes) {
                break;
            }

            List<NodeWeight> directNodes = listOfDirectNodesFrom(qm.nodeNo, qm.weight);
            visitedList[qm.nodeNo - 1] = true;

            for (NodeWeight directNode : directNodes) {

                if (!visitedList[directNode.nodeNo - 1]) {

                    // we need to update the weigth if this particular node is already in the PQ
                    NodeWeight nw = findNodeWeight(queue, directNode.nodeNo);

                    if (nw == null) {
                        queue.add(directNode);
                    } else {
                        if (nw.weight > directNode.weight) {
                            queue.remove(nw);
                            nw.weight = directNode.weight;
                            queue.add(nw);
                        }
                    }
                }
            }
        }

        return (qm == null) ? 0 : qm.weight ;
    }

    private void computeCost() {
        mPaths = new HashMap<>();
        for (int i = 0; i < g_from.length; i++) {
            int from = g_from[i];
            int to = g_to[i];
            int weight = g_weight[i];

            if (mPaths.containsKey(from)) {
                mPaths.get(from).put(to, weight);
            } else {
                HashMap<Integer, Integer> map = new HashMap<>();
                map.put(to, weight);
                mPaths.put(from, map);
            }
        }
    }

    private List<NodeWeight> listOfDirectNodesFrom(int from, int currentWeight) {
        List<NodeWeight> result = new ArrayList<>();

        if (mPaths.containsKey(from)) {
            for (Map.Entry<Integer, Integer> path : mPaths.get(from).entrySet()) {
                result.add(new NodeWeight(path.getKey(), currentWeight + path.getValue()));
            }
        }

        return result;
    }

    private NodeWeight findNodeWeight(Queue<NodeWeight> queue, int nodeNo) {
        for (NodeWeight nw : queue) {
            if (nw.nodeNo == nodeNo) {
                return nw;
            }
        }

        return null;
    }
}
