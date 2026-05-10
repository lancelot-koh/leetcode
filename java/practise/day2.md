1 Union Find

class UF {
    int[] parent;
    int[] rank;
    int count;

    public UF(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;

        for(int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);

        if(px == py) {
            return false;
        }

        if (rank[px] < rank[py]) {
            parent[py] = px;
        } else if (rank[px] > rank[py]) {
            parent[px] = py;
        } else {
            parent[px] = py;
            rank[py]++;    
        }
        count--;
        return true;
    }

    public boolean connected(int x , int y) {
        return find(x) == find(y);
    }
    public int getCount() {
        return count;
    }
}


Trie:

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}

class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode curr = root;
        for(int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (curr.children[c] == null) {
                curr.children[c] = new TrieNode();
            }
            curr = curr.children[c];
        }
        curr.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode curr = root;
        for(int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (curr.children[c] == null) {
                return false;
            }
            curr = curr.children[c];
        }
        return curr != null && curr.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        for(int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if (curr.children[c] == null) {
                return false;
            }
            curr = curr.children[c];
        }
        return true;
    }
}

class Node {
    int key;
    int value;
    Node prev;
    Node next;
}
class LRUCache {
    int capacity;
    Map<Integer, Node> map;

    public LRUCache(int n) {
        this.capacity = n;
        map = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node curr = map.get(key);
        moveToHead(curr);

        return curr.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            map.put(key, node);

            moveToHead(node);
        } else {
            Node node = new Node(key, value);
            map.put(key, node);

            addToHead(node);
            if (map.size() > this.capacity) {
                removeTail();
            }
        } 

    }

    private void addToHead(Node node) {
        node.prev = head;                                                             
        node.next = head.next;    
        head.next.prev = node;                                                        
        head.next = node;
    }

    private void moveToHead(Node node) {
        // detach from current position
        node.prev.next = node.next;                                                   
        node.next.prev = node.prev;
        addToHead(node); 
    }

    private void removeTail() {
        Node lru = tail.prev;  // the real last node (before dummy tail)
        lru.prev.next = tail;                                           
        tail.prev = lru.prev;                                                         
        map.remove(lru.key);
    }
}

===============
Claude code version:
class Node {    
      int key, value;
      Node prev, next;
      public Node() {}
      public Node(int key, int value) { this.key = key; this.value = value; }       
  }
                                                                                    
  class LRUCache {
      int capacity;
      Map<Integer, Node> map;
      Node head, tail;                                                              
   
      public LRUCache(int capacity) {                                               
          this.capacity = capacity;
          map = new HashMap<>();
          head = new Node();
          tail = new Node();
          head.next = tail;                                                         
          tail.prev = head;
      }                                                                             
                  
      public int get(int key) {
          if (!map.containsKey(key)) return -1;
          Node node = map.get(key);                                                 
          moveToHead(node);
          return node.value;                                                        
      }           

      public void put(int key, int value) {
          if (map.containsKey(key)) {
              Node node = map.get(key);                                             
              node.value = value;       // update value in-place
              moveToHead(node);                                                     
          } else {
              Node node = new Node(key, value);                                     
              map.put(key, node);
              addToHead(node);                                                      
              if (map.size() > capacity) {
                  removeTail();                                                     
              }
          }                                                                         
      }           

      private void addToHead(Node node) {
          node.prev = head;
          node.next = head.next;
          head.next.prev = node;                                                    
          head.next = node;
      }                                                                             
                  
      private void removeNode(Node node) {
          node.prev.next = node.next;
          node.next.prev = node.prev;
      }                                                                             
   
      private void moveToHead(Node node) {                                          
          removeNode(node);
          addToHead(node);
      }

      private void removeTail() {
          Node lru = tail.prev;
          removeNode(lru);
          map.remove(lru.key);                                                      
      }
  }   




  