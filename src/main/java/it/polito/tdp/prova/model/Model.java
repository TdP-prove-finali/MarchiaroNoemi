package it.polito.tdp.prova.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.prova.db.ProvaDAO;

public class Model 
{
	private ProvaDAO dao;
	
	// liste di cassonetti, una dei vertici del grafo (solo un tipo di rifiuto) e una globale
	private List<Cassonetto> vertici;
	
	private List<Cassonetto> cassonetti;
	
	// grafo di cassonetti 
	private Graph<Cassonetto, DefaultWeightedEdge> grafo;
	
	// classe separata per cercare il percorso ottimo
	private TrovaPercorso trovaPercorso;
	
	// percorso ottimo --> memorizzo l'ultimo percorso per stamparlo nella schermata di riepilogo
	private List<Cassonetto> percorso;
	
	// sede e discarica --> memorizzo sede e discarica per stamparli nella schermata di riepilogo
	private Luogo sede;
	private Luogo discarica;
	
	public Model()
	{
		dao = new ProvaDAO();

		// all'avvio del programma, recupero dal database tutti i cassonetti e le loro informazioni
		// che resteranno invariate durante l'utilizzo dell'applicativo
		this.cassonetti = dao.getAllCassonetti();
	}
	
	private void creaGrafo(String tipo, String zona)
	{
		// creo grafo
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo vertici
		this.vertici = this.getCassonettiByTipoZona(tipo, zona);

		Graphs.addAllVertices(grafo, this.vertici);
		
		Integer num = 0;
		Double somm = 0.0;
		Double max = Double.MIN_VALUE;
		Double min = Double.MAX_VALUE;
		
		// aggiungo archi
		for(Cassonetto c1: this.vertici)
		{
			for(Cassonetto c2: this.vertici)
			{
				if(!c1.equals(c2))
				{
					DefaultWeightedEdge arco = grafo.getEdge(c1, c2);
					
					if(arco == null)
					{
						// se l'arco non esiste ancora, lo creo
						
						Double peso = LatLngTool.distance(c1.getPosizione(), c2.getPosizione(), LengthUnit.KILOMETER);
					
						num++;
						somm = somm + peso;
						
						if(peso < min)
							min = peso;
						
						if(peso > max)
							max = peso;
						
						Graphs.addEdgeWithVertices(this.grafo, c1, c2, peso);
					}
				}
			}
		}
		
		// stampo numero di vertici e archi
		String ret = String.format("Grafo creato!\n# vertici: %d\n# archi: %d", grafo.vertexSet().size(), grafo.edgeSet().size());
	
		System.out.println(ret);
		
		Double media = somm / num;
		
		System.out.println("Distanza media = " + media);
		
		System.out.println("Distanza minima = " + min);
		
		System.out.println("Distanza massima = " + max);
	}
	
	public Integer getNumeroVertici()
	{
		return this.grafo.vertexSet().size();
	}

	/**
	 * Riempie tutti i cassonetti in modo pseudocasuale, con una bistribuzione Gaussiana
	 */
	public Double riempiCassonetti()
	{
		Double totale = 0.0;
		
		// all'inizio tutti i cassonetti sono vuoti
		// scorro la lista e li riempio
		for(Cassonetto c: this.cassonetti)
		{
			Integer dimensione = c.getDimensione();
			Integer percentuale;
			
			// genero un numero casuale con distribuzione normale
			// Returns the next pseudorandom, Gaussian ("normally") distributed double value 
			// with mean 0.0 and standard deviation 1.0 from this random number generator's sequence.
			Random random = new Random();
			Double gauss = random.nextGaussian();
			
			if(gauss < -2)
			{
				// riempimento casuale tra 0% <= x < 2% 

				Random rand = new Random();
				percentuale = rand.nextInt(2);
			}
			else if(gauss < -1)
			{
				// riempimento casuale tra 2% <= x < 16% 

				Random rand = new Random();
				percentuale = rand.nextInt(14) + 2;
			}
			else if(gauss < 1)
			{
				// riempimento casuale tra 16% <= x < 84% 

				Random rand = new Random();
				percentuale = rand.nextInt(68) + 16;
			}
			else if(gauss < 2)
			{
				// riempimento casuale tra 84% <= x < 98% 

				Random rand = new Random();
				percentuale = rand.nextInt(14) + 84;
			}
			else	// gauss >= 2
			{
				// riempimento casuale tra 98% <= x <= 100% 

				Random rand = new Random();
				percentuale = rand.nextInt(3) + 98;
			}
			
			// ora in percentuale abbiamo un numero compreso tra 0 e 100 (distribuito normalmente)
			// i cassonetti però sono riempibili al massimo fino al 80% 
			percentuale = (int) Math.round(percentuale * 0.8);
			
			Integer contenuto = (int) (dimensione*(percentuale/100.0));
			
			c.setContenuto(contenuto);
			c.setPercentuale(percentuale);
			
			totale = totale + contenuto;
			
			// System.out.println(Math.round(gauss*100.0)/100.0);
		}
		
		return totale;
	}
	
