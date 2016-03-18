package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.math.EmbMathEllipse;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbFillRadial extends A_EmbFill
{
    /*-----------------------------------------------------------------------*/
    
    private static final int NUM_STITCHES = 8;
    
    /*-----------------------------------------------------------------------*/
    /**
     * Constructs a Radial stitch filling strategy.
     */
    public EmbFillRadial()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Implements a shape filling strategy for Radial style fill stitches.
     * NOTE: Implementation incomplete. TBD.
     * 
     * @param shapeWrapper A_EmbShapeWrapper
     */
    @Override
    public void fillShape(A_EmbShapeWrapper shapeWrapper)
    {
        Ellipse ellipse;
        List<Line> lineList;
        
        ellipse = (Ellipse) shapeWrapper.getWrappedShape();
        lineList = new ArrayList<>();

        fillEllipse(ellipse, lineList);
        
        shapeWrapper.setLineList(lineList);
    }
    
    /*-----------------------------------------------------------------------*/
    //Note: if you use quandrants and flip stitch values, you will
    //have to account for and remove duplicate line segments at 90 degrees
    private void fillEllipse(Ellipse ellipse, List<Line> lineList)
    {   
        double endX, endY, radius, degree, degreeInterval;
        Line radialLine;
        
        degreeInterval = 90 / NUM_STITCHES;
        degree = 0;
        for(int i = 0; i <= NUM_STITCHES; i++)
        {
            
            radius = EmbMathEllipse.getEllipseRadiusDeg(ellipse, degree);
            endX = radius * Math.cos(degree);
            endY = radius * Math.sin(degree);
            
            radialLine = new Line(
                    ellipse.getCenterX(),
                    ellipse.getCenterY(),
                    endX,
                    endY);

            //add line to list
            lineList.add(radialLine);
            
            degree += degreeInterval;
        }
        
        //mirror line segments along x axis, update list,
        //mirror y axis, update list, remove duplicates
        
        
    }
    
    //get arclength
    //while distance(0) < arclength
    //calculate the radius line at that theta,
    //add dx to angle
    //repeat
    /*-----------------------------------------------------------------------*/
}
