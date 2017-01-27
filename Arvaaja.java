package ArvausPeli;

import java.rmi.Remote;

public interface Arvaaja extends Remote {
	
	public void aloitaPeli();
	
	public void arvaa();

	public void lopeta();
	
} // interface Arvaaja