	/**
	 * Restituisce una lista di Cassonetti del tipo specificato alla chiamata del metodo.
	 *  I cassonetti vengono ordinati per percentuale di riempimento decrescente.
	 */
	public List<Cassonetto> getCassonettiByTipoZona(String tipo, String zona)
	{
		List<Cassonetto> ret = new ArrayList<Cassonetto>();
		
		for(Cassonetto c: this.cassonetti)
		{
			if(c.getTipo().compareTo(tipo) == 0)
			{
				// il tipo di rifiuto corrisponde. la zona?
				
				if(zona.compareTo("Asti") == 0)
				{
					// aggiungo tutti i cassonetti
					ret.add(c);
				}
				else
				{
					// controllo che la zona corrisponda
					if(c.getZona().compareTo(zona) == 0)
					{
						ret.add(c);
					}
				}
			}
		}
		
		Collections.sort(ret, new ComparatoreCassonettiPercentuale());
		
		return ret;
	}
	
	public List<Cassonetto> calcolaPercorso(String tipo, String zona, Integer capacitaMezzo, Integer durataTurno)
	{
		this.creaGrafo(tipo, zona);
		
		this.trovaPercorso = new TrovaPercorso(this.grafo);
		
		this.sede = dao.getSede();
		
		this.discarica = null;
		
		if(tipo.compareTo("vetro") == 0)
		{
			this.discarica = dao.getDiscaricaVetro();
		}
		else
		{
			this.discarica = dao.getDiscaricaAltro();
		}
		
		this.trovaPercorso.init(tipo, zona, capacitaMezzo, durataTurno, this.sede, this.discarica);
		
		this.percorso = this.trovaPercorso.getPercorso();
		
		return this.percorso;
	}
	
	public void svuotaCassonettiPercorso()
	{
		// se il cassonetto fa parte del percorso ottimo accettato, viene svuotato
		
		for(Cassonetto c: this.cassonetti)
		{
			if(this.percorso.contains(c))
			{
				c.svuota();
			}
		}
	}
	
	public List<Cassonetto> getEsclusi()
	{
		return this.trovaPercorso.getEsclusi();
	}
	
	public Integer getDurataViaggio() 
	{
		return this.trovaPercorso.getDurataViaggio();
	}

	public Double getQuantitaRaccolta() 
	{
		return this.trovaPercorso.getQuantitaRaccolta();
	}
	
	public Double getQuantitaNonRaccolta()
	{
		return this.trovaPercorso.getQuantitaNonRaccolta();
	}
	
	public Integer getCassonettiSvuotati()
	{
		return this.trovaPercorso.getCassonettiSvuotati();
	}
	
	public Integer getCassonettiVuoti()
	{
		return this.trovaPercorso.getCassonettiVuoti();
	}
	
	public Integer getCassonettiNonSvuotati()
	{
		return this.trovaPercorso.getCassonettiNonSvuotati();
	}

	public Luogo getSede() 
	{
		return sede;
	}

	public Luogo getDiscarica() 
	{
		return discarica;
	}
	
	public Riepilogo getRiepilogo()
	{
		return this.trovaPercorso.getRiepilogo();
	}
	
}
