package Controller;

import Model.Forme_Sommet;
import Model.Graphe;
import Model.Sommet;
import com.sun.glass.ui.Size;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;

/**
 * Created by audreylentilhac on 18/03/2017.
 */
public class ModifierSommet extends FXMLController {

    private SommetController sommetControl;
    private ObservableList<Forme_Sommet> formes = FXCollections.observableArrayList(Forme_Sommet.values());

    @FXML private ChoiceBox formeSommet;
    @FXML private TextField PosSommet, tailleSommet, indiceSommet;
    @FXML private ColorPicker couleurSommet;
    @FXML private Label erreurSommet;

    public ModifierSommet(Graphe graphe, Sommet sommetSelectionneModel) throws IOException {
        super();
        sommetControl= new SommetController();
        this.grapheModel = graphe;
        this.sommetSelectionneModel = sommetSelectionneModel;
        if (graphe != null) {
            FXMLLoader fxmlLoaderPopUp = new FXMLLoader(getClass().getResource("/fxml/ModifierSommet.fxml"));
            popUpWindow.setTitle("Modifier le sommet " + sommetSelectionneModel.getId());
            if (popUpWindow.getScene() == null) {
                fxmlLoaderPopUp.setRoot(this);
                fxmlLoaderPopUp.setController(this);
                popUpWindow.setScene(new Scene((Parent) fxmlLoaderPopUp.load()));
                formeSommet.setValue(sommetSelectionneModel.getForme());
                formeSommet.setItems(formes);
            }
            popUpWindow.show();
        }
    }

    public void ModifierSommetSelectionne(){

        Size tailleSommet = sommetControl.déterminationTailleRentrerParUtilisateur(sommetSelectionneModel.getForme().toString(), this.tailleSommet, this.erreurSommet);
        Pair<Float, Float> coordSommet = null;
        int indiceSommet = 0;

        if (tailleSommet != null) {
            coordSommet = sommetControl.déterminationPositionRentrerParUtilisateur(this.PosSommet, this.erreurSommet);
            indiceSommet = sommetControl.déterminationIndiceRentrerParUtilisateur(this.indiceSommet, this.erreurSommet);
        }
        if (tailleSommet != null && coordSommet != null && indiceSommet != -1
                && coordSommet.getKey() < popUpWindow.getMaxWidth() && coordSommet.getValue() < popUpWindow.getMaxHeight()) {
            sommetSelectionneModel.setTaille(tailleSommet);
            sommetSelectionneModel.setIndice(indiceSommet);
            sommetSelectionneModel.setX(coordSommet.getKey());
            sommetSelectionneModel.setY(coordSommet.getValue());
            sommetSelectionneModel.setCouleur(couleurSommet.getValue());
            sommetSelectionneModel.setForme(formeSommet.getValue().toString());
            popUpWindow.close();
        }
        else {
            erreurSommet.setText("Valeurs invalides");
        }
    }

    public void fermerPopUpModifierSommet(){
        popUpWindow.close();
    }

}
