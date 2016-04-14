package stitchfilltest;

import ewu.embroidit.parkc.fill.EmbFillRadial;
import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import ewu.embroidit.parkc.io.FileManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.shape.EmbShapeWrapperLine;
import ewu.embroidit.parkc.shape.EmbShapeWrapperRadialFill;
import ewu.embroidit.parkc.shape.EmbShapeWrapperTatamiFill;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/*-----------------------------------------------------------------------*/
/**
 * Unit Tests for Stitch filling algorithms, shape 
 * color sorting. (more to come)
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class StitchFillTest extends Application 
{
    /*-----------------------------------------------------------------------*/
    @Override
    @SuppressWarnings("UnusedAssignment")
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("StitchFillTest");
        
        /*-----------------------------------------------------------------------*/
        //TEST Rectangle Fill
        Rectangle rect = new Rectangle(32, 128, 128, 64);
        A_EmbShapeWrapper rectWrapper = new EmbShapeWrapperTatamiFill(rect);
        EmbFillTatamiRect rectFillStrat = new EmbFillTatamiRect();                
        rectFillStrat.fillShape(rectWrapper);
        //END TEST Rectangle Fill
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Ellipse Fill
        Ellipse ellipse = new Ellipse(96, 64, 64, 32);
        A_EmbShapeWrapper ellipseWrapper = new EmbShapeWrapperRadialFill(ellipse);
        EmbFillRadial ellipseFillStrat = new EmbFillRadial();
        ellipseFillStrat.fillShape(ellipseWrapper);
        //END TEST Ellipse Fill
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
        //END TEST Rendering
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Color Sorting
        A_EmbShapeWrapper wrapper;
        List<A_EmbShapeWrapper> wrapperList;
        
        int sortCount;
        wrapperList = new ArrayList<>();
        
        //Create Wrappers
        for(int i = 0; i < 3 ; i++)
        {
            wrapper = new EmbShapeWrapperTatamiFill(
                    new Rectangle(32, 128, 128, 64));
            wrapperList.add(wrapper);
            wrapper = new EmbShapeWrapperRadialFill(
                    new Ellipse(96, 64, 64, 32));
            wrapperList.add(wrapper);
            wrapper = new EmbShapeWrapperLine(new Line(0, 0, 10, 10));
            wrapperList.add(wrapper);
        }
        
        //Test 2 Colors (5/4 Ratio)
        System.err.println("2 Colors");
        for( sortCount = 0; sortCount < wrapperList.size(); sortCount++)
        {
            if (sortCount < 4)
                wrapperList.get(sortCount).setThreadColor(Color.BLUE);
            else
                wrapperList.get(sortCount).setThreadColor(Color.GREEN);
        }
        wrapperList = this.testSort(wrapperList);
        
        //TEST 3 Colors (Even Ratio)
        System.err.println("3 Colors");
        for( sortCount = 0; sortCount < wrapperList.size(); sortCount++)
        {
            if (sortCount < 3)
                wrapperList.get(sortCount).setThreadColor(Color.BLUE);
            if (sortCount >= 3 || sortCount < 6)
                wrapperList.get(sortCount).setThreadColor(Color.GREEN);
            else
                wrapperList.get(sortCount).setThreadColor(Color.RED);
        }
        wrapperList = this.testSort(wrapperList);
        
        //TEST Single Color
        System.err.println("1 Colors");
        for( sortCount = 0; sortCount < wrapperList.size(); sortCount++)
            wrapperList.get(sortCount).setThreadColor(Color.BLUE);
        
        wrapperList = this.testSort(wrapperList);
        //END TEST Color Sorting
        /*-----------------------------------------------------------------------*/
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Used to test color sorting of shapes.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     */
    private List<A_EmbShapeWrapper> testSort(List<A_EmbShapeWrapper> wrapperList)
    {
        Collections.shuffle(wrapperList);
        
        System.err.println("List Randomized:");
        for(A_EmbShapeWrapper wrap : wrapperList)
            System.err.println("Color: " + wrap.getThreadColor().toString());
        
        wrapperList = FileManager.getInstance().sortWrappersByColor(wrapperList);
        
        System.err.println("List Sorted:");
        for(A_EmbShapeWrapper wrap : wrapperList)
            System.err.println("Color: " + wrap.getThreadColor().toString());
        
        return wrapperList;
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
        gc.setLineWidth(2);
        gc.setLineCap(StrokeLineCap.SQUARE);
        
        for(Line line : lineList)
        {           
            gc.strokeLine(line.getStartX() + 0.5,
                    line.getStartY() + 0.5,
                    line.getEndX() + 0.5,
                    line.getEndY() + 0.5 );
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
