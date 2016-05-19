/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embroid.it;

import ewu.embroidit.parkc.fill.A_EmbFill;
import ewu.embroidit.parkc.fill.EmbFillRadial;
import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import ewu.embroidit.parkc.fill.EmbFillLine;
import ewu.embroidit.parkc.io.FormatPES;
import ewu.embroidit.parkc.io.PECDecoder;
import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.*;
import ewu.embroidit.parkc.util.EmbCommand;
import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;


/**
 *
 * @author Trae Rawls, Christopher Park
 */
@SuppressWarnings("Convert2Lambda")
public class FXMLDocumentController implements Initializable {
    
    
    private final FileChooser fileBrowser = new FileChooser();
    private final DirectoryChooser dirBrowser = new DirectoryChooser();
    private final ObservableList listViewShapes = FXCollections.observableArrayList();
    private EmbPattern pattern = new EmbPattern();
    private List<A_EmbShapeWrapper> shapeList = new ArrayList();
    private List<EmbCommand> changesList = new ArrayList();
    private Stage primaryStage;
    private BorderPane root;
    private VBox centerContainer = new VBox();
    private StackPane canvasContainer = new StackPane();
    private double startCoordX, startCoordY, endCoordX, endCoordY;
    private int rectNameNum, ellipseNameNum, lineNameNum, changesIndex = -1;
    
    @FXML
    private Canvas stitchLayer;
    @FXML
    private Canvas shapeLayer;
    @FXML
    private Canvas previewLayer;
    @FXML
    private Label coordinateLabel;
    @FXML
    private ListView shapeListView;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField xField, yField, heightField, widthField, rotationField, endXField, endYField;
    @FXML
    private Button undoButton, redoButton;
    
