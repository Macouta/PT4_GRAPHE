package Controller;


import Model.Graphe;
import Model.Sommet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class AffichageProprieteSommet extends FXMLController {

    private Sommet sommetSelectionne;
    @FXML
    private Label idSommetSelectionne, tagSommetSelectionne, tailleSommetSelectionne, positionSommetSelectionne
            , couleurSommetSelectionne, formeSommetSelectionne;
    @FXML
    private Circle visualisationCouleur;
    public AffichageProprieteSommet(Graphe graphe, Sommet sommetSelectionne) throws IOException {
        super();
        this.grapheModel = graphe;
        this.sommetSelectionne = sommetSelectionne;
        if (graphe != null) {
            FXMLLoader fxmlLoaderPopUp = new FXMLLoader(getClass().getResource("/fxml/ProprietesSommetTab.fxml"));
            popUpWindow.setTitle("Propriété du sommet d'id " + sommetSelectionne.getId());
            if (popUpWindow.getScene() == null) {
                fxmlLoaderPopUp.setRoot(this);
                fxmlLoaderPopUp.setController(this);
                popUpWindow.setScene(new Scene((Parent) fxmlLoaderPopUp.load()));
            }

            visualisationCouleur.setFill(sommetSelectionne.getCouleur());

            idSommetSelectionne.setText(Integer.toString(sommetSelectionne.getId()));
            tagSommetSelectionne.setText(sommetSelectionne.getTag());
            tailleSommetSelectionne.setText("(" + sommetSelectionne.getTaille().width + ", " +
            sommetSelectionne.getTaille().height + ")");
            positionSommetSelectionne.setText("(" + sommetSelectionne.getX() + ", " +
                    sommetSelectionne.getY() + ")");
            couleurSommetSelectionne.setText(sommetSelectionne.getCouleur().toString());
            formeSommetSelectionne.setText(sommetSelectionne.getForme().toString());


            popUpWindow.show();
        }
    }
}
