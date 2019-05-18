package onTheRoad;

/** Common algorithms for Graphs.  All assume working with a directed graph.
 * Written 05/07/2019
 * @author Hussein Faara (based on algorithms in Bailey, Java Structures)
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
//import java.util.PriorityQueue;

import structure5.Association;
import structure5.ComparableAssociation;
import structure5.Map;
import structure5.MapList;
import structure5.Queue;
import structure5.QueueList;
import structure5.SkewHeap;
import structure5.Table;
import structure5.Graph;
import structure5.GraphListDirected;
import structure5.Edge;
import structure5.PriorityQueue;

public class GraphAlgorithms {
	
	protected static ArrayList<Edge<String, Double>> graph = new ArrayList<Edge<String, Double>>();
	protected static String st = "";
	protected static double cost;
	
	/**
	 * @param g
	 *            directed graph
	 * @return graph like g except all edges are reversed
	 */
	public static <V, E> Graph<V, E> graphEdgeReversal(Graph<V, E> g) {
		if (g == null) {
			throw new IllegalArgumentException();
		}
		
		//create a new directed graph that is stored as an adjacency list
		Graph<V, E> temp = new GraphListDirected<V, E>(); 
		
		// copy the vertices from the original graph g
		for (V vertex: g) {
			temp.add(vertex);
		}
		
		//iterate through the edges of g and add a new edge of reversed direction in the new graph
		Iterator<Edge<V, E>> edges = g.edges();
		while (edges.hasNext()) {
			Edge<V,E> current = edges.next();
			temp.addEdge(current.there(), current.here(), current.label());
		}
				
		//return the new graph
		return temp;
	}

	/**
	 * Perform breadTH-first search of g from vertex start. At end, can ask
	 * vertices in if they were visited
	 * 
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting vertex for search
	 */
	public static <V, E> void breadthFirstSearch(Graph<V, E> g, V start) {
		if (g == null || start == null) {
			throw new IllegalArgumentException();
		}
		else if (!g.contains(start)) {
			throw new IllegalArgumentException();
		}
		
		//reset graph
		g.reset();		
		
		//follow the BFS algorithm described in slides using a queue
		Queue<V> queue = new QueueList<V>();		
		queue.enqueue(start);
		
		while (!queue.isEmpty()) {
			V current = queue.dequeue();
			if (!g.isVisited(current)) {
				g.visit(current);
				
				Iterator<V> edges = g.neighbors(current);				
				while(edges.hasNext()) {
					V vert = edges.next();
					if (!g.isVisited(vert)) {
						queue.enqueue(vert);
					}
				}
			}
		}
	}

	/**
	 * @param g
	 *            directed graph
	 * @return whether graph g is strongly connected.
	 */
	public static <V, E> boolean isStronglyConnected(Graph<V, E> g) {
		// an iterator for the graph
		Iterator<V> gIterator = g.iterator();
		
		// the first vertex in the graph
		V startV = null;
		if (gIterator.hasNext()) {
			startV = gIterator.next();
		}
		
		// a breadth first search through the graph
		breadthFirstSearch(g, startV);
		
		// checking if all vertices were visited
		for (V vertex: g) {
			if (!g.isVisited(vertex)) {
				return false;
			}
		}
		
		// a replica of the given graph with its edges reversed
		Graph<V, E> reverseG = graphEdgeReversal(g);
		
		// a breadth first search through the graph
		breadthFirstSearch(reverseG, startV);	
		
		// checking if all vertices were visited
		for (V vertex: reverseG) {
			if (!reverseG.isVisited(vertex)) {
				return false;
			}
		}
		
		return true;
	}

	
	/**
	 * Perform Dijkstra's algorithm on graph g from vertex start.
	 * 
	 * @param g
	 * @param start
	 * @return map taking each vertex to cost to get there from start and the
	 *         last edge in a shortest path to the vertex
	 */
