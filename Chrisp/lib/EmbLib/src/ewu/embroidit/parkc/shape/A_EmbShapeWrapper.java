package ewu.embroidit.parkc.shape;

import ewu.embroidit.parkc.pattern.EmbStitch;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;


/*-----------------------------------------------------------------------*/
/**
 * Base abstract class for Shape fill property wrappers. 
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public abstract class A_EmbShapeWrapper
{
    /*-----------------------------------------------------------------------*/
    
    private Shape wrappedShape;
    private List<Line> lineList;
    private List<EmbStitch> stitchList;
    private Point2D startPoint;
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX shape, the starting location
     * for stitch filling, and an empty list of stitches to hold fill data once
     * calculated.
     * 
     * @param wrappedShape Shape
     * @param startPoint Point2D
     */
    public A_EmbShapeWrapper(Shape wrappedShape, Point2D startPoint)
    {
        this.wrappedShape = wrappedShape;
        this.startPoint = startPoint;
        this.stitchList = new ArrayList();
    }
    
    /*-----------------------------------------------------------------------*/
    
    public List<Line> getLineList()
    {
        return this.lineList;
    }
    
    public void setLineList(List<Line> lineList)
    {
        this.validateObject(lineList);
        this.lineList = lineList;
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the list of stitches.
     * 
     * @return EmbStitch[]
     */
    public List<EmbStitch> getStitchList()
    {
        return this.stitchList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the list of stitches.
     * 
     * @param stitchList List&lt;EmbStitch&gt;
     */
    public void setStitchList(List<EmbStitch> stitchList)
    {
        this.stitchList = stitchList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * gets the starting point for this stitch fill.
     * 
     * @return Point2D
     */
    public Point2D getStartPoint()
    {
        return this.startPoint;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the start point for this stitch fill.
     * 
     * @param startPoint Point2D
     */
    public void setStartPoint(Point2D startPoint)
    {
        this.startPoint = startPoint;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the JavaFx shape wrapped by this fill object
     * 
     * @return Shape
     */
    public Shape getWrappedShape()
    {
        this.validateObject(this.wrappedShape);
        return this.wrappedShape;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the JavaFX shape wrapped by this fill object
     * 
     * @param wrappedShape Shape
     */
    public void setWrappedShape(Shape wrappedShape)
    {
        this.validateObject(wrappedShape);
        this.wrappedShape = wrappedShape;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures the object sent as a parameter exists.
     * 
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        {
            throw new RuntimeException("EmbShapeWrapper: Null reference error");
        }
    }
}
