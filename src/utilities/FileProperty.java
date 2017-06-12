package utilities;

/**
 * Propriedades utilizadas no arquivo de entrada (input.txt).
 * *  
 * 
 * 
 * @file DatFile.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
 *  
**/

/***************************************************************************************************
 *                        Mapeamento das propriedades do arquivo de entrada	                       *
 ***************************************************************************************************/

public enum FileProperty {
	
/** Mapa de estacoes
 *
 *  Diagrama esta baseado na descricao do teste. 
 *  '*' Indica rotas de chegada na estacao
 *  (D) Indica a distancia
 * 
 * 	   ------------(3)----------------- 
 * 	   |                              |
 * 	   |                              *	
 * 	   | -(7)-[ A ]------(5)-------*[ B ]
 * 	   | |      |                     |
 * 	   | |     (5)                   (4)
 * 	   | |      |                     |
 * 	   | *      *                     *
 * 	  [ E ]*--[ D ] -----(8)-------*[ C ]
 * 	    *          *-----(8)-------   |
 * 	    |                             |
 * 	    ----------(2)------------------
 *
 * Mapeamento de todas as rotas disponiveis 
 * O SW esta trabalhando dinamicamente, podendo ser adicionadas novas rotas
 * Para numeros decimais usar '.'
 * Separador por virgula
 *
 */
 	//Ex: no input.txt:
	//graph.routes = A-B5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
	
	GRAPH_ROUTES("graph.routes"),


/** Questoes:
 *	1) The distance of the route A-B-C.
 *	2) The distance of the route A-D.
 *	3) The distance of the route A-D-C.
 *	4) The distance of the route A-E-B-C-D.
 *	5) The distance of the route A-E-D.
 *
 * Mapeamento das rotas para calculo da distancia
 * O hifem e opcional
 * Separador virgula
 * Possibilidade de adiconar novas rotas
 *
 */
 	//Ex: no input.txt:
	//distance.routes	= A-B-C , A-D , A-D-C , A-E-B-C-D , A-E-D
	
	DISTANCE_ROUTES("distance.routes"),
	
	TIME_ROUTES("time.routes"),	


/** Questoes:
 *	6) The number of trips starting at C and ending at C with a maximum of 3 stops. In the sample
 *	data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
 *	7) The number of trips starting at A and ending at C with exactly 4 stops. In the sample data
 *	below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
 *	10)The number of different routes from C to C with a distance of less than 30. In the sample
 *	data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
 *
 * Verifica se as rotas indicadas na propriedade filtertrips[X].routes satifazem a condicao
 * indicada em filtertrips[X].condition.
 * Condicoes permitidas: <,>,<=,>=,==,!=.
 * Operandos permitidos STOPS,DISTANCE.
 * O hifem e opcional.
 * Rotas invalidas serao desconsideradas.
 * Importante: Separador por virgula.
 * Possibilidade de adiconar novos filtros,diferenciar atraves do index [X].
 *
 */
 	//Ex: no input.txt:
	//filtertrips[1].condition = STOPS <= 3
	//filtertrips[1].routes	= C-D-C , C-E-B-C
	
	FILTER_CONDITION("filtertrips[%d].condition"),
	FILTER_ROUTES("filtertrips[%d].routes"),	


/** Questoes:
 *	8) The length of the shortest route (in terms of distance to travel) from A to C.
 *	9) The length of the shortest route (in terms of distance to travel) from B to B.
 *
 * Encontra as opcoes de rotas menores(distancia) entre o ponto de partida e o de chegada
 * Possibilidade de adiconar quantas viagens(rotas) necessarias.
 * Rotas invalidas serao desconsideradas  
 * O hifem e opcional
 * Separador virgula
 */

 	//Ex: no input.txt:
	//shortestRouter.trip	= A-C , B-B
	
	SHORTEST_ROUTES("shortestRouter.trip");


	private String name;
	
	FileProperty(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
