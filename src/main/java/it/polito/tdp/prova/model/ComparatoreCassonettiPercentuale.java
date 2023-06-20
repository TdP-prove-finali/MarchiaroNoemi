package it.polito.tdp.prova.model;

import java.util.Comparator;

public class ComparatoreCassonettiPercentuale implements Comparator<Cassonetto> 
{

	@Override
	public int compare(Cassonetto c1, Cassonetto c2) 
	{
		return (int) (c2.getPercentuale() - c1.getPercentuale());
	}

}
