package software;

/**
 * 
 * Metodo main do software estruturado em tres partes
 *  
 * 	statupSW()		Realiza todas as inicializacoes necessarias
 * 	runSW()			Executa a aplicacao principal
 * 	shutdownSW()	Finaliza o software
 *   
 * @file main.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
 * @param none
 */
		

import controller.RailSystem;
import frontend.Display;


public class main {
	
	public static void main(String[] args) {
		
		
		RailSystem.setFileName(args[0]);
		
		System.out.println(String.join(",",args));
		
		if(startupSW()) {
			
			runSW();
		}

		shutdownSW();
	}
	
	/**
	 * Inicializacao do SW
	 * 
	 * @return Retorna true se a inicializacao foi bem sucedida
	 */
	static boolean startupSW(){
		
		boolean startOK;
		
		startOK = RailSystem.init();
		
		return startOK;
	}
	
	/**
	 * Execucao do SW
	 */
	static void runSW(){

		Display.showAllResults();
	}

	/**
	 * Executa o shutdown do SW 
	 */
	static void shutdownSW(){
		
		RailSystem.deInit();
	}
}