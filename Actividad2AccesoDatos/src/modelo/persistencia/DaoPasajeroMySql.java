package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.persistencia.interfaces.DaoPasajero;

public class DaoPasajeroMySql implements DaoPasajero{

	private Connection conexion;
	
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/coches";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public boolean alta(Pasajero p) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;		
		String query = "insert into pasajero (NOMBRE,EDAD,PESO) values(?,?,?)";
		
		try {
			//preparamos la query con valores parametrizables(?)
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setDouble(3, p.getPeso());
//			ps.setInt(4, 0);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
	
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + p);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}

	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from pasajero where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			//sustituimos la primera interrgante por la id
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
			
		} catch (SQLException e) {
			borrado = false;
			System.out.println("baja -> No se ha podido dar de baja el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado; 
	}

	@Override
	public Pasajero obtener(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Pasajero p = null;		
		String query = "select ID,NOMBRE,EDAD,PESO,ID_COCHE from pasajero where id = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				p = new Pasajero();
				p.setId(rs.getInt(1));
				p.setNombre(rs.getString(2));
				p.setEdad(rs.getInt(3));
				p.setPeso(rs.getDouble(4));
				p.setIdCoche(rs.getInt(5));
			}
			
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el pasajero con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
				
		return p;
	}

	@Override
	public List<Pasajero> listar() {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		String query = "select ID,NOMBRE,EDAD,PESO,ID_COCHE from pasajero";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero p = new Pasajero();
				p.setId(rs.getInt(1));
				p.setNombre(rs.getString(2));
				p.setEdad(rs.getInt(3));
				p.setPeso(rs.getDouble(4));
				p.setPeso(rs.getDouble(4));
				p.setIdCoche(rs.getInt(5));
				
				listaPasajeros.add(p);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los pasajeros");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return listaPasajeros;
	}

	@Override
	public boolean añadir(int idPasajero, int idCoche) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean alta = true;
		String query = "update pasajero set ID_COCHE=? WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			ps.setInt(2, idPasajero);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
	
		} catch (SQLException e) {
			System.out.println("añadir -> Error al añadir pasajero " + idPasajero + " al coche " + idCoche);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}

	@Override
	public boolean eliminar(int idPasajero, int idCoche) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "update pasajero set ID_COCHE=? WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, null);
			ps.setInt(2, idPasajero);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
			
		} catch (SQLException e) {
			borrado = false;
			System.out.println("eliminar -> No se ha podido dar de baja al pasajero " + idPasajero + " del coche " + idCoche);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		return borrado; 
	}

	@Override
	public List<Pasajero> listar(int idCoche) {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		String query = "select ID,NOMBRE,EDAD,PESO,ID_COCHE from pasajero where ID_COCHE = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			
			ResultSet rs = ps.executeQuery();			
			while(rs.next()){
				Pasajero p = new Pasajero();
				p.setId(rs.getInt(1));
				p.setNombre(rs.getString(2));
				p.setEdad(rs.getInt(3));
				p.setPeso(rs.getDouble(4));
				p.setPeso(rs.getDouble(4));
				p.setIdCoche(rs.getInt(5));
				
				listaPasajeros.add(p);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los pasajeros del coche" + idCoche);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return listaPasajeros;
	}

}
