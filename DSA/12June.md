State:

Metric:
count/min/max

Transition:
Current State/Previous state

Aggregation:



我同意，而且如果我是你，我接下来会把 Graph 专题单独拉出来两天。

因为从 Google 这些年的题型来看：

Graph
BFS
DFS
Topological Sort
Dijkstra

出现概率真的比高级 DP 高很多。

⸻

其实它们是一家人

你最近一直在研究：

State
Transition

Graph 其实也是这个东西。

⸻

DFS

State:

current node

Transition:

go to children

⸻

BFS

State:

current node

Transition:

go to neighbors

⸻

区别：

DFS
=
go deep first
BFS
=
expand by layer

⸻

Topological Sort

State:

node
+
indegree

Transition:

remove dependency

⸻

Dijkstra

State:

node
+
current shortest distance

Transition:

relax edge

⸻

其实都是：

Node
+
Transition

⸻

我建议你建立这个 Graph Map

Layer 1

Connectivity

Number of Islands
Connected Components
Valid Tree
Clone Graph

核心：

Can I reach?

DFS/BFS

⸻

Layer 2

Shortest Path

Open Lock
Word Ladder
Bus Routes
Maze
Cheapest Flights

核心：

Minimum steps?

BFS

或者

Dijkstra

⸻

Layer 3

Dependency

Course Schedule
Course Schedule II
Alien Dictionary

核心：

A must happen before B

Topological Sort

⸻

Layer 4

Weighted Graph

Network Delay Time
Cheapest Flights
Path With Minimum Effort

核心：

minimum cost

Dijkstra

⸻

你现在最值得补的是

Topological Sort

因为它比较久没碰。

我建议至少做到：

Course Schedule (LC207)

判断：

Can finish?

⸻

Course Schedule II (LC210)

输出：

order

⸻

然后理解：

indegree
queue
remove edge

⸻

因为 Google 很喜欢把它包装成：

Task Dependency
Build System
Workflow
Pipeline

但本质还是：

Topological Sort

⸻

第二个是 Dijkstra

你最近 BFS 已经比较顺了。

下一步要理解：

BFS：

every edge weight = 1

⸻

Dijkstra：

edge weight can be different

⸻

这是最重要的一句话。

其实：

BFS
=
special case of Dijkstra

⸻

例如：

Open Lock

0000 -> 0001
cost = 1

⸻

所有边：

cost = 1

所以：

BFS

够了。

⸻

如果：

0000 -> 0001 cost=5
0000 -> 1000 cost=2

就必须：

Dijkstra

⸻

我会这样安排

明天：

Morning

Topological Sort

LC207
LC210
Alien Dictionary

重点：

indegree
queue

⸻

Afternoon

Dijkstra

Network Delay Time
Cheapest Flights
Path With Minimum Effort

重点：

dist[]
priority queue
relaxation

⸻

其实你今天最大的收获是：

State
Transition
Metric
Aggregation

明天你会发现：

Topological Sort
Dijkstra

也完全能套进去。

例如 Dijkstra：

State:
node
Metric:
shortest distance
Transition:
neighbor
Aggregation:
min

你会发现，它跟 Coin Change 的思维几乎一样：

Current State
=
Previous State
+
Current Cost
take MIN

这也是为什么我觉得你已经开始从“刷题”进入“建模”的阶段了。接下来把 Topological Sort 和 Dijkstra 补齐，你的 Graph 体系就比较完整了。