package domain;


/**
 * Gerenciamento dos containners de cidade e rotas existentes entre as cidades
 * As rotas sao carregadas via o arquivo 'input.txt' propriedade 'graph.routes' e baseado nelas
 * sao mapeadas as cidades
 *
 * @file Graph.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import domain.Route;
import utilities.DataFile;
import utilities.FileProperty;
import utilities.Permutation;


public class Graph {

	/**
	 * Referencia do Singleton
	 */
	static Graph graph = null;
	

	/**
	 * Containner de Rotas disponiveis
	 * Formato do key String ex: 'AB'   
	 */
	private Map<String , Route>	mapRoutes = null;
	
	/**
	 * Container de cidades 
	 * Formato do key String ex: 'A'
	 */
	private	Map<String , Town>	mapTowns = null;
	
	/**
	 * Construtor privado. 
	 * Para instanciar o objeto sera utilizado o metodo create()
	 * 
	 */
	private Graph(){

		mapRoutes	= new HashMap<String, Route>();
		mapTowns	= new HashMap<String, Town>();
	}
	/**
	 * Metodo construtor para manter um Singleton de Graph.
	 * 
	 * Padrao Singleton
	 * @return
	 */
	public static synchronized Graph create(){
		
		if(graph==null){
			
			graph = new Graph();
		}
		
		graph.loadGraphRoutesFromFile();
		
		return graph;
	}
	
	/**
	 *  Exclui todos as rotas e casas dos containners
	 */
	public void clear(){
		
		if(mapRoutes!=null)
			mapRoutes.clear();
		
		if(mapTowns!=null)
			mapTowns.clear();
	}

	/**
	 * Retorna a lista dos keys de todas as cidades disponiveis
	 *  
	 * @return Lista de keys das cidades
	 */
	public String[] getAllTowns(){
		
		return mapTowns.keySet().toArray(new String[mapTowns.keySet().size()]);
	}
	
	/**
	 * Retorna a lista dos keys de todas as rotas disponiveis
	 * 
	 * @return Lsita de key das rotas
	 */
	public String[] getAllRoutes(){
	
		return mapRoutes.keySet().toArray(new String[mapRoutes.keySet().size()]);
	}	
	
	/**
	 * Retorna todas as possiveis rotas para uma viagem
	 * 
	 * @param trip E a rota da viagem contendo a cidade de destino e origem da viagem
	 * @return Retorna todas as possiveis rotas para a viagem 
	 * 
	 */
	public String[] getAllRoutePossible(Route trip){
		
		Vector<String> result	= new Vector<String>();
		String[] towns			= getAllTowns();
		
		//Comecar a permutacao pelo nivel 2 elimina rotas com comprimentos = 1. Ex: "A","B"
		
		for(int i=2; i<=towns.length; i++){
			
			// Gera todas as combinacoes possiveis por nivel 
			String[] list = Permutation.getAllLists(towns, i);
			
			for(int j=0; j<list.length; j++){
				
				if(isRouteAvailable(list[j])){
				
					String s_town	= list[j].substring(0,1);
					String e_town	= list[j].substring(list[j].length()-1);
					String route	= Route.formatKey(list[j]);
					
					/* 
					 * Filtra as possibilidades por:
					 * 1) Condicao: apos formatacao se o Key_note ficou valido sera diferent de null
					 * 2) Excecao de rotas de comprimento=2(Ex:"AA"  para onde a cidade de destino e 
					 * origem sao as mesma
					 * 3e4) Cidade de origem e destino sao as desejadas  
					 **/
					
					if(		route !=null 									&&
							!(route.length()==2 && e_town.equals(s_town))	&&
							trip.getStartingTown().equals(s_town)			&&
							trip.getEndingTown().equals(e_town)){
						
							result.add(route);
					}
				}
			}
		}
		
		return result.toArray(new String[result.size()]);	
	}
	

	/**
	 * Retorna as conexoes disponiveis para uma determinada cidade
	 * 
	 * @param key_town Key da cidade que se deseja listar as conexoes existentes
	 * @return Lista das conexoes existentes para a cidade.
	 * Caso rotas ou a cidade nao exista retornara null 
	 */
	public String[] getConnections(String key_town){
		
		Vector<String> result = null;
		
		if(key_town!=null){
			
			String[] routes	= getAllRoutes();
			
			// Apenas o primeiro caracter e considerado
			key_town = key_town.toUpperCase().trim().substring(0, 1);	
			
			if(containsTown(key_town)){
				
				result	= new Vector<>();
				
				for(String key_route:routes){
					
					if(key_town.equals(key_route.substring(0, 1))){
						
						result.add(key_route);
					}
				}
			}
		}
		
		return result!=null? result.toArray(new String[result.size()]):null;
	}
	
	/**
	 * Cria e armazena no containner uma rota com o key indicado por key_route
	 *  
	 * @param key Chave utilizada no map
	 */
	public void addRoute(String key_route){
			
		if(Route.formatKey(key_route)!=null){
			
			Route _r = new Route(key_route);
			
			mapRoutes.put(_r.getKey(), _r);
				
			addTown(_r.getStartingTown());
			addTown(_r.getEndingTown());
		}
	}
	
	/**
	 * Cria e armazena no containner uma lista de rotas
	 * 
	 * @param routes Lista dos keys para criacao das rotas
	 * 
	 */
	public void addRoute(String[]routes){
		
		for(String s: routes){
			
			addRoute(s);
		}
	}

	
	/**
	 * Cria e armazena no containner uma cidade identificada por key_town
	 *  
	 * @param key Chave utilizada no map
	 */
	public void addTown(String key_town){
		
		key_town = Town.formatKey(key_town);
		
		if(key_town!=null){
			
			Town _t = new Town(key_town);
			
			mapTowns.put(_t.getKey(), _t);
		}
	}

	/**
	 * Retorna rota indicado por key_route
	 * 
	 * @param key_route Key da rota que se deseja retornar
	 * @return Retorna a rota correspondente ao key_route. Se a rota nao existir retorna null.
	 */
	public Route getRoute(String key_route){
		
		key_route= Route.formatKey(key_route);
	
		return mapRoutes.get(key_route);
	}

	/**
	 * Carrega as rotas do mapa contidas no arquivo de entrada "input.txt na propriedade "graph.routes"
	 * 
	 * @see input.txt
	 *  
	 */
	public void loadGraphRoutesFromFile(){
		
		clear();
		
		addRoute(DataFile.readList(FileProperty.GRAPH_ROUTES));
	}
	
	/**
	 * Verifica se a cidade existe e se a referencia do objeto no containner e valida
	 *  
	 * @param key_town ke da chave para ser verficada
	 * @return Retorna true se a cidade existe e com referencia valida
	 */
	public boolean containsTown(String key_town){
		
		return mapTowns.containsKey(key_town) && mapTowns.get(key_town)!=null;
	}
	
	/**
	 * Verifica se a rota e valida e disponivel 
	 * 
	 * @param route Key da rota a ser verificado
	 * @return Retorna true se a rota for possivel
	 */
	public boolean isRouteAvailable(String route){

		boolean result = true;   
		
		if(route!=null){
			for(int i=0;i < route.length()-1; i++){
				
				String id	= route.substring(i, i+2);
				Route r		= getRoute(id);
				
				if(r==null || !containsTown(r.getStartingTown()) || !containsTown(r.getEndingTown())){
	
					result = false;
					break;
				}
			}
		}else{
			
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Calcula a distancia total de uma rota(viagem)
	 * 
	 * @param route Key da rota(viagem) que se deseja calcular a distancia
	 * @return Valor da distancia. Retornara 0 se a rota nao for possivel
	 */
	public double calculateDistance(String trip){
		
		double result = 0.0;
		
		trip = Route.formatKey(trip);
		
		if(isRouteAvailable(trip)){
			
			Route route = new Route(trip);
			
			for(int i=0;i < route.getNumberOfNodes()-1; i++){
				
				String id	= route.getKeyOfStop(i);
				
				Route r		= getRoute(id);
				
				if(r!=null){

					result += r.getDistance();
					
				}else{
					
					result = 0.0;

					break;
				}
			}
		}
		
		return result;
	}	
	
	
	public double calculateTime(String trip){
		
		double result = 0.0;
		
		trip = Route.formatKey(trip);
		
		if(isRouteAvailable(trip)){
			
			Route route = new Route(trip);
			
			for(int i=0;i < route.getNumberOfNodes()-1; i++){
				
				String id	= route.getKeyOfStop(i);
				
				Route r		= getRoute(id);
				
				if(r!=null){

					result += r.getTotalTimeDistance();
					
				}else{
					
					result = 0.0;

					break;
				}
			}
		}
		
		return result;
	}	
}
