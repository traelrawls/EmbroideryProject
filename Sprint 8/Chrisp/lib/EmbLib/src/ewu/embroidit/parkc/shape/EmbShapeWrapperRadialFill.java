package ewu.embroidit.parkc.shape;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbShapeWrapperRadialFill extends A_EmbShapeWrapper
{
    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX shape, the starting location
     * for stitch filling, and an empty list of stitches to hold fill data once
     * calculated.
     * 
     * @param wrappedShape Shape
     * @param startPoint Point2D
     */
    public EmbShapeWrapperRadialFill(Shape wrappedShape, Point2D startPoint)
    {
        super(wrappedShape, startPoint);
    }
    
    /*-----------------------------------------------------------------------*/
    
}
