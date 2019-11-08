import java.io.File;
import java.security.KeyStore.Entry.Attribute;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.AttributeList;

public class Ejercicios {

	public static void main(String[] args) {
		Ejercicios ej=new Ejercicios();
		String docPath = System.getenv("USERPROFILE")+"/Desktop/pliclas.xml";
		Document doc = ej.createTree(docPath);
		//ej.showMoviesWithDirectors(doc, 1);
		//System.out.println(ej.getDifferentGenres(doc));
		//Scanner sc = new Scanner(System.in);
		//sc.nextLine();
		ej.addDirector(doc, "Dune", "Perico", "El de los palotes");
		ej.showMovies(doc);
//		ej.addMovie(doc, new String[] {"año","genero","idioma"},
//				new String[] {"1987","Acción","v.o."},"Depredador", 
//				new String[] {"John"}, new String[] {"Tiernan"});
	}
	//Ejercico 12
	//*****************************************************
	public void createNewDocument(Document doc) {
		Element empleadoNode = doc.createElement("empleado");
		empleadoNode.setAttribute("id", "1");
		empleadoNode.appendChild(doc.createTextNode("\n"));
		
		String[] datosNode = new String[] {"nombre","apellidos","apodo","salario"};
		String[] datosValue = new String[] {"Juan","López Pérez","Juanín","1000"};
		
		for(int i=0; i<datosNode.length; i++) {
			Element nombreNode = doc.createElement(datosNode[i]);
			Text nombreValue = doc.createTextNode(datosValue[i]);
			nombreNode.appendChild(nombreValue);
			empleadoNode.appendChild(nombreNode);
			empleadoNode.appendChild(doc.createTextNode("\n"));
		}
	}
	//Ejercicio 11
	//*****************************************************
	public void deleteMovie(Document doc, String movie) {
		if(getMovie(doc, movie)!=null)
			doc.removeChild(getMovie(doc, movie));
	}
	//Ejercicio 10
	//*****************************************************
	public void addDirector(Document doc, String movie, String name, String surrname) {
		if(getMovie(doc, movie)==null)	return;
		Element dirNode = doc.createElement("director");
		getMovie(doc, movie).appendChild(doc.createTextNode("\n"));
		
		Element dirNameNode = doc.createElement("nombre");
		Text dirNameText = doc.createTextNode(name);
		dirNameNode.appendChild(dirNameText);
		dirNode.appendChild(dirNameNode);
		getMovie(doc, movie).appendChild(doc.createTextNode("\n"));
		
		Element dirSurnameNode = doc.createElement("apellido");
		Text dirSurnameText = doc.createTextNode(surrname);
		dirSurnameNode.appendChild(dirSurnameText);
		dirNode.appendChild(dirSurnameNode);
		
		getMovie(doc, movie).appendChild(dirNode);
		getMovie(doc, movie).appendChild(doc.createTextNode("\n"));
	}
	//Ejercicio 9
	//*****************************************************
	public void modifyDirName(Document doc, String name, String newName) {
		NodeList dirsNode = doc.getElementsByTagName("director");
		for(int i=0; i<dirsNode.getLength(); i++) {
			NodeList aux = dirsNode.item(i).getChildNodes();
			for(int j=0; j<aux.getLength(); j++) {
				if(aux.item(j).getNodeName().equals("nombre") 
						&& aux.item(j).getFirstChild().getNodeValue().equals(name)) {
					System.out.println("entro");
					aux.item(j).getFirstChild().setNodeValue(newName);
				}
			}
		}
	}
	//Ejercicio 8
	//*****************************************************
	public void addMovie(Document doc, String[] atribsCode, 
			String[] atribsValue, String titulo, String[] dirNames, String[] dirSurname){
		if(dirNames.length!=dirSurname.length || atribsCode.length!=atribsValue.length) {
			return;
		}
		Element movieNode = doc.createElement("pelicula");
		for(int i=0; i<atribsCode.length; i++) {
			movieNode.setAttribute(atribsCode[i], atribsValue[i]);
		}
		movieNode.appendChild(doc.createTextNode("\n"));
		Element titleNode = doc.createElement("titulo");
		Text titleNodeText = doc.createTextNode(titulo);
		titleNode.appendChild(titleNodeText);
		movieNode.appendChild(titleNode);
		movieNode.appendChild(doc.createTextNode("\n"));
		for(int i=0; i<dirNames.length; i++) {
			addDirector(doc, titulo, dirNames[i], dirSurname[i]);
		}
	}
	//Ejercicio 7
	//*****************************************************
	public void addAttrib(Document doc, String movie, String atribCode, String atribValue) {
		boolean exists = false;
		NamedNodeMap attribMovie = getMovie(doc,movie).getAttributes();
		for(int i=0; i<attribMovie.getLength(); i++) {
			if(attribMovie.item(i).getNodeName().equals(atribCode))	exists = true;
		}
		if(!exists) {
			((Element)getMovie(doc,movie)).setAttribute(atribCode,atribValue);
		}
	}
	public void deleteAttrib(Document doc, String movie, String atrib) {
		boolean exists = false;
		NamedNodeMap attribMovie = getMovie(doc,movie).getAttributes();
		for(int i=0; i<attribMovie.getLength(); i++) {
			if(attribMovie.item(i).getNodeName().equals(atrib))	((Element)getMovie(doc,movie)).removeAttribute(atrib);	
		}
	}
	//Ejercicio 6
	//*****************************************************
	public int getDifferentGenres(Document doc) {
		ArrayList<String> genresList = new ArrayList<String>();
		NodeList moviesNode = doc.getElementsByTagName("pelicula");
		NamedNodeMap movieAttrib;
		for(int i=0; i<moviesNode.getLength(); i++) {
			movieAttrib = moviesNode.item(i).getAttributes();
			for(int j=0; j<movieAttrib.getLength(); j++) {
				if(movieAttrib.item(j).getNodeName().equals("genero")
					&& !genresList.contains(movieAttrib.item(j).getNodeValue())) {
					genresList.add(movieAttrib.item(j).getNodeValue());
				}
			}
		}
		return genresList.size();
	}
	public void showDifferentGenres(Document doc) {
		ArrayList<String> genresList = new ArrayList<String>();
		NodeList moviesNode = doc.getElementsByTagName("pelicula");
		NamedNodeMap movieAttrib;
		for(int i=0; i<moviesNode.getLength(); i++) {
			movieAttrib = moviesNode.item(i).getAttributes();
			for(int j=0; j<movieAttrib.getLength(); j++) {
				if(movieAttrib.item(j).getNodeName().equals("genero")
					&& !genresList.contains(movieAttrib.item(j).getNodeValue())) {
					System.out.println(movieAttrib.item(j).getNodeValue());
					genresList.add(movieAttrib.item(j).getNodeValue());
				}
			}
		}
	}
	//Ejercicio 5
	//*****************************************************
	public void showMoviesWithDirectors(Document doc, int n) {
		int count = 0;
		
		NodeList moviesNode = doc.getElementsByTagName("pelicula");
		for(int i=0; i<moviesNode.getLength(); i++) {
			count = 0;
			NodeList directorNode = moviesNode.item(i).getChildNodes();
			for(int j=0; j<directorNode.getLength(); j++) {
				if(directorNode.item(j).getNodeName().equals("director")) {
					count++;
				}
			}
			if(count>=n) {
				for(int j=0; j<directorNode.getLength(); j++) {
					if(directorNode.item(j).getNodeName().equals("titulo")) {
						System.out.println("Título: "+directorNode.item(j).getFirstChild().getNodeValue());
					}
				}
			}
		}
	}
//Ejercicio 3
//*****************************************************
	public void showMovies(Document doc) {
		NodeList movieNode;
		movieNode = doc.getElementsByTagName("pelicula");
		for(int i=0; i<movieNode.getLength(); i++) {
			for(int j=0; j<movieNode.item(i).getChildNodes().getLength(); j++) {
				if(movieNode.item(i).getChildNodes().item(j).getNodeName().equals("titulo")) {
					System.out.printf("%s: %s\n",movieNode.item(i).getChildNodes().item(j).getNodeName(),
							movieNode.item(i).getChildNodes().item(j).getFirstChild().getNodeValue());
					System.out.println("-----------------");
					showMovieGenres((Element)movieNode.item(i));
					System.out.println("-----------------");
					showDirectorNames(doc, movieNode.item(i).getChildNodes().item(j).getFirstChild().getNodeValue());
					System.out.println("-----------------");
					System.out.println("-----------------");
				}
			}
		}	
	}
	public Node getMovie(Document doc, String movie) {
		Node movieNode;
		NodeList movieNodeList = doc.getElementsByTagName("titulo");
		for(int i=0; i<movieNodeList.getLength(); i++) {
			if(movieNodeList.item(i).getFirstChild().getNodeValue().equals(movie)) {
				movieNode = movieNodeList.item(i).getParentNode();
				return movieNode;
			}
		}
		return null;
	}	
	public void showMovieGenres(Element doc) {
		//Muestra los géneros de una película a partir de su nodo
		NamedNodeMap attrib = doc.getAttributes();					
		for(int j=0; j<attrib.getLength(); j++) {
			System.out.printf("%s: %s\n",attrib.item(j).getNodeName(),attrib.item(j).getNodeValue());
		}			
	}
	public void showDirectorNames(Document doc, String movie) {
		//Con el nombre de una pelicula, muestra por pantalla el nombre del director o directores
		NodeList directores = getMovie(doc,movie).getChildNodes();
		for (int j = 0; j < directores.getLength(); j++) {
			if(directores.item(j).getNodeName().equals("director")) {
				NodeList aux = directores.item(j).getChildNodes();
				String frase = "";
				for (int k = 0; k < aux.getLength(); k++) {
					if (aux.item(k).getNodeType()==Node.ELEMENT_NODE) {
					if(aux.item(k).getNodeName().equals("nombre")) {
						frase="Nombre";
					}else {
						frase="Apellido";
					}
					System.out.printf("%s: %s\n",frase,aux.item(k).getFirstChild().getNodeValue());
					}
				}
			}
		}
	}
//Ejercicio 2
//*****************************************************
	public void showTitles(Document doc) {
		//Muestra los títulos de todas las películas
		NodeList movieNode = doc.getElementsByTagName("titulo");
		for(int i=0; i<movieNode.getLength(); i++) 
			System.out.println(movieNode.item(i).getFirstChild().getNodeValue());		
	}
//Ejercicio 1
//****************************************************
	public Document createTree(String path) {
		//Genera el árbol DOM
		Document doc = null;
		try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(path);
		}catch(Exception e) {
			System.out.println("Error generado el árbol DOM: "+e.getMessage());
		}
		return doc;
	}

}
