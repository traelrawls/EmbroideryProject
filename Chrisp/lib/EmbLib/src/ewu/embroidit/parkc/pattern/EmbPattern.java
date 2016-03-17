package ewu.embroidit.parkc.pattern;

import java.util.*;
import javafx.geometry.Point2D;
import ewu.embroidit.parkc.io.PECDecoder;
import javafx.scene.paint.Color;
import ewu.embroidit.parkc.io.StitchCode;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import javafx.scene.shape.Shape;

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
    
    private List<Shape> shapeList;
    private HashMap<Shape, A_EmbShapeWrapper> wrapperHash;
    
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
        this.shapeList = new ArrayList<>();
        this.wrapperHash = new HashMap<>();
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
     * Adds the shape wrapper passed as a parameter to the hash using the shape 
     * contained within it as the key.
     * 
     * @param shapeWrapper  A_EmbShapeWrapper
     */
    public void addShapeWrapper(A_EmbShapeWrapper shapeWrapper)
    {
        this.validateObject(shapeWrapper);
        this.wrapperHash.put(shapeWrapper.getWrappedShape(), shapeWrapper);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Removes the wrapper related to the given key shape if it exists.
     * 
     * @param keyShape Shape
     */
    public void removeShapeWrapper(Shape keyShape)
    {
        this.validateObject(keyShape);
        this.wrapperHash.remove(keyShape);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the shape wrapper that corresponds to the given shape.
     * 
     * @param keyShape Shape
     * @return A_EmbShapeWrapper wrapper
     */
    public A_EmbShapeWrapper getShapeWrapper(Shape keyShape)
    {
        A_EmbShapeWrapper wrapper;
        
        this.validateObject(keyShape);
        wrapper = this.wrapperHash.get(keyShape);
        this.validateObject(wrapper);
        return wrapper;
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
     * 
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
     * Sets the List&lt;EmbStitch&gt; passed as the new stitch list for this
     * pattern.
     * 
     * @param stitchList 
     */
    public void setStitchList(List<EmbStitch> stitchList)
    {
        this.stitchList = stitchList;
    }
    
    /*-----------------------------------------------------------------------*/
    /**
     * Gets the color at the given color index, creates a new thread of this
     * color, and adds it to the patterns thread list.
     * 
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
     * Ensures that object sent as a parameter is not null.
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
