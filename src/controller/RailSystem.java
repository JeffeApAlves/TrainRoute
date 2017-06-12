package controller;

import java.util.Map;

import domain.CalculateDistance;
import domain.CalculateTime;
import domain.Filter;
import domain.Route;
import domain.ShortestRoute;
import translate.Translate;
import utilities.DataFile;

/**
 * Classe Controle intermedia a interacao entre o View(TUI) e o domain
 * 
 * @file RailSystem.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 *
 */


public class RailSystem {

	/**
	 * Esse filtro esta mapeado no arquivo de entrada com o index = 1(DataFile.STOP1_CONDITION)
	 * Condicao do filtro STOPS <= 3 
	 * 
	 * @return Retorna rotas filtradas pela condicao
	 */
	static public Route[] filterByStops1(){
		
		Filter filter = new Filter();
		
		return filter.filterBy(DataFile.STOP1_CONDITION);
	}

	
	/**
	 * Esse filtro esta mapeado no arquivo de entrada com o index = 2(DataFile.STOP2_CONDITION) 
	 * Condicao do filtro STOPS == 4
	 * 
	 * @return Retorna uma lista de rotas filtradas pela condicao
	 */
	static public Route[] filterByStops2(){
		
		Filter filter = new Filter();
		
		return filter.filterBy(DataFile.STOP2_CONDITION);
	}
	
	/**
	 * Esse filtro esta mapeado no arquivo de entrada com o index = 3(DataFile.DISTANCE_CONDITION) 
	 * Condicao do filtro DISTANCE < 30
	 * 
	 * @return Retorna uma lista de rotas filtradas pela condicao
	 */
	static public Route[] filterByDistance(){
		
		Filter filter = new Filter();
		
		return filter.filterBy(DataFile.DISTANCE_CONDITION);
	}
	
	/**
	 * Calcula as distancias da rotas mapeadas no arquivo na propriedade 'distance.routes'
	 *  
	 * @return Retorna lista de rotas com as distancias calculadas
	 * 
	 */
	static public Route[] calculateDistance(){
		
		CalculateDistance cd = new CalculateDistance();
		
		return cd.calculateAll();
	}
	
	static public Route[] calculateTime(){
		
		CalculateTime ct = new CalculateTime();
		
		return ct.calculateAll();
	}	

	/**
	 * Mapeia todas as rotas mais curtas de todas as viagens 
	 * 
	 * @return Retorna uma lista de rotas mais curtas para cada viagem/rota
	 */
	static public Map<String ,Route[]> shortestRoute(){
		
		ShortestRoute sr = new ShortestRoute();
		
		return sr.getShortestRoute();
	}
	
	/**
	 * Inicializa o sistema de rotas
	 * 
	 * @return Se a inicializacao ocorreu com sucesso retorna true
	 */
	static public boolean init(){
		
		boolean initOK = false;
		
		if(!DataFile.checkFileExist()){
			
			initOK = false;
			
			throw new RuntimeException(Translate.MESSAGE.FILE_NOT_FOUND());
			
		} else {
			
			initOK = true;
		}
			
		
		return initOK;
		
	}
	
	/**
	 * Finaliza o sistema de rotas
	 */
	static public void deInit(){
		
		//TODO
	}	
	
	static public void setFileName(String file_name){
		
		DataFile.setFileName(file_name);
		
	}
}
