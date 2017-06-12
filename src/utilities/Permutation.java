package utilities;


/**
 * Lista todas as combinacoes poissiveis dos elementos de um array
 *
 * @file Permetuation.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/

public class Permutation {

	/**
	 * Cria todas as possiveis combinacoes entre os elementos de uma lista
	 * 
	 * @param elements Lista de elementos
	 * @param lengthOfList quantidade de elementos
	 * @return Lista com todas as combinacoes
	 * 
	 */
	public static String[] getAllLists(String[] elements, int lengthOfList){
		
	    String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];

	    if(lengthOfList == 1){
	    	
	    	return elements;
	    	
	    } else {
	    	
	        String[] allSublists = getAllLists(elements, lengthOfList - 1);

	        int arrayIndex = 0;

	        for(int i = 0; i < elements.length; i++){
	        	
	            for(int j = 0; j < allSublists.length; j++){
	            	
	                allLists[arrayIndex] = elements[i] + allSublists[j];
	                arrayIndex++;
	            }
	        }
	        
	        return allLists;
	    }
	}
}
