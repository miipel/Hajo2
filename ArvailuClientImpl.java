import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * 
 * @author Miika Peltotalo & Peetu Seilonen
 *
 */
public class ArvailuClientImpl extends UnicastRemoteObject implements ArvailuClient, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArvailuServer server;
	private String pelaajaNimi;
	private boolean vastasiOikein;

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		String osoite = "rmi://localhost/ArvailuServerImpl";
		String pelaajaNimi;
		Scanner sc = new Scanner(System.in);
		System.out.println("Anna nimi > ");
		pelaajaNimi = sc.nextLine();
		ArvailuServer server = (ArvailuServer) Naming.lookup(osoite);
		new ArvailuClientImpl(server, pelaajaNimi);
	} // main

	public ArvailuClientImpl(ArvailuServer server, String pelaajaNimi) throws RemoteException {
		this.server = server;
		this.pelaajaNimi = pelaajaNimi;
		server.lisaaClient(this);
		new Thread(this).start();
	}

	@Override
	public void yhdista() throws RemoteException {
		// TODO
	}

	@Override
	public void odotaNumeroa() throws RemoteException {
		// TODO Auto-generated method stub

	}

	public String pyydaSyotetta(Scanner sc) {
		
		System.out.println("Arvaa numero! >");
		String syote = sc.nextLine();
		return syote;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		String numero;
		int yritykset = 3;
		try {
			while (!server.getState().equals("END")) {
				odota("GAME");
				if (server.getState().equals("GAME")) {
					if (yritykset == 0) {
						System.out.println("Yritykset loppuivat!");
						server.paivitaTilanne(pelaajaNimi, 0);
						odota("END");
					}

					numero = pyydaSyotetta(sc);
					vastasiOikein = server.tarkista(Integer.parseInt(numero));
					if (vastasiOikein) {
						server.tiedotaKaikille(pelaajaNimi + " vastasi oikein!");
						server.paivitaTilanne(pelaajaNimi, 1);						
						yritykset = 0;
						odota("END");
					}
					yritykset--;
					System.out.println("Yrityksiä jäljellä: " + yritykset);

				}
				
			} // while
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Peli loppui!");
		System.exit(0);
	}

	@Override
	public String getPelaajaNimi() throws RemoteException {
		return pelaajaNimi;
	}

	@Override
	public void tiedota(String s) throws RemoteException {
		System.out.println(s);
	}

	@Override
	public void odota(String STATE) throws RemoteException {
		while (!server.getState().equals(STATE)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean getVastasikoOikein() throws RemoteException {
		return vastasiOikein;
	}

}