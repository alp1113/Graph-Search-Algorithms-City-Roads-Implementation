
// Description: This class represents an undirected graph of 
// landmarks which are nodes, and roads which are edges in a graph view using adjacency list.
// adding roads between landmarks, calculating the shortest paths between landmarks using 
// a Breadth-First Search (BFS), and identifying pairs of landmarks where adding a new road would 
// not decrease the distance between two specific landmarks, X (home) and Y (central landmark) are featured in this class.
// This class checks possible new connections, ensuring they have the original distance between X and Y, also outputs correct 
// pairs or indicates if such pairs do not exist.
//-----------------------------------------------------

import java.util.*;

public class city {
    private int N; // Number of landmarks in the city
    private List<List<Integer>> adj; // The adjacency list of the graph

    // Constructor for initializing the graph
    public city(int N) {
        // Summary: Initializes a graph of a city that has N landmarks and no roads.
        // Precondition: N being greater than zero, which represents the landmarks.
        // Postcondition: A city object with adjacency list.

        this.N = N;
        adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addRoad(int A, int B) {
        // Summary: This method adds an undirected road between two of the landmarks
        // Precondition: There has to be existing A and B landmarks, A bigger than or
        // equal to 1 and, B<=N, A not equal to B.
        // Postcondition: The adjacency list gets updated to include bidirectional
        // connection between A and B.
        adj.get(A - 1).add(B - 1);
        adj.get(B - 1).add(A - 1);
    }

    // The BFS(Breadth First Search) method to calculate shortest distances from a
    // given landmark
    public int[] bfs(int start) {
        // Summary: The BFS(Breadth First Search) method to calculate shortest distances
        // from a given landmark to all others.
        // Precondition: There must exist a starting landmark.
        // Postcondition: Returns an array where the value at index represents the
        // shortest distance from 'start' to indexed landmark. And if the landmark is
        // unreachable from the start distance is -1.

        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();

        dist[start] = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : adj.get(node)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[node] + 1;
                    queue.add(neighbor);
                }
            }
        }

        return dist;
    }

    // This function finds all valid pairs of landmarks
    public void findValidPairs(int X, int Y) {
        // Summary: This function finds pairs of landmarks where adding a new road
        // between them does not reduce the shortest path distance between landmarks X
        // and Y.
        // Precondition: 1 <= X, Y <= N, X != Y. Landmarks X and Y must be existing in
        // the city.
        // Postcondition: Prints the number of valid pairs and each pair's details if it
        // is existing. If no valid pairs exist, it prints -1.

        // Find the shortest distances from X and Y using the BFS method.
        int[] distFromX = bfs(X - 1);
        int[] distFromY = bfs(Y - 1);
        int originalDistance = distFromX[Y - 1];

        // Handle the edge case where there's no path between X and Y initially.
        if (originalDistance == -1) {
            System.out.println(-1);
            return;
        }

        List<int[]> newRoads = new ArrayList<>();

        // Check if adding a new road affects the distance between all pairs.
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // Continue if the landmarks are already connected
                if (adj.get(i).contains(j)) {
                    continue;
                }

                // Calculate the new potential distance from X to Y if a road between i and j is
                // added
                int newDistXToY = Math.min(distFromX[i] + 1 + distFromY[j], distFromX[j] + 1 + distFromY[i]);

                // The new road is valid if it does not reduce the distance between X and Y
                if (newDistXToY >= originalDistance) {
                    newRoads.add(new int[] { i + 1, j + 1 });
                }
            }
        }

        // The output of results
        if (newRoads.isEmpty()) {
            System.out.println(-1);
        } else {
            System.out.println(newRoads.size());
            for (int[] road : newRoads) {
                System.out.println(road[0] + " " + road[1]);
            }
        }
    }

    public static void main(String[] args) {
        // Summary: Reads inputs, initializes the city including roads, and finds valid
        // pairs of new roads between the landmarks.
        // Precondition: Input should contain four integers N, M, X, Y and also M pairs
        // of integers representing the roads between landmarks.
        // 1 <= N, M, X, Y <= 1000 (assuming reasonable constraints) and 1 <= A, B <= N
        // for each pair.
        // Postcondition: A graph of a city is constructed, and valid pairs of landmarks
        // where new roads can be added are printed out.

        Scanner sc = new Scanner(System.in);

        // Input parsing
        int N = sc.nextInt();
        int M = sc.nextInt();
        int X = sc.nextInt();
        int Y = sc.nextInt();

        City city = new City(N);

        for (int i = 0; i < M; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            city.addRoad(A, B);
        }

        // Find and print valid pairs of roads
        city.findValidPairs(X, Y);

        sc.close();
    }
}
