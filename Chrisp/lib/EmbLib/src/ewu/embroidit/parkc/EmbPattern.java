package ewu.embroidit.parkc;

import ewu.embroidit.parkc.io.StitchCode;
import java.util.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import com.sun.javafx.geom.Path2D;
import javafx.scene.shape.Circle;

/*-----------------------------------------------------------------------*/
/**
 * Represents an embroidery pattern. A pattern contains a combination of
 * lines and primitive shapes created by connecting stitch locations with
 * colored threads inside of an embroidery hoop.
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbPattern
{
    /*-----------------------------------------------------------------------*/
    
    //Class member variables
    private int colorIndex;                        //Current color index
    private double lastX;                          //Last x position
    private double lastY;                          //Last y position
    private Point2D homePoint;                     //Pattern starting point
    private EmbHoop hoop;                          //Embroidery hoop
    private List<EmbStitch> stitchList;            //List of stitches
    private List<EmbThread> threadList;            //List of threads
    private List<Rectangle> rectList;              //List of rectangles
    private List<Line> lineList;                   //List of lines
    private List<Circle> circleList;              //List of circles
    private List<Ellipse> ellipseList;             //List of ellipses
    private List<Path2D> pathList;                 //List of paths
    private List<Point2D> pointList;               //List of points
    private List<Polygon> polygonList;             //List of polygons
    private List<Polyline> polylineList;           //List of polylines
    
    //Object lists remaining
    //--Arc (not sure if this should be Arc or Cubic Curve)
    //--Spline (Uh....Find out what this is)
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a default empty pattern.
     */
    public EmbPattern()
    {
        this.colorIndex = 0;
        this.lastX = 0.0;
        this.lastY = 0.0;
        this.homePoint = new Point2D(lastX, lastY);
        this.stitchList = new ArrayList<>();
        this.threadList = new ArrayList<>();
        this.rectList = new ArrayList<>();
        this.lineList = new ArrayList<>();              
        this.circleList = new ArrayList<>();             
        this.ellipseList = new ArrayList<>();
        this.pathList = new ArrayList<>();
        this.pointList = new ArrayList<>();
        this.polygonList = new ArrayList<>();
        this.polylineList = new ArrayList<>();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Adds a stitch to the stitch list at the absolute position (x, y). 
     * 
     * @param x double
     * @param y double
     * @param flags int
     * @param isAutoColorIndex  int
     */
    public void addStitchAbs(double x, double y, int flags, int isAutoColorIndex)
    {
        
        if(flags == StitchCode.END)
        {
            if(this.stitchList.isEmpty())
                return;
            
            //this.fixColorCount();
        }
        
        if(flags == StitchCode.STOP)
        {
            if(this.stitchList.isEmpty())
                return;
            
            if(isAutoColorIndex > 0)    //Maybe change this to == 1?
                this.colorIndex++;    
        }
                
        if(this.stitchList.isEmpty())
        {
            Point2D coord = new Point2D(this.homePoint.getX(), this.homePoint.getY());
            EmbStitch stitch = new EmbStitch(coord, this.colorIndex, StitchCode.JUMP);
            this.stitchList.add(stitch);
        }
        
        this.lastX = x;
        this.lastY = y;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Adds a stitch to the stitch list relative to the previous stitch.
     * 
     * @param dx double
     * @param dy double
     * @param flags int
     * @param isAutocolorIndex int
     */
    public void addStitchRel(double dx, double dy, int flags, int isAutocolorIndex)
    {
        double x = 0;
        double y = 0;
        
        if(this.stitchList.isEmpty())
            this.homePoint.add(dx, dy);
        else
        {
            x = lastX + dx;
            y = lastY + dy;
        }
        
        this.addStitchAbs(x, y, flags, isAutocolorIndex);
    }
    
    /*-----------------------------------------------------------------------*/
    
    //Possible Methods (Should be renamed to better reflect what java uses)
    //hideStitchesOverLength
    //addThread
    //fixColorCount (compares color values in stitches and takes highest?)
    //copyStitchesToPolylines
    //copyPolylineToStitches
    //moveStitchesToPolylines
    //movePolylinesToStitches
    //changeColor
    //read (this reads a pattern file? seems out of place here)
    //write (same as above)
    //scale (whole pattern?)
    //calcBoundingBox
    //flipHorizontal
    //flipVerticle
    //combineJumpStitches
    //correctForMaxStitchLength
    //center (docs say its not used right now)
    //loadExternalColorFile
    //addCircleObjectsAbs
    //addEllipseObjectAbs
    //AddLineObjectAbs
    //AddPathObjectAbs
    //addPointObjectsAbs
    //addPolygonObjectsAbs
    //addPolylineObjectsAbs
    //addRectangleObjectsAbs
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that object sent as a parameter exists.
     * 
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        {
            throw new RuntimeException("EmbPattern: Null reference error");
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
