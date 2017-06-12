package domain;

import utilities.DataFile;
import utilities.FileProperty;

public class CalculateTime extends Base {
	
	/**
	 * Baseada na lista de rotas contida no arquivo 'input.txt', na proprieda 'time.routes',
	 * calcula o tempo de cada uma. 
	 * Rotas nao possiveis serao setada como nao existente atraves da chamada do
	 * metodo setAvailabe(false) e o valor do tempo sera 0.0   
	 * 
	 * @return Retorna uma lista de rotas com sua respectiva tempos calculados e com a indicacao 
	 * se e uma rota possivel
	 */
	public Route[] calculateAll(){
		
		return calculate(DataFile.readList(FileProperty.TIME_ROUTES)); 
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
				
				Route r = new Route(id);
				r.setTotalTimeDistance(graph.calculateTime(id));
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
