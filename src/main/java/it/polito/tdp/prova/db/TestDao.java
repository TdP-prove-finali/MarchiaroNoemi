package it.polito.tdp.prova.db;

public class TestDao {

	public static void main(String[] args) 
	{
		ProvaDAO dao = new ProvaDAO();
		
		System.out.println(dao.getDiscaricaAltro().toString());
	}

}
