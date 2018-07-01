package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class RouteIdMap {
	
private Map<Integer,Route> map;
	
	public RouteIdMap() {
		map = new HashMap<>();
	}

	public Route get(int routeId) { // id->ogetto
		return map.get(routeId);
	}
	
	public Route get(Route route) { // oggetto-> controllo se ce l'ho nella mappa, se non ce l'ha mette quello nuovo
		Route old = map.get(route.getRouteId());
		if(old == null) {
			map.put(route.getRouteId(), route);
			return route;
		}else
			return old; // riferimento a oggetto nuovo viene perso
	}
	
	public void put(Route route, int routeId) {
		map.put(routeId, route);
	}

}
