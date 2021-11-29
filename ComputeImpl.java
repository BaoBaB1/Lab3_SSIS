import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeImpl extends UnicastRemoteObject implements Compute {

    public ComputeImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean[][] defineNextGeneration(boolean[][] fullCurrentGeneration, boolean[][] partOfGeneration,
                                            int startRow, int rowsCount, int size) throws RemoteException {
        NeighbourHelper helper = new NeighbourHelper();
        for (int i = 0; i < rowsCount; i++) {
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
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Compute comp = new ComputeImpl();
            String name = "rmi://localhost/Compute";
            Naming.rebind(name, comp);
            System.out.println("ComputeEngine bound");
        } catch(Exception e) {
            System.err.println("ComputeEngine exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
