package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.*;
import java.util.ArrayList;
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
     * Break a line segment down into as many sub lines of the stitch length
     * as possible.
     * @param line Line
     * @param stitchLength double
     * @return List&lt;Line&gt;
     */
    protected List<Line> singleLineDivide(Line line, double stitchLength)
    {
        List<Line>dividedList;
        Line tempLine;
        Point2D startPoint, endPoint, unitVec, maxEndPoint;

        dividedList = new ArrayList<>();
        maxEndPoint = new Point2D(line.getEndX(), line.getEndY());
        tempLine = new Line(line.getStartX(), line.getStartY(),
                            line.getEndX(), line.getEndY());
        
        while(true)
        {
            startPoint = new Point2D(tempLine.getStartX(), tempLine.getStartY());
            unitVec = new Point2D((tempLine.getEndX() - tempLine.getStartX()),
                                  (tempLine.getEndY() - tempLine.getStartY()));
            unitVec = unitVec.normalize();
            unitVec = unitVec.multiply(stitchLength);
            endPoint = startPoint.add(unitVec);
            
            //if the new line segment extends beyond the old one exit the loop.
            if(this.calculateDistance(startPoint, endPoint) >= 
                    this.calculateDistance(startPoint, maxEndPoint))
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
     * Return the distance between two points.
     * TODO: (Refactor into math package or find JavaFX library replacement)
     * @param point1 Point2D
     * @param point2 Point2D
     * @return double
     */
    private double calculateDistance(Point2D point1, Point2D point2)
    {
        double deltaX, deltaY;
        
        deltaX = Math.pow((point1.getX() - point2.getX()), 2);
        deltaY = Math.pow((point1.getY() - point2.getY()), 2);
        
        return Math.sqrt(deltaX + deltaY);
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
