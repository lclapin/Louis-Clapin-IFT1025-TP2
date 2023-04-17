package server.models;

import java.io.Serializable;

/**
 * La classe RegistrationForm permet de definir ce qu'est un formulaire d'inscription.
 */
public class RegistrationForm implements Serializable {

    /**
     * L'attribut prenom represente le prenom de la personne.
     */
    private String prenom;
    /**
     * L'attribut nom represente le nom de la personne.
     */
    private String nom;
    /**
     * L'attribut email represente le courriel de la personne.
     */
    private String email;
    /**
     * L'attribut matricule represente le matricule de la personne.
     */
    private String matricule;
    /**
     * L'attribut course represente le cours pour lequel la personne veut s'inscrire.
     */
    private Course course;

    /**
     * Ce constructeur permet de construire un formulaire d'inscription avec tous ses attributs.
     * @param prenom represente le prenom de la personne.
     * @param nom represente le nom de la personne.
     * @param email represente le courriel de la personne.
     * @param matricule represente le matricule de la personne.
     * @param course represente le cour pour lequel la personne veut s'inscrire.
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * La methode getPrenom permet d'obtenir l'attribut prenom.
     * @return est le prenom retourne.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * La methode setPrenom permet de modifier l'attribut prenom.
     * @param prenom represente le prenom a modifier.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * La methode getNom permet d'obtenir l'attribut nom.
     * @return est le nom retourne.
     */
    public String getNom() {
        return nom;
    }

    /**
     * La methode setMom permet de modifier l'attribut nom.
     * @param nom represente le nom a modifier.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * La methode getEmail permet d'obtenir l'attribut email.
     * @return est le email retourne.
     */
    public String getEmail() {
        return email;
    }

    /**
     * La methode setEmail permet de modifier l'attribut email.
     * @param email represente le courriel a modifier.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * La methode getMatricule permet d'obtenir l'attribut matricule.
     * @return est le matricule retourne.
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * La methode setMatricule permet de modifier l'attribut matricule.
     * @param matricule represente le matricule a modifier.
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * La methode getCurse permet d'obtenir l'attribut course.
     * @return est le cours retourne.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * La methode setCourse permet de modifier l'attribut course.
     * @param course represente le cours a modifier.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * La methode toString permet d'afficher les attributs dans un format donne.
     * @return retourne le format donne.
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
