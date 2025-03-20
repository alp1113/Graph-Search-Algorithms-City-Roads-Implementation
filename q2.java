
import java.util.*;

public class q2 {
    private int M; // Number of landmarks
    private List<List<Integer>> adj; // Adjacency list for storing graph connections
    private boolean[] visited; // To track visited landmarks during DFS
    private List<Integer> dfsOrder; // To store the order of landmarks visited in DFS

    // Constructor to initialize the city with M landmarks
    public q2(int M) {
        this.M = M;
        adj = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            adj.add(new ArrayList<>());
        }
        visited = new boolean[M];
        dfsOrder = new ArrayList<>();
    }

    // Add an undirected path between landmarks U and V
    public void addPath(int U, int V) {
        adj.get(U - 1).add(V - 1);
        adj.get(V - 1).add(U - 1);
    }

    // Perform DFS starting from a given landmark
    private void dfs(int node) {
        visited[node] = true;
        dfsOrder.add(node + 1); // Store the landmark in 1-based format

        // Sort neighbors to ensure traversal order is in ascending order
        Collections.sort(adj.get(node));

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor); // Recursively visit all unvisited neighbors
            }
        }
    }

    // Check if all landmarks are connected and return the result
    public void findMinimumMoves() {
        // Sort adjacency lists to ensure consistency in traversal order
        for (List<Integer> neighbors : adj) {
            Collections.sort(neighbors);
        }

        // Start DFS from node 2 (which corresponds to landmark 3 in 1-based indexing)
        dfs(2);

        // Check if all landmarks were visited
        for (boolean v : visited) {
            if (!v) {
                System.out.println(-1); // If any landmark is unvisited, print -1
                return;
            }
        }

        // Number of edges in the DFS traversal is M - 1 for a connected graph
        int minMoves = M - 1;

        System.out.println(minMoves);
        for (int landmark : dfsOrder) {
            System.out.print(landmark + " ");
        }
        System.out.println();
    }

    // Main method to read input and execute the solution
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt(); // Number of landmarks
        int N = sc.nextInt(); // Number of paths

        q2 city = new q2(M);

        // Read N paths and add them to the graph
        for (int i = 0; i < N; i++) {
            int U = sc.nextInt();
            int V = sc.nextInt();
            city.addPath(U, V);
        }

        // Calculate the minimum moves and print the result
        city.findMinimumMoves();

        sc.close();
    }
}
