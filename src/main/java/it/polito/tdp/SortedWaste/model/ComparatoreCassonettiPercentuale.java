package it.polito.tdp.SortedWaste.model;

import java.util.Comparator;

public class ComparatoreCassonettiPercentuale implements Comparator<Cassonetto> 
{
	// permette di ordinare i cassonetti per percentuale DECRESCENTE
	
	@Override
	public int compare(Cassonetto c1, Cassonetto c2) 
	{
		return (int) (c2.getPercentuale() - c1.getPercentuale());
	}

}
