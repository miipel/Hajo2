import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArvailuServer extends Remote {
	public void lisaaClient(ArvailuClient client) throws RemoteException;

	public void arvoNumero() throws RemoteException;

	public boolean tarkista(int clientVastaus) throws RemoteException;

	public void lopeta() throws RemoteException;

	public void setState(String STATE) throws RemoteException;

	public String getState() throws RemoteException;

	public void tiedotaKaikille(String viesti) throws RemoteException;

	public void paivitaTilanne(String pelaajaNimi, int piste) throws RemoteException;

}
