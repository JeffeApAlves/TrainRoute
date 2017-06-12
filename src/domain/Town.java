package domain;

/**
 * Abstracao de cidades
 *
 * @file Town.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/

public class Town {
	
	private static final int MINIMAL_SIZE_OF_ID = 1;
	private static final String PATTERN_ID		="[^a-zA-Z]";

	
	/**
	 * Identificador usado nos maps 
	 */
	private String key;
	

	public Town(String id){
		
		setKey(id);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String id) {
		key = formatKey(id);
	}
	
	/**
	 * Formata uma string para o padrao do key de casa
	 * 
	 * @param key_route key a ser formatado
	 * @return string formatada
	 */
	public static String formatKey(String key_route){
		
		String id_format = null;
		
		if(key_route!=null && key_route.length()>=MINIMAL_SIZE_OF_ID){
		
			key_route = key_route.replaceAll(PATTERN_ID, "").trim().toUpperCase();
			
			//confere novamente apos a formatacao o tamanho minimo
			if(key_route.length()>=MINIMAL_SIZE_OF_ID){
				
				//considera apenas 1 caracter
				id_format =key_route.substring(0,MINIMAL_SIZE_OF_ID);
			}
		}
		
		return id_format;
	}
}
