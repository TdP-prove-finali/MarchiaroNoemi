package it.polito.tdp.SortedWaste.model;

public class VicinoDistanza 
{
	private Cassonetto vicino;
	private Double distanza;
	
	public VicinoDistanza(Cassonetto vicino, Double distanza) 
	{
		super();
		this.vicino = vicino;
		this.distanza = distanza;
	}

	public Cassonetto getVicino() 
	{
		return vicino;
	}

	public Double getDistanza() 
	{
		return distanza;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vicino == null) ? 0 : vicino.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VicinoDistanza other = (VicinoDistanza) obj;
		if (vicino == null) 
		{
			if (other.vicino != null)
				return false;
		} 
		else if (!vicino.equals(other.vicino))
			return false;
		return true;
	}
	
	
	
}
