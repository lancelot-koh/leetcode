class TrieNode {


}


class Trie {

}


LRU cache:

class Node {

}

class LRUCache {

}


===============================
Part 1:
### Number of islands
    connected components
    DFS/BFS

    int count = 0;

    for(int i =0)
        for(int j = 0) {
            if (grid[i][j] == '1') {
                Queue<int[]> q = new LinkedList<>();
                grid[i][j] == '0';
                q.offer(new int[]{i, j});
                count++;

                while(!q.isEmpty) {
                    
                    if (condition) {
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }



### Rotten oranges
    multiple source BFS

    int mintes = 0;
    Queue<int[]> q = new LinkedList<>();
    for(int i =0)
        for(int j = 0) {
            if (grid[i][j] == 2) {
                q.offer(new int[]{i, j});
            } else if (grid[i][j] == 1) {
                fresh++;
            }
        }
    }

    if (fresh == 0) return 0;

 
    while(!q.isEmpty) {
        int size = q.size();
        boolean rottingInThisMin = false; 
        
        if (condition) {
            q.offer(new int[]{nr, nc});
            fresh--;
            rottingInThisMin = true;
        }
        if(rottingInThisMin) {
           mintes++; 
        }
    }

    return fresh != 0 ? -1: mintes;




    level order





Part 2:
    Clone Graph
        -> Graph traversal
        -> control access condition(visited map)

    course schedule
        -> topological sort
    


Part 3:
    Binary tree level order traversal
    vertical order


    pacific altantic water flow




Final:
    BFS/DFS
        Gird flood fill
            number of islands
            max area
            surrand regoins
        
        Mutliple source BFS
            rotten orange
            01-Matrix
            walls and gates

        Graph traversal
            clone graph
            graph valid tree
        
        Topologic sort
            course schedule
            269. Alien Dictionary

        
        Tree DFS
            level order
            vertical order
            zigzag






