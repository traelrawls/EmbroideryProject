/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embroid.it;

import ewu.embroidit.parkc.fill.A_EmbFill;
import ewu.embroidit.parkc.fill.EmbFillRadial;
import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import ewu.embroidit.parkc.io.FormatPES;
import ewu.embroidit.parkc.io.PECDecoder;
import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.shape.EmbShapeWrapperRadialFill;
import ewu.embroidit.parkc.shape.EmbShapeWrapperTatamiFill;
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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    //Buttons
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in line drawing mode.
     */
    @FXML
    private void lineDrawingMode()
    {
        this.mouseDraggedGeneric = this.mouseDraggedHandlerLine;
        this.mouseReleasedGeneric = this.mouseReleasedHandlerLine;
        this.setMouseHandlers();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in rectangle drawing mode.
     */
    @FXML
    private void rectDrawingMode()
    {
        this.mouseDraggedGeneric = this.mouseDraggedHandlerRect;
        this.mouseReleasedGeneric = this.mouseReleasedHandlerRect;
        this.setMouseHandlers();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in ellipse drawing mode.
     */
    @FXML
    private void ellipseDrawingMode()
    {
        this.mouseDraggedGeneric = this.mouseDraggedHandlerRect;
        this.mouseReleasedGeneric = this.mouseReleasedHandlerEllipse;
        this.setMouseHandlers();
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
    //END BUTTONS
    /*-----------------------------------------------------------------------*/
    
    /*-----------------------------------------------------------------------*/
    //MENU OPERATIONS
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
    
    @FXML
    private void menuExportFile()
    {
     //Export call here
    }
    
    /*-----------------------------------------------------------------------*/
    
    @FXML
    private void menuOpenFile()
    {
        //Get pattern file
        //check for save on existing work
        //open file
    }
    
    /*-----------------------------------------------------------------------*/
    
    @FXML
    private void menuSaveFile()
    {
        //create file with file chooser
        //call file manager save
    }
    /*-----------------------------------------------------------------------*/
    //END MENU OPERATIONS
    /*-----------------------------------------------------------------------*/
    
    
    
    /*-----------------------------------------------------------------------*/
    
    
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
    
    EventHandler<MouseEvent> mouseDraggedHandlerRect = new EventHandler<MouseEvent>()
    {
        //Redundant - Same as Dragged Line and Dragged Rect
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
    
    EventHandler<MouseEvent> mouseReleasedHandlerRect = new EventHandler<MouseEvent>()
    {
       @Override
            public void handle(MouseEvent mouseEvent)
            {    
                if (startCoordX <= endCoordX && startCoordY <= endCoordY)
                {
                    drawRectangleToCanvas(startCoordX,startCoordY,endCoordX-startCoordX,endCoordY-startCoordY);
                }
                else if (startCoordX > endCoordX)
                {
                    if (startCoordY <= endCoordY)
                    {
                        drawRectangleToCanvas(endCoordX,startCoordY,startCoordX-endCoordX,endCoordY-startCoordY);
                    }
                    else if (startCoordY > endCoordY)
                    {
                        drawRectangleToCanvas(endCoordX,endCoordY,startCoordX-endCoordX,startCoordY-endCoordY);
                    }
                }
                else if (startCoordY > endCoordY)
                {
                    drawRectangleToCanvas(startCoordX,endCoordY,endCoordX-startCoordX,startCoordY-endCoordY);
                }
            }
    };
    
    /*-----------------------------------------------------------------------*/
    
    EventHandler<MouseEvent> mouseReleasedHandlerEllipse = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            if (startCoordX <= endCoordX && startCoordY <= endCoordY)
            {
                drawEllipseToCanvas(startCoordX+(endCoordX-startCoordX)/2,startCoordY+(endCoordY-startCoordY)/2,(endCoordX-startCoordX)/2,(endCoordY-startCoordY)/2);
            }
            else if (startCoordX > endCoordX)
            {
                if (startCoordY <= endCoordY)
                {
                    drawEllipseToCanvas(endCoordX+(startCoordX-endCoordX)/2,startCoordY+(endCoordY-startCoordY)/2,(startCoordX-endCoordX)/2,(endCoordY-startCoordY)/2);
                }
                else if (startCoordY > endCoordY)
                {
                    drawEllipseToCanvas(endCoordX+(startCoordX-endCoordX)/2,endCoordY+(startCoordY-endCoordY)/2,(startCoordX-endCoordX)/2,(startCoordY-endCoordY)/2);
                }
            }
            else if (startCoordY > endCoordY)
            {
                drawEllipseToCanvas(startCoordX+(endCoordX-startCoordX)/2,endCoordY+(startCoordY-endCoordY)/2,(endCoordX-startCoordX)/2,(startCoordY-endCoordY)/2);
            }  
        }    
    };
    
    /*-----------------------------------------------------------------------*/
    //END EVENT HANDLERS
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
    
    
    
    /*-----------------------------------------------------------------------*/
    
    private void drawLinesFromList(Canvas drawingCanvas, List<Line> lineList)
    {
        for (Line line : lineList)
        {
            drawingCanvas.getGraphicsContext2D().strokeLine(line.getStartX(),line.getStartY(),line.getEndX(),line.getEndY());  
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
    
    private void drawRectangleToCanvas(double xCoor, double yCoor, double width, double height)
    {
        Rectangle newRect = new Rectangle();
        newRect.setX(xCoor);
        newRect.setY(yCoor);
        newRect.setWidth(width);
        newRect.setHeight(height);
        A_EmbShapeWrapper rectWrapper = new EmbShapeWrapperTatamiFill(newRect);
        pattern.addShape(newRect);
        pattern.addShapeWrapper(rectWrapper);
        A_EmbFill fillStrat = new EmbFillTatamiRect();
        fillStrat.fillShape(rectWrapper);
        drawLinesFromList(stitchLayer, rectWrapper.getLineList());
        shapeLayer.getGraphicsContext2D().strokeRect(xCoor,yCoor,width,height);
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void drawEllipseToCanvas(double centerX, double centerY, double radiusX, double radiusY)
    {
        Ellipse newEllipse = new Ellipse();
        newEllipse.setCenterX(centerX);
        newEllipse.setCenterY(centerY);
        newEllipse.setRadiusX(radiusX);
        newEllipse.setRadiusY(radiusY);
        A_EmbShapeWrapper ellipseWrapper = new EmbShapeWrapperRadialFill(newEllipse);
        A_EmbFill fillStrat = new EmbFillRadial();
        pattern.addShape(newEllipse);
        pattern.addShapeWrapper(ellipseWrapper);
        fillStrat.fillShape(ellipseWrapper);
        drawLinesFromList(stitchLayer, ellipseWrapper.getLineList());
        shapeLayer.getGraphicsContext2D().strokeOval(centerX-radiusX,centerY-radiusY,radiusX*2,radiusY*2);    
    }
    
    /*-----------------------------------------------------------------------*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.shapeLayer.setVisible(true);
        this.stitchLayer.setVisible(false);
    }
    
    /*-----------------------------------------------------------------------*/
    
}