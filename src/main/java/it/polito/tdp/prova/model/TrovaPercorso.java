package it.polito.tdp.prova.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class TrovaPercorso 
{
	// ATTRIBUTI
	// grafo e vertici
	private Graph<Cassonetto, DefaultWeightedEdge> grafo;
	// input
	private String tipo; // carta plastica vetro indifferenziata
	private Integer capacitaMezzo; // in Litri
	private Integer durataTurno; // in minuti
	private Integer durataSvuotamentoDiscarica; // in minuti
	// costanti di riduzione, durata svuotamento e velocità camion
	private final Double riduzioneCarta = 0.15;
	private final Double riduzionePlastica = 0.3;
	private final Double riduzioneVetro = 0.05;
	private final Double riduzioneIndifferenziata = 0.1;
	private final Double durataSvuotamentoCassonetto = 1.5; // in miniti = 90 secondi
	private final Integer durataSvuotamentoDiscaricaVetro = 10; // in minuti
	private final Integer durataSvuotamentoDiscaricaAltro = 20; // in minuti
	private final Integer velocitaCamion = 30; // km/h
	// discarica e sede
	private Luogo sede;
	private Luogo discarica;
	// output
	private List<Cassonetto> percorso;
	private Double durataViaggio; // in minuti
	private Double quantitaRaccolta; // in Litri
	private Integer cassonettiVuoti;
	// Size di percorso = cassonetti svuotati
	// List cassonetti --> tolgo e aggiungo al percorso --> sono gli esclusi
	
	private Riepilogo riepilogo;
	
	// COSTRUTTORE
	public TrovaPercorso(Graph<Cassonetto, DefaultWeightedEdge> grafo)
	{
		// grafo solo del tipo specificato
		this.grafo = grafo;
	}
	
	// INIZIALIZZAZIONE
	public void init(String tipo, Integer capacitaMezzo, Integer durataTurno, Luogo sede, Luogo discarica)
	{
		// input
		this.tipo = tipo;
		this.capacitaMezzo = capacitaMezzo;
		this.durataTurno = durataTurno;
		
		if(tipo.compareTo("vetro") == 0)
		{
			this.durataSvuotamentoDiscarica = this.durataSvuotamentoDiscaricaVetro;
		}
		else
		{
			this.durataSvuotamentoDiscarica = this.durataSvuotamentoDiscaricaAltro;
		}
		
		// sede e discarica
		this.sede = sede;
		this.discarica = discarica;
		
		// output
		this.percorso = new LinkedList<Cassonetto>();
		this.durataViaggio = 0.0;
		this.quantitaRaccolta = 0.0;
		
		// stato mondo
		List<Cassonetto> parziale = new LinkedList<Cassonetto>();
		Double durata = 0.0;
		Double quantita = 0.0;
		
		// prima di lanciare la ricorsione, conto quanti cassonetti sono già vuoti
		this.cassonettiVuoti = 0;
		
		for(Cassonetto c: grafo.vertexSet())
		{
			if(c.getContenuto() == 0)
			{
				this.cassonettiVuoti++;
			}
		}
		
		// qui parziale.size() == 0
		
		// inserisco ogni cassonetto come primo del percorso
		// aggiungo la distanza sede --> primo cassonetto
		// lancio la ricorsione
		
		for(Cassonetto primo: this.grafo.vertexSet())
		{
			parziale.add(primo);
			
			// calcolo quantita raccolta
			Integer contenuto = primo.getContenuto();
			Double effettivo = 0.0;
			switch (this.tipo)
			{
				case "carta": effettivo = contenuto * (1 - this.riduzioneCarta);
				case "indifferenziata": effettivo = contenuto * (1 - this.riduzioneIndifferenziata);
				case "plastica": effettivo = contenuto * (1 - this.riduzionePlastica);
				case "vetro": effettivo = contenuto * (1 - this.riduzioneVetro);
			}
			// calcolo durata viaggio
			LatLng coord1 = primo.getPosizione();
			LatLng coord2 = this.sede.getPosizione();
			Double distanza = LatLngTool.distance(coord1, coord2, LengthUnit.KILOMETER);
			Integer durataViaggio = (int) (distanza / this.velocitaCamion * 60); // velocità in km/h --> durata in minuti
			
			// lancio la ricorsione
			cerca(parziale, 
					durata + durataViaggio + this.durataSvuotamentoCassonetto, 
					quantita + effettivo);
			
			// backtracking
			// rimuovo l'ultimo cassonetto
			parziale.remove(parziale.size()-1);
			
		}
		
		this.riepilogo = new Riepilogo(tipo, capacitaMezzo, this.percorso.size(), 
				this.durataViaggio, this.sede, this.percorso, this.discarica, this.sede);
		
	}
	
	
	// METODI
	private void cerca(List<Cassonetto> parziale, Double durata, Double quantita) 
	{
		// condizioni di terminazione
		
		// il percorso è valido?
		// limite ore non superato
		// limite carico non superato
		if(durata > this.durataTurno ||
				quantita > this.capacitaMezzo)
		{
			// il percorso non è accettabile
			return;
		}
		
		// qui il percorso è valido
		// se è la soluzione migliore
		if( quantita >= this.quantitaRaccolta)	// durata <= this.durataViaggio &&
		{
			this.percorso = new LinkedList<Cassonetto>(parziale);
			this.durataViaggio = durata;
			this.quantitaRaccolta = quantita;
			// vado avanti su questo ramo
		}
		
		
		
		// passo ricorsivo
		
		// scorro i vicini dell'ultimo inserito e provo le varie "strade"
		Cassonetto ultimo = parziale.get(parziale.size()-1);
		for(Cassonetto vicino: Graphs.neighborListOf(grafo, ultimo))
		{
			if(!parziale.contains(vicino))
			{
				// aggiungo
				parziale.add(vicino);
				// calcolo quantita raccolta
				Integer contenuto = vicino.getContenuto();
				Double effettivo = 0.0;
				switch (this.tipo)
				{
					case "carta": effettivo = contenuto * (1 - this.riduzioneCarta);
					case "indifferenziata": effettivo = contenuto * (1 - this.riduzioneIndifferenziata);
					case "plastica": effettivo = contenuto * (1 - this.riduzionePlastica);
					case "vetro": effettivo = contenuto * (1 - this.riduzioneVetro);
				}
				// calcolo durata viaggio
				LatLng coord1 = ultimo.getPosizione();
				LatLng coord2 = vicino.getPosizione();
				Double distanza = LatLngTool.distance(coord1, coord2, LengthUnit.KILOMETER);
				Integer durataViaggio = (int) (distanza / this.velocitaCamion * 60); // velocità in km/h --> durata in minuti
				
				//viaggio verso la discarica e svuotamento
				Double distanzaDiscarica = LatLngTool.distance(
						vicino.getPosizione(), this.discarica.getPosizione(), LengthUnit.KILOMETER);
				// viaggio in sede
				Double distanzaSede = LatLngTool.distance(
						this.discarica.getPosizione(), this.sede.getPosizione(), LengthUnit.KILOMETER);
				Integer viaggioDiscarica = (int) (distanzaDiscarica / this.velocitaCamion * 60);
				Integer viaggioSede = (int) (distanzaSede / this.velocitaCamion * 60);
				
				// lancio la ricorsione
				cerca(parziale, 
						durata + durataViaggio + this.durataSvuotamentoCassonetto + viaggioDiscarica + 
						this.durataSvuotamentoDiscarica + viaggioSede, 
						quantita + effettivo);
				
				// backtracking
				// rimuovo l'ultimo cassonetto
				parziale.remove(parziale.size()-1);
			}
		}
	}

	
	// GETTER RISULTATI
	public List<Cassonetto> getPercorso() 
	{
		return percorso;
	}
	
	public List<Cassonetto> getEsclusi()
	{
		List<Cassonetto> esclusi = new ArrayList<Cassonetto>();
		
		for(Cassonetto c: this.grafo.vertexSet())
		{
			if(!percorso.contains(c))
			{
				esclusi.add(c);
			}
		}
		
		return esclusi;
	}
	
	public Integer getCassonettiSvuotati()
	{
		return this.percorso.size();
	}
	
	public Integer getCassonettiVuoti()
	{
		return this.cassonettiVuoti;
	}
	
	public Integer getCassonettiNonSvuotati()
	{
		return this.grafo.vertexSet().size() - (this.percorso.size() + this.cassonettiVuoti);
	}

	public Integer getDurataViaggio() 
	{
		return this.durataViaggio.intValue();
	}

	public Double getQuantitaRaccolta() 
	{
		return this.quantitaRaccolta;
	}
	
	public Double getQuantitaNonRaccolta() 
	{
		Double totale = 0.0;
		
		for(Cassonetto c: this.grafo.vertexSet())
		{
			totale = totale + c.getContenuto();
		}
		
		return totale - this.quantitaRaccolta;
	}
	
	public Riepilogo getRiepilogo()
	{
		return this.riepilogo;
	}
}