    /*-----------------------------------------------------------------------*/
    //Buttons
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in line drawing mode.
     */
    @FXML
    private void lineDrawingMode()
    {
        this.mouseHandlerGeneric = this.mouseHandlerLine;
        this.setMouseHandlers();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in rectangle drawing mode.
     */
    @FXML
    private void rectDrawingMode()
    {
        this.mouseHandlerGeneric = this.mouseHandlerRect;
        this.setMouseHandlers();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets mouse handlers to operate in ellipse drawing mode.
     */
    @FXML
    private void ellipseDrawingMode()
    {
        this.mouseHandlerGeneric = this.mouseHandlerEllipse;
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
    
    EventHandler<MouseEvent> mouseHandlerGeneric;
    
    /*-----------------------------------------------------------------------*/
    
    EventHandler<MouseEvent> mouseHandlerLine = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            coordinateLabel.setText("[X, Y] : " + "[" + (mouseEvent.getX()) + ", " + (mouseEvent.getY()) + "]");
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
            {
                startCoordX = mouseEvent.getX();
                startCoordY = mouseEvent.getY();
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
            else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
            {
                previewLayer.getGraphicsContext2D().clearRect(0,0,previewLayer.getWidth(),previewLayer.getHeight());
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
                previewLayer.getGraphicsContext2D().strokeLine(startCoordX, startCoordY, endCoordX, endCoordY);
            }
            else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
            {
                Line newLine = new Line(startCoordX, startCoordY, endCoordX, endCoordY);
                A_EmbShapeWrapper lineWrapper = new EmbShapeWrapperLine(newLine);
                lineWrapper.setName("Line" + lineNameNum);
                lineWrapper.setThreadColor(colorPicker.getValue());
                lineNameNum++;
                pattern.addShape(newLine);
                pattern.addShapeWrapper(lineWrapper);
                shapeList.add(lineWrapper);
                listViewShapes.add(lineWrapper.getName());
                A_EmbFill fillStrat = new EmbFillLine();
                fillStrat.fillShape(lineWrapper);
                addChange(shapeList.size()-1, false, null);
                drawLinesFromList(stitchLayer, lineWrapper.getLineList());
                shapeLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX, endCoordY);
                previewLayer.getGraphicsContext2D().clearRect(0,0,previewLayer.getWidth(),previewLayer.getHeight());
            }
        }
    };
        
    /*-----------------------------------------------------------------------*/
    
    EventHandler<MouseEvent> mouseHandlerRect = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            coordinateLabel.setText("[X, Y] : " + "[" + (mouseEvent.getX()) + ", " + (mouseEvent.getY()) + "]");
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
            {
                startCoordX = mouseEvent.getX();
                startCoordY = mouseEvent.getY();
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
            else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
            {
                previewLayer.getGraphicsContext2D().clearRect(0,0,previewLayer.getWidth(),previewLayer.getHeight());
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
                previewRectangleOnCanvas();
            }
            else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
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
                previewLayer.getGraphicsContext2D().clearRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight());
            }
        }
    };
    
    /*-----------------------------------------------------------------------*/
    
    
    /*-----------------------------------------------------------------------*/

    
    /*-----------------------------------------------------------------------*/
    
    EventHandler<MouseEvent> mouseHandlerEllipse = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            coordinateLabel.setText("[X, Y] : " + "[" + (mouseEvent.getX()) + ", " + (mouseEvent.getY()) + "]");
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
            {
                startCoordX = mouseEvent.getX();
                startCoordY = mouseEvent.getY();
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
            else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
            {
                previewLayer.getGraphicsContext2D().clearRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight());
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
                previewEllipseOnCanvas();
            }
            else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
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
                previewLayer.getGraphicsContext2D().clearRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight());
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
        this.stitchLayer.setOnMouseEntered(this.mouseHandlerGeneric);
        this.stitchLayer.setOnMouseMoved(this.mouseHandlerGeneric);
        this.stitchLayer.setOnMousePressed(this.mouseHandlerGeneric);
        this.stitchLayer.setOnMouseDragged(this.mouseHandlerGeneric);
        this.stitchLayer.setOnMouseReleased(this.mouseHandlerGeneric);
        this.shapeLayer.setOnMouseEntered(this.mouseHandlerGeneric);
        this.shapeLayer.setOnMouseMoved(this.mouseHandlerGeneric);
        this.shapeLayer.setOnMousePressed(this.mouseHandlerGeneric);
        this.shapeLayer.setOnMouseDragged(this.mouseHandlerGeneric);
        this.shapeLayer.setOnMouseReleased(this.mouseHandlerGeneric);
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
    
    private void previewRectangleOnCanvas()
    {
        if (startCoordX <= endCoordX && startCoordY <= endCoordY)
        {
            previewLayer.getGraphicsContext2D().strokeRect(startCoordX,startCoordY,endCoordX-startCoordX,endCoordY-startCoordY);
        }
        else if (startCoordX > endCoordX)
        {
            if (startCoordY <= endCoordY)
            {
                previewLayer.getGraphicsContext2D().strokeRect(endCoordX,startCoordY,startCoordX-endCoordX,endCoordY-startCoordY);
            }
            else if (startCoordY > endCoordY)
            {
                previewLayer.getGraphicsContext2D().strokeRect(endCoordX,endCoordY,startCoordX-endCoordX,startCoordY-endCoordY);
            }
        }
        else if (startCoordY > endCoordY)
        {
            previewLayer.getGraphicsContext2D().strokeRect(startCoordX,endCoordY,endCoordX-startCoordX,startCoordY-endCoordY);
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
        rectWrapper.setName("Rectangle" + this.rectNameNum);
        rectWrapper.setThreadColor(colorPicker.getValue());
        this.rectNameNum++;
        pattern.addShape(newRect);
        pattern.addShapeWrapper(rectWrapper);
        this.shapeList.add(rectWrapper);
        listViewShapes.add(rectWrapper.getName());
        A_EmbFill fillStrat = new EmbFillTatamiRect();
        fillStrat.fillShape(rectWrapper);
        addChange(this.shapeList.size()-1, false, null);
        drawLinesFromList(stitchLayer, rectWrapper.getLineList());
        shapeLayer.getGraphicsContext2D().fillRect(xCoor,yCoor,width,height);
    }
    
     /*-----------------------------------------------------------------------*/
    
    private void previewEllipseOnCanvas()
    {
        if (startCoordX <= endCoordX && startCoordY <= endCoordY)
        {
            previewLayer.getGraphicsContext2D().strokeOval(startCoordX,startCoordY,endCoordX-startCoordX,endCoordY-startCoordY);
        }
        else if (startCoordX > endCoordX)
        {
            if (startCoordY <= endCoordY)
            {
                previewLayer.getGraphicsContext2D().strokeOval(endCoordX,startCoordY,startCoordX-endCoordX,endCoordY-startCoordY);
            }
            else if (startCoordY > endCoordY)
            {
                previewLayer.getGraphicsContext2D().strokeOval(endCoordX,endCoordY,startCoordX-endCoordX,startCoordY-endCoordY);
            }
        }
        else if (startCoordY > endCoordY)
        {
            previewLayer.getGraphicsContext2D().strokeOval(startCoordX,endCoordY,endCoordX-startCoordX,startCoordY-endCoordY);
        }          
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
        ellipseWrapper.setName("Ellipse" + this.ellipseNameNum);
        ellipseWrapper.setThreadColor(colorPicker.getValue());
        this.ellipseNameNum++;
        this.pattern.addShape(newEllipse);
        this.pattern.addShapeWrapper(ellipseWrapper);
        this.shapeList.add(ellipseWrapper);
        listViewShapes.add(ellipseWrapper.getName());
        fillStrat.fillShape(ellipseWrapper);
        addChange(this.shapeList.size()-1, false, null);
        drawLinesFromList(stitchLayer, ellipseWrapper.getLineList());
        shapeLayer.getGraphicsContext2D().fillOval(centerX-radiusX,centerY-radiusY,radiusX*2,radiusY*2);    
    }
    
    private void redrawCanvas()
    {
        stitchLayer.getGraphicsContext2D().clearRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight());
        shapeLayer.getGraphicsContext2D().clearRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight()); 
        for (int i = 0; i < this.shapeList.size(); i++)
        {
            EmbShapeDimension dims = this.shapeList.get(i).getDimensions();
            setColor(this.shapeList.get(i).getThreadColor());
            if (dims.type().equals("rectangle"))
            {
                Rectangle newRect = new Rectangle();
                newRect.setX(dims.getStartCoord().getX());
                newRect.setY(dims.getStartCoord().getY());
                newRect.setWidth(dims.getWidth());
                newRect.setHeight(dims.getHeight());
                A_EmbShapeWrapper rectWrapper = new EmbShapeWrapperTatamiFill(newRect);
                A_EmbFill fillStrat = new EmbFillTatamiRect();
                fillStrat.fillShape(rectWrapper);
                drawLinesFromList(stitchLayer, rectWrapper.getLineList());
                shapeLayer.getGraphicsContext2D().fillRect(dims.getStartCoord().getX(),dims.getStartCoord().getY(),dims.getWidth(),dims.getHeight());        
            }
            else if (dims.type().equals("ellipse"))
            {
                Ellipse newEllipse = new Ellipse();
                newEllipse.setCenterX(dims.getStartCoord().getX() + (dims.getWidth()/2));
                newEllipse.setCenterY(dims.getStartCoord().getY() + (dims.getHeight()/2));
                newEllipse.setRadiusX(dims.getWidth()/2);
                newEllipse.setRadiusY(dims.getHeight()/2);
                A_EmbShapeWrapper ellipseWrapper = new EmbShapeWrapperRadialFill(newEllipse);
                A_EmbFill fillStrat = new EmbFillRadial();
                fillStrat.fillShape(ellipseWrapper);
                drawLinesFromList(stitchLayer, ellipseWrapper.getLineList());
                shapeLayer.getGraphicsContext2D().fillOval(dims.getStartCoord().getX(),dims.getStartCoord().getY(),dims.getWidth(),dims.getHeight());                   
            }
            else if (dims.type().equals("line"))
            {
                stitchLayer.getGraphicsContext2D().strokeLine(dims.getStartCoord().getX(),dims.getStartCoord().getY(),
                                                             dims.getEndCoord().getX(),dims.getEndCoord().getY());
                shapeLayer.getGraphicsContext2D().strokeLine(dims.getStartCoord().getX(),dims.getStartCoord().getY(),
                                                             dims.getEndCoord().getX(),dims.getEndCoord().getY());
            }
        }
    }
    
    private void addChange(int index, boolean isAdding, A_EmbShapeWrapper wrapper)
    {
        if (changesIndex != 4)
        {
            if (changesIndex != -1)
            {
                int loops = changesList.size()-1;
                for (int i = changesIndex; i < loops; i++)
                {
                    this.changesList.remove(changesList.size()-1);
                }
            }
            else
            {
                this.changesList.clear();
            }
            this.changesIndex++;
        }
        else
        {
            this.changesList.remove(0);
        }
        this.undoButton.setDisable(false);
        this.redoButton.setDisable(true);
        this.changesList.add(new EmbCommand(index, isAdding, wrapper));
    }
    
    @FXML
    private void resetGUI()
    {
        this.stitchLayer.getGraphicsContext2D().clearRect(0,0,previewLayer.getWidth(),previewLayer.getHeight());
        this.shapeLayer.getGraphicsContext2D().clearRect(0,0,previewLayer.getWidth(),previewLayer.getHeight());
        this.xField.setText("");
        this.yField.setText("");
        this.heightField.setText("");
        this.widthField.setText("");
        this.pattern = new EmbPattern();
        this.shapeList.clear();
        this.listViewShapes.clear();
        this.rectNameNum = 0;
        this.ellipseNameNum = 0;
        this.lineNameNum = 0;
        this.colorPicker.setValue(Color.BLACK);
        setColor(Color.BLACK);
    }
    
    @FXML
    public void editShape()
    {      
        Shape shapeToEdit = this.pattern.getShapeList().get(shapeListView.getSelectionModel().getSelectedIndex());
        double xCoor = Double.parseDouble(xField.getText());
        double yCoor = Double.parseDouble(yField.getText());
        if (shapeToEdit.getClass().getSimpleName().equals("Line"))
        {
            double endXCoor = Double.parseDouble(endXField.getText());
            double endYCoor = Double.parseDouble(endYField.getText());
            Line newLine = new Line(xCoor, yCoor, endXCoor, endYCoor);
            A_EmbShapeWrapper lineWrapper = new EmbShapeWrapperLine(newLine);
            lineWrapper.setName(this.shapeList.get(this.shapeListView.getSelectionModel().getSelectedIndex()).getName());
            lineWrapper.setThreadColor(this.shapeList.get(this.shapeListView.getSelectionModel().getSelectedIndex()).getThreadColor());
            addChange(shapeListView.getSelectionModel().getSelectedIndex(), false, this.shapeList.get(shapeListView.getSelectionModel().getSelectedIndex()));
            this.pattern.getShapeList().set(shapeListView.getSelectionModel().getSelectedIndex(), newLine);
            this.shapeList.set(shapeListView.getSelectionModel().getSelectedIndex(), lineWrapper);
        }
        else
        {
            double height = Double.parseDouble(heightField.getText());
            double width = Double.parseDouble(widthField.getText()); 
            if (shapeToEdit.getClass().getSimpleName().equals("Rectangle"))
            {
                Rectangle newRect = new Rectangle();
                newRect.setX(xCoor);
                newRect.setY(yCoor);
                newRect.setHeight(height);
                newRect.setWidth(width);
                A_EmbShapeWrapper rectWrapper = new EmbShapeWrapperTatamiFill(newRect);
                rectWrapper.setName(this.shapeList.get(this.shapeListView.getSelectionModel().getSelectedIndex()).getName());
                rectWrapper.setThreadColor(this.shapeList.get(this.shapeListView.getSelectionModel().getSelectedIndex()).getThreadColor());
                addChange(shapeListView.getSelectionModel().getSelectedIndex(), false, this.shapeList.get(shapeListView.getSelectionModel().getSelectedIndex()));
                this.pattern.getShapeList().set(shapeListView.getSelectionModel().getSelectedIndex(), newRect);
                this.shapeList.set(shapeListView.getSelectionModel().getSelectedIndex(), rectWrapper);
            }
            else if (shapeToEdit.getClass().getSimpleName().equals("Ellipse"))
            {
                Ellipse newEllipse = new Ellipse();
                newEllipse.setCenterX(xCoor + (width/2));
                newEllipse.setCenterY(yCoor + (height/2));
                newEllipse.setRadiusX(width/2);
                newEllipse.setRadiusY(height/2);
                A_EmbShapeWrapper ellipseWrapper = new EmbShapeWrapperRadialFill(newEllipse);
                ellipseWrapper.setName(this.shapeList.get(this.shapeListView.getSelectionModel().getSelectedIndex()).getName());
                ellipseWrapper.setThreadColor(this.shapeList.get(this.shapeListView.getSelectionModel().getSelectedIndex()).getThreadColor());
                addChange(shapeListView.getSelectionModel().getSelectedIndex(), false, this.shapeList.get(shapeListView.getSelectionModel().getSelectedIndex()));
                this.pattern.getShapeList().set(shapeListView.getSelectionModel().getSelectedIndex(), newEllipse);
                this.shapeList.set(shapeListView.getSelectionModel().getSelectedIndex(), ellipseWrapper);           
            }        
        }
        redrawCanvas();
    }
    
    @FXML
    public void deleteShape()
    {
        addChange(this.shapeListView.getSelectionModel().getSelectedIndex(), true, this.shapeList.get(shapeListView.getSelectionModel().getSelectedIndex()));
        this.pattern.getShapeList().remove(this.shapeListView.getSelectionModel().getSelectedIndex());
        this.shapeList.remove(this.shapeListView.getSelectionModel().getSelectedIndex());
        this.listViewShapes.remove(this.shapeListView.getSelectionModel().getSelectedIndex());
        redrawCanvas();
    }
    
    @FXML
    public void undoChange()
    {
        EmbCommand change = this.changesList.get(changesIndex);
        if (change.getAddingFlag())
        {
            this.shapeList.add(change.getListIndex(), change.getWrapper());
            this.pattern.getShapeList().add(change.getListIndex(), change.getWrapper().getWrappedShape());
            this.listViewShapes.add(change.getListIndex(), change.getWrapper().getName());
        }
        else
        {
            if (change.getWrapper() == null)
            {
                this.shapeList.remove(change.getListIndex());
                this.pattern.getShapeList().remove(change.getListIndex());
                this.listViewShapes.remove(change.getListIndex());
            }
            else
            {
                this.shapeList.set(change.getListIndex(), change.getWrapper());
                this.pattern.getShapeList().set(change.getListIndex(), change.getWrapper().getWrappedShape());
                this.listViewShapes.set(change.getListIndex(), change.getWrapper().getName());
            }
        }
        this.changesIndex--;
        this.redoButton.setDisable(false);
        if (this.changesIndex < 0)
        {
            this.undoButton.setDisable(true);
        }
        redrawCanvas();
    }
    
    private void setColor(Color color)
    {
        this.stitchLayer.getGraphicsContext2D().setStroke(color);
        this.stitchLayer.getGraphicsContext2D().setFill(color);
        this.shapeLayer.getGraphicsContext2D().setStroke(color);
        this.shapeLayer.getGraphicsContext2D().setFill(color);       
    }
    
    /*-----------------------------------------------------------------------*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.shapeLayer.setVisible(true);
        this.stitchLayer.setVisible(false);
        this.shapeListView.setItems(listViewShapes);
        this.colorPicker.setValue(Color.BLACK);
        this.undoButton.setDisable(true);
        this.redoButton.setDisable(true);
        setColor(this.colorPicker.getValue());
        this.stitchLayer.getGraphicsContext2D().setLineWidth(2);
        this.stitchLayer.getGraphicsContext2D().setLineCap(StrokeLineCap.BUTT);
        this.shapeListView.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        xField.setText("");
                        yField.setText("");
                        endXField.setText("");
                        endYField.setText("");
                        heightField.setText("");
                        widthField.setText("");
                        if (shapeList.size() != 0)
                        {
                            A_EmbShapeWrapper selectedShape = shapeList.get(shapeListView.getSelectionModel().getSelectedIndex());
                            EmbShapeDimension dims = selectedShape.getDimensions();
                            xField.setText(""+dims.getStartCoord().getX());
                            yField.setText(""+dims.getStartCoord().getY());
                            if (selectedShape.getClass().getSimpleName().equals("EmbShapeWrapperLine"))
                            {
                                endXField.setText(""+dims.getEndCoord().getX());
                                endYField.setText(""+dims.getEndCoord().getY());
                            }
                            else
                            {
                                heightField.setText(""+dims.getHeight());
                                widthField.setText(""+dims.getWidth());
                            }
                        }
            }
        });
        this.colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                        stitchLayer.getGraphicsContext2D().setStroke(colorPicker.getValue());
                        stitchLayer.getGraphicsContext2D().setFill(colorPicker.getValue());
                        shapeLayer.getGraphicsContext2D().setStroke(colorPicker.getValue());
                        shapeLayer.getGraphicsContext2D().setFill(colorPicker.getValue());
            }
        });
    }
    
    /*-----------------------------------------------------------------------*/
    
}
