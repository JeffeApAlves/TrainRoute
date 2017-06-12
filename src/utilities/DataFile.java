package utilities;

/**
 * Responsavel pela leitura do arquivo de entrada (input.txt).
 * As informacoes estao persistida no formato de propriedades. 
 * Para execucao dos tests case existe um arquivo exclusivo FILE_TEST_CASE.txt
 * Para execucao da aplicacao o arquivo a ser usado e o input.txt 
 * 
 * 
 * @file DatFile.java
 * @authors Jefferson Alves
 * @date 24.04.2017
 * @version 0.1
 * @brief Software de gerenciamento das rotas para uma solucao de mobilidade sobre trilhos
 *
 * @see FileProperty.java 
 *  
**/

import java.io.File;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DataFile {
	
	public static final int	DISTANCE_CONDITION		=3;
	public static final int	STOP1_CONDITION			=1;
	public static final int	STOP2_CONDITION			=2;	

	private static final String PATTERN_LIST		="[^a-zA-Z0-9.,;]";
	private static final String PATTERN_OPERAND		="[^a-zA-Z,]";
	private static final String PATTERN_DOUBLE		="[^0-9.,]";
	private static final String PATTERN_INTEGER		="[^0-9]";	
	private static final String PATTERN_CONDITION	="[^<>=!]";
	private static final String SEPARATOR			=",";	
	
	/**
	 * Nome do Arquivo de entrada 
	 */
	public static final String PATH_FILE 			= "input.txt";
	public static final String FILE_TESTS_CASES		= "FILE_TEST_CASE.txt";
	
	static private String file_name	= PATH_FILE;
	static Properties prop			= new Properties();
	static InputStream input		= null;
	
	
	/**
	 * Configura o nome do arquivo
	 * 
	 * @param file path + nome do arquivo a ser utilizado para entrada dos valores
	 */
	public static void setFileName(String file){

		close();
		file_name = file;
	}
	
	/**
	 * 
	 * @return Retorna nome do arquivo em uso
	 */
	public static String getFileName(){

		return file_name==null?PATH_FILE:file_name;
	}

	
	/**
	 * Abre o arquivo de entrada
	 * @return Se o arquivo foi aberto com sucesso retorna true
	 */
	static public boolean open(){
		
		boolean flag_ret = false;
		
		try {

			if(input==null){
				input = new FileInputStream(getFileName());

				prop.load(input);
			}
			flag_ret = true;
			
		} catch (IOException ex) {
			
			flag_ret = false;
			
			ex.printStackTrace();
		}
		
		return  flag_ret;
	}
	
	/**
	 * Fecha o arquivo de entrada
	 */
	public static void close(){

		if (input != null) {
			
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			input = null;
		}
	}	
	
	/**
	 * Le uma lista de string do arquivo de entrada
	 * @param property identificador da propriedade a ser lida
	 * @return retorna o array de string lido do arquivo
	 */
	static public String[] readList(FileProperty  property){
		
		return readList(property,-1);
	}
	
	/**
	 * Le um array de string do arquivo de entrada
	 * @param property propriedade onde o array de string sera buscado
	 * @param index_filter index da propriedade a ser buscada ex: propriedade[index].condition
	 * @return retorna o array de string lido do arquivo
	 */
	static public String[] readList(FileProperty property,int index_filter){
		
		String[]values = null;
		String	name_property = "";
		
		
		if(open() && property!=null){
			
			name_property = index_filter>=0? String.format(property.getName(), index_filter) : property.getName();
			
			String s = prop.getProperty(name_property);
	
			if(s!=null){
	
				values = s.trim()
							.toUpperCase()
							.replaceAll(PATTERN_LIST, "")
							.split(SEPARATOR);
			}else{
				
//				System.out.println(Translate.MESSAGE.PROPERTY_NOT_FOUND());
			}
		}
		
		return values;
	}
	
	/**

	 * Le uma condicao do arquivo de entrada
	 * @param property propriedade onde a condicao sera buscado
	 * @param index_filter index da propriedade a ser buscada ex: propriedade[index].condition 
	 * @return retorna a condicao literal lido do arquivo
	 */
	static public String readCondition(FileProperty property,int index_filter){
		
		String value = "";
		String	name_property = "";		

		if(open() && property!=null){
			
			name_property = index_filter>=0? String.format(property.getName(), index_filter) : property.getName();

			value = prop.getProperty(name_property);
			
			if(value!=null){
				value = value.replaceAll(PATTERN_CONDITION, "").trim().toUpperCase();
			}else{
				
//				System.out.println(Translate.MESSAGE.PROPERTY_NOT_FOUND());
			}
		}
		
		return value;
	}

	/**
	 * Le um double do arquivo de entrada
	 * @param property propriedade onde o double sera buscado
	 * @param index_filter index identifica qual propriedade dentro do array que existe no arquivo
	 * @return retorna o double lido do arquivo
	 */
	static public double readDouble(FileProperty property,int index_filter){

		double value = Double.MAX_VALUE;
		String	name_property = "";

		if(open() && property!=null){
			
			name_property = index_filter>=0? String.format(property.getName(), index_filter) : property.getName();

			String s = prop.getProperty(name_property);
				
			if(s!=null){
				value = Double.parseDouble(s.replaceAll(PATTERN_DOUBLE, "").trim());
			}else{
				
//				System.out.println(Translate.MESSAGE.PROPERTY_NOT_FOUND());
			}
		}
		
		return value;
	}
	
	/**
	 * Le um interiro do arquivo de entrada
	 * @param property propriedade onde o inteiro sera buscado
	 * @param index_filter index identifica qual propriedade dentro do array que existe no arquivo
	 * @return retorna o inteiro lido do arquivo
	 */
	static public int readInteger(FileProperty property,int index_filter){

		int value = Integer.MAX_VALUE;
		String	name_property = "";

		if(open() && property!=null){
			
			name_property = index_filter>=0? String.format(property.getName(), index_filter) : property.getName();
			
			String s = prop.getProperty(name_property);
				
			if(s!=null){
				value = Integer.parseInt(s.replaceAll(PATTERN_INTEGER, "").trim());
			}else{
				
//				System.out.println(Translate.MESSAGE.PROPERTY_NOT_FOUND());
			}
		}
		
		return value;
	}

	/**
	 * Le um operando na forma literal do arquivo de entrada
	 * 
	 * @param property  propriedade a ser lida
	 * @return retorna o operando, na forma literal, lido do arquivo
	 * @param index_filter index identifica qual propriedade dentro do array que existe no arquivo
	 * @return retorna o operando, na forma literal, lido do arquivo
	 */
	static public String readLiteralOperand(FileProperty property,int index_filter){
		
		String value = "";
		String	name_property = "";

		if(open() && property!=null){
			
			name_property = index_filter>=0? String.format(property.getName(), index_filter) : property.getName();
			value	= prop.getProperty(name_property);
			
			if(value!=null){
				
				value = value.trim()
						.toUpperCase()
						.replaceAll(PATTERN_OPERAND, "")
						.trim();
			} else{
				
//				System.out.println(Translate.MESSAGE.PROPERTY_NOT_FOUND());
			}
		}
		
		return value;
	}

	public static boolean checkFileExist() {
		
		boolean fileOK = false;
	
		File file = new File(PATH_FILE);
		
		if(file.exists()){
			
			fileOK =true;
		}
		
		return fileOK;
	}
}
