package client;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class JavaClient {

	public static void main(String[] args) {

		try {
			Socket cS = new Socket("127.0.0.1", 1337);

			//OutputStreamWriter os = new OutputStreamWriter(cS.getOutputStream());

			ObjectOutputStream os = new ObjectOutputStream(cS.getOutputStream());
			//ObjectInputStream is = new ObjectInputStream(cS.getInputStream());

			//BufferedWriter bw = new BufferedWriter(os);

			System.out.println("Veuillez saisir 'CHARGER' suivi du numéro de la session (1 = automne, 2 = hiver" +
					" 3 = été) pour afficher les cours de la session choisie.");
			System.out.println("Vous pouvez aussi saisir 'INSCRIRE' pour vous inscrire à un cours.");

			Scanner sc = new Scanner(System.in);

			while (sc.hasNext()) {
				String line = sc.nextLine();
				//System.out.println("J'ai envoyé " + line);
				os.writeObject(line);
				//os.reset();
				//os.append(line+"\n");
				os.flush();
				if (line.equals("exit")) {
					System.out.println("Au revoir.");
					break;
				}

				if(line.equals("CHARGER 1") || line.equals("CHARGER 2") || line.equals("CHARGER 3")) {
					Thread.sleep(200);
					System.out.print("Voici les cours de la session d'");
					if(line.equals("CHARGER 1")){
						System.out.println("automne :");
					}
					if(line.equals("CHARGER 2")){
						System.out.println("hiver :");
					}
					if(line.equals("CHARGER 3")){
						System.out.println("été :");
					}

					//Thread.sleep(200);
					FileInputStream fileIs = new FileInputStream("course.dat");

					ObjectInputStream is = new ObjectInputStream(fileIs);

					ArrayList<Course> cours = new ArrayList<>();
					cours = (ArrayList<Course>) is.readObject();

					for (int i = 0; i < cours.size(); i++) {
						System.out.println(cours.get(i).getCode() + " " + cours.get(i).getName());
					}
				}
				if (line.equals("INSCRIRE")){
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
						//os.reset();
						os.flush();
					}

					Thread.sleep(200);
					FileInputStream fileIs = new FileInputStream("course.dat");

					ObjectInputStream is = new ObjectInputStream(fileIs);
					//Course course = new Course();
					Course course = (Course) is.readObject();
					//System.out.println(course.toString());
					RegistrationForm rf = new RegistrationForm(prenom, nom, email, matricule, course);


					//os.reset();
					//Thread.sleep(200);
					//ObjectOutputStream osrf = new ObjectOutputStream(cS.getOutputStream());
					os.writeObject(rf);
					//os.reset();
					os.flush();
					//osrf.reset();
					Thread.sleep(200);
					FileInputStream fileIsR = new FileInputStream("validation.dat");

					ObjectInputStream isR = new ObjectInputStream(fileIsR);
					String reussite = isR.readObject().toString();
					if(reussite.equals("reussite")){
						System.out.println("Félicitations ! Inscription réussie pour " + rf.getPrenom() +
								" au cour " + rf.getCourse().getCode() + ".");
					}
					if(reussite.equals("echec")){
						System.out.println("Vos informations n'ont pas été enregistrées correctement.  Veuillez réesssayer.");
					}
				}
			}
				os.close();
				sc.close();
				cS.close();

			} catch(ConnectException x){
				System.out.println("Connexion impossible sur port 1337: pas de serveur.");
			} catch(IOException e){
				e.printStackTrace();
			} catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

}
