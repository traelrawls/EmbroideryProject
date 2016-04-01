package ewu.embroidit.parkc.pattern;

import javafx.geometry.Point2D;


/*-----------------------------------------------------------------------*/
/**
 * Represents a single embroidery stitch in a pattern.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbStitch
{
    /*-----------------------------------------------------------------------*/
    
    private int flag;                //Machine code stitch flag
    private int colorIndex;          //Color number
    private Point2D absPosition;     //Absolute (x,y) position
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs an embroidery stitch with the given absolute position, color,
     * and machine code flag.
     * @param absPosition javafx.geometry.Point2D
     * @param color int
     * @param flag int
     */
    public EmbStitch(Point2D absPosition, int color, int flag )
    {
        this.validateObject(absPosition);
        this.absPosition = absPosition;
        this.colorIndex = color;
        this.flag = flag;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets this stitches machine code flag.
     * @return int
     */
    public int getFlag()
    { return this.flag; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets this stitches machine code flag.
     * @param flag int
     */
    public void setFlag(int flag)
    { this.flag = flag; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets this stitches color index.
     * @return int
     */
    public int getColorIndex()
    { return this.colorIndex; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets this stitches color index.
     * @param color int
     */
    public void setColor(int color)
    { this.colorIndex = color; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets this stitches absolute position.
     * @return javafx.geometry.Point2D
     */
    public Point2D getStitchPosition()
    { return this.absPosition; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets this stitches absolute position.
     * @param point javafx.geometry.Point2D
     */
    public void setStitchPosition(Point2D point)
    {
        this.validateObject(point);
        this.absPosition = point;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        { throw new RuntimeException("EmbStitch: Null reference error"); }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
