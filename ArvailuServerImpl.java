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
	private int pelaajaLaskuri;
	private ArrayList<ArvailuClient> clients = new ArrayList<ArvailuClient>();
	private String saannot = "Pelaajat arvaavat vuorotellen numeroa 0-9."
			+ " Jos jompi kumpi arvaa numeron oikein, toisella pelaajalla"
			+ " on vielä mahdollisuus arvata oikein tasoittavalla vuorolla."
			+ " Peli voi päättyä tasapeliin tai jomman kumman pelaajan voittoon.";

	public static void main(String[] args) {
		try {
			Naming.rebind("ArvailuServerImpl", new ArvailuServerImpl());
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	} // main

	/**
	 * Constructor
	 * 
	 * @throws RemoteException
	 */
	public ArvailuServerImpl() throws RemoteException {
		arvoNumero();
		STATE = "WAIT";
	}

	
	/**
	 * Asettaa tilan pelille.
	 * Mahdollisia tiloja: START, WAIT, GAME tai END
	 */
	@Override
	public void setState(String STATE) throws RemoteException {
		this.STATE = STATE;
		tiedotaKaikille("Pelin tila: " + STATE);
	}

	/**
	 * Lisää asiakkaan peliin. Samalla myös listaan josta löytyy
	 * kaikki asiakkaat.
	 */
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

	/**
	 * Arpoo numeron 0-9
	 */
	@Override
	public void arvoNumero() throws RemoteException {
		vastaus = rnd.nextInt(10);
	}

	/**
	 * Tarkistaa asiakkaan syöttämän vastauksen.
	 * Vastaako palvelimen oikeaa vastausta vai ei.
	 */
	@Override
	public boolean tarkista(int clientVastaus) throws RemoteException {
		if (clientVastaus == vastaus)
			return true;
		return false;
	}

	/**
	 * Lopettaa pelin ja tarkistaa kuka on voittanut.
	 * Voittajia on joko yksi tai ei ollenkaan.
	 */
	@Override
	public void lopeta() throws RemoteException {
		ArrayList<String> voittajat = new ArrayList<String>();
		for (ArvailuClient arvailuClient : clients) {
			if (arvailuClient.getVastasikoOikein()) {
				voittajat.add(arvailuClient.getPelaajaNimi());
			}
		}
		if (voittajat.size() > 1)
			tiedotaKaikille("Ei voittajaa!");
		if (voittajat.size() == 1)
			tiedotaKaikille(voittajat.get(0) + " voitti pelin!");
		if (voittajat.isEmpty())
			tiedotaKaikille("Ei voittajaa!");
		setState("END");
	}

	/**
	 * Getteri
	 */
	@Override
	public String getState() throws RemoteException {
		return STATE;
	}

	/**
	 * Käytetään koko pelin välisten viestien lähettämiseen.
	 */
	@Override
	public void tiedotaKaikille(String viesti) throws RemoteException {
		for (ArvailuClient arvailuClient : clients) {
			arvailuClient.tiedota(viesti);
		}
	}

	/**
	 * Kutsutaan jos pelaaja on vastannut oikein tai arvaukset ovat
	 * loppuneet. Kutsuu molemmissa tapauksissa lopeta() -metodia.
	 */
	@Override
	public void paivitaTilanne(String pelaajaNimi, int piste) throws RemoteException {
		if (piste == 1) {
			tiedotaKaikille(pelaajaNimi + " vastasi oikein!");
			pelaajaLaskuri++;
			if (pelaajaLaskuri == 2)
				lopeta();
		}
		if (piste == 0) {
			pelaajaLaskuri++;
			if (pelaajaLaskuri == 2)
				lopeta();
		}
	}

} // class NoppaServer