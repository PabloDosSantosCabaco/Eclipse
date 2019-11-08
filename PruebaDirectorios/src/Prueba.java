import java.io.File;

public class Prueba {
	
	public static void main(String[] args) {
		Prueba p1 = new Prueba();
		File f1 = new File("C:\\Users\\Pablo\\Desktop","ruta");

		System.out.println("Directorios");
		System.out.println("------------");
		p1.getList(f1,true);
		System.out.println();
		System.out.println("Archivos");
		System.out.println("------------");
		p1.getList(f1,false);
	}
	
	/*public void ListarDirectorios(File f1) {
		for(int i=0; i<f1.listFiles().length; i++) {
			if(f1.listFiles()[i].isDirectory()) {
				System.out.println(f1.listFiles()[i].getName());
			}
		}
	}
	public void ListarArchivos(File f1) {
		for(int i=0; i<f1.listFiles().length; i++) {
			if(f1.listFiles()[i].isFile()) {
				System.out.println(f1.listFiles()[i].getName());
			}
		}
	}*/
	
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
	
}
