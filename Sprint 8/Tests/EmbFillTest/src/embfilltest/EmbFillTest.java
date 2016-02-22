package embfilltest;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbFillTest extends Application 
{
   @Override 
   @SuppressWarnings("Convert2Lambda")
   public void start(Stage primaryStage)
   {
       primaryStage.setTitle("Stitch Fill Tester");
       
       
       //Grid
       GridPane grid = new GridPane();
       grid.setAlignment(Pos.CENTER);
       grid.setHgap(10);
       grid.setVgap(10);
       grid.setPadding(new Insets(25, 25, 25, 25));
       
       grid.setGridLinesVisible(true);
       
       Canvas shapeCanvas = new Canvas(275.00, 275.00);
       GraphicsContext context = shapeCanvas.getGraphicsContext2D();
       
       drawSquare(context);
       
       //grid.getChildren().add(shapeCanvas);
       
       grid.add(shapeCanvas, 1, 1);
       
       
       
       Scene scene = new Scene(grid, 400, 300);
       primaryStage.setScene(scene);
       primaryStage.show();
   }

    /*-----------------------------------------------------------------------*/
   
   public void drawSquare(GraphicsContext context)
   {
       
       context.setFill(Color.GREEN);
       context.setStroke(Color.BLACK);
       
       context.fillRect(64, 64, 32, 32);
       context.strokeRect(0, 0, 32, 32);
   }
   
    /*-----------------------------------------------------------------------*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    /*-----------------------------------------------------------------------*/
    
}
