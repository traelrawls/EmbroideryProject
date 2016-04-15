/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embroid.it;

import ewu.embroidit.parkc.io.FormatPES;
import ewu.embroidit.parkc.io.PECDecoder;
import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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
 * @author Trae Rawls, Christopher Park
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
    
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in line drawing mode.
     */
    @FXML
    private void lineDrawingMode()
    {
        this.mouseDraggedGeneric = this.mouseDraggedHandlerLine;
        this.mouseReleasedGeneric = this.mouseReleasedHandlerLine;
        setMouseHandlers();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Handles operations when stitch layer is activated.
     */
    @FXML
    private void buttonStitchLayerPressed()
    {
        this.stitchLayer.setVisible(true);
        this.shapeLayer.setVisible(false);
        
        //DEBUG: REMOVE AFTER TESTING
        System.err.println("Stitch Layer Pressed");
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Handles operations when shape layer is activated.
     */
    @FXML
    private void buttonShapeLayerPressed()
    {
        this.stitchLayer.setVisible(false);
        this.shapeLayer.setVisible(true);
        
        //DEBUG: REMOVE AFTER TESTING
        System.err.println("Shape Layer");
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Imports a pattern from PES file, and switches to the stitch layer.
     * (Stitch Layer only) 
     */
    @FXML
    private void menuImportFile()
    {
        File file = fileBrowser.showOpenDialog(primaryStage);
        
        if(file != null)
        {
            FormatPES formatter = new FormatPES(file);
            List<EmbStitch> stitchList = formatter.getPattern().getStitchList();
            
            this.stitchFromStitchList(stitchList);
            this.buttonStitchLayerPressed();
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Helper method for drawing stitches from a given list.
     * @param stitchList 
     */
    private void stitchFromStitchList(List<EmbStitch> stitchList)
    {
        Point2D prevPoint = null;
        boolean firstStitch = true;
        for (EmbStitch stitch : stitchList)
        {
            if (!firstStitch)
            {
                //this.stitchLayer.getGraphicsContext2D().setStroke(pattern.getThread(stitch.getColorIndex()));
                this.stitchLayer.getGraphicsContext2D().setStroke(PECDecoder.getInstance().getColorByIndex(stitch.getColorIndex()));
                this.stitchLayer.getGraphicsContext2D().strokeLine(prevPoint.getX()+(stitchLayer.getWidth()/2),prevPoint.getY()+(stitchLayer.getHeight()/2),
                                                                                stitch.getStitchPosition().getX()+(stitchLayer.getWidth()/2),
                                                                                stitch.getStitchPosition().getY()+(stitchLayer.getHeight()/2));
            }
            prevPoint = stitch.getStitchPosition();
            firstStitch = false;
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Helper method for setting mouse handlers.
     */
    private void setMouseHandlers()
    {
        this.stitchLayer.setOnMousePressed(this.mousePressedHandler);
        this.stitchLayer.setOnMouseDragged(this.mouseDraggedGeneric);
        this.stitchLayer.setOnMouseReleased(this.mouseReleasedGeneric);
        this.shapeLayer.setOnMousePressed(this.mousePressedHandler);
        this.shapeLayer.setOnMouseDragged(this.mouseDraggedGeneric);
        this.shapeLayer.setOnMouseReleased(this.mouseReleasedGeneric);
    }
 
    /*-----------------------------------------------------------------------*/
    //EVENT HANDLERS
    /*-----------------------------------------------------------------------*/
    
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
    
    /*-----------------------------------------------------------------------*/
    
    EventHandler<MouseEvent> mouseDraggedHandlerLine = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {    
            endCoordX = mouseEvent.getX();
            endCoordY = mouseEvent.getY();
        }
    };
        
    /*-----------------------------------------------------------------------*/
    
    EventHandler<MouseEvent> mouseReleasedHandlerLine = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {    
            stitchLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX,endCoordY);
            shapeLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX, endCoordY);
        }
    };
    
    /*-----------------------------------------------------------------------*/
    //END EVENT HANDLERS
    /*-----------------------------------------------------------------------*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.shapeLayer.getGraphicsContext2D().strokeLine(0, 0, 20, 20);    
    }
    
    /*-----------------------------------------------------------------------*/
    
}
