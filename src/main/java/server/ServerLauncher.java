package server;
/**
 * La classe ServerLauncher permet de lancer un nouveau serveur.
 */
public class ServerLauncher {
    /**
     * L'attribut PORT represente le port d'ecoute.
     */
    public final static int PORT = 1337;

    /**
     * La methode main permet de lancer un serveur sir un port d'ecoute precis.
     * @param args represente la chaine d'arguments passee a la methode.
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}