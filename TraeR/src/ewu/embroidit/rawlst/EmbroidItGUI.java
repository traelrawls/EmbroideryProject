package ewu.embroidit.rawlst;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import java.util.ArrayList;
import java.util.List;
import ewu.embroidit.parkc.io.*;
import ewu.embroidit.parkc.pattern.*;

/**
 *
 * @author Trae
 */
public class EmbroidItGUI extends Application
{
    private final ArrayList<Canvas> layerList =  new ArrayList<>();
    private int currLayerIndex;
    private BorderPane root;
    private Pane canvas;
    private Path path;
    private double coordX;
    private double coordY;
    private double startCoordX;
    private double startCoordY;
    
    public void openGUI(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage)
    {
        primaryStage.setTitle("EmbroidIt");
        this.root = new BorderPane();
        this.canvas = new Pane();
        this.layerList.add(new Canvas(500,500));
        this.currLayerIndex = 0;
        this.canvas.getChildren().add(this.layerList.get(currLayerIndex));
        VBox topContainer = createMenuBar();
        VBox botContainer = createColorBar();
        VBox leftContainer = createOptionsMenu();
        VBox rightContainer = createPropertiesBar();
        path = new Path();

        initializeCanvas();

        root.setTop(topContainer);
        root.setLeft(leftContainer);
        root.setBottom(botContainer);
        root.setRight(rightContainer);
        root.setCenter(this.canvas);
        
        Scene scene = new Scene(root,350,200);
        freeDrawingMode();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createMenuBar()
    {
        VBox newVBox = new VBox();
        MenuBar menuBar = new MenuBar();
        FlowPane layerOptions = new FlowPane();
        Button layerButton = new Button();
        Button testButton = new Button();
        layerButton.setAlignment(Pos.CENTER);
        testButton.setAlignment(Pos.CENTER);
        layerButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                addLayer();
            }
        }); 
        testButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                testLayer();
            }
        });
        layerOptions.setStyle("-fx-background-color: gray");
        layerOptions.getChildren().addAll(layerButton,testButton);
        Menu menuFile = new Menu("File");
        MenuItem menuItemOpen = new MenuItem("Open");
        MenuItem menuItemSave = new MenuItem("Save");
        menuFile.getItems().addAll(menuItemOpen,menuItemSave);
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

        Image drawImage = new Image(getClass().getResourceAsStream("graphics/DrawButton.png"));
        Image lineImage = new Image(getClass().getResourceAsStream("graphics/LineButton.png"));
        Image rectImage = new Image(getClass().getResourceAsStream("graphics/RectButton.png"));
        Image ovalImage = new Image(getClass().getResourceAsStream("graphics/OvalButton.png"));
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
        newVBox.setPrefWidth(70);
        newVBox.getChildren().addAll(drawButton,lineButton,rectButton,ovalButton);
        newVBox.setStyle("-fx-background-color: gray; -fx-border-width: 2px; -fx-border-color: black;");
        return newVBox;
    }
    
    private VBox createColorBar()
    {
        VBox newVBox = new VBox();
        ToolBar toolBar = new ToolBar();
        Button blackButton = new Button();
        Button redButton = new Button();
        Button blueButton = new Button();
        Image blackImg = new Image(getClass().getResourceAsStream("graphics/blackColor.png"));
        Image redImg = new Image(getClass().getResourceAsStream("graphics/RedColor.png"));
        Image blueImg = new Image(getClass().getResourceAsStream("graphics/BlueColor.png"));
        blackButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                layerList.get(currLayerIndex).getGraphicsContext2D().setStroke(Color.BLACK);
            }
        });
        redButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                layerList.get(currLayerIndex).getGraphicsContext2D().setStroke(Color.RED);
            }
        });
        blueButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                layerList.get(currLayerIndex).getGraphicsContext2D().setStroke(Color.BLUE);
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
        newVBox.setStyle("-fx-background-color: gray; -fx-border-width: 2px; -fx-border-color: black;");

        return newVBox;
    }
    
    private void initializeCanvas(){
        double canvasWidth = layerList.get(currLayerIndex).getGraphicsContext2D().getCanvas().getWidth();
        double canvasHeight = layerList.get(currLayerIndex).getGraphicsContext2D().getCanvas().getHeight();
         
        layerList.get(currLayerIndex).getGraphicsContext2D().setFill(Color.LIGHTGRAY);
        layerList.get(currLayerIndex).getGraphicsContext2D().setStroke(Color.BLACK);
        layerList.get(currLayerIndex).getGraphicsContext2D().setLineWidth(5);
 
        layerList.get(currLayerIndex).getGraphicsContext2D().fill();
        layerList.get(currLayerIndex).getGraphicsContext2D().strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
         
        layerList.get(currLayerIndex).getGraphicsContext2D().setFill(Color.RED);
        layerList.get(currLayerIndex).getGraphicsContext2D().setLineWidth(1);
         
    }
    
    private void freeDrawingMode()
    {
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    layerList.get(currLayerIndex).getGraphicsContext2D().beginPath();
                    layerList.get(currLayerIndex).getGraphicsContext2D().moveTo(mouseEvent.getX(), mouseEvent.getY());
                    layerList.get(currLayerIndex).getGraphicsContext2D().stroke();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    layerList.get(currLayerIndex).getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());
                    layerList.get(currLayerIndex).getGraphicsContext2D().stroke();
                }
            }  
        };
        layerList.get(currLayerIndex).setOnMousePressed(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseDragged(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseReleased(mouseHandler);
    }
    
    private void lineDrawingMode()
    {
        coordX = 0;
        coordY = 0;
        startCoordX = 0;
        startCoordY = 0;

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    coordX = mouseEvent.getX();
                    coordY = mouseEvent.getY();
                    startCoordX = mouseEvent.getX();
                    startCoordY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    coordX = mouseEvent.getX();
                    coordY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    layerList.get(currLayerIndex).getGraphicsContext2D().strokeLine(startCoordX,startCoordY,coordX,coordY);
                }
            }
        };
        layerList.get(currLayerIndex).setOnMousePressed(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseDragged(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseReleased(mouseHandler);
    }
    
    private void rectDrawingMode()
    {
        coordX = 0;
        coordY = 0;
        startCoordX = 0;
        startCoordY = 0;

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    coordX = mouseEvent.getX();
                    coordY = mouseEvent.getY();
                    startCoordX = mouseEvent.getX();
                    startCoordY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    coordX = mouseEvent.getX();
                    coordY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    if (startCoordX <= coordX && startCoordY <= coordY)
                    {
                        layerList.get(currLayerIndex).getGraphicsContext2D().strokeRect(startCoordX,startCoordY,coordX-startCoordX,coordY-startCoordY);
                    }
                    else if (startCoordX > coordX)
                    {
                        if (startCoordY <= coordY)
                        {
                            layerList.get(currLayerIndex).getGraphicsContext2D().strokeRect(coordX,startCoordY,startCoordX-coordX,coordY-startCoordY);
                        }
                        else if (startCoordY > coordY)
                        {
                            layerList.get(currLayerIndex).getGraphicsContext2D().strokeRect(coordX,coordY,startCoordX-coordX,startCoordY-coordY);
                        }
                    }
                    else if (startCoordY > coordY)
                    {
                        layerList.get(currLayerIndex).getGraphicsContext2D().strokeRect(startCoordX,coordY,coordX-startCoordX,startCoordY-coordY);
                    }
                }
            }
        };
        layerList.get(currLayerIndex).setOnMousePressed(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseDragged(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseReleased(mouseHandler);
    }
    
    private void ovalDrawingMode()
    {
        coordX = 0;
        coordY = 0;
        startCoordX = 0;
        startCoordY = 0;

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    coordX = mouseEvent.getX();
                    coordY = mouseEvent.getY();
                    startCoordX = mouseEvent.getX();
                    startCoordY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    coordX = mouseEvent.getX();
                    coordY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    if (startCoordX <= coordX && startCoordY <= coordY)
                    {
                        layerList.get(currLayerIndex).getGraphicsContext2D().strokeOval(startCoordX,startCoordY,coordX-startCoordX,coordY-startCoordY);
                    }
                    else if (startCoordX > coordX)
                    {
                        if (startCoordY <= coordY)
                        {
                            layerList.get(currLayerIndex).getGraphicsContext2D().strokeOval(coordX,startCoordY,startCoordX-coordX,coordY-startCoordY);
                        }
                        else if (startCoordY > coordY)
                        {
                            layerList.get(currLayerIndex).getGraphicsContext2D().strokeOval(coordX,coordY,startCoordX-coordX,startCoordY-coordY);
                        }
                    }
                    else if (startCoordY > coordY)
                    {
                        layerList.get(currLayerIndex).getGraphicsContext2D().strokeOval(startCoordX,coordY,coordX-startCoordX,startCoordY-coordY);
                    }
                }
            }
        };
        layerList.get(currLayerIndex).setOnMousePressed(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseDragged(mouseHandler);
        layerList.get(currLayerIndex).setOnMouseReleased(mouseHandler);
    }
    
    private void addLayer()
    {
        Canvas newLayer = new Canvas(500,500);
        this.currLayerIndex = this.layerList.size();
        this.layerList.add(newLayer);
        this.canvas.getChildren().add(this.layerList.get(this.currLayerIndex));
        initializeCanvas();
        freeDrawingMode();
        this.layerList.get(this.currLayerIndex).toFront();
    }
    
    private void testLayer()
    {
        FormatPES formatter = new FormatPES("C:/Users/Trae/Documents/NetBeansProjects/EmbroidIt/src/ewu/embroidit/rawlst/Golfcrest.pes");
        List<EmbStitch> stitchList = formatter.getPattern().getStitchList();
        stitchShapes(stitchList);
        
    }
    
    private void stitchShapes(List<EmbStitch> stitchList)
    {
        Point2D prevPoint = null;
        boolean firstStitch = true;
        for (EmbStitch stitch : stitchList)
        {
            if (!firstStitch)
            {
                layerList.get(currLayerIndex).getGraphicsContext2D().setStroke(PECDecoder.getInstance().getColorByIndex(stitch.getColorIndex()));
                layerList.get(currLayerIndex).getGraphicsContext2D().strokeLine(prevPoint.getX()+100,prevPoint.getY()+200,
                                                                                stitch.getStitchPosition().getX()+100,stitch.getStitchPosition().getY()+200);
            }
            prevPoint = stitch.getStitchPosition();
            firstStitch = false;
        }
    }
}
