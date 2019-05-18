package onTheRoad;

import java.util.List;

import structure5.Graph;

/**
 * Class whose main method reads in description of graph and trip requests,
 * and then returns shortest paths (according to distance or time) from one
 * given vertex to another.  The input file is given by a command line argument.
 * @author Hussein Faara
 * @date 05/07/2019
 */

public class Optimizer {
	public static void main(String[] args) {
		// a file parser for the input file
		FileParser fp = new FileParser(args[0]);		
		
		// a list of trips in the input file
		List<TripRequest> trips = fp.getTrips();
		
		try {
			// a distance graph with with file input
			Graph<String, Double> distanceGraph = fp.makeGraph(true);
			
			// a time graph with given file input
			Graph<String, Double> timeGraph = fp.makeGraph(false);
			
			// processing each trip and printing out the optimal route
			for(TripRequest trip: trips) {
				try {
					if(trip.isDistance()) {
						// printing out the trip request
						System.out.println(trip);
						
						// prints out optimal output if trip request is for an optimal distance route
						System.out.println("Shortest distance from " + trip.getStart() + " to " + trip.getEnd());
						GraphAlgorithms.printShortestPathDistance(
								GraphAlgorithms.getShortestPath(distanceGraph, trip.getStart(), trip.getEnd()));
					}
					else {
						// printing out the trip request
						System.out.println(trip);
						
						// prints out optimal output if trip request is for an optimal time route
						System.out.println("Shortest time from " + trip.getStart() + " to " + trip.getEnd());
						GraphAlgorithms.printShortestPathTime(
								GraphAlgorithms.getShortestPath(timeGraph, trip.getStart(), trip.getEnd()));
					}
				} catch (Exception e) {
					
				}	
			}
		} catch (Exception e) {
			return;
		}
		
		
	}
}
