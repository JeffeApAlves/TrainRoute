# Sistema gestao de rotas para mobilidade sobre trilhos

## Descricao da arquitetura (3 camadas)
A implementacao do software esta baseada na arquitetura MVC (dominio, controller e View(Front End - TUI)
Toda a logica de negocio se encontra na camada dominio.

+ O View recebe uma solicitacao do usuario, neste caso nenhuma entrada e feita pelo o usuario. 
+ A camada controller recebe as solicitacoes do View e as delega para o domain. 
+ Apos o domain processar e concluir as requisicoes, os resultados sao devolvidos ao controller.
+ O controller disponibiliza o resultado para o View.
+ View mostra o resultado.  


### Diagrama

Esse diagrama representa uma abstracao high level da implementacao, nao faz parte do portifolio de diagramas  do padrao UML.

![](attached/Arquitetura.png)

---

## Arquivo de entrada (input.txt)

As informacoes no arquivo de entrada estao estruturadas na forma de propriedades


**1. Mapeamentos das estacoes**

	graph.routes = AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
	
![](attached/map.png)

**Caracterisitcas:**
* Numeros decimais usar '.'
* Separador por virgula
* Hifem de separacao e opcional
* O SW esta trabalhando dinamicamente, podendo ser adicionadas novas rotas

>Diagrama esta baseado na descricao do teste. 

----

**2. Mapeamento das rotas para calculo da distancia**

	distance.routes	= A-B-C , A-D , A-D-C , A-E-B-C-D , A-E-D

**Caracteristicas:**
* Hifem de separacao e opcional
* Separador virgula
* Possibilidade de adicionar novas rotas.
* Rotas nao existentes serao avisadas
	
>Questoes abordadas
>* 1-The distance of the route A-B-C
>* 2-The distance of the route A-D.
>* 3-The distance of the route A-D-C.
>* 4-The distance of the route A-E-B-C-D.
>* 5-The distance of the route A-E-D.

----

**3. Condicao de STOPS**
	
	filtertrips[1].condition = STOPS <= 3
	filtertrips[1].routes	= C-D-C , C-E-B-C

**Caracterisiticas:**

* Verifica se as rotas indicadas na propriedade filtertrips[X].routes satifazem a condicao indicada em filtertrips[X].condition.
* Condicoes permitidas: <,>,<=,>=,==,!=
* Operandos permitidos STOPS,DISTANCE
* O hifem e opcional
* Rotas invalidas serao desconsideradas
* Separador por virgula
* Possibilidade de adiconar novos filtros, diferenciar atraves do index [X]
 
>Questoes abordadas
>* 6) The number of trips starting at C and ending at C with a maximum of 3 stops. In the sample data below, there are two such trips: 
C-D-C (2 stops). and C-E-B-C (3 stops).

---

**4. Condicao de STOPS**
	
	filtertrips[2].condition = STOPS==4
	filtertrips[2].routes	= A-B-C-D-C , A-D-C-D-C, A-D-E-B-C, AB

**Caracteristicas:**
* Verifica se as rotas indicadas na propriedade filtertrips[X].routes satifazem a condicao
* indicada em filtertrips[X].condition.
* Condicoes permitidas: <,>,<=,>=,==,!=
* Operandos permitidos STOPS,DISTANCE
* O hifem e opcional
* Rotas invalidas serao desconsideradas sem nenhuma  notificacao ao usuario
* Separador por virgula
* Possibilidade de adiconar novos filtros, diferenciar atraves do index [X]

>Questoes abordadas

>* 7-The number of trips starting at A and ending at C with exactly 4 stops. In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B). 

---

**5. Procurar menor rota possivel**

	shortestRouter.trip	= A-C , B-B

**Caracteristicas:**
* Encontra as opcoes de rotas menores(distancia) entre o ponto de partida A e o de chegada B
* Possibilidade de adiconar quantas viagens(rotas) necessarias.
* Rotas invalidas serao desconsideradas sem nenhuma  notificacao ao usuario  
* O hifem e opcional
* Separador por virgula

>Questoes abordadas
>* 8-The length of the shortest route (in terms of distance to travel) from A to C.
>* 9-The length of the shortest route (in terms of distance to travel) from B to B.

----
 
**6. Condicao de Distancia**

	filtertrips[3].condition	= DISTANCE < 30
	filtertrips[3].routes		= CDC , CEBC , CEBCDC , CDCEBC , CDEBC , CEBCEBC , CEBCEBCEBC

