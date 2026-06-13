

World                           Pattern
Dependency world                Topological sort
Reachability                    BFS/DFS
Shortest path                   BFS/Dijkstra
Optimazation                    DP/Greedy
Search Space                    Binary Search
Enumeration                     Backtracking
Continuous Range                Sliding Window
Priority                        Heap
Scheduling                      Interval
State Transition                State machine

# Tier 1

## Graph Framework
Entity(Node)
Relationship(Edge)
Goal
State
Transition
Visited
Termination

Reachability    -> DFS/BFS
Shortest path   -> BFS/Dijkstra
Dependency      -> Topological sort

## Clarification Framework
Input
Output
Constraint
Optimazation Goal
-> or upgrade to 
Entity
Relationship
Goal
Constraint

## DP Framework
State
State definition
Stored value
Decision
Transition
Base Case
Dependency
Optimazation Goal


# Tier 2
## Binary search framework
Search space
check(mid)
monotonic property
First true/last true
update rule
return value

**Two variations:**
```
Finding MINIMUM that works (FFFF TTTT):
if (works(mid)) {
    answer = mid;
    high = mid - 1;  // Try smaller
} else {
    low = mid + 1;   // Need larger
}

Finding MAXIMUM that works (TTTT FFFF):
if (works(mid)) {
    answer = mid;
    low = mid + 1;   // Try larger
} else {
    high = mid - 1;  // Need smaller
}
```


## Sliding window Framework
Window
Expand
Condition
Shrink
Answer

Template:
    right++
    valid_Condtion
        -> Yes: Update Answer
        -> No: left++


# Tier 3
## Greedy Framework
Greedy Choice
Why correct
invariant
Proof

Asking: local optimum? Can it lead to global optimum?

classic question:
    Jump Game
    Gas station
    Meeting rooms
    Intervals

## Heap/Priority Queue Framework
What's the priority?
Min/Max Heap?
When insert?
When remove?


# Tier 4
## Backtracking Framework
State
Choice
Constraint
Goal
Undo

Template:
    Current State 
        ->  For each Choice
            -> Choose
                -> Recurse
            -> Undo 


# Tier 5
# Interval Framework
Sort
Overlap?
Merge?
Count?


Template:
    Sort by start/end
    Compare current and previous



# State Mechine Framework
Current State -> Next State

Stock: holding/not holding
House rob: rob/not rob




# 2 Pointers Framework

## 🎯 Decision Tree

```
See a problem with arrays/pointers?
  │
  ├─ Looking for pairs/groups?
  │  └─ Is array sorted? → Two Pointers ✓
  │  
  ├─ Palindrome/validation problem?
  │  └─ Yes → Two Pointers (compare from ends) ✓
  │  
  ├─ Cycle detection?
  │  └─ Yes → Two Pointers (slow & fast) ✓
  │  
  ├─ Partition/rearrange in-place?
  │  └─ Yes → Two Pointers (partition) ✓
  │  
  └─ Can solve with left/right pointers?
     └─ Yes → Likely Two Pointers
```


# Core Template:

## BFS Template

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


## DFS Template


## Dijkstra Template


## Heap/Priority Queue Template


## Topological Sort Template


## Binary search Template



## Interval Template