import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
    boolean[][]  defineNextGeneration(boolean[][] full, boolean[][] part, int startRow,
                                     int rowCount, int size) throws RemoteException;
}
