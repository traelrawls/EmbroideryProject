package ewu.embroidit.parkc;


import java.util.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import com.sun.javafx.geom.Path2D;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbPattern
{
    /*-----------------------------------------------------------------------*/
    
    //Class member variables
    int colorIndex;                        //Current colorIndex
    double lastX;                          //Last x position
    double lastY;                          //Last y position
    Point2D homePoint;                     //Starting point
    
    EmbHoop hoop;                          //Hoop
    List<EmbStitch> stitchList;            //Stitch list
    List<EmbThread> threadList;            //Thread list
    List<Rectangle> rectList;              //Rectangle list
    List<Line> lineList;                   //Line list
    List<Ellipse> circleList;              //Circle list
    List<Ellipse> ellipseList;             //Ellipse list
    List<Path2D> pathList;                 //Path list
    List<Point2D> pointList;               //Point list
    List<Polygon> polygonList;             //Polygon list
    List<Polyline> polylineList;           //Polyline list
    
    //Object lists remaining
    //--Arc (not sure if this should be Arc or Cubic Curve)
    //--Spline (Uh....Find out what this is)
    
    /*-----------------------------------------------------------------------*/
    
    //This is a list of pointer references in the modder api pattern file
    //they should be uneccesary since we can just get a reference from the
    //Lists<> that we use in this class
  
    //lastStitch
    //lastThread
    //lastArc
    //lastCircle
    //lastEllipse
    //lastLine
    //lastPath
    //lastPoint
    //lastPolygon
    //lastPolyline
    //lastRect
    //lastSpleen (Except this one, I don't know what this spline thing is)
    
    /*-----------------------------------------------------------------------*/
    
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
     * @param x
     * @param y
     * @param flags
     * @param isAutoColorIndex 
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
     * If this is the first stitch in the pattern then homePoint 
     * is set using its coordinates. autoColorIndex
     * @param dx
     * @param dy
     * @param flags
     * @param isAutocolorIndex
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
    
    private void validateObject(Object obj)
    {
        if (obj == null)
        {
            throw new RuntimeException("EmbPattern: Null reference error");
        }
    }
    
    /*-----------------------------------------------------------------------*/
}
