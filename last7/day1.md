# Universal dsa thinking framwork
All questions is essential related to 
* state
* state transition
* traversal
* constraint
* optimazation

## Pattern Map
Problem signal                          Pattern
连续区间                               Sliding window
求出现过的                             Hashmap
子数组求和/任意起点                     Prefix sum
最短路径/层级扩算                       BFS
所有可能性/所有组合                     backtracking
DAG依赖                               topologic sort
Connectivity联通性                    Union Find
单调关系                               Monotonic stack
answer range                          Binary search



DSA world map
Array
LinkedList?
HashMap
Stack
Queue
Heap
Tree
Graph
Trie
Union Find

Sliding Window
Prefix Sum
Binary Search
DFS
BFS
DP
Backtracking

write for each：
what’s state they maintain？
what‘s the questions they resolve
what’s the traversal？
what‘s the possible optimazation




Tree的本质？
DFS = subtree report state to their parent


Tree DFS 4 pattern
1 subtree
int dfs（node）
* maxDepth
* diameter
* balanced tree

2 path traversal
dfs（node， path）
* path sum
* backtracking

3 global varaible
max = Math.max()
* max path sum
* diameter

4 return meaningful node
TreeNode dfs(..)
* LCA
* subtree search


Questions:
* what's brute force?
* why it's slow
* what's the optimazation 
* what's data structure can help to optimazation?
* what's state?
* what's the traversal?
* why this pattern works?



========== Recommended practise=========
Sliding window
1 longest substring without repeating characters
2 minmum window substring

important:
* frequnecy map
* shrink condition
* valid window

Prefix sum
1 Subarray Sum equals K
2 Continuous subarray sum

impoart:
* 出现过的Prefix
* remainder
* hashmap meaning 

Binary Search
1 Koko eating bananas
2 Ship within D days
important:
* Binary search on Answer
* feasible function

Night:

Task 1:
5 minutes structural details a 

1 Clarify
    Input range
    deduplicate
    negative
    sorted
    memnory constraint

2 brute force
    explain
    complexity

3 Optimazation
    bottleneck
    better DS/pattern

4 Final Solution
    explain state
    traversal
    why correct

5 Complexity
    Time
    Space

6 Trade Off
for example: 
    heap vs sorting
    BFS vs DFS
    hashmap vs array 



Task 2:
How to structural thinking

when come to a system design question:
1 Clarify requirement
* users
* user case
* read/write
* realtime
* consistency?

2 Non-Functional
* QPS
* latency
* scalability
* availablity
* reliabilty

3 Baseline first
    I will start with a simple baseline design first to avoid over-engineering, then evovle it accoring to scaling requirement.

4 Find the core component
For example:
    cache?
    DB?
    queue?
    rank?
    consistency?
    realtime?

5 Deep dive only interview cared part

