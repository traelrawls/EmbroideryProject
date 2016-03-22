package ewu.embroidit.parkc.util.math;

import javafx.scene.shape.Ellipse;

/*-----------------------------------------------------------------------*/
/**
 * A set of math operations for ellipses used to calculate stitch filling.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbMathEllipse
{
    /*-----------------------------------------------------------------------*/
    
    private EmbMathEllipse()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the length of an arc equal to 1/4th of the total perimeter
     * of the ellipse.
     * @return 
     */
    public static double getQuadArcLength(Ellipse ellipse)
    {
        double perimeter, majorAxis, minorAxis, chunk1, chunk2;
        
        majorAxis = Math.max(ellipse.getRadiusX(),
                ellipse.getRadiusY());
        minorAxis = Math.min(ellipse.getRadiusX(),
                ellipse.getRadiusY());
        
        chunk1 = 3 * (majorAxis + minorAxis);
        chunk2 = Math.sqrt( ( (3 * majorAxis) + minorAxis ) *
                            ( majorAxis + (3 * minorAxis) ) );
        perimeter = Math.PI * (chunk1 - chunk2);
        
        return perimeter / 4.0;
        
    }
    /*-----------------------------------------------------------------------*/
    
    
    public static double getEllipseRadiusDeg(Ellipse ellipse, double degree)
    {
        double numerator, denominator, minorAxis, majorAxis;
        
        majorAxis = Math.max(ellipse.getRadiusX(),
                ellipse.getRadiusY());
        minorAxis = Math.min(ellipse.getRadiusX(),
                ellipse.getRadiusY());
        
        //numerator = majorAxis * minorAxis;
        
        numerator = ellipse.getRadiusX() * ellipse.getRadiusY();
        
        denominator = Math.pow(ellipse.getRadiusY() * Math.cos(Math.toRadians(degree)), 2) +
                      Math.pow(ellipse.getRadiusX() * Math.sin(Math.toRadians(degree)), 2);
        
        //denominator = Math.pow(minorAxis * Math.cos(degree), 2) +
        //              Math.pow(majorAxis * Math.sin(degree), 2);
        denominator = Math.sqrt(denominator);
            
        return (numerator / denominator);
    }
    
    /*-----------------------------------------------------------------------*/
}
