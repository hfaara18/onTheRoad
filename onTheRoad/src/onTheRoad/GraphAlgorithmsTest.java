/**
 * 
 */
package onTheRoad;

import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import structure5.Edge;
import structure5.Graph;
import structure5.GraphListDirected;

/**
 * A JUnit test case for 3 methods in the GraphAlgorithms class
 * 
 * @author Hussein
 * 
 * 
 *
 */
public class GraphAlgorithmsTest {
	// the test graph
	Graph<String, Double> testGraph;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// initializing test graph
		testGraph = new GraphListDirected<String, Double>();
		
		// adding vertices to the graph
		testGraph.add("a");
		testGraph.add("b");
		testGraph.add("c");
		testGraph.add("d");
		
		// adding edges to the graph
		testGraph.addEdge("a", "b", 1.0);
		testGraph.addEdge("a", "c", 1.0);
		testGraph.addEdge("b", "d", 3.0);
		testGraph.addEdge("c", "d", 3.0);
	}

	/**
	 * Test method for {@link onTheRoad.GraphAlgorithms#graphEdgeReversal(structure5.Graph)}.
	 */
	@Test
	public void testGraphEdgeReversal() {
		// a replica of the test graph with reversed edges
		Graph<String, Double> reverseTestGraph = GraphAlgorithms.graphEdgeReversal(testGraph);
		
		// checking if no vertices were deleted after edge reversal
		for (String v: testGraph) {
			assertTrue(reverseTestGraph.contains(v));
		}
		
		// an iterator for the edges in the test graph and its reverse graph
		Iterator<Edge<String, Double>> testEdges = testGraph.edges();
		Iterator<Edge<String, Double>> revTestEdges = reverseTestGraph.edges();
		
		// checking if test graph edges were correctly reversed
		while(testEdges.hasNext()) {
			assertTrue(testEdges.next().here().equals(revTestEdges.next().there()));
		}	
	}

	/**
	 * Test method for {@link onTheRoad.GraphAlgorithms#breadthFirstSearch(structure5.Graph, java.lang.Object)}.
	 */
	@Test
	public void testBreadthFirstSearch() {
		// performing a BFS on test graph
		GraphAlgorithms.breadthFirstSearch(testGraph, "a");

		// checking if all edges were visited during BFS
		for (String v: testGraph) {
			assertTrue(testGraph.isVisited(v));
		}
	}

	/**
	 * Test method for {@link onTheRoad.GraphAlgorithms#isStronglyConnected(structure5.Graph)}.
	 */
	@Test
	public void testIsStronglyConnected() {
		// checking if test graph is strongly connected
		assertFalse(GraphAlgorithms.isStronglyConnected(testGraph));
		
		// adding components to test graph to make it strongly connected
		testGraph.addEdge("d", "a", 3.0);
		testGraph.add("e");
		testGraph.addEdge("d", "e", 3.0);
		testGraph.addEdge("e", "b", 3.0);
		
		// checking if test graph is now strongly connected
		assertTrue(GraphAlgorithms.isStronglyConnected(testGraph));
	}
}
