package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {
	
	private Map<Integer,Airport> map;
	
	public AirportIdMap() {
		map = new HashMap<>();
	}

	public Airport get(int airportId) { // id->ogetto
		return map.get(airportId);
	}
	
	public Airport get(Airport airport) { // oggetto-> controllo se ce l'ho nella mappa, se non ce l'ha mette quello nuovo
		Airport old = map.get(airport.getAirportId());
		if(old == null) {
			map.put(airport.getAirportId(), airport);
			return airport;
		}else
			return old; // riferimento a oggetto nuovo viene perso
	}
	
	public void put(Airport airport, int airportId) {
		map.put(airportId, airport);
	}
}