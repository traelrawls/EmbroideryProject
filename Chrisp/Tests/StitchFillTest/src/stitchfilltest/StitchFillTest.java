package stitchfilltest;



import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.shape.EmbShapeWrapperTatamiFill;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;


/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class StitchFillTest extends Application 
{
    
    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("StitchFillTest");
        
        //create rect and it's wrapper
        Rectangle rect = new Rectangle(128, 64, 128, 128);
        A_EmbShapeWrapper shapeWrapper = new EmbShapeWrapperTatamiFill(rect, new Point2D(0,0), 0.0, 0.0 );
        
        //create fill strategy
        EmbFillTatamiRect fillStrat = new EmbFillTatamiRect();
                
        fillStrat.fillShape(shapeWrapper);
        
        Group root = new Group();
        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        drawShape(gc, shapeWrapper);
        drawFill(gc, shapeWrapper);
        
        
        root.getChildren().add(canvas);
        
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    
    private void drawShape(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper )
    {
        Rectangle rect = (Rectangle) shapeWrapper.getWrappedShape();
       
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }
    
    private void drawFill(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper)
    {
        List<Line> lineList = shapeWrapper.getLineList();
         
        System.err.println("Line List Size is: " + lineList.size());
        
        gc.setStroke(Color.CORNFLOWERBLUE);
        gc.setLineWidth(1);
        gc.setLineCap(StrokeLineCap.SQUARE);
        
        for(Line line : lineList)
        {
            System.err.println("Line points: (" + line.getStartX() + ", " + line.getStartY() + ") " +
                                            "(" + line.getEndX() + ", " + line.getEndY() + ")" );
            
            gc.strokeLine(line.getStartX() + 0.5,
                    line.getStartY(),
                    line.getEndX() + 0.5,
                    line.getEndY());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
