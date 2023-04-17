package server.models;

//import mvc.CourseMVC;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;

/**
 * La classe Course permet de definir ce qu'est un cours.
 */
public class Course implements Serializable {

    /**
     * L'attribut name represente le nom du cours.
     */
    private String name;
    /**
     * L'attribut code represente le code du cours.
     */
    private String code;
    /**
     * L'attribut session represente la session du cours.
     */
    private String session;

    /**
     * Ce constructeur permet de construire un cours sans attribut.
     */
    public Course(){

    }

    /**
     * Ce constructeur permet de construire un cours avec deux de ses attributs.
     * @param name represente le nom du cours.
     * @param code represente le code du cours.
     */
    public Course(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Ce constructeur permet de construire un cours avec tous ses attributs.
     * @param name represente le nom du cours.
     * @param code represente le code du cours.
     * @param session represente la session pour lequel le cours se donne.
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * La methode getName permet d'obtenir l'attribut name.
     * @return est le nom de cours retourne.
     */
    public String getName() {
        return name;
    }

    /**
     * La methode setName permet de modifier l'attribut name.
     * @param name represente le nom du cours a modifier.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * La methode getCode permet d'obtenir l'attribut code.
     * @return est le code du cours retourne.
     */
    public String getCode() {
        return code;
    }

    /**
     * La methode setCode permet de modifier l'attribut code.
     * @param os represente le flux de sortie.
     * @param code represente le code du cours a modifier.
     */
    public void setCode(ObjectOutputStream os, String code) {
        this.code = code;
    }

    /**
     * La methode getSession permet d'obtenir l'attribut session.
     * @return est la session du cours retourne.
     */
    public String getSession() {
        return session;
    }

    /**
     * La methode setSession permet de modifier l'attribut session.
     * @param session represente la session du cours a modifier.
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * La methode connection permet que le client puisse se connecter au serveur sur un port donne.
     * @return retourne un flux d'objet de sortie au controleur.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     */
    public ObjectOutputStream connection() throws IOException {
        Socket cS = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(cS.getOutputStream());
        return os;
    }

    /**
     * La methode setCoursAutomne permet de recuperer la liste des cours qui se donnent en automne.
     * @param os represente le flux d'objet de sortie fourni par le client.
     * @return retourne la liste des cours qui se donnent en automne.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
     * @throws InterruptedException gere toutes les sortes d'interruptions.
     */
    public ArrayList<Course> setCoursAutomne(ObjectOutputStream os) throws IOException, ClassNotFoundException, InterruptedException {

        String line = "CHARGER 1";
        os.writeObject(line);
        os.flush();

        Thread.sleep(200);
        FileInputStream fileIs = new FileInputStream("course.dat");

        ObjectInputStream is = new ObjectInputStream(fileIs);
        ArrayList<Course> cours = new ArrayList<>();
        cours = (ArrayList) is.readObject();

        return cours;

    }

    /**
     * La methode setCoursHiver permet de recuperer la liste des cours qui se donnent en hiver.
     * @param os represente le flux d'objet de sortie fourni par le client.
     * @return retourne la liste des cours qui se donnent en hiver.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
     * @throws InterruptedException gere toutes les sortes d'interruptions.
     */
    public ArrayList<server.models.Course> setCoursHiver(ObjectOutputStream os) throws IOException, InterruptedException, ClassNotFoundException {
        String line = "CHARGER 2";
        os.writeObject(line);
        os.flush();

        Thread.sleep(200);
        FileInputStream fileIs = new FileInputStream("course.dat");

        ObjectInputStream is = new ObjectInputStream(fileIs);

        ArrayList<Course> cours = new ArrayList<>();
        cours = (ArrayList) is.readObject();
        return cours;

    }

    /**
     * La methode setCoursEte permet de recuperer la liste des cours qui se donnent en ete.
     * @param os represente le flux d'objet de sortie fourni par le client.
     * @return retourne la liste des cours qui se donnent en ete.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
     * @throws InterruptedException gere toutes les sortes d'interruptions.
     */
    public ArrayList<server.models.Course> setCoursEte(ObjectOutputStream os) throws IOException, InterruptedException, ClassNotFoundException {
        String line = "CHARGER 3";
        os.writeObject(line);
        os.flush();

        Thread.sleep(200);
        FileInputStream fileIs = new FileInputStream("course.dat");

        ObjectInputStream is = new ObjectInputStream(fileIs);

        ArrayList<Course> cours = new ArrayList<>();
        cours = (ArrayList) is.readObject();
        return cours;
    }

    /**
     * Le methode setCodeCours permet de prendre les informations entrees par le client et de les envoyer au serveur.
     * @param os represente le flux d'objet de sortie fourni par le client.
     * @param code represente le code du cours pour lequel la personne veut s'inscrire.
     * @param prenom represente le prenom de la personne.
     * @param nom represente le nom de la personne.
     * @param email represente le courriel de la personne.
     * @param matricule represente le matricule de la personne.
     * @return retourne si l'operation a reussi.
     * @throws IOException gere les exceptions au niveau des entrees et des sorties.
     * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
     * @throws InterruptedException gere toutes les sortes d'interruptions.
     */
    public String setCodeCours(ObjectOutputStream os, String code, String prenom, String nom, String email, String matricule) throws IOException, ClassNotFoundException, InterruptedException {
        String line = "INSCRIRE";
        os.writeObject(line);
        os.writeObject(code);
        os.flush();
        Thread.sleep(200);
        FileInputStream fileIs = new FileInputStream("course.dat");

        ObjectInputStream is = new ObjectInputStream(fileIs);
        Course course = (Course) is.readObject();
        RegistrationForm rf = new RegistrationForm(prenom, nom, email, matricule, course);
        os.writeObject(rf);
        os.flush();

        Thread.sleep(200);
        FileInputStream fileIsR = new FileInputStream("validation.dat");

        ObjectInputStream isR = new ObjectInputStream(fileIsR);
        String reussite = isR.readObject().toString();

        return reussite;
    }

    /**
     * La methode toString permet d'afficher les attributs dans un format donne.
     * @return retourne le format donne.
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }

}
