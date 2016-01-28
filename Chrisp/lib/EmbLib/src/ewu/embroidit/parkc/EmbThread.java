package ewu.embroidit.parkc;

import java.awt.Color;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbThread
{
    /*-----------------------------------------------------------------------*/
    
    //Class member variables
    private Color threadColor;                      //Thread Color
    
    /*-----------------------------------------------------------------------*/
    
    public EmbThread(Color threadColor)
    {
        this.validateObject(threadColor);
        this.threadColor = threadColor;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public Color getThreadColor()
    {
        return this.threadColor;
    }
    
    /*-----------------------------------------------------------------------*/
    
    public void setThreadColor(Color threadColor)
    {
        this.validateObject(threadColor);
        this.threadColor = threadColor;
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void validateObject(Object obj)
    {
        if (obj == null)
        {
            throw new RuntimeException("EmbThread: Null reference error");
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
