package it.polito.tdp.SortedWaste.model;

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
	private String zona; // 1 2 3 4 5 6 centro
	private Integer capacitaMezzo; // in Litri
	private Integer durataTurno; // in minuti
	private Integer durataSvuotamentoDiscarica; // in minuti
	// durata svuotamento cassonetto e velocità camion
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
		// grafo solo del tipo e zona specificati
		this.grafo = grafo;
	}
	
	// INIZIALIZZAZIONE
	public void init(String tipo, String zona, Integer capacitaMezzo, Integer durataTurno, Luogo sede, Luogo discarica)
	{
		// input
		this.tipo = tipo;
		this.zona = zona;
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
			// se il cassonetto NON è vuoto, inizio il percorso da lui
			if(primo.getContenuto() != 0)
			{
				parziale.add(primo);
				
				// calcolo quantita raccolta --> contenuto del cassonetto "compresso" nel camion --> costanti di riduzione
				Double effettivo = primo.getEffettivo();
				// calcolo durata viaggio
				LatLng coord1 = primo.getPosizione();
				LatLng coord2 = this.sede.getPosizione();
				Double distanza = LatLngTool.distance(coord1, coord2, LengthUnit.KILOMETER);
				Integer durataViaggio = (int) (distanza / this.velocitaCamion * 60); // velocità in km/h --> durata in minuti
				
				//viaggio verso la discarica e svuotamento
				Double distanzaDiscarica = LatLngTool.distance(
						primo.getPosizione(), this.discarica.getPosizione(), LengthUnit.KILOMETER);
				// viaggio in sede
				Double distanzaSede = LatLngTool.distance(
						this.discarica.getPosizione(), this.sede.getPosizione(), LengthUnit.KILOMETER);
				Integer viaggioDiscarica = (int) (distanzaDiscarica / this.velocitaCamion * 60);
				Integer viaggioSede = (int) (distanzaSede / this.velocitaCamion * 60);
				
				
				// lancio la ricorsione
				cercaNearestNeighbors(parziale, 
						durata + durataViaggio + this.durataSvuotamentoCassonetto + viaggioDiscarica + 
						this.durataSvuotamentoDiscarica + viaggioSede, 
						quantita + effettivo);
				
				// backtracking
				// rimuovo l'ultimo cassonetto
				parziale.remove(parziale.size()-1);
			}
			else
			{
				// se sono qui, tutti i cassonetti sono vuoti
				// il percorso ottimo sarà una lista vuota
				// aggiungo il messaggio di errore nella schermata
			}
		}
		
		this.riepilogo = new Riepilogo(this.tipo, this.zona, this.capacitaMezzo, this.percorso.size(), 
				this.durataViaggio, this.sede, this.percorso, this.discarica, this.sede);
		
	}
	
	
	// METODI
	private void cercaNearestNeighbors(List<Cassonetto> parziale, Double durata, Double quantita) 
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
		Cassonetto ultimo = parziale.get(parziale.size()-1);
		
		
		// ALGORITMO NEAREST NEIGHBOR
		// algoritmo euristico --> approssimato
		// proseguo il percorso nel "vicino più vicino" non ancora svuotato
		
		// trovo il vicino più vicino 
		// NON deve far già parte del percorso
		// NON deve essere vuoto
		
		Double distanzaMinima = Double.MAX_VALUE;
		Cassonetto piuVicino = null;
		
		for(Cassonetto vicino: Graphs.neighborListOf(grafo, ultimo))
		{
			if(!parziale.contains(vicino) && vicino.getContenuto()!=0)
			{
				Double distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(vicino, ultimo));
				
				if(distanza < distanzaMinima)
				{
					distanzaMinima = distanza;
					piuVicino = vicino;
				}
			}
		}
		
		if(piuVicino == null)
		{
			// i cassonetti sono o tutti vuoti o tutti già presenti nel percorso
			// fermo la ricorsione
			return;
		}
		
		// aggiungo
		parziale.add(piuVicino);
		// calcolo quantita raccolta --> contenuto del cassonetto "compresso" nel camion --> costanti di riduzione
		Double effettivo = piuVicino.getEffettivo();
		
		// calcolo durata viaggio tra ultimo e piuVicino
		Double distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(piuVicino, ultimo));
		Integer durataViaggio = (int) (distanza / this.velocitaCamion * 60); // velocità in km/h --> durata in minuti
		
		// proseguendo su questo ramo, aggiungo un altro cassonetto
		// questo avrà una diversa distanza dalla discarica rispetto all'ultimo inserito
		// --> lanciando la ricorsione devo:
		// rimuovere dalla durata il tempo di viaggio dall'ultimo cassonetto alla discarica
		// aggiungere alla durata il tempo di viaggio dal nuovo cassonetto alla discarica
		// NON aggiungere il tempo di svuotamento del cassonetto in discarica
		// NON aggiungere il tempo di viaggio dalla discarica alla sede
		// questi ultimi due tempi sono già stati inseriti nella durata all'inizio della ricorsione
		// in quanto restano invariabili, a prescindere dall'ultimo cassonetto inserito nel percorso
		
		// viaggio verso la discarica di piuVICINO
		Double distanzaDiscaricaPlus = LatLngTool.distance(
				piuVicino.getPosizione(), this.discarica.getPosizione(), LengthUnit.KILOMETER);
		Integer viaggioDiscaricaPlus = (int) (distanzaDiscaricaPlus / this.velocitaCamion * 60);
		
		// viaggio verso la discarica di ULTIMO 
		Double distanzaDiscaricaMinus = LatLngTool.distance(
				ultimo.getPosizione(), this.discarica.getPosizione(), LengthUnit.KILOMETER);
		Integer viaggioDiscaricaMinus = (int) (distanzaDiscaricaMinus / this.velocitaCamion * 60);
		
		// lancio la ricorsione
		cercaNearestNeighbors(parziale, 
				durata + durataViaggio + this.durataSvuotamentoCassonetto 
				+ viaggioDiscaricaPlus
				- viaggioDiscaricaMinus, 
				quantita + effettivo);
		
		// backtracking
		// rimuovo l'ultimo cassonetto
		parziale.remove(parziale.size()-1);
		
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
			if(!percorso.contains(c))
			{
				totale = totale + c.getContenuto();
			}
		}
		
		return totale;
	}
	
	public Riepilogo getRiepilogo()
	{
		return this.riepilogo;
	}
	
}
