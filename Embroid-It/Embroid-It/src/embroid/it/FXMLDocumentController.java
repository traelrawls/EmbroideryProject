/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embroid.it;

import ewu.embroidit.parkc.pattern.EmbPattern;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Trae Rawls
 */
@SuppressWarnings("Convert2Lambda")
public class FXMLDocumentController implements Initializable {
    
    
    private final FileChooser fileBrowser = new FileChooser();
    private final DirectoryChooser dirBrowser = new DirectoryChooser();
    private EmbPattern pattern = new EmbPattern();
    private Stage primaryStage;
    private BorderPane root;
    private VBox centerContainer = new VBox();
    private StackPane canvasContainer = new StackPane();
    private double startCoordX, startCoordY, endCoordX, endCoordY;
    
    @FXML
    private Canvas stitchLayer;
    @FXML
    private Canvas shapeLayer;
    @FXML
    private Label label;
    
    @FXML
    private void lineDrawingMode()
    {
        this.mouseDraggedGeneric = this.mouseDraggedHandlerLine;
        this.mouseReleasedGeneric = this.mouseReleasedHandlerLine;
        setMouseHandlers();
    }
    
    
    private void setMouseHandlers()
    {
        this.stitchLayer.setOnMousePressed(this.mousePressedHandler);
        this.stitchLayer.setOnMouseDragged(this.mouseDraggedGeneric);
        this.stitchLayer.setOnMouseReleased(this.mouseReleasedGeneric);
        this.shapeLayer.setOnMousePressed(this.mousePressedHandler);
        this.shapeLayer.setOnMouseDragged(this.mouseDraggedGeneric);
        this.shapeLayer.setOnMouseReleased(this.mouseReleasedGeneric);
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.shapeLayer.getGraphicsContext2D().strokeLine(0, 0, 20, 20);
        
    }
    
    EventHandler<MouseEvent> mouseDraggedGeneric;
    EventHandler<MouseEvent> mouseReleasedGeneric;
    EventHandler<MouseEvent> mousePressedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                startCoordX = mouseEvent.getX();
                startCoordY = mouseEvent.getY();
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
        };
        EventHandler<MouseEvent> mouseDraggedHandlerLine = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
        };
        
        EventHandler<MouseEvent> mouseReleasedHandlerLine = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                stitchLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX,endCoordY);
                shapeLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX, endCoordY);
            }
        };
    
}
