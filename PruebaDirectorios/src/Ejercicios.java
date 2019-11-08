import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Ejercicios {
	
	ArrayList<Caracter> letras = new ArrayList<>();
	
	public class Caracter{
		char letra;
		int valor;
		public Caracter(char letra) {
			this.letra = letra;
			this.valor = 1;
		}
		public void aumentar() {
			this.valor++;
		}
		public char getChar() {
			return this.letra;
		}
		public int getValor() {
			return this.valor;
		}
		@Override
		public boolean equals(Object obj) {
			return this.letra == ((Caracter)obj).getChar();
		}
		
	}
	public class Persona{
		private String nombre;
		private int edad;
		public Persona(String nombre, int edad) {
			setNombre(nombre);
			setEdad(edad);
		}
		public Persona() {
			this("",0);
		}
		public String getNombre() {
			return this.nombre;
		}
		public void setNombre(String nombre) {
			this.nombre=nombre;
		}
		public int getEdad() {
			return this.edad;
		}
		public void setEdad(int edad) {
			if(edad>0) this.edad=edad;
			else this.edad=0;
		}
		public void getDatos() {
			System.out.printf("Nombre: %s\n",this.getNombre());
			System.out.printf("Edad: %S\n",this.getEdad());
		}
	}
	public class Depart{
		private String nombre;
		private int puerta;
		public Depart(String nombre, int puerta) {
			setNombre(nombre);
			setPuerta(puerta);
		}
		public Depart() {
			this("",0);
		}
		public String getNombre() {
			return this.nombre;
		}
		public void setNombre(String nombre) {
			this.nombre=nombre;
		}
		public int getPuerta() {
			return this.puerta;
		}
		public void setPuerta(int puerta) {
			if(puerta>0) this.puerta=puerta;
			else this.puerta=0;
		}
		public void getDatos() {
			System.out.printf("Nombre: %s\n",this.getNombre());
			System.out.printf("Puerta: %S\n",this.getPuerta());
		}
	}
//	EJERCICIO 11
//	******************************************************
	public void copiarConBuffer(File fichero, File copia) {
		byte[] buffer = new byte[100000];
		try(BufferedInputStream bIn= new BufferedInputStream(new FileInputStream(fichero))){
			while((bIn.available())!=-1) {
				try(BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(copia, true))){
					bout.write(buffer);
				}
			}
		}catch(FileNotFoundException ex) {
			System.err.println("File doesn't found.");
		}catch(IOException ioex) {
			System.err.println("An IOException has ocurrered.");
		}
	}
	public void copiarConFile(File fichero, File copia) {
		byte[] buffer = new byte[100000];
		try(FileInputStream fin = new FileInputStream(fichero)){
			while((fin.available())!=-1) {
				try(FileOutputStream fout = new FileOutputStream(copia, true)){
					fout.write(buffer);
				}
			}
		}catch(FileNotFoundException ex) {
			System.err.println("File doesn't found.");
		}catch(IOException ioex) {
			System.err.println("An IOException has ocurrered.");
		}
	}
