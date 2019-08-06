package dominio;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	public void prestar(String isbn) {
		Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
		if(libro != null){
			int ini = 0;
			int fin = libro.getIsbn().length()-1;
			boolean espalindromo = true;
			while((ini<fin) && espalindromo){
				if(libro.getIsbn().charAt(ini) == libro.getIsbn().charAt(fin)){
					ini++;
					fin--;
				}else{
					espalindromo = false;
				}
			}

			if(espalindromo){
				throw new UnsupportedOperationException("los libros palíndromos solo se pueden utilizar en la biblioteca");
			}else{
				int suma = 0;
				for(int i = 0; i< libro.getIsbn().length(); i++){
					if(isNumeric(String.valueOf(libro.getIsbn().charAt(i)))){
						suma = suma + Integer.valueOf(libro.getIsbn().charAt(i));
					}
				}

				Date fechaEntre = null;
				Calendar calendar = new GregorianCalendar();
				if(suma > 30){
					calendar.add(Calendar.DAY_OF_YEAR,15);
					if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
						calendar.add(Calendar.DAY_OF_YEAR,1);
					}
					fechaEntre = calendar.getTime();
				}
				Date fechasoli = new Date();
				Prestamo prestamo = new Prestamo(fechasoli,libro,fechaEntre,"");
				repositorioPrestamo.agregar(prestamo);
			}


		}else{
			//throw new 
		}
		//throw new UnsupportedOperationException("Método pendiente por implementar");

	}

	public static boolean isNumeric(String cadena) {

		boolean numero;

		try {
			Integer.parseInt(cadena);
			numero = true;
		} catch (NumberFormatException excepcion) {
			numero = false;
		}

		return numero;
	}

	public boolean esPrestado(String isbn) {
		Libro libro = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		if(libro != null){
			return true;
		}else{
			return false;
		}

	}

}
