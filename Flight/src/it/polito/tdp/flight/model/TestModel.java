package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestModel {
	
	public static void main(String args[]) {
		Model m = new Model();
		
		Airport a = m.getAirports().get(0);
		System.out.println(a);
		System.out.println(a.getRoutes());
		
		m.createGraph();
		m.printStats();
		
		Set<Airport> biggestSCC = m.getBiggestSCC();
		System.out.println(biggestSCC.size());
		
			
			
			List<Airport> airportList = new ArrayList<Airport>(biggestSCC);
			
			int id1 = airportList.get(0).getAirportId();
			int id2 = airportList.get(1).getAirportId();
			
			System.out.println(m.getShortestPath(id1, id2));
		
			System.out.println("Airport code error.");
		
	}

}
