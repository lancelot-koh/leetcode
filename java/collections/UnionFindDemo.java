package collections;
import java.util.*;

public class UnionFindDemo {

    // ── Core implementation ───────────────────────────────────────────────────
    static int[] parent, rank;

    static void init(int n) {
        parent = new int[n];
        rank   = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    // path compression
    static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // union by rank
    static boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) {
            return false; // already connected
        }
        if (rank[px] < rank[py]) {
            parent[px] = py;
        } else if (rank[px] > rank[py]) {
            parent[py] = px;
        } else {
            parent[py] = px;
            rank[px]++;
        }
        return true;
    }

    static boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // ── Basic operations ──────────────────────────────────────────────────
        init(6); // nodes 0..5
        System.out.println("connected(0,1) before union: " + connected(0, 1)); // false

        union(0, 1);
        union(1, 2);
        union(3, 4);
        System.out.println("connected(0,2): " + connected(0, 2)); // true
        System.out.println("connected(0,3): " + connected(0, 3)); // false

        union(2, 3);
        System.out.println("connected(0,4) after union(2,3): " + connected(0, 4)); // true

        // ── Count connected components ────────────────────────────────────────
        init(6);
        int[][] edges = {{0,1},{1,2},{3,4}};
        int components = 6;
        for (int[] e : edges) {
            if (union(e[0], e[1])) {
                components--;
            }
        }
        System.out.println("Components: " + components); // 3  (0-1-2, 3-4, 5)

        // ── Detect cycle in undirected graph ──────────────────────────────────
        init(4);
        int[][] edgesCycle = {{0,1},{1,2},{2,3},{3,0}};
        boolean hasCycle = false;
        for (int[] e : edgesCycle) {
            if (!union(e[0], e[1])) { // already same component → cycle
                hasCycle = true;
                break;
            }
        }
        System.out.println("Has cycle: " + hasCycle); // true

        // ── Kruskal's MST (minimum spanning tree) ─────────────────────────────
        // edges: {u, v, weight}
        int[][] weightedEdges = {{0,1,1},{0,2,4},{1,2,2},{1,3,5},{2,3,3}};
        Arrays.sort(weightedEdges, Comparator.comparingInt(e -> e[2]));
        init(4);
        int mstCost = 0;
        List<int[]> mst = new ArrayList<>();
        for (int[] e : weightedEdges) {
            if (union(e[0], e[1])) {
                mstCost += e[2];
                mst.add(e);
            }
        }
        System.out.print("MST edges: ");
        for (int[] e : mst) {
            System.out.print(e[0] + "-" + e[1] + "(" + e[2] + ") ");
        }
        System.out.println();
        System.out.println("MST cost: " + mstCost); // 6

        // ── Map-based Union Find (for string / non-integer keys) ──────────────
        Map<String, String> parentMap = new HashMap<>();

        // find with path compression
        // init on first access
        // use array wrapper to allow lambda self-reference
        @SuppressWarnings("unchecked")
        java.util.function.Function<String, String>[] findRef = new java.util.function.Function[1];
        findRef[0] = x -> {
            parentMap.putIfAbsent(x, x);
            if (!parentMap.get(x).equals(x)) {
                parentMap.put(x, findRef[0].apply(parentMap.get(x)));
            }
            return parentMap.get(x);
        };

        // union cities
        parentMap.put("A", "A");
        parentMap.put("B", "B");
        parentMap.put("C", "C");
        parentMap.put(findRef[0].apply("A"), findRef[0].apply("B")); // union A-B
        parentMap.put(findRef[0].apply("B"), findRef[0].apply("C")); // union B-C
        System.out.println("A and C same group: " +
            findRef[0].apply("A").equals(findRef[0].apply("C"))); // true
    }
}
