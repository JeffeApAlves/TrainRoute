package translate;

/**
 * Textos em protugues
 * 
 * @file PtBR.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 * 
**/

public class PtBR implements TranslateMessage{

	static final String TOW_NO_EXIST		= "Cidade nao existente"; 
	static final String ROUTE_NO_AVAILABLE	= "Rota nao disponivel";
	static final String INCORRECT_PARAMETERS= "Parametros do filtro errado.Verificar o arquivo de entrada";
	static final String FILE_NOT_FOUND		= "Arquivo de entrada(input.txt) nao encontrado";
	static final String PROPERTY_NOT_FOUND	= "Propriedade nao encontrada no arquivo de entrada";	

	@Override
	public String TOW_NO_EXIST() {

		return TOW_NO_EXIST;
	}
	@Override
	public String ROUTE_NO_AVAILABLE() {

		return ROUTE_NO_AVAILABLE;
	}
	@Override
	public String INCORRECT_PARAMETERS() {

		return INCORRECT_PARAMETERS;
	}
	
	@Override
	public String FILE_NOT_FOUND() {

		return FILE_NOT_FOUND	;
	}
	
	@Override
	public String PROPERTY_NOT_FOUND() {

		return PROPERTY_NOT_FOUND;
	}

}
