package it.polito.tdp.prova.model;

import java.util.Comparator;

public class ComparatoreVicinoDistanza implements Comparator<VicinoDistanza> 
{
	// permette di ordinare i vicini per distanza CRESCENTE

	@Override
	public int compare(VicinoDistanza v1, VicinoDistanza v2) 
	{
		return (int) (v1.getDistanza() - v2.getDistanza());
	}

}
