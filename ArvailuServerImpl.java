import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Miika Peltotalo & Peetu Seilonen
 *
 */
public class ArvailuServerImpl extends UnicastRemoteObject implements ArvailuServer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String STATE = "START";
	private Random rnd = new Random();
	private int vastaus;
	private ArrayList<ArvailuClient> clients = new ArrayList<ArvailuClient>();
	private String saannot = "Pelaajat arvaavat vuorotellen numeroa 0-9."
			+ " Jos jompi kumpi arvaa numeron oikein, toisella pelaajalla"
			+ " on viel‰ mahdollisuus arvata oikein tasoittavalla vuorolla."
			+ " Peli voi p‰‰tty‰ tasapeliin tai jomman kumman pelaajan voittoon.";

	public ArvailuServerImpl() throws RemoteException {
		arvoNumero();
		STATE = "WAIT";
	}

	public static void main(String[] args) {
		try {
			Naming.rebind("ArvailuServerImpl", new ArvailuServerImpl());
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	} // main

	@Override
	public void setState(String STATE) throws RemoteException {
		this.STATE = STATE;
		tiedotaKaikille("Pelin tila: " + STATE);
	}

	@Override
	public void lisaaClient(ArvailuClient client) throws RemoteException {
		clients.add(client);
		
		tiedotaKaikille("Pelaaja " + client.getPelaajaNimi() + " liittyi.");
		if (clients.size() == 2) {
			setState("GAME");
			tiedotaKaikille(saannot);
			tiedotaKaikille("\n");
			tiedotaKaikille("Peli alkaa!");
			
		}
			
	}

	@Override
	public void arvoNumero() throws RemoteException {
		vastaus = rnd.nextInt(10);
	}

	@Override
	public boolean tarkista(int clientVastaus) throws RemoteException {
		if (clientVastaus == vastaus)
			return true;
		return false;
	}

	@Override
	public void lopeta() throws RemoteException {
		ArrayList<String> voittajat = new ArrayList<String>();
		for (ArvailuClient arvailuClient : clients) {
			if (arvailuClient.getVastasikoOikein()) {
				arvailuClient.getPelaajaNimi();
			}
		}
		if (voittajat.size() > 1) tiedotaKaikille("Ei voittajaa!");
		else tiedotaKaikille(voittajat.get(0) + " voitti pelin!");
		
		setState("END");
	}

	@Override
	public String getState() throws RemoteException {
		return STATE;
	}

	@Override
	public void tiedotaKaikille(String viesti) throws RemoteException {
		for (ArvailuClient arvailuClient : clients) {
			arvailuClient.tiedota(viesti);
		}		
	}
	
	

	@Override
	public void paivitaTilanne(String pelaajaNimi, int piste) throws RemoteException {
		if (piste == 1) {
			tiedotaKaikille(pelaajaNimi + " vastasi oikein!");
		}
		if (piste == 0) {
			lopeta();
		}
	}

} // class NoppaServer