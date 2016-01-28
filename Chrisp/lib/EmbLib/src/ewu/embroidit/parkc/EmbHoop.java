package ewu.embroidit.parkc;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbHoop
{
    /*-----------------------------------------------------------------------*/
    
    //Class member variables
    private double width, height;
    
    /*-----------------------------------------------------------------------*/
    
    public EmbHoop(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public double getWidth()
    {
        return this.width;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public void setWidth(double width)
    {
        this.width = width;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public double getHeight()
    {
        return this.height;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public void setHeight(double height)
    {
        this.height = height;
    }
    
    /*-----------------------------------------------------------------------*/
    
}
