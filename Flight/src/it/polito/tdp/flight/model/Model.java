package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	FlightDAO dao = null;
	List<Airport> airports;
	List<Airline> airlines;
	List<Route> routes;
	
	AirlineIdMap airlineIdMap;
	AirportIdMap airportIdMap;
	RouteIdMap routeIdMap;
	
	SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>graph;
	
	
	public Model()
	{
		dao = new FlightDAO();
		
		airlineIdMap = new AirlineIdMap();
		airportIdMap = new AirportIdMap();
		routeIdMap = new RouteIdMap();
		
		this.airlines = dao.getAllAirlines(airlineIdMap); // quando vai a inserire l'oggetto lo fai nell'identity map
		System.out.println(airlines.size());

		this.airports = dao.getAllAirports(airportIdMap);
		System.out.println(airports.size());

		this.routes = dao.getAllRoutes(airlineIdMap,airportIdMap,routeIdMap); // deve creare gli incroci
		System.out.println(routes.size());
		
	}
	
	public List<Airport> getAirports() {
		if(this.airports == null)
			return new ArrayList<Airport>(); // SE E VUOTO RICEVO UNA LISTA VUOTA!!! -> IMPORTANTE
		return this.airports;
	}
	
	public void createGraph() {
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph,  this.airports);
		for(Route r :  routes) {
			Airport sourceAirport = r.getSourceAirport();
			Airport destinationAirport = r.getDestinationAirport();
			if(!sourceAirport.equals(destinationAirport)) {
				double weight = LatLngTool.distance(new LatLng(sourceAirport.getLatitude(),sourceAirport.getLongitude()), new LatLng (destinationAirport.getLatitude(),destinationAirport.getLongitude()), LengthUnit.KILOMETER );
				Graphs.addEdge(this.graph, sourceAirport, destinationAirport, weight);
			}
		}
		
		System.out.println("Numero vertici:"+this.graph.vertexSet().size());
		System.out.println("Numero archi:"+this.graph.edgeSet().size());
		
	}
	
	public void printStats() {
		if(this.graph == null) {
			this.createGraph(); // se non c'è crealo
		}
		
		ConnectivityInspector<Airport,DefaultWeightedEdge> ci = new ConnectivityInspector<Airport,DefaultWeightedEdge>(this.graph);
		System.out.println(ci.connectedSets().size());
		
	
	}
	
	public Set<Airport> getBiggestSCC(){
		
		ConnectivityInspector<Airport,DefaultWeightedEdge> ci = new ConnectivityInspector<Airport,DefaultWeightedEdge>(this.graph);
		Set<Airport> bestSet = null;
		int bestSize = 0;
		for(Set<Airport> s : ci.connectedSets()) {
			if(s.size()>bestSize) {
				bestSet = new HashSet<>(s); // faccio deepcopy
				bestSize = s.size();
			}
		}
		
		return bestSet;

	}
	
	public List<Airport> getShortestPath(int id1, int id2) {
		Airport nyc = airportIdMap.get(id1);
		Airport bgy = airportIdMap.get(id2);
		
		if(nyc == null || bgy == null) {
			throw new RuntimeException("Gli aeroporti selezionati non sono presenti nel grafo");
		}
		
		
		ShortestPathAlgorithm<Airport,DefaultWeightedEdge> spa = new DijkstraShortestPath<Airport,DefaultWeightedEdge>(this.graph);
		double weight = spa.getPathWeight(nyc, bgy);
		System.out.println(weight);
		GraphPath<Airport,DefaultWeightedEdge> gp = spa.getPath(nyc, bgy);
		return gp.getVertexList();
	}
}
