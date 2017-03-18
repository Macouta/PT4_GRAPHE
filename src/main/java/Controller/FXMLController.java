package Controller;

import Model.Graphe;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FXMLController extends VBox {

    protected Parent Pop_up_view;
    protected Stage popUpWindow;
    public View.Graphe grapheView;
    protected Model.Graphe grapheModel;
    private Stage primaryStage;
    private VBox vbox;


    public FXMLController() throws IOException {

        initPopup();
        grapheView = new View.Graphe();
    }

    public void setGrapheView(View.Graphe g) {
        grapheView = g;
    }

    public void initPopup() {
        popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setResizable(false);
    }

    protected void afficherPopup() {
        if (grapheModel != null) {
            popUpWindow.show();
        }
    }

    @FXML private StackPane subPane;

    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem proprieteSommet = new MenuItem("Tableau de propriétés du sommet");
    private MenuItem supprimerSommet = new MenuItem("Supprimer le sommet");
    private MenuItem etiquetteSommet = new MenuItem("Etiquette du sommet");
    private MenuItem copierEtiquetteSommet = new MenuItem("Copier l'étiquette du sommet");
    private MenuItem collerEtquetteSommet = new MenuItem("Coller l'étiquette au sommet");

    public void clicPane(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.SECONDARY){
            contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(proprieteSommet,etiquetteSommet,supprimerSommet,copierEtiquetteSommet,collerEtquetteSommet);

            proprieteSommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProprietesSommetTab.fxml"));
                        Parent root1 = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            supprimerSommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    clickSupprimer();
                }
            });

            etiquetteSommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EtiquetteSommet.fxml"));
                        Parent root1 = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            copierEtiquetteSommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TODO Il faut pouvoir récupérer un sommet
                }
            });

            collerEtquetteSommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TODO Il faut pouvoir récupérer un sommet
                }
            });

            contextMenu.show(subPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        } else {
            //Actions autre que clic droit

            contextMenu.hide();
        }
    }


    /**
     * Fonction ouvrant une fenetre (FileChooser) permettant l'importation d'un fichier dans le logiciel.
     */
    @FXML public void clickFichierImporter() {
        FileChooser fileChooser = createFileChooser("Importer");
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            grapheModel = new Graphe(file.getAbsolutePath(), 600);
            grapheView.chargerGraphe(grapheModel);
        }

    }

    /**
     * Fonction ouvrant une fenetre (FileChooser) permettant l'exportation d'un fichier dans le logiciel.
     */
    @FXML public void clickFichierExporter() {
        if (grapheModel != null) {
            FileChooser fileChooser = createFileChooser("Exporter");
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                grapheModel.sauvegarderGraphe(file.getAbsolutePath());
            }
        }
    }

    private FileChooser createFileChooser(String exporter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(exporter);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GV", "*.gv"),
                new FileChooser.ExtensionFilter("GRAPHML", "*.graphml")
        );
        return fileChooser;
    }

    /**
     * Fonction ouvrant une fenetre (FileChooser) permettant l'enregistrement d'un fichier dans le logiciel.
     */
    @FXML public void clickFichierEnregistrer() {
        grapheModel.sauvegarderGraphe(null);
    }

    /**
     * Fonction permettant d'appliquer une distribution aléatoire des positions des sommets du graphe
     */
    @FXML public void clickRepresentationAleatoire() {
        grapheModel.setAlgorithmeRepresentation('a',600);
    }

    /**
     * Fonction permettant d'appliquer une distribution circulaire des positions des sommets du graphe
     */
    @FXML public void clickRepresentationCirculaire() {
        grapheModel.setAlgorithmeRepresentation('c',600);
    }

    /**
     * Fonction permettant d'appliquer une distribution aléatoire des positions des sommets du graphe
     */
    @FXML
    public void clickRepresentationForces() {
        grapheModel.setAlgorithmeRepresentation('f',600);
    }

    /**
     * Fonction permettant d'indicer aléatoirement tous les sommets du graphe
     */
    @FXML
    public void clickIndicageAleatoire(){
        if (grapheModel != null) {
            this.grapheModel.setIndiceAleatoire();
        }
    }

    @FXML
    public void clickIndicageDegré() {
        if (grapheModel != null) {
            this.grapheModel.setIndiceDegre();
        }
    }

    @FXML
    public void clickToggleRepresentation(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModeleRepresentation.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Choix du modèle de la représentation");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void clickAjouterSommet() throws IOException {
        new AjoutSommetController(grapheModel);
    }

    @FXML
    public void clickAjouterArete() throws IOException {
        new AjoutAreteController(grapheModel);
    }
    @FXML
    public void clickTailleGraphe(MouseEvent mouseEvent) throws IOException {
        new TailleGrapheController(grapheModel);
    }

    /**
     *
     * @param event
     */
    @FXML public void clickCouleurGraphe(MouseEvent event) throws IOException {
        CouleurGrapheController c = new CouleurGrapheController(grapheModel);
    }

    protected void fermerPopup(Button button) {
        popUpWindow.close();
    }

    @FXML public void clickSupprimer() {
        if (grapheModel != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SupprSommer.fxml"));
                popUpWindow.setTitle("Confirmer suppression");
                popUpWindow.setScene(new Scene((Parent) fxmlLoader.load()));
                popUpWindow.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fonction traitant le ToggleButton permettant l'affichage ou non des sommets du graphe.
     */
    @FXML public void clickToggleSommet(ActionEvent event) {
        if(((ToggleButton)event.getSource()).isSelected()) {
            //TODO affichage Sommet
        } else {
            //TODO suppression de l'affichage sommet
        }
    }
    /**
     * Fonction traitant le ToggleButton permettant l'affichage ou non des aretes du graphe.
     */
    @FXML public void clickToggleArete(ActionEvent event) {
        if(((ToggleButton)event.getSource()).isSelected()) {
            //TODO affichage Arete
        } else {
            //TODO suppression de l'affichage arete
        }
    }
    /**
     * Fonction traitant le ToggleButton permettant l'affichage ou non des etiquettes du graphe.
     */
    @FXML public void clickToggleEtiquette(ActionEvent event) {
        if(((ToggleButton)event.getSource()).isSelected()) {
            //TODO affichage etiquette
        } else {
            //TODO suppression de l'affichage etiquette
        }
    }
    /**
     *
     */
    @FXML public void clickZoomMinus() {
        //TODO zoom arriere
    }

    /**
     *
     */
    @FXML public void clickZoomPlus() {
        //TODO zoom plus
    }
    /**
     *
     */
    @FXML private ColorPicker couleurFond;
    @FXML public void clickCouleurFond(MouseEvent event) throws IOException {
        couleurFond.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = couleurFond.getValue();
                vbox.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));

            }
        });
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
}
