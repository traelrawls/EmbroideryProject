package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;
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
            modifiedLineList.addAll(singleLineDivide(line, stitchLength));
        
        shapeWrapper.setLineList(modifiedLineList);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Break a line segment down into as many sub sub lines of the stitch length
     * as possible.
     * @param line Line
     * @param stitchLength double
     * @return List&lt;Line&gt;
     */
    protected List<Line> singleLineDivide(Line line, double stitchLength)
    {
        List<Line>dividedList;
        Line tempLine;
        Point2D startPoint, endPoint, normal, maxEndPoint;

        dividedList = new ArrayList<>();
        maxEndPoint = new Point2D(line.getEndX(), line.getEndY());
        tempLine = new Line(line.getStartX(), line.getStartY(),
                            line.getEndX(), line.getEndY());
        
        while(true)
        {
            startPoint = new Point2D(tempLine.getStartX(), tempLine.getStartY());
            normal = startPoint.normalize();
            endPoint = startPoint.add(normal.multiply(stitchLength));
            
            //This wont work. It should account for when the line
            //is exceeded, not if its greater. the coordinates could be
            //positive or negative.
            if(endPoint.getX() >= maxEndPoint.getX() || endPoint.getY() >= maxEndPoint.getY())
                break;
            
            dividedList.add(new Line(startPoint.getX(), startPoint.getY(),
                            endPoint.getX(), endPoint.getY()));
            
            tempLine = new Line(endPoint.getX(), endPoint.getY(),
                                tempLine.getEndX(), tempLine.getEndY());
        }
            
        dividedList.add(new Line(startPoint.getX(), startPoint.getY(),
                        tempLine.getEndX(), tempLine.getEndY()));
        
        return dividedList;
        
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
