package modelo.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.DaoPasajeroMySql;
import modelo.persistencia.interfaces.DaoCoche;
import modelo.persistencia.interfaces.DaoPasajero;


public class Main {

	public static void main(String[] args) {
		
		try (Scanner sc = new Scanner(System.in)){
			int opcion;
			String texto = "";
			boolean continuar = true;
			
			
			do {
				escribirMenu();
				opcion = Integer.parseInt(sc.nextLine());
				Coche coche = new Coche();
				DaoCoche dc = new DaoCocheMySql();
				
				switch (opcion) {
				case 1:
					System.out.println("************ ALTA COCHE *************");					
					System.out.println("Introduce la matrícula del coche:");
					texto = sc.nextLine();
					coche.setMatricula(texto);
					
					System.out.println("Introduce la marca del coche:");
					texto = sc.nextLine();
					coche.setMarca(texto);
					
					System.out.println("Introduce el modelo del coche:");
					texto = sc.nextLine();
					coche.setModelo(texto);	
					
					System.out.println("Introduce el color del coche:");
					texto = sc.nextLine();
					coche.setColor(texto);	
					
					boolean alta = dc.alta(coche);
					if(alta){
						System.out.println("El coche se ha dado de alta");
					}else{
						System.out.println("El coche NO se ha dado de alta");
					}
					
					break;
					
				case 2:		
					System.out.println("********** BORRAR COCHE **************");
					System.out.println("Introduce el id del coche:");
					texto = sc.nextLine();
					boolean baja = dc.baja(Integer.parseInt(texto));
					
					if(baja){
						System.out.println("El coche se ha dado de baja");
					}else{
						System.out.println("El coche NO se ha dado de baja");
					}
					
					break;
					
				case 3:				
					System.out.println("********* CONSULTA COCHE *************");
					System.out.println("Introduce el id del coche:");
					texto = sc.nextLine();
					coche = dc.obtener(Integer.parseInt(texto));
					
					if(coche != null) {
						System.out.println(coche);
						
					}else {
						System.out.println("El ID del coche no se encuentra en el almacén");
					}

					break;
					
				case 4:
					System.out.println("************ MODIFICAR COCHE ***************");
					System.out.println("Introduce el id del coche:");
					texto = sc.nextLine();
					coche.setId(Integer.parseInt(texto));
					
					System.out.println("Introduce la matrícula del coche:");
					texto = sc.nextLine();
					coche.setMatricula(texto);
					
					System.out.println("Introduce la marca del coche:");
					texto = sc.nextLine();
					coche.setMarca(texto);
					
					System.out.println("Introduce el modelo del coche:");
					texto = sc.nextLine();
					coche.setModelo(texto);	
					
					System.out.println("Introduce el color del coche:");
					texto = sc.nextLine();
					coche.setColor(texto);	
					
					boolean modificado = dc.modificar(coche);
					
					if(modificado){
						System.out.println("El coche se ha modificado");
					}else{
						System.out.println("El coche NO se ha modificado");
					}
					
					break;
					
				case 5:
					System.out.println("************ LISTADO COCHES ***************");
					List<Coche> listaCoches = new ArrayList<>();
					listaCoches = dc.listar();
					
					for (Coche c : listaCoches) {
						System.out.println(c);						
					}
					
					break;
					
				case 6:
					System.out.println("************ GESTIÓN DE PASAJEROS ***************");
					escribirMenuPasajeros();
					
					Pasajero pasajero = new Pasajero();
					DaoPasajero dp = new DaoPasajeroMySql();
					int idPasajero, idCoche;
					List<Pasajero> listaPasajeros = new ArrayList<>();
					opcion = Integer.parseInt(sc.nextLine());
					
					switch (opcion) {
					case 1:
			
						System.out.println("************ ALTA PASAJERO *************");					
						System.out.println("Introduce la nombre del pasajero:");
						texto = sc.nextLine();
						pasajero.setNombre(texto);
						
						System.out.println("Introduce la edad del pasajero:");
						texto = sc.nextLine();
						pasajero.setEdad(Integer.parseInt(texto));
						
						System.out.println("Introduce el peso del pasajero:");
						texto = sc.nextLine();
						pasajero.setPeso(Double.parseDouble(texto));	
						
						boolean altaPasajero = dp.alta(pasajero);
						if(altaPasajero){
							System.out.println("El pasajero se ha dado de alta");
						}else{
							System.out.println("El pasajero NO se ha dado de alta");
						}
						
						break;
						
					case 2:			
						System.out.println("********** BORRAR PASAJERO **************");
						System.out.println("Introduce el id del pasajero:");
						texto = sc.nextLine();
						boolean bajaPasajero = dp.baja(Integer.parseInt(texto));
						
						if(bajaPasajero){
							System.out.println("El pasajero se ha dado de baja");
						}else{
							System.out.println("El pasajero NO se ha dado de baja");
						}
						
						break;
						
					case 3:
						System.out.println("********* CONSULTA PASAJERO *************");
						System.out.println("Introduce el id del pasajero:");
						texto = sc.nextLine();
						pasajero = dp.obtener(Integer.parseInt(texto));
						
						if(pasajero != null) {
							System.out.println(pasajero);
							
						}else {
							System.out.println("El ID del pasajero no se encuentra");
						}
			
						break;
						
					case 4:
						System.out.println("************ LISTADO PASAJEROS ***************");				
						listaPasajeros = dp.listar();
						
						for (Pasajero p : listaPasajeros) {
							System.out.println(p);						
						}
						
						break;
						
					case 5:
						System.out.println("************ AÑADIR PASAJERO A COCHE ***************");			
						System.out.println("Los coches disponibles son:");
						listaCoches = dc.listar();
						
						for (Coche c : listaCoches) {
							System.out.println(c);						
						}
						
						System.out.println("Introduce el id del pasajero:");
						idPasajero = Integer.parseInt(sc.nextLine());
						
						System.out.println("Introduce el id del coche:");
						idCoche = Integer.parseInt(sc.nextLine());
						
						boolean añadir = dp.añadir(idPasajero, idCoche);
						if(añadir){
							System.out.println("El pasajero se ha añadido al coche");
						}else{
							System.out.println("El pasajero NO se ha añadido al coche");
						}
						
						break;
						
					case 6:
						System.out.println("************ ELIMINAR PASAJERO DE COCHE ***************");
//						System.out.println("Los coches disponibles son:");
//						listaCoches = dc.listar();
//						
//						for (Coche c : listaCoches) {
//							System.out.println(c);						
//						}
//						
						System.out.println("Introduce el id del pasajero:");
						idPasajero = Integer.parseInt(sc.nextLine());
						
						System.out.println("Introduce el id del coche:");
						idCoche = Integer.parseInt(sc.nextLine());
						
						boolean eliminar = dp.eliminar(idPasajero, idCoche);
						if(eliminar){
							System.out.println("El pasajero se ha eliminado del coche");
						}else{
							System.out.println("El pasajero NO se ha eliminado del coche");
						}
						
						break;
			
					case 7:
						System.out.println("************ LISTADO PASAJEROS ***************");
						System.out.println("Introduce el id del coche:");
						idCoche = Integer.parseInt(sc.nextLine());
						
						listaPasajeros = dp.listar(idCoche);
						
						for (Pasajero p : listaPasajeros) {
							System.out.println(p);						
						}
						
						break;
						
					default:
						System.out.println("Opción incorrecta");
					}
					
					break;
					
				case 7:
					System.out.println("******************************************");		
					System.out.println("******** Parando el programa *********");
					
					continuar = false;
					break;

				
				default:
					System.out.println("Opción incorrecta");
				}
				
			}while(continuar);

		} catch (Exception e) {
			System.err.println("Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("Fin del programa");	
	}

	private static void escribirMenu() {
		System.out.println();
		System.out.println("Elige la opción deseada:");
		System.out.println("--------------------------");
		System.out.println("1 = Añadir nuevo coche");
		System.out.println("2 = Borrar coche por ID");
		System.out.println("3 = Consulta coche por ID");
		System.out.println("4 = Modificar coche por ID");
		System.out.println("5 = Listado de coches");
		System.out.println("6 = Gestión de pasajeros");
		System.out.println("7 = Salir de la aplicación");
		System.out.println("--------------------------");
		System.out.println("¿Qué opción eliges?");
	}
	
	private static void escribirMenuPasajeros() {
		System.out.println();
		System.out.println("Elige la opción deseada:");
		System.out.println("--------------------------");
		System.out.println("1 = Añadir nuevo pasajero");
		System.out.println("2 = Borrar pasajero por ID");
		System.out.println("3 = Consulta pasajero por ID");
		System.out.println("4 = Listado de todos los pasajeros");
		System.out.println("5 = Añadir nuevo pasajero a un coche");
		System.out.println("6 = Eliminar pasajero de un coche");
		System.out.println("7 = Listado de los pasajeros de un coche");
		System.out.println("--------------------------");
		System.out.println("¿Qué opción eliges?");

	}
	

}
