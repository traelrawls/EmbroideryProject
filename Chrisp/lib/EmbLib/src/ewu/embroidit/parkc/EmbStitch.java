package ewu.embroidit.parkc;

import javafx.geometry.Point2D;


/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbStitch
{
    /*-----------------------------------------------------------------------*/
    
    //Class member variables
    private int flag;                       //Machine Code stitch flag
    private int color;                      //Color Number
    private Point2D absPosition;     //Absolute (x,y) position
    
    /*-----------------------------------------------------------------------*/
    
    public EmbStitch(Point2D absPosition, int color, int flag )
    {
        this.validateObject(absPosition);
        this.absPosition = absPosition;
        this.color = color;
        this.flag = flag;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public int getFlag()
    {
        return this.flag;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public void setFlag(int flag)
    {
        this.flag = flag;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public int getColor()
    {
        return this.color;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public void setColor(int color)
    {
        this.color = color;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public Point2D getSitchPosition()
    {
        return this.absPosition;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public void setStitchPosition(Point2D point)
    {
        this.validateObject(point);
        this.absPosition = point;
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void validateObject(Object obj)
    {
        if(obj == null)
            throw new RuntimeException("EmbStitch: Null reference error");
    }
    
    /*-----------------------------------------------------------------------*/
    
}
