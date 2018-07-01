package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirlineIdMap {
	
	private Map<Integer,Airline> map;
	
	public AirlineIdMap() {
		map = new HashMap<>();
	}

	public Airline get(int airlineId) { // id->ogetto
		return map.get(airlineId);
	}
	
	public Airline get(Airline airline) { // oggetto-> controllo se ce l'ho nella mappa, se non ce l'ha mette quello nuovo
		Airline old = map.get(airline.getAirlineId());
		if(old == null) {
			map.put(airline.getAirlineId(), airline);
			return airline;
		}else
			return old; // riferimento a oggetto nuovo viene perso
	}
	
	public void put(Airline airline, int airlineId) {
		map.put(airlineId, airline);
	}
}
