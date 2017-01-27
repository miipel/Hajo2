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
		sc.close();
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

	@Override
	public void run() {
		try {
			while (!server.getState().equals("END")) {
				if (server.getState().equals("WAIT")) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else if (server.getState().equals("GAME")) {
					Scanner sc = new Scanner(System.in);
					int yritykset = 3;
					while (yritykset > 0) {
						if (vastasiOikein) {
							server.tiedotaKaikille(pelaajaNimi + " vastasi oikein!");
							server.paivitaTilanne(pelaajaNimi, 1);
							odota();
							yritykset = 0;
						}
						System.out.println("Arvaa numero! >");
						vastasiOikein = server.tarkista(Integer.parseInt(sc.nextLine()));
						yritykset--;
						System.out.println("Yrityksiä jäljellä: " + yritykset);
					}
					System.out.println("Yritykset loppuivat!");
					server.paivitaTilanne(pelaajaNimi, 0);
					odota();

				}
			System.out.println("Peli loppui!");
			System.exit(0);
			} // while
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void odota() throws RemoteException {
		while (!server.getState().equals("END")) {
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