**Caracteristicas:**
* Verifica se as rotas indicadas na propriedade filtertrips[X].routes satifazem a condicao indicada em filtertrips[X].condition.
* Condicoes permitidas: <,>,<=,>=,==,!=
* Operandos permitidos STOPS,DISTANCE
* O hifem e opcional
* Rotas invalidas serao desconsideradas sem nenhuma  notificacao ao usuario
* Separador por virgula
* Possibilidade de adiconar novos filtros, diferenciar atraves do index [X]

>Questoes abordadas
>* 10-The number of different routes from C to C with a distance of less than 30. In the sample data, the trips are: CDC, CEBC, 
CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.

[Veja o arquivo de entrada](../input.txt)

---

## Leitura do arquivo de entrada

A classe responsavel pela leitura do arquivo de entrada e a DataFile

Todas as entradas(propriedades) do arquivo estao mapeadas no **enun** chamado FileProperty

	public enum FileProperty {
	
		/*Nome da proriedade onde estara mapeados as rotas disponiveis*/
		GRAPH_ROUTES("graph.routes"),
		/*Nome da proriedade onde estara mapeados as rotas para calculo da distancia */
		DISTANCE_ROUTES("distance.routes"),
		/*Nome da proriedade onde estara a condicao para filtro de rotas*/
		FILTER_CONDITION("filtertrips[%d].condition"),
		/*Nome da proriedade onde estara mapeados as rotas para aplicacao do filtro*/
		FILTER_ROUTES("filtertrips[%d].routes"),
		/*Nome da proriedade onde estara mapeados as rotas para calculo da memor distancia*/
		SHORTEST_ROUTES("shortestRouter.trip");
		
		private String name;
		
		FileProperty(String name){this.name = name;	}
		public String getName(){	return name;	}
	}

Para leitura e filtro das informacoes foram utilizada expressoes regulares (regex)

	if(open() && property!=null){
		name_property = index>=0? String.format(property.getName(), index) : property.getName();
		String s = prop.getProperty(name_property);
		if(s!=null){
			values = s.trim().toUpperCa.replaceAll("[^a-zA-Z0-9.,]", "").split(SEPARATOR);
		}
	}
	
Versao (local): [[click para mais detahes]](html/classutilities_1_1_data_file.html)

