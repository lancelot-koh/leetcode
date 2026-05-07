package collections;
import java.util.*;

public class GraphDemo {
    public static void main(String[] args) {

        // ── Build adjacency list (undirected) ─────────────────────────────────
        int n = 6; // nodes 0..5
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        int[][] edges = {{0,1},{0,2},{1,3},{2,4},{3,5},{4,5}};
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]); // omit for directed graph
        }
        System.out.println("Adjacency list: " + graph);

        // ── BFS (shortest path / level order) ────────────────────────────────
        System.out.print("BFS from 0: ");
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");
            for (int nb : graph.get(node)) {
                if (!visited[nb]) {
                    visited[nb] = true;
                    queue.offer(nb);
                }
            }
        }
        System.out.println();

        // ── BFS shortest distance ─────────────────────────────────────────────
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        Queue<Integer> bfsQ = new LinkedList<>();
        bfsQ.offer(0);
        while (!bfsQ.isEmpty()) {
            int node = bfsQ.poll();
            for (int nb : graph.get(node)) {
                if (dist[nb] == -1) {
                    dist[nb] = dist[node] + 1;
                    bfsQ.offer(nb);
                }
            }
        }
        System.out.println("BFS distances from 0: " + Arrays.toString(dist));

        // ── DFS iterative ─────────────────────────────────────────────────────
        System.out.print("DFS from 0 (iterative): ");
        boolean[] vis2 = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (vis2[node]) {
                continue;
            }
            vis2[node] = true;
            System.out.print(node + " ");
            for (int nb : graph.get(node)) {
                if (!vis2[nb]) {
                    stack.push(nb);
                }
            }
        }
        System.out.println();

        // ── DFS recursive ─────────────────────────────────────────────────────
        System.out.print("DFS from 0 (recursive): ");
        boolean[] vis3 = new boolean[n];
        dfs(0, graph, vis3);
        System.out.println();

        // ── Directed graph + in-degree (for topo sort) ────────────────────────
        List<List<Integer>> directed = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            directed.add(new ArrayList<>());
        }
        directed.get(0).add(1);
        directed.get(0).add(2);
        directed.get(1).add(3);
        directed.get(2).add(3);

        int[] inDeg = new int[4];
        for (int u = 0; u < 4; u++) {
            for (int v : directed.get(u)) {
                inDeg[v]++;
            }
        }
        System.out.println("In-degrees: " + Arrays.toString(inDeg));

        // ── Weighted graph (adjacency list with int[]{neighbor, weight}) ──────
        List<List<int[]>> weighted = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            weighted.add(new ArrayList<>());
        }
        weighted.get(0).add(new int[]{1, 4});
        weighted.get(0).add(new int[]{2, 1});
        weighted.get(2).add(new int[]{1, 2});
        weighted.get(1).add(new int[]{3, 1});
        weighted.get(2).add(new int[]{3, 5});

        // Dijkstra from node 0
        int[] dists = new int[n];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{0, 0}); // {node, dist}
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], d = curr[1];
            if (d > dists[node]) {
                continue;
            }
            for (int[] nb : weighted.get(node)) {
                int next = nb[0], w = nb[1];
                if (dists[node] + w < dists[next]) {
                    dists[next] = dists[node] + w;
                    pq.offer(new int[]{next, dists[next]});
                }
            }
        }
        System.out.println("Dijkstra distances from 0: " + Arrays.toString(dists));
    }

    static void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");
        for (int nb : graph.get(node)) {
            if (!visited[nb]) {
                dfs(nb, graph, visited);
            }
        }
    }
}
