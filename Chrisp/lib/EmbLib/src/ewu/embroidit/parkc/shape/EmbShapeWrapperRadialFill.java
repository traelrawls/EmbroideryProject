package ewu.embroidit.parkc.shape;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/*-----------------------------------------------------------------------*/
/**
 * A property wrapper for an ellipse.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbShapeWrapperRadialFill extends A_EmbShapeWrapper
{
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX shape, and default values.
     * @param wrappedShape Shape
     */
    public EmbShapeWrapperRadialFill(Shape wrappedShape)
    {
        super(wrappedShape);
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX Shape, the shapes fill stitch 
     * length, and default values. Ensures a minimum stitch length of 1mm.
     * @param wrappedShape Shape
     * @param stitchLength double
     */
    public EmbShapeWrapperRadialFill(Shape wrappedShape, double stitchLength)
    {
        super(wrappedShape, stitchLength);
    }
    
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
    
    /*-----------------------------------------------------------------------*/}
