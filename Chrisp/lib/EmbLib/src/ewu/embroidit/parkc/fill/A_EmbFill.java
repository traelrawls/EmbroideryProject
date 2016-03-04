package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.shape.*;

/*-----------------------------------------------------------------------*/
/**
 * Base abstract class for shape filling strategies. 
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public abstract class A_EmbFill
{
    /*-----------------------------------------------------------------------*/
    protected static final double MM_TO_PXL = 3.779527559;
    /*-----------------------------------------------------------------------*/
    
    public A_EmbFill()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Defines a strategy for filling a shape with stitches. Implemented in
     * sub classes.
     * 
     * @param shapeWrapper A_EmbShapeWrapper
     */
    public abstract void fillShape(A_EmbShapeWrapper shapeWrapper);
    
    /*-----------------------------------------------------------------------*/
}
