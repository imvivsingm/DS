package graph;

import java.util.*;

public class DFSExample {

    Map<String, List<String>> graph = new HashMap<>();

    public void addEdge(String src,String dest){
        graph.putIfAbsent(src, new ArrayList<>());
        graph.putIfAbsent(dest, new ArrayList<>());
        graph.get(src).add(dest);
    }

    public void dfs(String start){
        Set<String> visited = new HashSet<>();
        dfsHelper(start,visited);
    }

    private void dfsHelper(String start, Set<String> visited) {
        if(visited.contains(start)) return;

        System.out.println(start);
        visited.add(start);

        for(String neighbour:graph.get(start)){
            if(!visited.contains(neighbour)){
                dfsHelper(neighbour, visited);
            }
        }
    }

    public void dfsIterative(String start){
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty()){
            String cur = stack.pop();
            if(!visited.contains(cur)){
                visited.add(cur);
                System.out.println(cur);

            List<String> neighbours = graph.get(cur);
            for(String neighbour:neighbours.reversed()){
                if(!visited.contains(neighbour)){
                    stack.push(neighbour);
                }
            }}
        }
    }

    public static void main(String[] args) {
        DFSExample g = new DFSExample();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("B", "E");
        g.addEdge("C", "F");
        g.addEdge("E", "F");

        g.dfs("A");
        System.out.println();
        g.dfsIterative("A");
        System.out.println();

    }

}