/**
	public static Map<String, ComparableAssociation<Double, Edge<String, Double>>> dijkstra(
			Graph<String, Double> g, String start) {
		
		// a map of vertices and its distance from start
		Map<String, Double> dist = new MapList<String, Double>();
		
		// a map of the predecessor of the vertices
		Map<String, String> parents = new MapList<String, String>();
		
		// the starting vertex
		st = start;
		
		// a priority queue of distances from the sources
		PriorityQueue<ComparableAssociation<String, Double>> queue = new 
				PriorityQueue<ComparableAssociation<String, Double>>(g.size(), 
				new Comparator<ComparableAssociation<String, Double>>() {
			public int compare(ComparableAssociation<String, Double> a1, ComparableAssociation<String, Double> a2) {
				if (a1.getValue() > a2.getValue()) {
					return 1;
				}
				else if(a1.getValue() < a2.getValue()) {
					return -1;
				}
				else {
					return 0;
				}
			};
		});
		
		for (String v: g) {
			if (v.equals(start)) {
				dist.put(v, 0.0);
				queue.add(new ComparableAssociation<String, Double>(v, 0.0));
			}
			else {
				dist.put(v, Double.MAX_VALUE);
				queue.add(new ComparableAssociation<String, Double>(v, dist.get(v)));
			}
			parents.put(v, v);
		}
		
		while(!queue.isEmpty()) {
			ComparableAssociation<String, Double> vAssoc = queue.remove();
			String v = vAssoc.getKey();
			
			Iterator<String> vIt = g.neighbors(v);
			
			while(vIt.hasNext()) {
				String u = vIt.next();
				Double tentative = dist.get(v) + g.getEdge(v, u).label();
				
				if (tentative < dist.get(u)) {
					dist.put(u, tentative);
					parents.put(u, v);
					queue.add(new ComparableAssociation<String, Double>(u, tentative));
				}				
			}
		}
		
		// the return map
		Map<String, ComparableAssociation<Double, Edge<String, Double>>> map = new MapList<String, ComparableAssociation<Double, Edge<String, Double>>>();
		
		// adding entries to the return map
		for (String v: g) {	
			// an association of the distance of each vertex from the start and the last edge to the vertex
			ComparableAssociation<Double, Edge<String, Double>> assoc = new ComparableAssociation<Double, Edge<String, Double>>(dist.get(v), g.getEdge(parents.get(v), v));
			map.put(v, assoc);		
		}
		
		return map;
	}
**/
	/**
	 * Perform Dijkstra's algorithm on graph g from vertex start.
	 * 
	 * @param g
	 * @param start
	 * @return map taking each vertex to cost to get there from start and the
	 *         last edge in a shortest path to the vertex
	 */	
	public static Map<String,ComparableAssociation<Double,Edge<String,Double>>> 
	dijkstra(Graph<String,Double> g, String start)
	// pre: g is a graph; start is source vertex
	// post: returns a dictionary of vertex-based results
	// value is association (total-distance,prior-edge)
	{
		st = start;
		// keep a priority queue of distances from source
		PriorityQueue<ComparableAssociation<Double,Edge<String,Double>>> q = 
				new SkewHeap<ComparableAssociation<Double, Edge<String,Double>>>();
		
		// results, sorted by vertex
		Map<String,ComparableAssociation<Double,Edge<String,Double>>> result = 
				new Table<String, ComparableAssociation<Double, Edge<String,Double>>>();
		String v = start; // last vertex added
		// result is a (total-distance,previous-edge) pair
		
		ComparableAssociation<Double,Edge<String,Double>> possible = 
				new ComparableAssociation<Double,Edge<String,Double>>(0.0,null);
		// as long as we add a new vertex...
		
		while (v != null)
		{
			if (!result.containsKey(v)) {
				// visit node v - record incoming edge
				result.put(v,possible);
				// vDist is shortest distance to v
				double vDist = possible.getKey();
				// compute and consider distance to each neighbor
				Iterator<String> ai = g.neighbors(v);
				while (ai.hasNext()) {
					// get edge to neighbor
					Edge<String,Double> e = g.getEdge(v,ai.next());
					// construct (distance,edge) pair for possible result
					possible = new ComparableAssociation<Double,
					Edge<String,Double>>(vDist+e.label(), e);
					q.add(possible); // add to priority queue
				}
			}
					// now, get closest (possibly unvisited) vertex
			if (!q.isEmpty()) {
				possible = q.remove();
				// get destination vertex (take care w/undirected graphs)
				v = possible.getValue().there();
				if (result.containsKey(v))
					v = possible.getValue().here();
			} else {
				// no new vertex (algorithm stops)
				v = null;
			}
		}
		
		return result;
	}
	
	/**
	 * Compute shortest path from start to end using Dijkstra's algorithm
	 * 
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting node in search for shortest path
	 * @param end
	 *            ending node in search for shortest path
	 * @return pair of the total cost from start to end in shortest path as well
	 *         as a list of edges in that shortest path
	 */
	public static Association<Double, ArrayList<Edge<String, Double>>> getShortestPath(
			Graph<String, Double> g, String start, String end) {
		
		Map<String, ComparableAssociation<Double, Edge<String, Double>>> map = dijkstra(g, start);
		cost = Math.max(cost, map.get(end).getKey());
		
		if (start.equals(end)) {
			double temp = cost;
			cost = 0.0;
			
			ArrayList<Edge<String, Double>> tempGraph = graph;
			graph = new ArrayList<Edge<String, Double>>();
			
			return new Association<Double, ArrayList<Edge<String, Double>>>(temp, tempGraph);
		} 
		else {			
			graph.add(map.get(end).getValue());			
			return getShortestPath(g, start, map.get(end).getValue().here());
		}
	}
	
	/**
	 * Using the output from Dijkstra, print the shortest path (according to
	 * distance) between two nodes
	 * 
	 * @param pathInfo
	 *            Cost and path information from run of Djikstra
	 */
	public static void printShortestPathDistance(
			Association<Double, ArrayList<Edge<String, Double>>> pathInfo) {
		
		ArrayList<Edge<String, Double>> path = pathInfo.getValue();
		double cost = pathInfo.getKey();
		
		System.out.println("\tBegin at " + st);
		
		for (int i= path.size() - 1; i>= 0; i--) {
			Edge<String, Double> e = path.get(i);
			System.out.println("\tContinue to " + e.there() + " (" + e.label() + " miles)");
		}
		
		System.out.format("Total distance: %.2f miles\n\n", cost);
		
	}

	/**
	 * Using the output from Dijkstra, print the shortest path (according to
	 * time) between two nodes
	 * 
	 * @param pathInfo
	 *            Pair consisting of total time and shortest times to each node
	 */
	public static void printShortestPathTime(
			Association<Double, ArrayList<Edge<String, Double>>> pathInfo) {
		
		// FIX THIS! 
		ArrayList<Edge<String, Double>> path = pathInfo.getValue();
		double cost = pathInfo.getKey();
		
		System.out.println("\tBegin at " + st);
		
		for (int i= path.size() - 1; i>= 0; i--) {
			Edge<String, Double> e = path.get(i);
			System.out.println("\tContinue to " + e.there() + " (" + hoursToHMS(e.label()) + ")");
		}
		
		System.out.println("Total distance: " + hoursToHMS(cost) + "\n");
	}

	/**
	 * Convert hours (in decimal) to
	 * 
	 * @param rawhours
	 *            time elapsed
	 * @return Equivalent of rawhours in hours, minutes, and seconds (to
	 *         nearest 10th of a second)
	 */
	private static String hoursToHMS(double rawhours) {
		int numHours = (int)rawhours;
		double fractionalHours = rawhours - numHours;
		int tenthSeconds = (int)Math.round(fractionalHours * 36000);
		int minutes = tenthSeconds / 600;
		int tenthSecondsLeft = tenthSeconds - (600 * minutes);
		
		int secs = tenthSecondsLeft/ 10;
		return (numHours > 0 ? numHours + " hrs " :"") + 
				(minutes > 0 ? minutes + " mins " :"") + secs +"." + (tenthSecondsLeft - secs*10)+ " secs";
	}
}
