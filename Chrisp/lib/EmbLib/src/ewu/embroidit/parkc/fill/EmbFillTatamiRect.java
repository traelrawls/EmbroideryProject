package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.io.StitchCode;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.VerticalLineSort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbFillTatamiRect extends A_EmbFill
{
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a Tatami stitch filling strategy.
     */
    public EmbFillTatamiRect()
    {
        super();
        System.err.println("Fill Strat Created");
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Implements a shape filling strategy for Tatami style fill stitches.
     * NOTE: Implementation incomplete. TBD.
     * 
     * @param shapeWrapper shapeWrapper
     */
    @Override
    public void fillShape(A_EmbShapeWrapper shapeWrapper)
    {
        Rectangle rect;
        List<Line> lineList;
        
        rect = (Rectangle) shapeWrapper.getWrappedShape();
        lineList = new ArrayList<>();
        
        System.err.println("Before Initial Recursive Call!!!");
        
        divideRectRecursive(rect, lineList);
        
        Collections.sort(lineList, new VerticalLineSort());
        
        //add the line list to the wrapper
        shapeWrapper.setLineList(lineList);
        
        //break lines down into individual stitches
        //and add to shapeWrapper(subdivisions later?)
        shapeWrapper.setStitchList(createStitchList(lineList));
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void divideRectRecursive(Rectangle rect, List<Line> lineList)
    {
        System.err.println("Rect Width is:" + rect.getWidth());
        
        //Base case: if rect width is < MM_TO_PXL * 2
        if(rect.getWidth() >= MM_TO_PXL * 2)
        {
            System.err.println("Splitting Rect");
            
            Rectangle rectLeft, rectRight;
            
            //cut rect in half creating two new rects
            rectLeft = new Rectangle(
                    rect.getX(),
                    rect.getY(),
                    rect.getWidth() / 2.0,
                    rect.getHeight());
            
            rectRight = new Rectangle(
                    (rect.getX() + rect.getWidth()) / 2.0,
                    rect.getY(),
                    rect.getWidth() / 2.0,
                    rect.getHeight());
        
            //recurse
            divideRectRecursive(rectLeft, lineList);
            divideRectRecursive(rectRight, lineList);
        }
        
        //create midpoint line
        Line midPointLine = new Line(
                (rect.getX() + rect.getWidth() / 2.0),
                rect.getY(),
                (rect.getX() + rect.getWidth() / 2.0),
                rect.getY() + rect.getHeight());
        
        //add line to list
        lineList.add(midPointLine);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Breaks line segments for fill stitch down into list of individual stitches
     * alternating start end coordinates to reduce jump stitching in a fill.
     * 
     * NOTE: This doesn't account for stitch type or color appropriately yet.
     * 
     * @param lineList
     * @return stitchList
     */
    public List<EmbStitch> createStitchList(List<Line> lineList)
    {
        boolean isOddStitch = true;
        List<EmbStitch> stitchList = new ArrayList<>();
        
        for(Line lineSegment: lineList)
        {
            if(isOddStitch)
            {
                stitchList.add(new EmbStitch(
                        new Point2D(lineSegment.getStartX(), lineSegment.getStartY()),
                        1, StitchCode.NORMAL));

                isOddStitch = false;
            }
            else
            {
                stitchList.add(new EmbStitch(
                        new Point2D(lineSegment.getEndX(), lineSegment.getEndY()),
                        1, StitchCode.NORMAL));
                
                isOddStitch = true;
            }
        }
        
        return stitchList;
    }
    
    /*-----------------------------------------------------------------------*/
}
