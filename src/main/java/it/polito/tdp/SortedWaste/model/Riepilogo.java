package it.polito.tdp.SortedWaste.model;

import java.util.List;

public class Riepilogo 
{
	private String tipoRifiuto;
	private String zona;
	private Integer capacitaCamion; // in Litri
	private Integer nCassonetti;
	private Double durataPrevista; // in minuti
	
	private Luogo partenza;
	private List<Cassonetto> percorso;
	private Luogo discarica;
	private Luogo arrivo;
	
	
	public Riepilogo(String tipoRifiuto, String zona, Integer capacitaCamion, Integer nCassonetti, Double durataPrevista,
			Luogo partenza, List<Cassonetto> percorso, Luogo discarica, Luogo arrivo) 
	{
		super();
		this.tipoRifiuto = tipoRifiuto;
		this.zona = zona;
		this.capacitaCamion = capacitaCamion;
		this.nCassonetti = nCassonetti;
		this.durataPrevista = durataPrevista;
		this.partenza = partenza;
		this.percorso = percorso;
		this.discarica = discarica;
		this.arrivo = arrivo;
	}


	public String getTipoRifiuto() 
	{
		return tipoRifiuto;
	}
	
	public String getZona() 
	{
		return zona;
	}

	public Integer getCapacitaCamion() 
	{
		return capacitaCamion;
	}

	public Integer getnCassonetti() 
	{
		return nCassonetti;
	}

	public Double getDurataPrevista() 
	{
		return durataPrevista;
	}

	public Luogo getPartenza() 
	{
		return partenza;
	}

	public List<Cassonetto> getPercorso() 
	{
		return percorso;
	}

	public Luogo getDiscarica() 
	{
		return discarica;
	}

	public Luogo getArrivo() 
	{
		return arrivo;
	}
	
}
