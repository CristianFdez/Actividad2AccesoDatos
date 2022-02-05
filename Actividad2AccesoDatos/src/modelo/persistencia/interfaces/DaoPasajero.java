package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Pasajero;

public interface DaoPasajero {
	public boolean alta(Pasajero p);
	public boolean baja(int id);
	public Pasajero obtener(int id);
	public List<Pasajero> listar();
	public boolean añadir(int idPasajero, int idCoche);
	public boolean eliminar(int idPasajero, int idCoche);
	public List<Pasajero> listar(int idCoche);
}
