package ewu.embroidit.rawlst;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import java.util.List;
import ewu.embroidit.parkc.io.*;
import ewu.embroidit.parkc.pattern.*;
import ewu.embroidit.parkc.shape.*;
import ewu.embroidit.parkc.fill.*;
import javafx.scene.shape.*;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Trae
 */
public class EmbroidItGUI extends Application
{
    private final FileChooser fileBrowser = new FileChooser();
    private final DirectoryChooser dirBrowser = new DirectoryChooser();
    private EmbPattern pattern = new EmbPattern();
    private Stage primaryStage;
    private BorderPane root;
    private VBox centerContainer = new VBox();
    private StackPane canvasContainer = new StackPane();
    private double startCoordX, startCoordY, endCoordX, endCoordY;
    private Canvas stitchLayer, shapeLayer;
    
    public void openGUI(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("EmbroidIt");
        this.root = new BorderPane();

        this.canvasContainer.setMaxHeight(500);
        this.canvasContainer.setMaxWidth(500);
        this.canvasContainer.setStyle("-fx-background-color: white");
        this.stitchLayer = new Canvas(500,500);
        this.shapeLayer = new Canvas(500,500);
        this.canvasContainer.getChildren().addAll(stitchLayer,shapeLayer);
        this.centerContainer.getChildren().add(this.canvasContainer);
        this.centerContainer.setAlignment(Pos.CENTER);
        
        VBox topContainer = createMenuBar();
        VBox botContainer = createColorBar();
        VBox leftContainer = createOptionsMenu();
        VBox rightContainer = createPropertiesBar();

        root.setTop(topContainer);
        root.setLeft(leftContainer);
        root.setBottom(botContainer);
        root.setRight(rightContainer);
        root.setCenter(this.centerContainer);
        
        Scene scene = new Scene(root,1000,700);
        freeDrawingMode();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @SuppressWarnings("Convert2Lambda")
    private VBox createMenuBar()
    {
        VBox newVBox = new VBox();
        MenuBar menuBar = new MenuBar();
        FlowPane layerOptions = new FlowPane();
        layerOptions.setAlignment(Pos.CENTER);
        
        Button stitchLayerButton = new Button("STITCH");
        Button shapeLayerButton = new Button("SHAPE");
        stitchLayerButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                stitchLayer.setVisible(true);
                shapeLayer.setVisible(false);
            }
        }); 
        shapeLayerButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                stitchLayer.setVisible(false);
                shapeLayer.setVisible(true);
            }
        });
        
        layerOptions.setStyle("-fx-background-color: #C8C8C8;");
        layerOptions.getChildren().addAll(stitchLayerButton,shapeLayerButton);
        Menu menuFile = new Menu("File");
        MenuItem menuItemImport = new MenuItem("Import");
        menuItemImport.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                File file = fileBrowser.showOpenDialog(primaryStage);
                if (file != null) {
                    openFile(file);
                }
            }
        });
        MenuItem menuItemSave = new MenuItem("Save");
        menuItemSave.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                File directory = dirBrowser.showDialog(primaryStage);
                if (directory != null) {
                    //saveFile(directory);
                }
            }
        });
        menuFile.getItems().addAll(menuItemImport,menuItemSave);
        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().addAll(menuFile,menuEdit);
        newVBox.getChildren().addAll(menuBar,layerOptions);
        return newVBox;
    }
    
    private VBox createOptionsMenu()
    {
        VBox newVBox = new VBox();
        Button drawButton = new Button();
        Button lineButton = new Button();
        Button rectButton = new Button();
        Button ovalButton = new Button();
        
        Image drawImage = new Image(getClass().getResourceAsStream("res/DrawButton.PNG"));
        Image lineImage = new Image(getClass().getResourceAsStream("res/LineButton.PNG"));
        Image rectImage = new Image(getClass().getResourceAsStream("res/RectButton.PNG"));
        Image ovalImage = new Image(getClass().getResourceAsStream("res/OvalButton.PNG"));
        
        drawButton.setGraphic(new ImageView(drawImage));
        lineButton.setGraphic(new ImageView(lineImage));
        rectButton.setGraphic(new ImageView(rectImage));
        ovalButton.setGraphic(new ImageView(ovalImage));
        drawButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                freeDrawingMode();
            }
        });
        lineButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                lineDrawingMode();
            }
        });
        rectButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                rectDrawingMode();
            }
        });
        ovalButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                ovalDrawingMode();
            }
        });
        
        drawButton.setPrefHeight(70);
        drawButton.setPrefWidth(70);
        lineButton.setPrefHeight(70);
        lineButton.setPrefWidth(70);
        rectButton.setPrefHeight(70);
        rectButton.setPrefWidth(70);
        ovalButton.setPrefHeight(70);
        ovalButton.setPrefWidth(70);
        
        newVBox.setPrefWidth(100);
        newVBox.setAlignment(Pos.TOP_CENTER);
        newVBox.getChildren().addAll(drawButton,lineButton,rectButton,ovalButton);
        newVBox.setStyle("-fx-background-color: #C8C8C8;");
        return newVBox;
    }
    
    private VBox createColorBar()
    {
        VBox newVBox = new VBox();
        ToolBar toolBar = new ToolBar();
        Button blackButton = new Button();
        Button redButton = new Button();
        Button blueButton = new Button();
        
        Image blackImg = new Image(this.getClass().getResourceAsStream("res/BlackColor.PNG"));
        Image redImg = new Image(getClass().getResourceAsStream("res/RedColor.PNG"));
        Image blueImg = new Image(getClass().getResourceAsStream("res/BlueColor.PNG"));
        
        blackButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                stitchLayer.getGraphicsContext2D().setStroke(Color.BLACK);
                shapeLayer.getGraphicsContext2D().setStroke(Color.BLACK);
            }
        });
        redButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                stitchLayer.getGraphicsContext2D().setStroke(Color.RED);
                shapeLayer.getGraphicsContext2D().setStroke(Color.RED);
            }
        });
        blueButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                stitchLayer.getGraphicsContext2D().setStroke(Color.BLUE);
                shapeLayer.getGraphicsContext2D().setStroke(Color.BLUE);
            }
        });
        blackButton.setGraphic(new ImageView(blackImg));
        redButton.setGraphic(new ImageView(redImg));
        blueButton.setGraphic(new ImageView(blueImg));
        toolBar.getItems().addAll(blackButton,redButton,blueButton);
        newVBox.getChildren().add(toolBar);
        return newVBox;
    }
    
    private VBox createPropertiesBar()
    {
        VBox newVBox = new VBox();
        TextField xProperty = new TextField();
        TextField yProperty = new TextField();
        TextField heightProperty = new TextField();
        TextField widthProperty = new TextField();
        TextField rotateProperty = new TextField();
        Label xLabel = new Label();
        Label yLabel = new Label();
        Label heightLabel = new Label();
        Label widthLabel = new Label();
        Label rotateLabel = new Label();
        xLabel.setText("X:");
        yLabel.setText("Y:");
        heightLabel.setText("Height:");
        widthLabel.setText("Width:");
        rotateLabel.setText("Rotation:");
        newVBox.getChildren().addAll(xLabel, xProperty, yLabel, yProperty,
                                     heightLabel, heightProperty, widthLabel,
                                     widthProperty, rotateLabel, rotateProperty);
        newVBox.setStyle("-fx-background-color: #C8C8C8;");

        return newVBox;
    }
    
    private Canvas initializeCanvas(Canvas newCanvas){
        double canvasWidth = newCanvas.getGraphicsContext2D().getCanvas().getWidth();
        double canvasHeight = newCanvas.getGraphicsContext2D().getCanvas().getHeight();
         
        newCanvas.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
        newCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
        newCanvas.getGraphicsContext2D().setLineWidth(5);
 
        newCanvas.getGraphicsContext2D().fill();
        newCanvas.getGraphicsContext2D().strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
         
        newCanvas.getGraphicsContext2D().setFill(Color.RED);
        newCanvas.getGraphicsContext2D().setLineWidth(1);
        return newCanvas;
         
    }
    
    private void freeDrawingMode()
    {
        EventHandler<MouseEvent> mousePressedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                stitchLayer.getGraphicsContext2D().beginPath();
                stitchLayer.getGraphicsContext2D().moveTo(mouseEvent.getX(), mouseEvent.getY());
                stitchLayer.getGraphicsContext2D().stroke();
            }
        };
        EventHandler<MouseEvent> mouseDraggedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                stitchLayer.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());
                stitchLayer.getGraphicsContext2D().stroke();
            }  
        };
        EventHandler<MouseEvent> mouseReleasedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                // Do nothing
            }  
        };
        setMouseHandlers(mousePressedHandler,mouseDraggedHandler,mouseReleasedHandler);
    }
    
    private void lineDrawingMode()
    {
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
        EventHandler<MouseEvent> mouseDraggedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
        };
        EventHandler<MouseEvent> mouseReleasedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                stitchLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX,endCoordY);
                shapeLayer.getGraphicsContext2D().strokeLine(startCoordX,startCoordY,endCoordX, endCoordY);
            }
        };
        setMouseHandlers(mousePressedHandler,mouseDraggedHandler,mouseReleasedHandler);
    }
    
    private void rectDrawingMode()
    {
        endCoordX = 0;
        endCoordY = 0;
        startCoordX = 0;
        startCoordY = 0;

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
        EventHandler<MouseEvent> mouseDraggedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();
            }
        };

        EventHandler<MouseEvent> mouseReleasedHandler = new EventHandler<MouseEvent>()
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
        setMouseHandlers(mousePressedHandler,mouseDraggedHandler,mouseReleasedHandler);
    }
    
    private void ovalDrawingMode()
    {
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
        EventHandler<MouseEvent> mouseDraggedHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                endCoordX = mouseEvent.getX();
                endCoordY = mouseEvent.getY();                
            }    
        };
        EventHandler<MouseEvent> mouseReleasedHandler = new EventHandler<MouseEvent>()
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
        setMouseHandlers(mousePressedHandler,mouseDraggedHandler,mouseReleasedHandler);
    }
    
    private void openFile(File file)
    {
        FormatPES formatter = new FormatPES(file);
        List<EmbStitch> stitchList = formatter.getPattern().getStitchList();
        stitchFromStitchList(stitchList);
    }
    
    
    private void stitchFromStitchList(List<EmbStitch> stitchList)
    {
        Point2D prevPoint = null;
        boolean firstStitch = true;
        for (EmbStitch stitch : stitchList)
        {
            if (!firstStitch)
            {
                stitchLayer.getGraphicsContext2D().setStroke(PECDecoder.getInstance().getColorByIndex(stitch.getColorIndex()));
                stitchLayer.getGraphicsContext2D().strokeLine(prevPoint.getX()+(stitchLayer.getWidth()/2),prevPoint.getY()+(stitchLayer.getHeight()/2),
                                                                                stitch.getStitchPosition().getX()+(stitchLayer.getWidth()/2),
                                                                                stitch.getStitchPosition().getY()+(stitchLayer.getHeight()/2));
            }
            prevPoint = stitch.getStitchPosition();
            firstStitch = false;
        }
    }
    
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
    
    private void drawLinesFromList(Canvas drawingCanvas, List<Line> lineList)
    {
        for (Line line : lineList)
        {
            drawingCanvas.getGraphicsContext2D().strokeLine(line.getStartX(),line.getStartY(),line.getEndX(),line.getEndY());  
        }
    }
    
    private void setMouseHandlers(EventHandler pressedHandler, EventHandler draggedHandler, EventHandler releasedHandler)
    {
        stitchLayer.setOnMousePressed(pressedHandler);
        stitchLayer.setOnMouseDragged(draggedHandler);
        stitchLayer.setOnMouseReleased(releasedHandler);
        shapeLayer.setOnMousePressed(pressedHandler);
        shapeLayer.setOnMouseDragged(draggedHandler);
        shapeLayer.setOnMouseReleased(releasedHandler);
    }
}