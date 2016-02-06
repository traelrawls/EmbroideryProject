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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;

/**
 *
 * @author Trae
 */
public class EmbroidItGUI extends Application
{
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Path path;
    private double rectX;
    private double rectY;
    private double startRectX;
    private double startRectY;
    
    public void openGUI(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage)
    {
        primaryStage.setTitle("EmbroidIt");
        
        BorderPane root = new BorderPane();
        canvas = new Canvas(500,500);
        graphicsContext = canvas.getGraphicsContext2D();
        VBox topContainer = createMenuBar();
        VBox botContainer = createColorBar();
        VBox leftContainer = createOptionsMenu();
        path = new Path();

        initializeCanvas();

        root.setTop(topContainer);
        root.setLeft(leftContainer);
        root.setBottom(botContainer);
        root.setCenter(canvas);
        
        Scene scene = new Scene(root,350,200);
        freeDrawingMode();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createMenuBar()
    {
        VBox newVBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");        
        menuBar.getMenus().addAll(menuFile,menuEdit);
        newVBox.getChildren().add(menuBar);
        return newVBox;
    }
    
    private VBox createOptionsMenu()
    {
        VBox newVBox = new VBox();
        Button freeDrawButton = new Button();
        Button rectButton = new Button();
        Image lineImage = new Image(getClass().getResourceAsStream("graphics/LineButton.png"));
        Image rectImage = new Image(getClass().getResourceAsStream("graphics/RectButton.png"));
        freeDrawButton.setGraphic(new ImageView(lineImage));
        rectButton.setGraphic(new ImageView(rectImage));
        freeDrawButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                freeDrawingMode();
            }
        });
        rectButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                rectDrawingMode();
            }
        });
        newVBox.setPrefWidth(70);
        newVBox.getChildren().addAll(freeDrawButton,rectButton);
        newVBox.setStyle("-fx-background-color: gray");
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
                graphicsContext.setStroke(Color.BLACK);
            }
        });
        redButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                graphicsContext.setStroke(Color.RED);
            }
        });
        blueButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                graphicsContext.setStroke(Color.BLUE);
            }
        });
        blackButton.setGraphic(new ImageView(blackImg));
        redButton.setGraphic(new ImageView(redImg));
        blueButton.setGraphic(new ImageView(blueImg));
        toolBar.getItems().addAll(blackButton,redButton,blueButton);
        newVBox.getChildren().add(toolBar);
        return newVBox;
    }
    
    private void initializeCanvas(){
        double canvasWidth = graphicsContext.getCanvas().getWidth();
        double canvasHeight = graphicsContext.getCanvas().getHeight();
         
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(5);
 
        graphicsContext.fill();
        graphicsContext.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
         
        graphicsContext.setFill(Color.RED);
        graphicsContext.setLineWidth(1);
         
    }
    
    private void freeDrawingMode()
    {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
                    graphicsContext.stroke();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
                    graphicsContext.stroke();
                }
            }  
        };
        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMouseReleased(mouseHandler);
    }
    
    private void rectDrawingMode()
    {
        rectX = 0;
        rectY = 0;
        startRectX = 0;
        startRectY = 0;

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {    
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    rectX = mouseEvent.getX();
                    rectY = mouseEvent.getY();
                    startRectX = mouseEvent.getX();
                    startRectY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    rectX = mouseEvent.getX();
                    rectY = mouseEvent.getY();
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    if (startRectX <= rectX && startRectY <= rectY)
                    {
                        graphicsContext.strokeRect(startRectX,startRectY,rectX-startRectX,rectY-startRectY);
                    }
                    else if (startRectX > rectX)
                    {
                        if (startRectY <= rectY)
                        {
                            graphicsContext.strokeRect(rectX,startRectY,startRectX-rectX,rectY-startRectY);
                        }
                        else if (startRectY > rectY)
                        {
                            graphicsContext.strokeRect(rectX,rectY,startRectX-rectX,startRectY-rectY);
                        }
                    }
                    else if (startRectY > rectY)
                    {
                        graphicsContext.strokeRect(startRectX,rectY,rectX-startRectX,startRectY-rectY);
                    }
                }
            }
        };
        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMouseReleased(mouseHandler);
    }
}
