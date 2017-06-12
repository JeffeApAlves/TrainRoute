package frontend;

/**
 * Responsavel pelo View - Text User interface(TUI)
 * 
 * @file Display.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 *  
 * Recebe o conteudo a ser mostrado atraves de arrays do tipo roto
 * 
 * Layout do TUI
 * 
 * Output  | Resultado            | Detalhes            
 * #1      | 9                    | [ABC] 
 * #2      | 5                    | [AD] 
 * #3      | 13                   | [ADC] 
 * #4      | 22                   | [AEBCD] 
 * #5      | Rota nao disponivel  | [AED] 
 * #6      | 2                    | [CDC] [CEBC] 
 * #7      | 3                    | [ABCDC] [ADCDC] [ADEBC] 
 * #8      | 9                    | [ABC] 
 * #9      | 9                    | [BCEB] 
 * #10     | 7                    | [CDC] [CEBC] [CEBCDC] [CDCEBC] [CDEBC] [CEBCEBC] [CEBCEBCEBC] 
 * 
 * 
 */

import java.util.Map;

import controller.RailSystem;
import domain.Route;
import translate.Translate;

public class Display {
	
	static int index_line = 1;
	
	static final String	FORMAT_DOUBLE	= "%.0f";
	static final String	FORMAT_DECIMAL	= "%d";
	static final String	FORMAT_ROUTE	= "[%s] ";	
	static final String	FORMAT_STRING	= "%-20s";	
	static final String	FORMAT_COL1		= "%-7s";	
	static final String	SEPARATOR		= " | ";	
	static final String	NEW_LINE		= "\n";
	
	
	/**
	 * Mostra todos os resultados.
	 * 
	**/
	public static void showAllResults() {
		
		Display.printHead();
		Display.printResultCalculateDistance(RailSystem.calculateDistance());
		Display.printResultFilter(RailSystem.filterByStops1());
		Display.printResultFilter(RailSystem.filterByStops2());
		Display.printResultShortest(RailSystem.shortestRoute());
		Display.printResultFilter(RailSystem.filterByDistance());
		Display.printResultCalculateTime(RailSystem.calculateTime());
	}	


	/**
	 * Imprime cabecalho
	 */
	private static void printHead() {
		
		System.out.printf(FORMAT_COL1+SEPARATOR+FORMAT_STRING+SEPARATOR+FORMAT_STRING+NEW_LINE,"Output","Resultado","Detalhes");
		
		index_line = 1;
	}
	
	/**
	 * Imprime a linha na tela adicionando o numero da linha
	 * 
	 * @param line Formatada e tabulada
	 */
	private static void printLine(String line){

		System.out.printf(FORMAT_COL1+SEPARATOR+FORMAT_STRING+NEW_LINE,"#"+String.format(FORMAT_DECIMAL, index_line),line);
		
		index_line++;
	}

	/**
	 * Imprime resulatado relacionado da consulta de menores rotas 
	 * 
	 * @param shortesd Lista das menores rotas
	 */
	private static void printResultShortest(Map<String, Route[]> shortest) {
		
		if(shortest!=null){
			
			for(String trip:shortest.keySet()){
				for(Route r:shortest.get(trip)){
					
					String line = String.format(FORMAT_STRING + SEPARATOR + FORMAT_ROUTE, String.format(FORMAT_DOUBLE, r.getDistance()), r.getKey());
					
					printLine(line);
				}
			}
		}
	}

	/**
	 * Imprime resultado dos calculos de distancias das rotas
	 * 
	 * @param routes Lista das rotas e seus respectivo valores de distancia
	 */
	private static void printResultCalculateDistance(Route[] routes){ 

		if(routes!=null){
			
			for(Route r:routes){
				
				String line;
	
				if(r.isAvailable()){
					
					line = String.format(FORMAT_STRING + SEPARATOR+ FORMAT_ROUTE, 
							String.format(FORMAT_DOUBLE,r.getDistance()) ,r.getKey());
					
					
				}else{
					
					line = String.format(FORMAT_STRING + SEPARATOR + FORMAT_ROUTE,
										Translate.MESSAGE.ROUTE_NO_AVAILABLE(),	r.getKey());
					
				}
				
				printLine(line);				
			}
		}
	}

	/**
	 * Imprime resultados dos filtros
	 * 
	 * @param routes
	 */
	private static void printResultFilter(Route[] routes){
		
		if(routes!=null){
			
			String line;
			
			line = String.format(FORMAT_STRING + SEPARATOR,
								String.format(FORMAT_DECIMAL, routes.length));
			
			// Imprime detalhes
			for(Route r:routes){
			
				line += String.format(FORMAT_ROUTE ,r.getKey());
			}
			
			printLine(line);		
		}
	}

	private static void printResultCalculateTime(Route[] routes){ 

		if(routes!=null){
			for(Route r:routes){
	
				String line;
				
				if(r.isAvailable()){
					
					line = String.format(FORMAT_STRING + SEPARATOR + FORMAT_ROUTE, 
							String.format(FORMAT_DOUBLE,r.getTotalTimeDistance()) ,r.getKey());
				}else{
					
					line = String.format(FORMAT_STRING + SEPARATOR + FORMAT_ROUTE,
										Translate.MESSAGE.ROUTE_NO_AVAILABLE(),	r.getKey());
				}
				
				printLine(line);		
			}
		}
	}
}
