package mvc;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * La classe Controleur definit le lien entre la vue et le modele.
 */
public class Controleur {

	private Course course;

	private RegistrationForm form;
	private Vue vue;


	/**
	 * Ce constructeur permet de construire un controleur avec tous ses attributs.
	 * @param c represente une classe Course.
	 * @param r represente une classe RegistrationForm.
	 * @param v represente une classe Vue.
	 */
	public Controleur(Course c, RegistrationForm r, Vue v) {
		this.course = c;
		this.form = r;
		this.vue = v;
	}

	/**
	 * Ce constructeur permet de construire un controleur avec un attribut.
	 * @param c represente une classe Course.
	 */
	public Controleur(Course c){
		this.course = c;
	}

	/**
	 * La methode initialize permet de lier la vue a la methode connection du modele d'un cours.  Ceci la connection du
	 * client au serveur.
	 * @return retourne un flux d'objet de sortie a la vue.
	 * @throws IOException gere les exceptions au niveau des entrees et des sorties.
	 */
	public ObjectOutputStream initialize() throws IOException {
		ObjectOutputStream os = this.course.connection();
		return os;
	}

	/**
	 * La methode coursAutomne permet de lier la vue a la methode setCoursAutomne du modele d'un cours.  Elle sert à
	 * recuperer la liste des cours qui se donnent en automne.
	 * @param os represente le flux d'objet de sortie fourni par le client.
	 * @return retourne la liste des cours qui se donnent en automne.
	 * @throws IOException gere les exceptions au niveau des entrees et des sorties.
	 * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
	 * @throws InterruptedException gere toutes les sortes d'interruptions.
	 */
	public ArrayList<Course> coursAutomne(ObjectOutputStream os) throws IOException, ClassNotFoundException, InterruptedException {
		ArrayList<Course> cours = this.course.setCoursAutomne(os);
		return cours;
	}

	/**
	 * La methode coursHiver permet de lier la vue a la methode setCoursHiver du modele d'un cours.  Elle sert à
	 * recuperer la liste des cours qui se donnent en hiver.
	 * @param os represente le flux d'objet de sortie fourni par le client.
	 * @return retourne la liste des cours qui se donnent en hiver.
	 * @throws IOException gere les exceptions au niveau des entrees et des sorties.
	 * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
	 * @throws InterruptedException gere toutes les sortes d'interruptions.
	 */
	public ArrayList<Course> coursHiver(ObjectOutputStream os) throws IOException, InterruptedException, ClassNotFoundException {
		ArrayList<Course> cours = this.course.setCoursHiver(os);
		return cours;
	}

	/**
	 * La methode coursEte permet de lier la vue a la methode setCoursEte du modele d'un cours.  Elle sert à
	 * recuperer la liste des cours qui se donnent en ete.
	 * @param os represente le flux d'objet de sortie fourni par le client.
	 * @return retourne la liste des cours qui se donnent en ete.
	 * @throws IOException gere les exceptions au niveau des entrees et des sorties.
	 * @throws ClassNotFoundException gere les exceptions au niveau des classes non trouvees.
	 * @throws InterruptedException gere toutes les sortes d'interruptions.
	 */
	public ArrayList<Course> coursEte(ObjectOutputStream os) throws IOException, InterruptedException, ClassNotFoundException {
		ArrayList<Course> cours = this.course.setCoursEte(os);
		return cours;
	}

	/**
	 * La methode setCode permet de lier la vue a la methode setCodeCOurs du modele d'un cours.  Elle sert à
	 * donner les informations du client au serveur.
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
	public String setCode(ObjectOutputStream os, String code, String prenom, String nom, String email, String matricule) throws IOException, ClassNotFoundException, InterruptedException {
		String reussite = this.course.setCodeCours(os, code, prenom, nom, email, matricule);
		return reussite;
	}
}