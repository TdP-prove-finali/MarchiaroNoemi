package it.polito.tdp.prova.model;

import java.util.List;

public class Riepilogo 
{
	private String tipoRifiuto;
	private Integer capacitaCamion; // in Litri
	private Integer nCassonetti;
	private Double durataPrevista; // in minuti
	
	private Luogo partenza;
	private List<Cassonetto> percorso;
	private Luogo discarica;
	private Luogo arrivo;
	
	
	public Riepilogo(String tipoRifiuto, Integer capacitaCamion, Integer nCassonetti, Double durataPrevista,
			Luogo partenza, List<Cassonetto> percorso, Luogo discarica, Luogo arrivo) 
	{
		super();
		this.tipoRifiuto = tipoRifiuto;
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
