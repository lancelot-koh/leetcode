# T4-9 — Deque as Stack / Queue / Heap 双端队列用作栈/队列/堆

> **Core idea:** Java's `ArrayDeque` is the preferred implementation for both stack and queue use cases. It outperforms `Stack` (synchronized) and `LinkedList` (pointer overhead). Understand which end to push/pop for each use case.
> **核心思想：** Java的`ArrayDeque`是栈和队列的首选实现，优于`Stack`（同步开销）和`LinkedList`（指针开销）。掌握不同用途应操作哪端。
>
> Complexity: O(1) amortized for all push/pop/peek operations.

---

## When to Use 什么时候用

| Use case | Deque API | When |
|---|---|---|
| Stack (LIFO) | `push`/`pop`/`peek` (operate on FRONT) | Expression evaluation, DFS, monotonic stack |
| Queue (FIFO) | `offer`/`poll`/`peek` (add back, remove front) | BFS, sliding window |
| Deque (both ends) | `offerFirst`/`offerLast`/`pollFirst`/`pollLast` | Monotonic deque (T3-7), palindrome check |
| Max-heap | `new PriorityQueue<>(Collections.reverseOrder())` | Top-K, scheduling |

---

## Core Templates 核心模板

### Stack operations (LIFO)

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(val);        // = offerFirst (add to front)
int top = stack.pop();  // = pollFirst  (remove from front)
int peek = stack.peek(); // = peekFirst

// Never use java.util.Stack — it's synchronized and slow
```

### Queue operations (FIFO)

```java
Deque<Integer> queue = new ArrayDeque<>();
// Or: Queue<Integer> queue = new LinkedList<>(); (acceptable but slower)

queue.offer(val);       // = offerLast  (add to back)
int front = queue.poll();// = pollFirst (remove from front)
int peek = queue.peek(); // = peekFirst
```

### Priority Queue (min-heap and max-heap)

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Custom comparator
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

pq.offer(val);   // insert O(log n)
pq.poll();       // remove min/max O(log n)
pq.peek();       // view min/max O(1)
```

---

## API Reference Java API参考

```java
// ArrayDeque — DO use
Deque<T> dq = new ArrayDeque<>();

dq.push(x)        // offerFirst — stack push
dq.pop()          // pollFirst  — stack pop
dq.peek()         // peekFirst  — stack/queue peek

dq.offerFirst(x)  // add to front
dq.offerLast(x)   // add to back (queue offer)
dq.pollFirst()    // remove from front (queue poll)
dq.pollLast()     // remove from back
dq.peekFirst()    // view front
dq.peekLast()     // view back

dq.isEmpty()
dq.size()
```

---

## Common Patterns 常见模式

### Monotonic Stack (see T2-4)
```java
Deque<Integer> stack = new ArrayDeque<>();  // stores indices
// push: stack.push(i)
// pop: stack.pop()
// peek: stack.peek()
```

### BFS Queue
```java
Queue<int[]> queue = new LinkedList<>();
// or Deque<int[]> queue = new ArrayDeque<>();
queue.offer(new int[]{0, 0});
while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    // ...
}
```

### Expression Evaluation (two stacks)
```java
Deque<Integer> nums = new ArrayDeque<>();
Deque<Character> ops = new ArrayDeque<>();
// Process tokens: push numbers; for operators, apply pending higher-precedence ops
```

### Browser History (two stacks)
```java
Deque<String> back = new ArrayDeque<>();
Deque<String> forward = new ArrayDeque<>();
// visit: forward.clear(); back.push(current); current = url
// back: forward.push(current); current = back.pop()
// forward: back.push(current); current = forward.pop()
```

---

## PriorityQueue Pitfalls 优先队列陷阱

| Pitfall | Fix |
|---|---|
| `a - b` comparator overflow | Use `Integer.compare(a, b)` |
| Can't remove arbitrary element efficiently | Use `TreeMap` for O(log n) arbitrary removal |
| No O(1) decrease-key | Use lazy deletion: push new entry, skip stale on pop |
| Default is MIN-heap | For max-heap: `Collections.reverseOrder()` or `(a,b) -> b - a` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 232 Implement Queue using Stacks |
| Easy | LC 225 Implement Stack using Queues |
| Medium | LC 155 Min Stack |
| Medium | LC 20 Valid Parentheses |
| Hard | LC 224 Basic Calculator |

**Order:** 20 → 232 → 225 → 155 → 224

---

## One-line Summary

```
Prefer ArrayDeque over Stack/LinkedList; push/pop for LIFO, offer/poll for FIFO; PriorityQueue for heap with O(log n) insert/remove.
优先用ArrayDeque而非Stack/LinkedList；push/pop做LIFO，offer/poll做FIFO；PriorityQueue做O(log n)堆操作。
```
