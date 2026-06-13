# 🔗 Union Find - Dynamic Connectivity

**Efficiently track and merge connected components**

---

## Interview Frequency: **4% of problems** ⭐⭐⭐

---

## Core Template

```java
class UnionFind {
    int[] parent;
    int[] rank;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return;
        
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
    
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

---

## Examples

- **Number of Connected Components:** Count components
- **Accounts Merge:** Union accounts with common email
- **Kruskal's Algorithm:** Minimum spanning tree
- **Cycle Detection:** In undirected graph

---

## Key Points

- **Find:** Which component does element belong to
- **Union:** Merge two components
- **Path compression:** Flatten structure for O(α(n)) amortized
- **Union by rank:** Keep tree shallow

---

**Master Union Find. It's 4% of interviews.** 🚀
