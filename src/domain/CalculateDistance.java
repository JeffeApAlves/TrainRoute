package domain;

/**
 * Calcula as distancias das rotas indicadas no arquivo 'input.txt' na propriedade 'distance.routes'
 *
 * @file CalculateDistance.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
 */


import utilities.DataFile;
import utilities.FileProperty;

public class CalculateDistance extends Base {

	/**
	 * Baseada na lista de rotas contida no arquivo 'input.txt', na proprieda 'distance.routes',
	 * calcula a distancia de cada uma. 
	 * Rotas nao possiveis serao setada como nao existente atraves da chamada do
	 * metodo setAvailabe(false) e o valor da distancia sera 0.0   
	 * 
	 * @return Retorna uma lista de rotas com sua respectiva distancia calculada e com a indicacao 
	 * se e uma rota possivel
	 */
	public Route[] calculateAll(){
		
		return calculate(DataFile.readList(FileProperty.DISTANCE_ROUTES)); 
	}
	
	/**
	 * Calcula a distancia de uma lista de rotas(viagens) 
	 *  
	 * @param trips lista de key das rotas(viagens) para o calculo da distancia
	 * @return Rotas com suas respectivas distancias calculadas
	 */
	static public Route[] calculate(String[] trips){
		
		Route[] result = null;
		
		int i = 0;
		
		if(trips!=null && trips.length>0){
			
			result = new Route[trips.length];
			
			for(String id: trips){
				
				Route r = new Route(id,graph.calculateDistance(id));
				r.setAvailable(graph.isRouteAvailable(id));
			
				result[i++] = r;
			}
		}
		
		return result; 
	}
	
	@Override
	public boolean execute() {
		
		calculateAll();
		
		return true;
	}	
}
