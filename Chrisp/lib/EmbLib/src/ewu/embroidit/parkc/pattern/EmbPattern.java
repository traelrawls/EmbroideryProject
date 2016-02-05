package ewu.embroidit.parkc.pattern;

import java.util.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import com.sun.javafx.geom.Path2D;
import ewu.embroidit.parkc.io.PECDecoder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ewu.embroidit.parkc.io.StitchCode;

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
    
    private int colorIndex;                        //Current color index
    private double lastX;                          //Last x position
    private double lastY;                          //Last y position
    private Point2D homePoint;                     //Pattern starting point
    private EmbHoop hoop;                          //Embroidery hoop
    private List<EmbStitch> stitchList;            //List of stitches
    private List<EmbThread> threadList;            //List of threads
    private List<Rectangle> rectList;              //List of rectangles
    private List<Line> lineList;                   //List of lines
    private List<Circle> circleList;               //List of circles
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
     * @param isAutoColorIndex int
     */
    public void addStitchAbs(double x, double y, int flags, int isAutoColorIndex)
    {
        EmbStitch stitch;
        
        if((flags & StitchCode.END) != 0)
        {
            if(this.stitchList.isEmpty())
                return;
            
            //this.getMaxColorIndex(); May not be used.
        }
        
        if((flags & StitchCode.STOP) != 0)
        {
            if(this.stitchList.isEmpty())
                return;
               
            if(isAutoColorIndex > 0)
                this.colorIndex++;    
        }
                
        if(this.stitchList.isEmpty())
        {
            Point2D coord = new Point2D(this.homePoint.getX(), this.homePoint.getY());
            stitch = new EmbStitch(coord, this.colorIndex, StitchCode.JUMP);
            this.stitchList.add(stitch);
            return;
        }
        
        //need to add stitch here either relative or absolute. not sure yet
        //This is why we only ever got a jump stitch(from the first stitch
        //homepoint in the if above
        stitch = new EmbStitch(new Point2D(x, y), this.colorIndex,
                StitchCode.getInstance().getStitchCode(flags));
        this.stitchList.add(stitch);
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
    
    /**
     * Returns this patterns list of threads
     * 
     * @return List&lt;EmbThread&gt;
     */
    public List<EmbThread> getThreadList()
    {
        return this.threadList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the List&lt;EmbThread&gt; passed as the new thread list for this
     * pattern.
     * @param threadList List&lt;EmbThread&gt;
     */
    public void setThreadList(List<EmbThread> threadList)
    {
        this.threadList = threadList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns this patterns list of stitches
     * 
     * @return List&lt;EmbStitch&gt;
     */
    public List<EmbStitch> getStitchList()
    {
        return this.stitchList;
    }
    
    /*-----------------------------------------------------------------------*/
    /**
     * Gets the color at the given color index, creates a new thread of this
     * color, and adds it to the patterns thread list.
     * @param index int
     */
    public void addThread(int index)
    {
        Color threadColor;
        EmbThread thread;
        
        threadColor = PECDecoder.getInstance().getColorByIndex(index);
        thread = new EmbThread(threadColor);
        this.threadList.add(thread);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the maximum color index found in this patterns stitch list.
     * @return maxIndex int
     */
    private int getMaxColorIndex()
    {
        int maxIndex = 0;
        
        for(EmbStitch stitch: this.stitchList)
            maxIndex = Math.max(maxIndex, stitch.getColorIndex());
        
        System.err.println("Max Color Index: " + maxIndex);
        return maxIndex;
    }
    
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
