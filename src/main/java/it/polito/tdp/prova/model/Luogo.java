package it.polito.tdp.prova.model;

import com.javadocmd.simplelatlng.LatLng;

public class Luogo 
{
	private String indirizzo;
	private LatLng posizione;
	
	public Luogo(String indirizzo, LatLng posizione) 
	{
		this.indirizzo = indirizzo;
		this.posizione = posizione;
	}

	public String getIndirizzo() 
	{
		return indirizzo;
	}

	public LatLng getPosizione() 
	{
		return posizione;
	}

	@Override
	public String toString() 
	{
		return String.format("%s", indirizzo);
	}
	
	
}
