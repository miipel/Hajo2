/**
 * 
 */
package ArvausPeli;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * @author Miika
 *
 */
public class ArvaajaToteutus extends UnicastRemoteObject implements Arvaaja, Runnable {

	private ArvausPeli peli;
	private boolean kaynnissa = true;
	private boolean omaVuoro;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Syötä serverin nimi");
		String serveriNimi = sc.nextLine();
		System.out.println("Syötä nimesi");		
		String pelaajaNimi = sc.nextLine();
		ArvausPeli peli = (ArvausPeli) Naming.lookup(serveriNimi);
		new ArvaajaToteutus(peli, pelaajaNimi);
	}

	public ArvaajaToteutus(ArvausPeli peli, String pelaajaNimi) throws RemoteException {
		// TODO 
	}
	
	

	@Override
	public void arvaa() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aloitaPeli() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lopeta() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(kaynnissa) {
			if( omaVuoro ) {
				System.out.println("Arvaa numero: ");
				
			}
		}
		
	}

}
