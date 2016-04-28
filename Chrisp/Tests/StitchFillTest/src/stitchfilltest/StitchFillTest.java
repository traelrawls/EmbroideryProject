package stitchfilltest;

import ewu.embroidit.parkc.fill.EmbFillLine;
import ewu.embroidit.parkc.fill.EmbFillRadial;
import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import ewu.embroidit.parkc.io.FileManager;
import ewu.embroidit.parkc.pattern.EmbStitch;
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
        //Test Line Fill
        Line line = new Line(170, 170, 202, 202);
        A_EmbShapeWrapper lineWrapper = new EmbShapeWrapperLine(line);
        EmbFillLine lineFillStrat = new EmbFillLine();
        lineFillStrat.fillShape(lineWrapper);
        //END TEST Line Fill
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Shape Rendering
        
        Group root = new Group();
        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        //Rectangle
        drawRect(gc, rectWrapper);
        drawFill(gc, rectWrapper, Color.CORNFLOWERBLUE);
        
        //Ellipse
        drawEllipse(gc, ellipseWrapper);
        drawFill(gc, ellipseWrapper, Color.CHOCOLATE);
        
        //Line
        //drawLine(gc, lineWrapper);
        drawFill(gc, lineWrapper, Color.BROWN);
        
        root.getChildren().add(canvas);
        
        //END TEST Rendering
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Color Sorting
        System.err.println();
        System.err.println("---COLOR SORT TESTS---");
        
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
        
        System.err.println("---END COLOR SORT TESTS---");
        //END TEST Color Sorting
        /*-----------------------------------------------------------------------*/
        
        /*-----------------------------------------------------------------------*/
        //TEST Stitch Breakdown Values
        //Description: Prints out the stitch lists created by each shape's stitch
        //fill strategy to check for expected coordinate progression.
        
        /*
        System.err.println();
        System.err.println("---STITCH BREAKDOWN TESTS---");
        System.err.println("Rectangle Stitch List:");
        printStitchList(rectWrapper);
        System.err.println();
        System.err.println("Ellipse Stitch List:");
        printStitchList(ellipseWrapper);
        System.err.println();
        System.err.println("Line Stitch List:");
        printStitchList(lineWrapper);
        System.err.println("---END STITCH BREAKDOWN TESTS---");
        */
        
        //END Stitch Breakdown Values
        
        /*-----------------------------------------------------------------------*/
        //TEST Encoding Values
        
        System.err.println();
        System.err.println("---STITCH ENCODE TEST---");
        List<A_EmbShapeWrapper> encodeWrapperList = new ArrayList<>();
        
        Rectangle encodeRect = new Rectangle(0, 0, 32, 32);
        A_EmbShapeWrapper encodeRectWrapper = new EmbShapeWrapperTatamiFill(encodeRect);
        encodeRectWrapper.setThreadColor(Color.BLUE);
        EmbFillTatamiRect encodeRectFillStrat = new EmbFillTatamiRect();                
        encodeRectFillStrat.fillShape(encodeRectWrapper);
        
        encodeWrapperList.add(encodeRectWrapper);
        
        Ellipse encodeEllipse = new Ellipse(0, 0, 32, 32);
        A_EmbShapeWrapper encodeEllipseWrapper = new EmbShapeWrapperRadialFill(encodeEllipse);
        encodeEllipseWrapper.setThreadColor(Color.BLACK);
        EmbFillRadial encodeEllipseFillStrat = new EmbFillRadial();
        encodeEllipseFillStrat.fillShape(encodeEllipseWrapper);
        
        encodeWrapperList.add(encodeEllipseWrapper);
        
        
        
        System.err.println();
        System.err.println("Wrapper Count: " + encodeWrapperList.size());
        
        //encode the wrapperList
        FileManager.getInstance().assignStitchCodes(encodeWrapperList);
        this.printStitchFlags(encodeWrapperList);
        
        
        System.err.println("---END STITCH ENCODE TEST---");
        //END TEST Encoding Values
        /*-----------------------------------------------------------------------*/
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    
    /*-----------------------------------------------------------------------*/
    
    private void printStitchFlags(List<A_EmbShapeWrapper> wrapperList)
    {
        //print flag values
        List<EmbStitch> encodeStitchList;
        for(A_EmbShapeWrapper wrap : wrapperList)
        {
            encodeStitchList = wrap.getStitchList();
            System.err.println("---Shape Start---");
            for(EmbStitch stitch : encodeStitchList)
                System.err.println("Stitch Code: " + stitch.getFlag());
            
            System.err.println("---Shape End---");
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Prints the shape wrappers stitch list to System.err.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    private void printStitchList(A_EmbShapeWrapper shapeWrapper)
    {
        
        int count = 1;
        List<EmbStitch> stitchList = shapeWrapper.getStitchList();
        
        for(EmbStitch stitch : stitchList)
        {
            System.err.println("Stitch #: " + count);
            System.err.println("Coord: X: " + stitch.getStitchPosition().getX());
            System.err.println("Coord: Y: " + stitch.getStitchPosition().getY());
            count++;
        }
        
        System.err.println("Total Stitch Count: " + (count - 1) +".");
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
    
    /**
     * Draws the given ellipse.
     * @param gc GraphicsContext
     * @param shapeWrapper A_EmbShapeWrapper
     */
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
    
    /**
     * Draw the given rectangle.
     * @param gc GraphicsContext
     * @param shapeWrapper A_EmbShapeWrapper
     */
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
    
    /**
     * Draws the given line.
     * @param gc GraphicsContext
     * @param shapeWrapper A_EmbShapeWrapper
     */
    private void drawLine(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper)
    {
        Line line = (Line) shapeWrapper.getWrappedShape();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(
                line.getStartX(),
                line.getStartY(),
                line.getEndX(),
                line.getEndY());
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Draws the fill line segments from the given shape wrapper.
     * @param gc GraphicsContext
     * @param shapeWrapper A_EmbShapeWrapper
     * @param color Color
     */
    private void drawFill(GraphicsContext gc, A_EmbShapeWrapper shapeWrapper, Color color)
    {
        List<Line> lineList = shapeWrapper.getLineList();
         
        System.err.println("Line List Size is: " + lineList.size());
        
        gc.setStroke(color);
        gc.setLineWidth(2);
        gc.setLineCap(StrokeLineCap.BUTT);
        
        for(Line line : lineList)
        {           
            gc.strokeLine(line.getStartX()+ 0.5,
                    line.getStartY(),// + 0.5,
                    line.getEndX() + 0.5,
                    line.getEndY());// + 0.5 );
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
