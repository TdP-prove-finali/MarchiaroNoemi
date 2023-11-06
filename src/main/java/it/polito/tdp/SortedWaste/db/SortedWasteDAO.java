package it.polito.tdp.SortedWaste.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.SortedWaste.model.Cassonetto;
import it.polito.tdp.SortedWaste.model.Luogo;

public class SortedWasteDAO {
	
	/**
	 * 
	 * @return Lista di tutti i cassonetti presenti nel database, indipendentemente dal tipo
	 */
	public List<Cassonetto> getAllCassonetti()
	{
		String sql = "SELECT * "
				+ "FROM cassonetti ";
		
		List<Cassonetto> result = new ArrayList<Cassonetto>();
		
		try 
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) 
			{
				String id = res.getString("id");
				Double lat = Double.parseDouble(res.getString("latitudine"));
				Double lng = Double.parseDouble(res.getString("longitudine"));
				LatLng coord = null;
				coord = new LatLng(lat, lng);
				String indirizzo = res.getString("indirizzo");
				Integer dimensione = res.getInt("dimensione");
				String tipo = res.getString("tipo");
				String zona = res.getString("zona");
				
				result.add(new Cassonetto(id, coord, indirizzo, dimensione, tipo, zona));
			}
			
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return Luogo denominato 'sede'
	 */
	public Luogo getSede()
	{
		String sql = "SELECT * "
				+ "FROM luoghi "
				+ "WHERE nome = 'sede'";
		
		Luogo sede = null;
		
		try 
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) 
			{
				String indirizzo = res.getString("indirizzo");
				Double lat = Double.parseDouble(res.getString("latitudine"));
				Double lng = Double.parseDouble(res.getString("longitudine"));
				LatLng coord = null;
				coord = new LatLng(lat, lng);
				
				sede = new Luogo(indirizzo, coord);
			}
			
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return sede;
	}
	
	/**
	 * 
	 * @return Luogo denominato 'discaricaVetro'
	 */
	public Luogo getDiscaricaVetro()
	{
		String sql = "SELECT * "
				+ "FROM luoghi "
				+ "WHERE nome = 'discaricaVetro'";
		
		Luogo discV = null;
		
		try 
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) 
			{
				String indirizzo = res.getString("indirizzo");
				Double lat = Double.parseDouble(res.getString("latitudine"));
				Double lng = Double.parseDouble(res.getString("longitudine"));
				LatLng coord = null;
				coord = new LatLng(lat, lng);
				
				discV = new Luogo(indirizzo, coord);
			}
			
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return discV;
	}
	
	/**
	 * 
	 * @return Luogo denominato 'discaricaAltro'
	 */
	public Luogo getDiscaricaAltro()
	{
		String sql = "SELECT * "
				+ "FROM luoghi "
				+ "WHERE nome = 'discaricaAltro'";
		
		Luogo discA = null;
		
		try 
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) 
			{
				String indirizzo = res.getString("indirizzo");
				Double lat = Double.parseDouble(res.getString("latitudine"));
				Double lng = Double.parseDouble(res.getString("longitudine"));
				LatLng coord = null;
				coord = new LatLng(lat, lng);
				
				discA = new Luogo(indirizzo, coord);
			}
			
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return discA;
	}

}
