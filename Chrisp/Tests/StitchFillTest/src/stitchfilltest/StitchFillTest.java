/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


/**
 *
 * @author Desolis
 */
public class StitchFillTest extends Application 
{
    
    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("StitchFillTest");
        
        //create rect and it's wrapper
        Rectangle rect = new Rectangle(128, 64, 100, 64);
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
        gc.setLineWidth(1);
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }
    
    private void drawFill(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper)
    {
        List<Line> lineList = shapeWrapper.getLineList();
         
        System.err.println("Line List Size is: " + lineList.size());
        
        for(Line line : lineList)
        {
            gc.setLineWidth(1);
            gc.strokeLine(line.getStartX(),
                    line.getStartY(),
                    line.getEndX(),
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
