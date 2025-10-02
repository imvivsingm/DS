package graph;

import java.util.*;

public class BFSExample {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public void addEdge(int src, int dest) {
        graph.computeIfAbsent(src, k -> new ArrayList<>()).add(dest);
        graph.computeIfAbsent(dest, k -> new ArrayList<>()).add(src);
    }

    // Recursive BFS starter method
    public void bfsRecursive(int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        visited.add(start);
        queue.offer(start);

        bfsHelper(queue, visited);
    }

    private void bfsHelper(Queue<Integer> queue, Set<Integer> visited) {
        if (queue.isEmpty()) return;

        int current = queue.poll();
        System.out.print(current + " ");

        for (int neighbor : graph.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                queue.offer(neighbor);
            }
        }

        bfsHelper(queue, visited); // recursive call with updated queue
    }

    // Iterative BFS for comparison
    public void bfs(int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        BFSExample bfsExample = new BFSExample();

        bfsExample.addEdge(1, 2);
        bfsExample.addEdge(1, 3);
        bfsExample.addEdge(2, 4);
        bfsExample.addEdge(3, 5);

        System.out.println("Iterative BFS starting from node 1:");
        bfsExample.bfs(1);

        System.out.println("\nRecursive BFS starting from node 1:");
        bfsExample.bfsRecursive(1);
    }
}