//	EJERCICIO 10
//	******************************************************
	public void altaObjeto(File fichero, Object obj) {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichero))){
			out.writeObject(obj);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public void verObjetoEnFichero(File fichero, Object obj, String nombre) {
		Persona p = new Persona();
		Depart d = new Depart();
		if(obj.getClass()==p.getClass()) {
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichero))){
				while(true) {
					verObjeto(obj);
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	public void verObjeto(Object obj) {
		if(obj instanceof Persona) {
			((Persona)obj).getDatos();
		}else{
			((Depart)obj).getDatos();
		}
	
}
	public void borrarObjeto(File fichero, Object obj) {
		
	}
	
//	EJERCICIO 9
//	******************************************************
//	public static void main (String[] args) {
//		Ejercicios e = new Ejercicios();
//		Scanner sc = new Scanner(System.in);
//		String ruta1 = "c:\\Users\\Pablo\\Desktop\\ruta\\alumno.txt";
//		File f = new File(ruta1);
//		e.altaAlumno(f,"A1",02,11101997);
//	}
	public void altaAlumno(File alumno,String nombre,int codigo, int fecha) {
		try(DataOutputStream out = new DataOutputStream(new FileOutputStream(alumno,true))) {
			out.writeUTF(nombre);
			out.writeInt(codigo);
			out.writeInt(fecha);
		}catch(FileNotFoundException er) {
			er.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}		
	}
	public void modificarAlumno(File fichero, String nombre, int codigo, int fecha) {
		String cadena;
		int cod, fech;
		try(DataInputStream in = new DataInputStream(new FileInputStream(fichero))){
			while(true) {
				cadena=in.readUTF();
				cod=in.readInt();
				fech=in.readInt();
				if(codigo!=cod) {
					altaAlumno(fichero,cadena,cod,fech);
				}else {
					altaAlumno(fichero,nombre,codigo,fecha);
				}
			}
		}catch(EOFException ef) {
			ef.printStackTrace();	
		}catch(FileNotFoundException er) {
			er.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public void borrarAlumno(File fichero, int codigo) {
		String cadena;
		int cod, fecha;
		try(DataInputStream in = new DataInputStream(new FileInputStream(fichero))){
			while(true) {
				cadena=in.readUTF();
				cod=in.readInt();
				fecha=in.readInt();
				if(codigo!=cod) {
					altaAlumno(fichero,cadena,cod,fecha);
				}
			}
		}catch(EOFException ef) {
			ef.printStackTrace();
			
		}catch(FileNotFoundException er) {
			er.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public void consultarAlumno(File fichero, int codigo) {
		String cadena;
		int cod, fech;
		try(DataInputStream in = new DataInputStream(new FileInputStream(fichero))){
			while(true) {
				cadena=in.readUTF();
				cod=in.readInt();
				fech=in.readInt();
				if(codigo==cod) {
					System.out.printf("Nombre: %s\nCodigo: %s\nFecha de nacimiento: %s\n",cadena,cod,fech);
					return;
				}
			}
		}catch(EOFException ef) {
			ef.printStackTrace();	
		}catch(FileNotFoundException er) {
			er.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
//	
//	EJERCICIO 8
//	******************************************************
//	public static void main (String[] args) {
//		Ejercicios e = new Ejercicios();
//		String ruta1 = "c:\\Users\\Pablo\\Desktop\\ruta\\B.txt";
//		File f = new File(ruta1);
//		byte[] buffer = new byte[5];
//		e.clonarArchivoBinario(f, f.getAbsolutePath().replace(f.getName(), f.getName().replace(".txt", "_copia.txt")),buffer);
//	
//		int[] sizes= {10,20,30,40,1000};
//		for (int i:sizes) {
//			e.clonarArchivoBinario(f, f.getAbsolutePath().replace(f.getName(), f.getName().replace(".txt", "_copia.txt")),new byte[i]);	
//		}
//}
	public void clonarArchivoBinario(File f,String destino) {
		int i;
		try(FileInputStream fileIn = new FileInputStream(f)){
			try(FileOutputStream fileOut = new FileOutputStream(destino)){
				while((i=fileIn.read())!=-1) {
					fileOut.write(i);
				}
			}
		}catch (IOException ex) {
			System.err.println("No se ha podido encontrar el archivo deseado.");
		}
	}
	public void clonarArchivoBinario(File f,String destino,byte[] buffer) {
		int i;
		try(FileInputStream fileIn = new FileInputStream(f)){
			try(FileOutputStream fileOut = new FileOutputStream(destino)){
				while((i=fileIn.read(buffer))!=-1) {
					fileOut.write(buffer,0,i);
				}
			}
		}catch (IOException ex) {
			System.err.println("No se ha podido encontrar el archivo deseado.");
		}
	}
//	
//	EJERCICIO 7
//	******************************************************
//	public static void main (String[] args) throws IOException {
//		Ejercicios e = new Ejercicios();
//		String ruta1 = "c:\\Users\\Pablo\\Desktop\\ruta\\Ejercicio.txt";
//		File f = new File(ruta1);
//		System.out.printf("El archivo tiene %s lineas%n",e.cuentaPalabras(f,true));
//		System.out.printf("El archivo tiene %s palabras",e.cuentaPalabras(f,false));
//		e.archivoOrdLin(f,true,true);
//		e.archivoOrdLin(f,false,true);
//		e.archivoOrdLin(f,false,false);
//		e.archivoOrdLin(f,true,false);
//	}
//	//Crea un fichero ordenado por orden ascendente o descendente de lineas segun el booleano
//	//y a partir del fichero pasado como parámetro
//	
	public void archivoOrdLin(File f, boolean des, boolean sensible) {
		//Collections.sort(array,String.CASE_INSENSITIVE_ORDER);
		String fileName;
		if(des && sensible) fileName = "LineasDescendentesSensible.txt";
		else if(des && !sensible)fileName = "LineasDescendentes.txt";
		else if(!des && sensible)fileName = "LineasAscendentesSensible.txt";
		else fileName = "LineasAscendentes.txt";
		String nuevoArchivo = f.getAbsolutePath().replace(".txt", fileName);
		File newF = new File(nuevoArchivo);
		ArrayList<String> aLineas = new ArrayList<String>();
		try(Scanner sc = new Scanner(f)){
			while(sc.hasNext()) {
				aLineas.add(sc.nextLine());
			}
			if(sensible) Collections.sort(aLineas,String.CASE_INSENSITIVE_ORDER);	
			else Collections.sort(aLineas);
			if(des) Collections.reverse(aLineas);
			try(PrintWriter fichOut = new PrintWriter(newF)){
				for(int i=0; i<aLineas.size(); i++) {
//					if(des) 
						fichOut.println(aLineas.get(i));
//					else fichOut.printf(aLineas.get(aLineas.size()-i-1)+"%n");
				}	
			}
		}catch(FileNotFoundException ex) {
			System.err.println("Archivo deseado no encontrado.");
		}
	}
	//Cuenta las palabras (false) o lineas (true) de un archivo pasado como parámetro
	private int cuentaElementos(File f, boolean lineas) {
		int contador = 0;
		try(Scanner sc = new Scanner(f)){
			while(sc.hasNext()) {
				contador++;
				if(lineas)	sc.nextLine();
				else sc.next();
			}
		}catch(FileNotFoundException err) {
			System.err.println("No se ha encontrado el archivo deseado.");
		}
		return contador;
	}
	
	public int cuentaPalabras(File f) {
		return cuentaElementos(f, false);
	}
	
	public int cuentaLineas(File f) {
		return cuentaElementos(f, true);
	}

	
//	EJERCICIO 6
//	******************************************************
//	public static void main (String[] args) throws IOException {
//		Ejercicios e = new Ejercicios();
//		String rutaArchivo = "C:\\Users\\Pablo\\Desktop\\ruta\\Ejercicio.txt";
//		String rutaArchivo2 = "C:\\Users\\Pablo\\Desktop\\ruta\\A.txt";
//		String rutaArchivo3 = "C:\\Users\\Pablo\\Desktop\\ruta\\B.txt";
//		File f = new File(rutaArchivo), f2 = new File(rutaArchivo2), f3 = new File(rutaArchivo3);
//		e.unificarFicheros(f,f2,f3);
//		
//	}
	public void separarPorLineas(File f, int lineas) {
		int contador=0, contadorLineas = 0;
		Scanner sc;
		try {
			sc = new Scanner(f);
			while(sc.hasNext()) {
				if(contadorLineas<lineas) {
					try(PrintWriter fichOut = new PrintWriter((new FileWriter(f.getAbsolutePath().replace(".txt","")+contador+".txt",true)))){						
						fichOut.write(sc.nextLine()+"\n");
						contadorLineas++;
					}catch(IOException er) {
						
					}
				}else {
					contadorLineas = 0;
					contador++;
				}
			}
		}catch(FileNotFoundException e) {
			
		}finally {
				
		}
	}
	public void separarPorCaracter(File f, int caracteres){
		char[] buffer = new char[caracteres];
		int i, contador = 0;
		try (FileReader fichIn = new FileReader(f)){
			while((i=fichIn.read(buffer))!=-1) {
				try(FileWriter fichOut = new FileWriter(f.getAbsolutePath().replace(".txt","")+contador+".txt")){
					fichOut.write(buffer,0,i);
					contador++;
				}catch(IOException e) {
					
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	public void unificarFicheros(File... ficheros) {
		String rutaUnificado = "C:\\Users\\Pablo\\Desktop\\ruta\\unificado.txt";
		try(PrintWriter fichOut = new PrintWriter(rutaUnificado)){
			for (File f:ficheros) {
				try(Scanner sc = new Scanner(f)){
					while(sc.hasNext()) {
						fichOut.println(sc.nextLine());
					}
				}
			}
		}catch(FileNotFoundException er) {
			System.err.println("Archivo destino no encontrado.");
		}
	}
//	
	
//	EJERCICIO 5
//	******************************************************
//	public static void main (String[] args) throws IOException {
//		Ejercicios ejercicio = new Ejercicios();
//		Ejercicios e = new Ejercicios();
//		String ruta = "C:\\Users\\Pablo\\Desktop\\ruta\\Ejercicio.txt";
//		File f = new File(ruta);
//		try {
//			ejercicio.buscaLineas("ejercicio",f);
//		}catch(FileNotFoundException err) {
//			System.err.println("Podés dejar de cagarla, boludo?");
//		}
//	}
	public void buscaLineas(String cad, File f) throws FileNotFoundException {
//		Scanner sc = null;
		int cont = 1;
		String frase = "";
		try (Scanner sc=new Scanner(f)){
//			sc = new Scanner(f);
			System.out.printf("Frases con \"%s\"\n",cad);
			System.out.println("*********************");
			while(sc.hasNext()) {
				frase = sc.nextLine();
				if(frase.contains(cad)) {
					System.out.printf("%s | %s",cont,frase);
					System.out.println();
				}
				cont++;
			}
		} 
//		finally {
//			if(sc != null) {
//				sc.close();
//			}
//		}
	}
//	EJERCICIO 4
//	******************************************************
//	public static void main (String[] args) throws IOException {
//		Ejercicios e = new Ejercicios();
//		String ruta = "C:\\Users\\Pablo\\Desktop\\ruta\\B.txt";
//		File f = new File(ruta);
//		e.contar(f);
//	}
	public void contar(File f) throws IOException {
		int cont=0;
		
		FileReader fichIn=null;
		try {
			fichIn = new FileReader(f);
			int i;
			while((i = fichIn.read()) != -1) {
				
				if(letras.contains(new Caracter((char)i))) {
					letras.get(letras.indexOf(new Caracter((char)i))).aumentar();
				}else {
					Caracter caracter = new Caracter((char)i);
					letras.add(caracter);
				}
			}
		} finally {
			int max = 0;
			char maxChar = 'g';
			for(Caracter letra:letras) {
				if(letra.getValor()>=max) {
					max = letra.getValor();
					maxChar = letra.getChar();
				}
			}
			System.out.println("El caracter más aparecido es "+maxChar);
			if(fichIn != null) {
				fichIn.close();
			}
		}
	}
//	EJERCICIO 3
//	******************************************************
	public int contarOcurrencias(File f,char cad) throws IOException{
		int cont=0;
		
		FileReader fichIn=null;
		try {
			fichIn = new FileReader(f);
			int i;
			while((i = fichIn.read()) != -1) {
				if(((char)i)==cad) {
					cont++;
				}
			}
		} finally {
			if(fichIn != null) {
				fichIn.close();
			}
		}
		return cont;
	}
//	EJERCICIO 2
// **************************************************
//	public static void main (String[] args) {
//		Ejercicios e2 = new Ejercicios();
//		String ruta = "C:\\Users\\Pablo\\Desktop\\ruta";
//		File directorio = new File(ruta);
//		e2.getListDir(directorio);
//	}

	public void getListDir(File f) {
		File[] contenido = f.listFiles();
		
//		for(File file:contenido) {
//			
//		}
		for(File file:contenido) {
			System.out.println(file.getAbsolutePath());
			if(file.isDirectory()) {
				getListDir(file);
			}
		}
	}
	
//	EJERCICIO 1
//************************************************
//	public static void main(String[] args) {
//		Ejercicios p1 = new Ejercicios();
//		File f1 = new File("C:\\Users\\Pablo\\Desktop","ruta");
//
//		System.out.println("Directorios");
//		System.out.println("------------");
////		p1.getList(f1,true);
//		System.out.println();
//		System.out.println("Archivos");
//		System.out.println("------------");
//		p1.getList(f1,true);
//		System.out.println("------------");
//		
////		p1.getList2(f1,true);
//	}
//	
	public void getList(File ruta, boolean dir) {
		
		File[] files = ruta.listFiles();
		
		if(dir) {
			for(File f:files) {
				if(f.isDirectory()) System.out.println(f.getAbsolutePath());
			}
		}else {
			for(File f:files) {
				if(f.isFile()) System.out.println(f.getAbsolutePath());
			}
		}
	}
//	
//	public void getList2(File ruta, boolean dir) {
//		File[] files = ruta.listFiles();
//	
//		for(File f:files) {
//			if(!f.isDirectory() || dir ) System.out.println(f.getAbsolutePath());
//		}
//	
//	}
//	
	
}
