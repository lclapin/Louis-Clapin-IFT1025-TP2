package mvc;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import server.models.Course;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * La classe Vue definit une interface graphique pour le client et demarre des evenements soient "charger" et "envoyer".
 */
public class Vue extends Application {

    private static Controleur controleur;

    //Controleur controleur = new Controleur();

    /**
     * La methode start permet de construire les differentes fenetres et de creer des evenements avec les boutons.
     * @param primaryStage represente la fenetre dans laquelle se trouvera l'interface graphique.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            HBox root = new HBox();
            Scene scene = new Scene(root, 600, 400);

            root.setPadding(new Insets(20));

            VBox vGauche = new VBox(10);
            GridPane pane = new GridPane();
            pane.setAlignment(Pos.TOP_CENTER);
            pane.setHgap(10);
            pane.setVgap(10);
            pane.setPadding(new Insets(0, 25, 25, 25));

            root.getChildren().add(vGauche);
            root.getChildren().add(pane);

            Text listeDesCours = new Text("Liste des cours");
            listeDesCours.setFont(Font.font("Arial", FontWeight.NORMAL,20));
            vGauche.getChildren().add(listeDesCours);
            vGauche.setAlignment(Pos.CENTER);

            TableView tableView = new TableView();

            TableColumn<Map, String> codeColumn = new TableColumn<>("Code");
            codeColumn.setCellValueFactory(new MapValueFactory<>("Code"));

            TableColumn<Map, String> coursColumn = new TableColumn<>("Cours");
            coursColumn.setCellValueFactory(new MapValueFactory<>("Cours"));

            tableView.getColumns().add(codeColumn);
            tableView.getColumns().add(coursColumn);

            codeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
            coursColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.6));

            codeColumn.setResizable(false);
            coursColumn.setResizable(false);

            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            tableView.prefHeightProperty().bind(primaryStage.heightProperty());
            tableView.prefWidthProperty().bind(primaryStage.widthProperty());

            HBox hBoxBas = new HBox(20);
            hBoxBas.setAlignment(Pos.CENTER);
            //hBoxBas.setSpacing(50);

            String sessions[] = { "Automne", "Hiver", "Ete" };
            ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(sessions));

            Button charger = new Button("charger");

            charger.setAlignment(Pos.BOTTOM_RIGHT);

            vGauche.getChildren().add(tableView);
            vGauche.getChildren().add(hBoxBas);
            hBoxBas.getChildren().add(comboBox);
            hBoxBas.getChildren().add(charger);

            Text formulaire = new Text("Formulaire d'inscription");
            formulaire.setFont(Font.font("Arial", FontWeight.NORMAL,20));
            pane.add(formulaire, 0, 0, 2, 1);
            Label prenom = new Label("Prénom");
            pane.add(prenom, 0, 1);
            final TextField prenomField = new TextField();
            pane.add(prenomField, 1, 1);
            Label nom = new Label("Nom");
            pane.add(nom,0,2);
            final TextField nomField = new TextField();
            pane.add(nomField, 1, 2);
            Label courriel = new Label("Courriel");
            pane.add(courriel,0,3);
            final TextField courrielField = new TextField();
            pane.add(courrielField, 1, 3);
            Label matricule = new Label("Matricule");
            pane.add(matricule,0,4);
            final TextField matriculeField = new TextField();
            pane.add(matriculeField, 1, 4);

            Button envoyer = new Button("envoyer");
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            hbox.getChildren().add(envoyer);
            pane.add(hbox, 1, 5);

            final Text taxMessage = new Text();
            pane.add(taxMessage, 1, 6);

            controleur = new Controleur(new Course());

            ObjectOutputStream osd = controleur.initialize();

            ObservableList<Map<String, Object>> items =
                    FXCollections.<Map<String, Object>>observableArrayList();

            charger.setOnAction((action) -> {
                ObjectOutputStream os = null;
                try {
                    os = controleur.initialize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(comboBox.getValue().equals("Automne")){
                    try {
                        items.clear();
                        tableView.getItems().clear();
                        ArrayList<Course> cours = controleur.coursAutomne(os);
                        for (int i = 0; i < cours.size(); i++){
                            Map<String, Object> item = new HashMap<>();
                            item.put("Code", cours.get(i).getCode());
                            item.put("Cours" , cours.get(i).getName());
                            items.add(item);
                        }
                        tableView.getItems().addAll(items);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (comboBox.getValue().equals("Hiver")) {
                    try {
                        items.clear();
                        tableView.getItems().clear();
                        ArrayList<Course> cours = controleur.coursHiver(os);
                        for (int i = 0; i < cours.size(); i++){
                            Map<String, Object> item = new HashMap<>();
                            item.put("Code", cours.get(i).getCode());
                            item.put("Cours" , cours.get(i).getName());
                            items.add(item);
                        }
                        tableView.getItems().addAll(items);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (comboBox.getValue().equals("Ete")) {
                    try {
                        items.clear();
                        tableView.getItems().clear();
                        ArrayList<Course> cours = controleur.coursEte(os);
                        for (int i = 0; i < cours.size(); i++){
                            Map<String, Object> item = new HashMap<>();
                            item.put("Code", cours.get(i).getCode());
                            item.put("Cours" , cours.get(i).getName());
                            items.add(item);
                        }
                        tableView.getItems().addAll(items);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            envoyer.setOnAction((action) -> {
                ObjectOutputStream os = null;
                try {
                    os = controleur.initialize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String prenomText = prenomField.getText();
                String nomText = nomField.getText();
                String courrielText = courrielField.getText();
                String matriculeText = matriculeField.getText();
                if(matriculeField.getText().matches("^[0-9]{8,8}$")){
                    matriculeField.setStyle("-fx-text-fill: black");
                }
                if(courrielField.getText().matches("^(.+)@(.+)$")){
                    courrielField.setStyle("-fx-text-fill: black");
                }
                if ((matriculeField.getText().matches("^[0-9]{8,8}$")) && (courrielField.getText().matches("^(.+)@(.+)$"))) {
                    Map course = (Map) tableView.getSelectionModel().getSelectedItem();
                    if (course == null) {
                        echecCode(primaryStage);
                    }
                    String code = (String) course.get("Code");
                    try {
                        String reussite = controleur.setCode(os, code, prenomText, nomText, courrielText, matriculeText);
                        if(reussite.equals("reussite")){
                          succes(primaryStage, code, prenomText, nomText);
                        }


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!(matriculeField.getText().matches("^[0-9]{8,8}$"))) {
                    matriculeField.setStyle("-fx-text-fill: red");
                    echecMatricule(primaryStage);
                }
                if (!(courrielField.getText().matches("^(.+)@(.+)$"))) {
                    courrielField.setStyle("-fx-text-fill: red");
                    echecCourriel(primaryStage);
                }
                });

            primaryStage.setTitle("Inscription UdeM");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void succes(Stage primaryStage, String code, String prenomText, String nomText) {

        Label secondLabel = new Label("Félicitations! " + prenomText + " " + nomText + " est inscrit(e)\n" + "avec succès pour le cours " + code + "!");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        secondLabel.setFont(Font.font("Arial", FontWeight.NORMAL,15));

        Scene secondScene = new Scene(secondaryLayout, 300, 150);

        Stage newWindow = new Stage();
        newWindow.setTitle("Message");
        newWindow.setScene(secondScene);

        newWindow.setX(primaryStage.getX() + 160);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }

    void echecCode(Stage primaryStage) {

        Label secondLabel = new Label("Le formulaire est invalide.\n" + "Vous devez sélectionner un cours.");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        secondLabel.setFont(Font.font("Arial", FontWeight.NORMAL,15));

        Scene secondScene = new Scene(secondaryLayout, 300, 150);

        Stage newWindow = new Stage();
        newWindow.setTitle("Erreur");
        newWindow.setScene(secondScene);

        newWindow.setX(primaryStage.getX() + 160);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }

    void echecCourriel(Stage primaryStage) {

        Label secondLabel = new Label("Le formulaire est invalide.\n" + "Le courriel doit être du bon format.");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        secondLabel.setFont(Font.font("Arial", FontWeight.NORMAL,15));

        Scene secondScene = new Scene(secondaryLayout, 300, 150);

        Stage newWindow = new Stage();
        newWindow.setTitle("Erreur");
        newWindow.setScene(secondScene);

        newWindow.setX(primaryStage.getX() + 160);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }
    void echecMatricule(Stage primaryStage) {

        Label secondLabel = new Label("Le formulaire est invalide.\n" + "Le matricule doit contenir huit chiffres.");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        secondLabel.setFont(Font.font("Arial", FontWeight.NORMAL,15));

        Scene secondScene = new Scene(secondaryLayout, 300, 150);

        Stage newWindow = new Stage();
        newWindow.setTitle("Erreur");
        newWindow.setScene(secondScene);

        newWindow.setX(primaryStage.getX() + 160);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }

    /**
     * La methode main fait appel a la methode launch.
     * @param args represente la chaine d'arguments de la methode main.
     */
    public static void main(String[] args) {
        launch(args);
    }
}