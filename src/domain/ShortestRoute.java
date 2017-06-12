package domain;

/**
 * Identifica as rotas mais curtas para as viagens(rotas) que foram indicada no arquivo input.txt 
 * na propriedade shourtestRouter.trip
 *
 * @file ShortestRoute.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import utilities.DataFile;
import utilities.FileProperty;

public class ShortestRoute extends Base {
	
	/**
	 * Retorna uma lista das rotas mais curtas para cada viagem(rota) com entrada no arquivo input.txt
	 * 
	 * @return Lista de rotas mais curtas para cada viagem(rota)
	 */
	public Map<String ,Route[]> getShortestRoute(){

		Map<String,Route[]>	 mapRoutes = null;
		String[]trips		= DataFile.readList(FileProperty.SHORTEST_ROUTES);

		if(trips!=null && trips.length>0){
			
			mapRoutes = new LinkedHashMap<String ,Route[]>();
			
			for(String id_trip: trips){
				
				id_trip = Route.formatKey(id_trip);
				
				if(id_trip!=null){
					
					Route trip = new Route(id_trip);
	
					Route[] routes = getShortestRoute(trip);
					
					if(routes!=null){
						
						mapRoutes.put(id_trip, routes);
					}
				}
			}
		}
		
		return mapRoutes;
	}

	/**
	 * Retorna uma lista das rotas mais curtas para uma viagem(rota)
	 * 
	 * @param trip key de uma viagem(rota) que se deseja solicitar as rotas mais curtas
	 * @return Lista de rotas mais curtas
	 */
	public Route[] getShortestRoute(Route trip){
		
		Route[] result			= null;
		Route[] possible_routes	= CalculateDistance.calculate(graph.getAllRoutePossible(trip));
		double dmin				= Double.MAX_VALUE;
		
		if(possible_routes!=null){
			
			Vector<Route>vroutes = new Vector<>();
			
			for(Route route : possible_routes){
				
				if(route.getDistance() <=dmin) {
						
					dmin = route.getDistance();
						
					vroutes.add(route);
				}
			}
			result = vroutes.toArray(new Route[vroutes.size()]);
		}
		
		return result;
	}
}
