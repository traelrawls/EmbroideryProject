package ewu.embroidit.parkc.util.math;

/*-----------------------------------------------------------------------*/

import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import java.util.List;
import javafx.geometry.Point2D;

/*-----------------------------------------------------------------------*/
/**
 * Math helper functions for scaling.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbMathScale
{
    
    /*-----------------------------------------------------------------------*/ 
    
    private EmbMathScale()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Scales all shape stitches in a pattern by the given scaleFactor.
     * @param pattern EmbPattern
     * @param scaleFactor double
     */
    public static void scaleStitches(EmbPattern pattern, double scaleFactor)
    {
        List<EmbStitch> stitchList = pattern.getStitchList();
        double x, y;
        
        for(EmbStitch stitch : stitchList)
        {
            x = stitch.getStitchPosition().getX() * scaleFactor;
            y = stitch.getStitchPosition().getX() * scaleFactor;
            stitch.setStitchPosition(new Point2D(x, y));
        }
    }
    
    /*-----------------------------------------------------------------------*/
}
