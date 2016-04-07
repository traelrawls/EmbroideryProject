package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Line;

/*-----------------------------------------------------------------------*/
/**
 * Base abstract class for shape filling strategies. 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public abstract class A_EmbFill
{
    /*-----------------------------------------------------------------------*/
    public static final double MM_TO_PXL = 3.779527559;
    /*-----------------------------------------------------------------------*/
    
    public A_EmbFill()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Defines a strategy for filling a shape with stitches. Implemented in
     * sub classes.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    public abstract void fillShape(A_EmbShapeWrapper shapeWrapper);
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Takes the takes a line list and breaks it down into
     * an ordered sequence of stitches.
     * @param lineList List&lt;Line&gt;
     * @return ArrayList&lt;EmbStitch&gt;
     */
    protected abstract List<EmbStitch> generateStitchList(A_EmbShapeWrapper shapeWrapper);
    
    /*-----------------------------------------------------------------------*/
    
       
    /**
     * Takes the shapeWrappers line list and breaks it down into chains of
     * smaller stitches based on that wrappers stitch length property.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    protected void subDivideFillLines(A_EmbShapeWrapper shapeWrapper)
    {
        List<Line> lineList;
        List<Line> modifiedLineList;
        double stitchLength;
        
        this.validateObject(shapeWrapper);
        lineList = shapeWrapper.getLineList();
        modifiedLineList = new ArrayList<>();
        stitchLength = shapeWrapper.getStitchLength();
        
        for(Line line : lineList)
        {
            modifiedLineList.addAll(singleLineDivide(line, stitchLength));
        }
        //Subdivide Segments
        //for each line in line list
        //break that line down into subdivisions based on the set stitch length
        //add all of those into a new list
        //replace the old list with the new list.
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Break a line segment down into as many sub divisions of the given
     * stitch length as possible.
     * @param line Line
     * @param stitchLength double
     * @return List&lt;Line&gt;
     */
    protected List<Line> singleLineDivide(Line line, double stitchLength)
    {
        List<Line>dividedList;
        Line tempLine;
        
        //LEFT OFF HERE 4/7/16
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        { throw new RuntimeException("A_EmbFill: Null reference error"); }
    }
    /*-----------------------------------------------------------------------*/
}
