import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArvailuClient extends Remote {
	public void yhdista() throws RemoteException;

	public void odotaNumeroa() throws RemoteException;
	
	public String getPelaajaNimi() throws RemoteException;
	
	public boolean getVastasikoOikein() throws RemoteException;
	
	public void tiedota(String s) throws RemoteException;
	
	public void odota() throws RemoteException;
	

} // interface Noppa