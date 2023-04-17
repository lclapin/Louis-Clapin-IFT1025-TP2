package client;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * La classe JavaClient definit les operations qu'un client peut faire avec des lignes de commandes.
 */
public class JavaClient {

	/**
	 * La methode main permet de connecter un client au serveur, de recevoir les informations des cours d'une session
	 * donnee et d'envoyer les informations du client qui souhaite s'inscrire a un cour.
	 *
	 * @param args represente la chaine d'arguments passee a la methode.
	 */
	public static void main(String[] args) {

		try {
			Socket cSd = new Socket("127.0.0.1", 1337);

			ObjectOutputStream osd = new ObjectOutputStream(cSd.getOutputStream());

			System.out.println("Veuillez saisir 'CHARGER' suivi du numéro de la session (1 = automne, 2 = hiver" +
					" 3 = été) pour afficher les cours de la session choisie.");
			System.out.println("Vous pouvez aussi saisir 'INSCRIRE' pour vous inscrire à un cours.");

			Scanner sc = new Scanner(System.in);

			while (sc.hasNext()) {
				String line = sc.nextLine();
				Socket cS = new Socket("127.0.0.1", 1337);
				ObjectOutputStream os = new ObjectOutputStream(cS.getOutputStream());
				os.writeObject(line);
				os.flush();
				if (line.equals("exit")) {
					System.out.println("Au revoir.");
					break;
				}

				if (line.equals("CHARGER 1") || line.equals("CHARGER 2") || line.equals("CHARGER 3")) {
					Thread.sleep(200);
					System.out.print("Voici les cours de la session d'");
					if (line.equals("CHARGER 1")) {
						System.out.println("automne :");
					}
					if (line.equals("CHARGER 2")) {
						System.out.println("hiver :");
					}
					if (line.equals("CHARGER 3")) {
						System.out.println("été :");
					}

					FileInputStream fileIs = new FileInputStream("course.dat");
					ObjectInputStream is = new ObjectInputStream(fileIs);

					ArrayList<Course> cours = new ArrayList<>();
					cours = (ArrayList<Course>) is.readObject();

					for (int i = 0; i < cours.size(); i++) {
						System.out.println(cours.get(i).getCode() + " " + cours.get(i).getName());
					}
				}
				if (line.equals("INSCRIRE")) {
					System.out.println("Veuillez saisir votre prénom :");
					Scanner scPrenom = new Scanner(System.in);
					String prenom = scPrenom.nextLine();
					System.out.println("Veuillez saisir votre nom :");
					Scanner scNom = new Scanner(System.in);
					String nom = scNom.nextLine();
					System.out.println("Veuillez saisir votre courriel :");
					Scanner scEmail = new Scanner(System.in);
					String email = scEmail.nextLine();
					System.out.println("Veuillez saisir votre matricule :");
					Scanner scMatricule = new Scanner(System.in);
					String matricule = scMatricule.nextLine();
					System.out.println("Veuillez saisir le code du cours :");
					Scanner scCode = new Scanner(System.in);
					if (scCode.hasNext()) {
						String code = scCode.nextLine();
						os.writeObject(code);
						os.flush();
					}

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
					if (reussite.equals("reussite")) {
						System.out.println("Félicitations ! Inscription réussie pour " + rf.getPrenom() +
								" au cour " + rf.getCourse().getCode() + ".");
					}
					if (reussite.equals("echec")) {
						System.err.println("Vos informations n'ont pas été enregistrées correctement.  Veuillez réesssayer.");
					}
				}
				os.close();
				cS.close();
			}
			sc.close();

		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}

