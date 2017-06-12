package translate;


/**
 * Interface para acesso aos textos traduzidos 
 * 
 * @file TranslateMessage.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/


public interface TranslateMessage {
	
	String TOW_NO_EXIST();
	String ROUTE_NO_AVAILABLE();
	String INCORRECT_PARAMETERS();	
	String FILE_NOT_FOUND();
	String PROPERTY_NOT_FOUND();
}