Versao (online): [[click para mais detahes]](https://rails-mobility-system.000webhostapp.com/enumutilities_1_1_file_property.html)

---

## Gerenciamento das rotas

As informcaoes, sobre as rotas disponiveis, lidas do arquivo de entrada ficam na entidade **graph**
Essas mesmas informacoes deverao ser compartilhadas com todas as outras entidades e para que nao tenhamos diveras instancias de fontes de informacoes com o mesmo conteudo adotamos um padrao Singleton. A criacao do objeto do tipo Graph e feita pela chamada do metodo **Graph.create()** e o contrutor esta com sua visibilidade como private.

As rotas sao armazenadas em maps e utiliza como chave o formato "AB"
As cidades sao armazenadas em maps e utiliza como chave o formato "A"

![](html/classdomain_1_1_graph__coll__graph.png) 


Versao (local): [[click para mais detalhes]](html/classdomain_1_1_graph.html)

Versao (online): [[click para mais detalhes](https://rails-mobility-system.000webhostapp.com/classdomain_1_1_graph.html)

---

##Execucao das consultas

Enquanto a Graph disponibiliza de forma generica meios e mecanismos para execucao de consultas, existem as classes especialistas 
sao elas:  

**Filter:** Utilizara a propriedades *filtertrips[X].routes* e *filtertrips[X].condition* do arquivo de entrada. Como existe a possibilidade dessa tag ser utilizada n vezes no arquivo de entrada se faz necessario informar tambem o indice [X].

![](html/classdomain_1_1_filter__coll__graph.png)

Versao (local): [[click para mais detalhes]](html/classdomain_1_1_filter.html)

Versao (online): [[click para mais detalhes]](https://rails-mobility-system.000webhostapp.com/classdomain_1_1_filter.html)

**ShortestRoute:** Utilizara a propriedade *shortestRouter.trip* do arquivo de entrada.

![](html/classdomain_1_1_shortest_route__coll__graph.png)

Versao (local): [[click para mais detalhes]](html/classdomain_1_1_shortest_route.html)

Versao (online): [[click para mais detalhes]](https://rails-mobility-system.000webhostapp.com/classdomain_1_1_shortest_route.html)

**CalculateDistance** Utilizara a propriedade *distance.routes* do arquivo de entrada.

![](html/classdomain_1_1_calculate_distance__coll__graph.png)

Versao (local): [[click para mais detalhes]](html/classdomain_1_1_calculate_distance.html)

Versao (online): [[click para mais detalhes]](https://rails-mobility-system.000webhostapp.com/classdomain_1_1_calculate_distance.html)

---

## Controle

Realizada pela classe RailSystem, onde sua implementacao e bem slim devido nao ter nenhuma logica de negocio. 
Delegando todo o trabalho as classes especialistas.

**Static Public Member Functions**

	static Route[] filterByStops1()
	static Route[] filterByStops2()
	static Route[] filterByDistance()
	static Route[] calculateDistance()
	static Map< String,Route[]> shortestRoute ()
	static boolean init()
	static void deInit()

Versao (local): [[click para mais detalhes]](html/classcontroller_1_1_rail_system.html)

Versao (online): [[click para mais detalhes]](https://rails-mobility-system.000webhostapp.com/classcontroller_1_1_rail_system.html)
 
----

## Packages

**software:** Main

**controller:**	Intermedia a interacao entre o Front End(TUI) e o domini

**frontend:** Cuida da apresentacao dos resultados ao usuario

**domain:**	Cuida de todo o negocio (gerenciamento das rotas).

**utilities:** Classes de uso gerais

**translate:** Traducao

**test:** Tests cases para JUnit

Versao (local): [[click para mais detalhes]](html/namespaces.html)

Versao (online): [[click para mais detalhes]](https://rails-mobility-system.000webhostapp.com/namespaces.html)

---

## Testes

Tests cases para serem utilizado com o framework JUnit. Para cada classe da aplicacao existe uma classecom o mesmo none e um sufixo 'Test'


![](attached/testscase.png)

----

## Text User interface
Abaixo podemos visualizar a saida no console.

![ScreenShot](attached/screenshot.png)

---

## Boas praticas

**1. Coesao:** Metodos e classes com o objetivo claro,preciso e tamanho reduzio

**2. Encapsulamento:** Todos os atributos foram encapsulado e mantidos como private unica forma de acesso e atraves de metodos

**3. Visibilidade:** Mantido somente os metodos necessarios como public
 
**4. Designer Patterns**
* Interface
* Containner
* Singleton
* Factory Method

**5. Baixo Acoplamento:** Com as 3  camadas o acoplamento entre o business layer e o front end e bem baixo. Como melhoria podera ser definido uma interface de entregua dos resultados entre o controler e o domain. 

**6. Reducao de fluxos alternativos (ifs):** Facilita a implementacao de testes unitarios.

**7. Escalabilidade (Alocacao dinamica):** Recurso alocados conforme a demanda. Podemos dizer que se necessario novas rotas podem ser adicionadas. 

**8. Constantes(numeros magicos):** Foram declaradas constantes (static final) ou enumeradores para valores con uma trativa especial.

**9. Reutilizacao:** Com a implementacao de uma uma unica forma de coleta de informacoes do arquivo praticamente todos os resultados sao obtidos da mesma forma.

**10. Unico ponto de retorno dentros do metodos:** Um unico ponto de retorno dentro do metodo facilita durante o debug

**11. Modularizacao:** Os pacotes foram criados para modularizar a solucao. 

**12. Code Style:**

* Metodos inicia com verbo e caixa baixa

* atributos (substantivos) - camelCasee

* Constantes caixa alta

* Indentacao

* Padronizacao do idioma para definicao de nomes de metodos /classes/ atributos

**13. Refatoracao:** Durante o processo de implementacao alguns codigos foram melhorados para melhorar a testabilidade, manutenibilidade, flexibilidade (escalabilidade)

**14. Modelagem e documentacao:** As ferramentas de modelagem e documentacao estao sincronizadas com a solucao

---


## Engenharia de requisitos
Para prospeccao dos requistos foi utilizado como material o documento do link desafio fornecido pelo **vagas.com**

**Atributos/entidades (substantivos do texto)**

towns, route, graph, starting and ending town,railroad services,stops

**Metodos (verbos do texto)**

compute the distance, shortest route
	
[Instrucoes](attached/desafioVAGASp&d2017.pdf)

[Desafio](attached/desafioVAGAS3TrainsVagadeDesenvolvedorRuby.pdf)

----

## Ambiente de desenvolvimento
1. IDE			Eclipse
2. Linguagem	JAVA
3. Tests		JUnit
4. Doxygen		Documentacao
5. WikiText

---

## Links

Segue o link dos repositorios e da documentacao completa gerada pela ferramenta Doxygen.

Foran disponibilizados 2 links. A versao local pode apresentar problemas devido a exclusao dos arquivos .js necessaria antes do envio. 
 
[Repositorio do projeto (Google Drive)](https://drive.google.com/open?id=0B1Y36ILbPc_-MW5laDVsWmFLNGs)

[Documentacao (local)](html/index.html)

[Documentacao (online)](https://rails-mobility-system.000webhostapp.com/index.html)

[Documentacao (pdf)](latex/refman.pdf)

---
