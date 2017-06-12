package domain;

/**
 * Filtra as rotas indicadas no arquivo 'input.txt' na propriedade 'filtertrips[X].routes' 
 * utilizando a condicao indicada na propriedade filtertrips[1].condition
 *
 * @file Filter.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/


import utilities.DataFile;
import utilities.FileProperty;

public class Filter extends Base {
	

	/**
	 * 
     * Verifica se as rotas indicadas no arquivo 'input.txt' na propriedade 'filtertrips[X].routes' 
     * satifazem a condicao indicada em 'filtertrips[X].condition'.
     * Condicoes permitidas: '<','>','<=','>=','==','!='
     * Operandos permitidos STOPS,DISTANCE
     * Rotas invalidas serao desconsideradas
 	 * 
	 * @param num_test indice do filtro
	 * @return Rotas filtradas pela condincao
	 */
	public Route[] filterBy(int index_filter ){
	
		Route routes[]		= null;
		String[]values		= DataFile.readList(FileProperty.FILTER_ROUTES,index_filter);
		String str_operand	= DataFile.readLiteralOperand(FileProperty.FILTER_CONDITION,index_filter);
		String condition	= DataFile.readCondition(FileProperty.FILTER_CONDITION,index_filter);		
		int ref				= DataFile.readInteger(FileProperty.FILTER_CONDITION,index_filter);
		
		if(values!=null && values.length>0){
			
			Route[] temp = new Route[values.length];
			
			Route r;
		
			int i = 0 ;
			
			for(String s: values){
				
				r = new Route(s,graph.calculateDistance(s));
				r.setAvailable(graph.isRouteAvailable(s));
				
				if(r.testCondition(condition,str_operand,ref) && r.isAvailable()){
	
					temp[i++] = r;
				}
			}
			
			routes	= new Route[i];
			System.arraycopy(temp, 0,routes, 0, i);
		}
		
		return routes;
	}
}
