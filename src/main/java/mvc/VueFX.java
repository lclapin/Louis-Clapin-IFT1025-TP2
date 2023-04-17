package mvc;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Application.launch;

/**
 * La classe VueFx permet de creer un fichier jar en appelant une classe Vue qui herite de la classe Application.
 */
public class VueFX {
    private static Vue vue = new Vue();
    private static javafx.stage.Stage Stage;

    /**
     * La methode main permet de lancer une vue qui définit une interface graphique.
     * @param args represente la chaine d'arguments passee a la methode.
     */
    public static void main(String[] args) {
        vue.start(Stage);
        vue.main(args);
    }

}