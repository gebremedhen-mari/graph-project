import java.util.*;
import java.util.function.Predicate;

class Graph {
    private Map<String, List<Edge>> adj = new HashMap<>();

    static class Edge {
        String to;
        int weight;
        Edge(String to, int weight) { this.to = to; this.weight = weight; }
        @Override
        public String toString() { return to + "(" + weight + ")"; }
    }

    void addNode(String node) {
        adj.putIfAbsent(node, new ArrayList<>());
    }

    void addEdge(String from, String to, int weight) {
        addNode(from);
        addNode(to);
        adj.get(from).add(new Edge(to, weight));
    }

    void printGraph() {
        System.out.println("Adjacency List:");
        for (String node : adj.keySet()) {
            System.out.println(node + " -> " + adj.get(node));
        }
    }

    void bfs(String start) {
        System.out.print("BFS: ");
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            System.out.print(node + " ");
            for (Edge e : adj.get(node)) {
                if (!visited.contains(e.to)) {
                    visited.add(e.to);
                    queue.add(e.to);
                }
            }
        }
        System.out.println();
    }

    void dfs(String start) {
        System.out.print("DFS: ");
        Set<String> visited = new HashSet<>();
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(String node, Set<String> visited) {
        visited.add(node);
        System.out.print(node + " ");
        for (Edge e : adj.get(node)) {
            if (!visited.contains(e.to)) dfsHelper(e.to, visited);
        }
    }

    // Dijkstra's algorith for shortest path
    void shortestPath(String start) {
        Map<String, Integer> dist = new HashMap<>();
        for (String node : adj.keySet()) dist.put(node, Integer.MAX_VALUE);
        dist.put(start, 0);

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            for (Edge e : adj.get(u)) {
                int newDist = dist.get(u) + e.weight;
                if (newDist < dist.get(e.to)) {
                    dist.put(e.to, newDist);
                    pq.add(e.to);
                }
            }
        }

        System.out.println("Shortest paths from " + start + ":");
        for (String node : dist.keySet()) {
            System.out.println(node + " -> " + dist.get(node));
        }
    }

    // for All paths from start to end
    void allPaths(String start, String end) {
        System.out.println("All paths from " + start + " to " + end + ":");
        List<String> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsAllPaths(start, end, visited, path);
    }

    private void dfsAllPaths(String curr, String end, Set<String> visited, List<String> path) {
        visited.add(curr);
        path.add(curr);

        if (curr.equals(end)) {
            System.out.println(path);
        } else {
            for (Edge e : adj.get(curr)) {
                if (!visited.contains(e.to)) dfsAllPaths(e.to, end, visited, path);
            }
        }

        path.remove(path.size() - 1);
        visited.remove(curr);
    }
}

public class GraphWoApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph g = new Graph();

        System.out.println("Enter number of edges:");
        int E = sc.nextInt();
        sc.nextLine(); // for consuming newline

        for (int i = 0; i < E; i++) {
            System.out.println("Enter edge (from to weight):");
            String[] parts = sc.nextLine().split(" ");
            g.addEdge(parts[0], parts[1], Integer.parseInt(parts[2]));
        }

        System.out.println("\nGraph built!\n");
        g.printGraph();

        System.out.println();
        System.out.print("Enter BFS/DFS start node: ");
        String start = sc.nextLine();
        g.bfs(start);
        g.dfs(start);

        System.out.println();
        g.shortestPath(start);

        System.out.println();
        System.out.print("Enter end node for all paths: ");
        String end = sc.nextLine();
        g.allPaths(start, end);

        sc.close();
    }
}
