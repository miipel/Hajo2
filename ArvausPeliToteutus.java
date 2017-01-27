/**
 * 
 */
package ArvausPeli;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Miika
 *
 */
public class ArvausPeliToteutus extends UnicastRemoteObject implements ArvausPeli {
	
	private Arvaaja arvaaja1;
	private Arvaaja arvaaja2;
	private int[] pisteet;
	private int vastaus;

	protected ArvausPeliToteutus() throws RemoteException {
		
	} // ArvausPeliToteutus

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Naming.rebind("ArvausPeliToteutus", new ArvausPeliToteutus());

	} // main

	@Override
	public void vaihdaVuoroa() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arvoAloittaja() {
		// TODO Auto-generated method stub
		
	}

}
