package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.VerticalLineSort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    {}
    
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
        
        divideRectRecursive(rect, lineList);
        
        Collections.sort(lineList, new VerticalLineSort());
        
        //break lines down into stitches (subdivisions?)
        
        //add those stitches to the shapeWrappers stitch list
    }
    
    /*-----------------------------------------------------------------------*/
    
    private void divideRectRecursive(Rectangle rect, List<Line> lineList)
    {
        //Base case: if rect width is >= MM_TO_PXL * 2
        if(rect.getWidth() >= MM_TO_PXL * 2)
        {
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
}
