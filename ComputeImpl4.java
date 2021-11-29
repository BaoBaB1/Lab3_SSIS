import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeImpl4 extends UnicastRemoteObject implements Compute {
    public ComputeImpl4() throws RemoteException {
        super();
    }

    @Override
    public boolean[][] defineNextGeneration(boolean[][] fullCurrentGeneration, boolean[][] partOfGeneration,
                                            int startRow, int endRow, int size) throws RemoteException {
        NeighbourHelper helper = new NeighbourHelper();
        for (int i = 0; i < endRow; i++) {
            for (int j = 0; j < size; j++) {
                partOfGeneration[i][j] = helper.isAlive(i + startRow, j, fullCurrentGeneration, size);
            }
        }

        return partOfGeneration;
    }

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "D:\\rmi.policy");
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            LocateRegistry.createRegistry(2024);
            Compute comp4 = new ComputeImpl();
            String name = "rmi://localhost/Compute4";
            Naming.rebind(name, comp4);
            System.out.println("ComputeEngine4 bound");
        } catch(Exception e) {
            System.err.println("ComputeEngine exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
