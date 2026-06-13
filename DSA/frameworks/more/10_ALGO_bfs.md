# 📊 BFS - Level-by-Level Exploration

**Shortest unweighted paths and level-order processing**

---

## Interview Frequency: **5% of problems** ⭐⭐⭐

---

## Core Template

```java
public int bfs(Node start, Node target) {
    Queue<Node> queue = new LinkedList<>();
    Set<Node> visited = new HashSet<>();
    
    queue.offer(start);
    visited.add(start);
    int level = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            Node node = queue.poll();
            
            if (node == target) return level;
            
            for (Node neighbor : node.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        level++;
    }
    
    return -1;  // Not found
}
```

---

## Key Points

- **First to reach = shortest:** BFS finds shortest unweighted path
- **Level processing:** Process all nodes at level k before k+1
- **Use Queue:** FIFO for BFS
- **Track Visited:** Avoid revisiting nodes
- **Time:** O(V + E)

---

## Examples

- **Shortest path:** Unweighted graph
- **Level order:** Tree/graph level-by-level
- **Word Ladder:** Minimum transformations
- **Rotting Oranges:** Multi-source BFS

---

**Master BFS. It's 5% of interviews.** 🚀
