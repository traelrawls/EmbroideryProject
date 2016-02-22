/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ewu.embroidit.rawlst;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Trae
 */
public class Tester extends Application {
    Path path;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EmbroidIt");
        Button btn = new Button();
        Button btnRect = new Button();
        Button btnOval = new Button();
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        Pane root = new Pane();
        Pane options = new Pane();
        Pane canvas = new Pane();
        options.setStyle("-fx-background-color: gray");
        root.getChildren().addAll(options,canvas);
//        root.getChildren().add(btnRect);
//        root.getChildren().add(btnOval);
        
        Scene scene = new Scene(root, 300, 250);
        
        path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);
        
        canvas.getChildren().add(path);
        options.getChildren().add(btn);
        
        scene.setOnMouseClicked(mouseHandler);
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMouseEntered(mouseHandler);
        scene.setOnMouseExited(mouseHandler);
        scene.setOnMouseMoved(mouseHandler);
        scene.setOnMousePressed(mouseHandler);
        scene.setOnMouseReleased(mouseHandler);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
            {
                path.getElements().add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));
            }
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
            {
                path.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
            }        
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}

