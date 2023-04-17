package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

/**
 * La classe Server permet d'ecouter sur un port pour verifier si un client se connecte, de lire les lignes
 * d'instruction, de differencier les commandes des arguments, de charger des cours selon une session donnee et
 * de d'inscrire les donnees d'une nouvelle inscription.
 */
public class Server {

    /**
     * L'attribut REGISTER_COMMAND represente la commande pour l'inscription.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * L'attribut LOAD_COMMAND represente la commande pour le chargement.
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private static Socket client;
    /**
     * L'attribut objectInputStream represente le flux d'entree.
     */
    public static ObjectInputStream objectInputStream;
    /**
     * L'attribut objectOutputStream represente le flux de sortie.
     */
    public static ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Le constructeur definit le port d'ecoute et le backlog, cree un arraylist des manipulateurs d'evenements et
     * definit la methode pour ajouter un manipulateur d'evenement.
     * @param port represente l'argument du port d'ecoute.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 2);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * La methode addEventHandler permet d'ajouter un manipulateur d'evenement au arrylist handlers.
     * @param h represente le manipulateur d'evenement a ajouter.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * La methode alertHandlers permet de differencier les commandes des arguments des manipulateurs d'evenement
     * du arraylist Handlers.
     * @param cmd represente la commande.
     * @param arg represente l'argument.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    private void alertHandlers(String cmd, String arg) throws IOException {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * La methode run permet d'accepter qu'un client se connecte au serveur et implemente le multithreading.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * La methode listen permet de lire une ligne pour entreposer les commandes et les arguments dans une structure
     * de donnees.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
            if ((line = this.objectInputStream.readObject().toString()) != null) {
                Pair<String, String> parts = processCommandLine(line);
                String cmd = parts.getKey();
                String arg = parts.getValue();
                this.alertHandlers(cmd, arg);
            }
    }

    /**
     * La methode processCommandLine permet de separer une ligne en commande et argument et de les placer dans une
     * structure de donnees.
     * @param line represente la ligne envoyee par le client.
     * @return est une structure de donnes contenant les commandes et les arguments.
     */
    public static Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * La methode disconnect permet de fermer tous les flux et les sockets.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    public static void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * La methode handleEvents permet de determiner si la commande va rediriger vers l'inscription d'une nouvelle
     * personnes ou vers le chargement des cours d'une session donnee.
     * @param cmd represente la commande.
     * @param arg represente la session choisie.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    public void handleEvents(String cmd, String arg) throws IOException {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /*
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
     */

    /**
     * La methode handleLoadCourses permet de charger une liste de cours pour une session donnee.
     * @param arg représente la session choisie par le client soit 1 pour automne, 2 pour hiver et 3 pour eteé
     */
    public void handleLoadCourses(String arg) {
        switch (arg) {
            case "1":
                try {
                    ArrayList<Course> courses = new ArrayList<Course>();
                    Scanner scan = new Scanner(new File("src\\main\\java\\server\\data\\cours.txt"));
                    while (scan.hasNext()) {
                        String code = scan.next().substring(0);
                        String nom = scan.next().substring(0);
                        String session = scan.next().substring(0);
                        Course cours = new Course(nom, code, session);
                        FileOutputStream fileOs = new FileOutputStream("course.dat");
                        ObjectOutputStream os = new ObjectOutputStream(fileOs);
                        if(session.equals("Automne")){
                            courses.add(cours);
                        }
                        os.writeObject(courses);
                        os.close();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("Erreur à l'ouverture du fichier");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "2":
                try {
                    ArrayList<Course> courses = new ArrayList<Course>();
                    Scanner scan = new Scanner(new File("src\\main\\java\\server\\data\\cours.txt"));

                    while (scan.hasNext()) {
                        String code = scan.next().substring(0);
                        String nom = scan.next().substring(0);
                        String session = scan.next().substring(0);
                        Course cours = new Course(nom, code, session);
                        FileOutputStream fileOs = new FileOutputStream("course.dat");
                        ObjectOutputStream os = new ObjectOutputStream(fileOs);
                        if(session.equals("Hiver")){
                            courses.add(cours);
                            //System.out.println(code + " " + nom + " " + session);
                        }
                        os.writeObject(courses);
                        os.close();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("Erreur à l'ouverture du fichier");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "3":
                try {
                    ArrayList<Course> courses = new ArrayList<Course>();
                    Scanner scan = new Scanner(new File("src\\main\\java\\server\\data\\cours.txt"));
                    while (scan.hasNext()) {
                        String code = scan.next().substring(0);
                        String nom = scan.next().substring(0);
                        String session = scan.next().substring(0);
                        Course cours = new Course(nom, code, session);
                        FileOutputStream fileOs = new FileOutputStream("course.dat");
                        ObjectOutputStream os = new ObjectOutputStream(fileOs);
                        if(session.equals("Ete")){
                            courses.add(cours);
                        }
                        os.writeObject(courses);
                        os.close();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("Erreur à l'ouverture du fichier");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Commande inconnue");
        }
    }

    /*
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    /**
     * La methode handleRegistration permet d'inscrire les donnees envoyees par un client dans un fichier pour creer
     * son inscrition.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    public void handleRegistration() throws IOException {
        try{
            String codeC;

            codeC = this.objectInputStream.readObject().toString();
            Scanner scan = new Scanner(new File("src\\main\\java\\server\\data\\cours.txt"));
            while (scan.hasNext()) {
                String code = scan.next().substring(0);
                String nom = scan.next().substring(0);
                String session = scan.next().substring(0);
                if(code.equals(codeC)){
                    Course cours = new Course(nom, code, session);
                    FileOutputStream fileOs = new FileOutputStream("course.dat");
                    ObjectOutputStream os = new ObjectOutputStream(fileOs);
                    os.writeObject(cours);
                    os.close();
                }
            }

            RegistrationForm rfs = (RegistrationForm) this.objectInputStream.readObject();
            System.out.println(rfs.toString());
            FileWriter fw = new FileWriter("src\\main\\java\\server\\data\\inscription.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            String s = rfs.getCourse().getSession() + "\t" + rfs.getCourse().getCode() + "\t" + rfs.getMatricule() +
                    "\t" + rfs.getPrenom() + "\t" + rfs.getNom() + "\t" + rfs.getEmail() + "\n";
            writer.append(s);
            writer.close();

            FileOutputStream fileOs = new FileOutputStream("validation.dat");
            ObjectOutputStream os = new ObjectOutputStream(fileOs);
            os.writeObject("reussite");

        } catch (IOException ex) {
            System.out.println("Erreur à l'écriture du fichier");
            FileOutputStream fileOs = new FileOutputStream("validation.dat");
            ObjectOutputStream os = new ObjectOutputStream(fileOs);
            os.writeObject("echec");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ClientHandler class
    private class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run()
        {
            final Object lockCompteur = new Object();
            try {
                synchronized(lockCompteur) {
                    lockCompteur.notifyAll();
                    objectInputStream = new ObjectInputStream(client.getInputStream());
                    objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                    listen();
                    disconnect();
                    System.out.println("Client déconnecté!");
                    lockCompteur.wait();

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        }
    }

