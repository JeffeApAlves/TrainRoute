package domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import translate.Translate;


/**
 * Abstracao de rotas(viagens) 
 *
 * @file Route.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/


public class Route {
	
	private static final int MINIMAL_SIZE_OF_ID	= 2;
	private static final String PATTERN_ID		="[^a-zA-Z]";
	private static final String PATTERN_DOUBLE	="[^0-9.,;]";
	private static final String PATTERN_NO_DUPLICATE="(\\D)\\1{1}";
	
 
	/**
	 * Identificador usado nos maps 
	 */
	private String		key;
	/**
	 * Distancia total da rota ou viagem
	 */
	private	double 		totalDistance;	
	
	
	/**
	 * Tempo de percurso
	 * 
	 */
	
	private double totalTimeDistance;
	
	/**
	 * Indica se a rota existe 
	 */
	private	boolean		available;
	
	/**
	 * Construtor
	 * 
	 */
	public Route (){
		
		setKey("");
		available		= true;		
		totalDistance	= 0.0;
	}
	
	/**
	 * Construtor
	 * 
	 * @param id key da rota
	 * @param total_distance distancia da rota
	 */
	public Route (String id,double total_distance){
		
		setKey(id);
		setTotalDistance(total_distance);
		available = true;
	}
	
	/**
	 * Construtor
	 * 
	 * @param route Key+distancia em formato de string Ex: AB50
	 */
	public Route (String route){
		
		setRoute(route);
		available = true;
	}

	/**
	 * Retona cidade de origem de uma rota(viagem)
	 * 
	 * @return Retorna a cidade de origem de uma rota(viagen)
	 */
	public String getStartingTown(){
		
		return key.substring(0,1);
	}
	
	/**
	 * 
	 * Retorna a distancia total de uma rota(viagem)
	 * 
	 * @return Retorna a distancia total de uma rota(viagem)
	 */
	public double getDistance(){
		
		return totalDistance;
	}
	/**
	 * Retorna o numero de node des uma rota(viagem)
	 *  
	 * @return Numero de nodes
	 */
	public int getNumberOfNodes(){
		
		return key.length();
	}
	
	/**
	 * Retorna o numero de paradas que serao feitas em uma rota(viagem) a partida nao e considerada 
	 * como uma parada
	 * 
	 * @return Numero de paradas
	 */
	public int getNumberOfStops(){
		
		return key.length()-1;
	}
	
	/**
	 * Retorna o key do trecho entre uma parada ate a proxima estacao
	 *  
	 * @param n_stop numero da parada que se deseja o retorno do trecho
	 * @return
	 */
	public String getKeyOfStop(int n_stop)
	{	
		String result="";
		
		if(n_stop<=key.length()-2)
			result = getKey().substring(n_stop, n_stop+2);
		
		return result;
	}
	
	/**
	 * Configura o key e a distancia da rota atraves de uma string
	 * 
	 * @param route Key+distancia em formato de string Ex: AB50
	 */
	public void setRoute(String route){
		
		setKey(route);
		
		setTotalDistance(route);
		
		setTotalTimeDistance(route);
	}
	
	private void setTotalTimeDistance(String val) {
		
		
		if(val!=null && val.length()>0){
			
			val	= val.replaceAll(PATTERN_DOUBLE, "").trim();
					
			if(val.indexOf(";")>=0){
				
				val	= val.split(";")[1];
				
				val	= val.replaceAll(",", ".");
				
				if(val.length()>0){
					setTotalTimeDistance(Double.parseDouble(val));
				}
			}
		}
	}

	public void setKey(String r){
		
		key	= formatKey(r);
	}
	
	/**
	 * Set a distancia de uma rota(viagem)
	 * 
	 * @param val valor da distancia(no formato de texto) que sera usado para definir a distancia total da rota.
	 */
	public void setTotalDistance(String val){
		
		if(val!=null && val.length()>0){
			
			val	= val.replaceAll(PATTERN_DOUBLE, "").trim();
			
			if(val.indexOf(";")>=0){
				
				val	= val.split(";")[0];
			
				val	= val.replaceAll(",", ".");
				
				if(val.length()>0){
					setTotalDistance(Double.parseDouble(val));
				}
			}
		}
	}
	
	/**
	 * Set a distancia de uma rota(viagem)
	 * 
	 * @param val valor da distancia que sera usado para definir a distancia total da rota
	 */
	public void setTotalDistance(double val){

		if(val>0){
			
			totalDistance = val;
		}
	}	
	
	/**
	 * Retorna a key da rota
	 * 
	 * @return O key da rota
	 */
	public String getKey(){
		
		return key;
	}

	/**
	 * Retona a cidade de destino de uma rota
	 * 
	 * @return Cidade de destino
	 */
	public String getEndingTown(){
		
		return key.substring(key.length()-1);
	}

	/**
	 * Retorna a disponibilidade de uma rota
	 * 
	 * @return Retorna se a rota e valida/disponivel
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * Set a disponibilidade da rota
	 * @param exist
	 */
	public void setAvailable(boolean exist) {
		this.available = exist;
	}
	
	/**
	 * Formata uma string para o padrao do key de rota
	 * 
	 * @param key_route key a ser formatado
	 * @return string formatada
	 */
	public static String formatKey(String key_route){
		
		String id_format = null;

		if(key_route!=null){

			key_route 	= key_route.replaceAll(PATTERN_ID, "").trim().toUpperCase();
			
			if(key_route.length()>=MINIMAL_SIZE_OF_ID){
				
				/*regex para identificar caracters repetidos em sequencia*/

				Pattern p = Pattern.compile(PATTERN_NO_DUPLICATE);
				Matcher m = p.matcher( key_route );

				while(key_route.length()>MINIMAL_SIZE_OF_ID && m.find()){
					
					key_route = m.replaceAll("$1");
					m = p.matcher( key_route );
				}

				//confere novamente apos a formatacao o tamanho minimo
				if(key_route.length()>=MINIMAL_SIZE_OF_ID){				
					id_format = key_route;
				}
			}
		}
		
		return id_format;
	}
	
	/**
	 * Executa o teste da condicao 
	 * 
	 * @param condition Condicao literal(texto) a ser considerada 
	 * @param operand o operando literal (texto) que se deseja verificar 
	 * @param valor2 valor usado como referencia na verificacao
	 * @return Retorna o resultado do teste
	 */
	public boolean testCondition(String condition,String operand,double valor2){
		
		boolean	result = false;
		double	valor1;
		
		if(	!("[STOPS][DISTANCE]".contains("["+operand.toUpperCase()+"]")) ||
			!("[<][>][<=][>=][==][!=]".contains("["+condition.toUpperCase()+"]"))){
			
			throw new RuntimeException(Translate.MESSAGE.INCORRECT_PARAMETERS());

		}else{
		
			valor1 =	operand.equals("STOPS") ?	getNumberOfStops():
						operand.equals("DISTANCE")?	getDistance():0;
					
			result =	condition.equals("<") ? valor1 <  valor2:
						condition.equals(">") ? valor1 >  valor2:
						condition.equals("<=")? valor1 <= valor2:
						condition.equals(">=")? valor1 >= valor2:
						condition.equals("!=")? valor1 >= valor2:							
						condition.equals("==")? valor1 == valor2:false;
		}
		
		return result;
	}

	public double getTotalTimeDistance() {
		return totalTimeDistance;
	}

	public void setTotalTimeDistance(double totalTimeDistance) {
		this.totalTimeDistance = totalTimeDistance;
	}	
	
 }
