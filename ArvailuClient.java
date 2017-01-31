import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArvailuClient extends Remote {

	public String getPelaajaNimi() throws RemoteException;

	public boolean getVastasikoOikein() throws RemoteException;

	public void tiedota(String s) throws RemoteException;

	public void odota(String STATE) throws RemoteException;

} // interface Noppa