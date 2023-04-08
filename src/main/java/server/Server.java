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

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) throws IOException {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        while ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            if (cmd.equals("exit"))
                break;
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) throws IOException {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
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
                            System.out.println(code + " " + nom + " " + session);
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

                    /*File f = new File("src/main/java/server/data/cours.txt");
                    String absolute = f.getAbsolutePath();
                    System.out.println("Original  path: " + f.getPath());
                    System.out.println("Absolute  path: " + absolute);
                    Scanner scan = new Scanner(absolute);*/
                    while (scan.hasNext()) {
                        String code = scan.next().substring(0);
                        String nom = scan.next().substring(0);
                        String session = scan.next().substring(0);
                        Course cours = new Course(nom, code, session);
                        FileOutputStream fileOs = new FileOutputStream("course.dat");
                        ObjectOutputStream os = new ObjectOutputStream(fileOs);
                        if(session.equals("Hiver")){
                            courses.add(cours);
                            System.out.println(code + " " + nom + " " + session);
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
                            System.out.println(code + " " + nom + " " + session);
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

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
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
                    //System.out.println(code + " " + nom + " " + session);
                    os.writeObject(cours);
                    //os.reset();
                    os.close();
                }
            }
            //Thread.sleep(200);
            //this.objectInputStream.reset();

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
}