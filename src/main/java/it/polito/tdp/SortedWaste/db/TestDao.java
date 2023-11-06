package it.polito.tdp.SortedWaste.db;

public class TestDao {

	public static void main(String[] args) 
	{
		SortedWasteDAO dao = new SortedWasteDAO();
		
		System.out.println(dao.getDiscaricaAltro().toString());
	}

}
