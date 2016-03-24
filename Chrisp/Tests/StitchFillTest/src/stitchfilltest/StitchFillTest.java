package stitchfilltest;

import ewu.embroidit.parkc.fill.EmbFillRadial;
import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.shape.EmbShapeWrapperRadialFill;
import ewu.embroidit.parkc.shape.EmbShapeWrapperTatamiFill;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class StitchFillTest extends Application 
{
    /*-----------------------------------------------------------------------*/
    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("StitchFillTest");
        
        /*-----------------------------------------------------------------------*/
        //TEST Rectangle Fill
        Rectangle rect = new Rectangle(32, 128, 128, 64);
        A_EmbShapeWrapper rectWrapper = new EmbShapeWrapperTatamiFill(rect, new Point2D(0,0), 0.0, 0.0 );
        EmbFillTatamiRect rectFillStrat = new EmbFillTatamiRect();                
        rectFillStrat.fillShape(rectWrapper);
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Ellipse Fill
        Ellipse ellipse = new Ellipse(96, 64, 64, 32);
        A_EmbShapeWrapper ellipseWrapper = new EmbShapeWrapperRadialFill(ellipse, new Point2D(0,0) );
        EmbFillRadial ellipseFillStrat = new EmbFillRadial();
        ellipseFillStrat.fillShape(ellipseWrapper);
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Rendering
        Group root = new Group();
        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        drawRect(gc, rectWrapper);
        drawFill(gc, rectWrapper, Color.CORNFLOWERBLUE);
        
        drawEllipse(gc, ellipseWrapper);
        drawFill(gc, ellipseWrapper, Color.CHOCOLATE);
        
        root.getChildren().add(canvas);
        /*-----------------------------------------------------------------------*/
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void drawEllipse(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper)
    {
        Ellipse ellipse = (Ellipse) shapeWrapper.getWrappedShape();
        Bounds bounds = ellipse.getBoundsInLocal();
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(
                bounds.getMinX(), 
                bounds.getMinY(), 
                2 * ellipse.getRadiusX(),
                2 * ellipse.getRadiusY());
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void drawRect(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper )
    {
        Rectangle rect = (Rectangle) shapeWrapper.getWrappedShape();
       
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(
                rect.getX(), 
                rect.getY(), 
                rect.getWidth(), 
                rect.getHeight());
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void drawFill(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper, Color color)
    {
        List<Line> lineList = shapeWrapper.getLineList();
         
        System.err.println("Line List Size is: " + lineList.size());
        
        gc.setStroke(color);
        gc.setLineWidth(3);
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
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /*-----------------------------------------------------------------------*/
}
