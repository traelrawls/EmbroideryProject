package ewu.embroidit.parkc.util.math;

import javafx.geometry.Point2D;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbMathPoint
{
    
    /*-----------------------------------------------------------------------*/
    
    private EmbMathPoint()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Return the distance between two points.
     * TODO: (Refactor into math package or find JavaFX library replacement)
     * @param point1 Point2D
     * @param point2 Point2D
     * @return double
     */
    public static double calculateDistance(Point2D point1, Point2D point2)
    {
        double deltaX, deltaY;
        
        deltaX = Math.pow((point1.getX() - point2.getX()), 2);
        deltaY = Math.pow((point1.getY() - point2.getY()), 2);
        
        return Math.sqrt(deltaX + deltaY);
    }
    
    /*-----------------------------------------------------------------------*/
}
