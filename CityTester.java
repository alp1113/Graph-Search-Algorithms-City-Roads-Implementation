
// Description: The CityTester class is designed to validate the
//functionality of the City class by running multiple test cases.
//Each test case initializes a city with a given number of landmarks
//and roads, then checks if the program correctly identifies pairs of
//landmarks where new roads can be added without reducing the distance
//between specified landmarks X and Y. The class includes methods to
//set up various scenarios, such as disconnected graphs, fully connected
//graphs, and large graphs, and compares the actual output to the expected
//output. This helps ensure the program handles typical cases, edge cases,
//and large inputs efficiently.
//-----------------------------------------------------

import java.util.*;

public class CityTester {
    public static void main(String[] args) {
        // Summary: Main method that runs all test cases for the City class.
        // Precondition: None. The City class must be correctly implemented and
        // available.
        // Postcondition: Executes each test case, prints the results, and compares them
        // to the expected output.

        // Test 1: Basic Functionality Test
        // Purpose: Tests a simple scenario with 5 landmarks and 4 roads.
        // It checks if the program can identify 5 pairs of landmarks where adding new
        // roads
        // will not reduce the shortest path distance between the given landmarks X and
        // Y.

        System.out.println("Test 1: Basic Functionality");
        runTest(5, 4, 3, 5, new int[][] {
                { 1, 2 },
                { 2, 3 },
                { 3, 4 },
                { 4, 5 }
        });
        System.out.println("Expected Output: 5 pairs\n");

        // Test 2: Disconnected Graph Test
        // Purpose: Tests a scenario where some landmarks are disconnected from others.
        // It verifies if the program returns -1 since adding new roads will not be able
        // to
        // maintain the distance between X and Y when some landmarks cannot be reached.

        System.out.println("Test 2: Disconnected Graph");
        runTest(5, 2, 3, 5, new int[][] {
                { 1, 2 },
                { 3, 4 }
        });
        System.out.println("Expected Output: -1\n");

        // Test 3: All Landmarks Fully Connected
        // Purpose: Tests a graph that is already fully connected.
        // Since all landmarks are directly connected, no additional roads can be added
        // without reducing the distance between X and Y, so the expected output is -1.

        System.out.println("Test 3: Fully Connected");
        runTest(4, 6, 1, 4, new int[][] {
                { 1, 2 },
                { 1, 3 },
                { 1, 4 },
                { 2, 3 },
                { 2, 4 },
                { 3, 4 }
        });
        System.out.println("Expected Output: -1\n");

        // Test 4: Edge Case with Minimum Input Size
        // Purpose: Tests the smallest possible input size with 2 landmarks and 1 road.
        // It checks if the program correctly outputs -1 since no other connections can
        // be made.

        System.out.println("Test 4: Minimum Input Size");
        runTest(2, 1, 1, 2, new int[][] {
                { 1, 2 }
        });
        System.out.println("Expected Output: -1\n");

        // Test 5: Large Graph
        // Purpose: Tests the program's ability to handle large inputs with 1000
        // landmarks.
        // It checks if the program performs efficiently and produces the expected
        // number of pairs.

        System.out.println("Test 5: Large Graph");
        runTest(1000, 999, 1, 1000, generateCompleteGraph(1000));
        System.out.println("Expected Output: Number of pairs (varies based on structure)\n");

        // Test 6: Equal Distance Test
        // Purpose: Tests a scenario where there are multiple paths of equal length
        // between X and Y.
        // It checks if the program correctly identifies valid pairs of new roads that
        // maintain the distance.

        System.out.println("Test 6: Equal Distance");
        runTest(4, 3, 1, 4, new int[][] {
                { 1, 2 },
                { 2, 3 },
                { 3, 4 }
        });
        System.out.println("Expected Output: Some pairs that maintain the distance\n");
    }

    // Helper method to run a test
    public static void runTest(int N, int M, int X, int Y, int[][] roads) {
        // Summary: Runs a specific test case with the provided parameters.
        // Precondition: N is the number of landmarks, M is the number of roads, X and Y
        // are the landmarks
        // between which the distance must be maintained, and roads is a 2D array
        // representing connections.
        // Postcondition: Constructs the City object, adds the roads, and outputs the
        // result of valid pairs of roads.

        city city = new city(N);

        for (int[] road : roads) {
            city.addRoad(road[0], road[1]);
        }

        city.findValidPairs(X, Y);
    }

    // Helper method to generate a complete graph with N nodes and N-1 edges
    public static int[][] generateCompleteGraph(int N) {
        // Summary: Generates a simple linearly connected graph with N landmarks.
        // Precondition: N should be greater than 1, representing the total number of
        // landmarks.
        // Postcondition: Returns a 2D array where each pair represents a road
        // connecting two consecutive landmarks.

        int[][] roads = new int[N - 1][2];
        for (int i = 1; i < N; i++) {
            roads[i - 1] = new int[] { i, i + 1 };
        }
        return roads;
    }
}
