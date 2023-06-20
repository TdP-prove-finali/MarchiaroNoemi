package it.polito.tdp.prova.model;

import com.javadocmd.simplelatlng.LatLng;

public class Cassonetto 
{
	private String id;
	private LatLng posizione;
	private String indirizzo;
	private Integer dimensione;
	private Integer contenuto;
	private Integer percentuale;
	private String tipo;
	
	
	public Cassonetto(String id, LatLng posizione, String indirizzo, Integer dimensione, String tipo) 
	{
		super();
		this.id = id;
		this.posizione = posizione;
		this.indirizzo = indirizzo;
		this.dimensione = dimensione;
		// ogni cassonetto viene creato vuoto
		this.contenuto = 0;
		this.percentuale = 0;
		this.tipo = tipo;
	}
	
	
	
	public String getId() 
	{
		return id;
	}

	public LatLng getPosizione() 
	{
		return posizione;
	}
	public String getIndirizzo() 
	{
		return indirizzo;
	}
	public Integer getDimensione() 
	{
		return dimensione;
	}

	public Integer getContenuto() 
	{
		return contenuto;
	}

	public Integer getPercentuale() 
	{
		return percentuale;
	}
	
	public String getTipo() 
	{
		return tipo;
	}

	
	public void setContenuto(Integer contenuto) 
	{
		this.contenuto = contenuto;
	}

	public void setPercentuale(Integer percentuale) 
	{
		this.percentuale = percentuale;
	}
	
	

	/**
	 * svuota il cassonetto: 
	 * contenuto e percentuale di riempimento di azzerano
	 */
	public void svuota()
	{
		this.contenuto = 0;
		this.percentuale = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cassonetto other = (Cassonetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return String.format(
				"Cassonetto [id=%s, posizione=%s, indirizzo=%s, dimensione=%s, contenuto=%s, percentuale=%s, tipo=%s]", id,
				posizione, indirizzo, dimensione, contenuto, percentuale, tipo);
	}


	
}