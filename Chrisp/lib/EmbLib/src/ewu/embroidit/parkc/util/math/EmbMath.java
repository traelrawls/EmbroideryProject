package ewu.embroidit.parkc.util.math;

import ewu.embroidit.parkc.io.PECDecoder;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/*-----------------------------------------------------------------------*/
/**
 * Helper methods for simple math related tasks within the
 * Embroid-It library.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbMath
{
    /*-----------------------------------------------------------------------*/
    
    private EmbMath()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    public static EmbMath getInstance()
    { return EmbMathHolder.INSTANCE; }
    
    /*-----------------------------------------------------------------------*/
    
    private static class EmbMathHolder
    { private static final EmbMath INSTANCE = new EmbMath(); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the length of an arc equal to 1/4th of the total perimeter
     * of the ellipse.
     * @param ellipse Ellipse
     * @return double 
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
    
    /**
     * Returns the radial line of an ellipse at a given degree.
     * @param ellipse Ellipse
     * @param degree double
     * @return double
     */
    public static double getEllipseRadiusDeg(Ellipse ellipse, double degree)
    {
        double numerator, denominator;

        numerator = ellipse.getRadiusX() * ellipse.getRadiusY();
        denominator = Math.pow(ellipse.getRadiusY() * Math.cos(Math.toRadians(degree)), 2) +
                      Math.pow(ellipse.getRadiusX() * Math.sin(Math.toRadians(degree)), 2);
        denominator = Math.sqrt(denominator);
            
        return (numerator / denominator);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Return the distance between two points.
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
    
    /**
     * Scales all shape stitches in a pattern by the given scaleFactor.
     * @param pattern EmbPattern
     * @param scaleFactor double
     */
    public static void scaleStitches(List<A_EmbShapeWrapper> wrapperList,
            double scaleFactor)
    {
        List<EmbStitch> stitchList;
        double x, y;
        
        for(A_EmbShapeWrapper wrapper : wrapperList)
        {
            stitchList = wrapper.getStitchList();
            
            for(EmbStitch stitch : stitchList)
            {
                x = stitch.getStitchPosition().getX() * scaleFactor;
                y = stitch.getStitchPosition().getY() * scaleFactor;
                stitch.setStitchPosition(new Point2D(x, y));
            }
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
    public static BoundingBox calcBoundingRect(List<EmbStitch> stitchList)
    {
        double xMin, yMin, xMax, yMax;
        
        if(stitchList.isEmpty())
            return new BoundingBox(0.0, 0.0, 1.0, 1.0);
        
        xMin = yMin = 99999.0;
        xMax = yMax = 0.0;
        
        for(EmbStitch stitch : stitchList)
        {
            xMin = Math.min(xMin , stitch.getStitchPosition().getX());
            yMin = Math.min(yMin , stitch.getStitchPosition().getY());
            yMax = Math.max(xMax, stitch.getStitchPosition().getX());
            yMax = Math.max(yMax, stitch.getStitchPosition().getY());
        }
        
        return new BoundingBox(xMin, yMin, (xMax - xMin), (yMax - yMin));
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the index of the available color that most closely
     * resembles the color passed as a parameter.
     * @param color Color
     * @return Color
     */
    public static int approximateColorIndex(Color color)
    {
        Color tempColor;
        int closestIndex;
        double closestDist, dist;
        double red, green, blue; 
        double dRed, dGreen, dBlue;
        
        closestIndex = -1;
        closestDist = 9999999;
        red =  color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        
        for(int i = 0; i < PECDecoder.NUM_COLORS; i++)
        {
           tempColor = PECDecoder.getInstance().getColorByIndex(i);
           dRed = red - tempColor.getRed();
           dGreen = green - tempColor.getGreen();
           dBlue = blue - tempColor.getBlue();
           dist = Math.sqrt( Math.pow(dRed, 2) 
                           + Math.pow(dGreen, 2) 
                           + Math.pow(dBlue, 2));
           
           if(dist <= closestDist)
           {
               closestDist = dist;
               closestIndex = i;
           }
        }
        
        if(closestIndex == -1) 
            System.err.println("EmbMath: Color approximation Failed."); 
        
        return closestIndex;
    }
    
    /*-----------------------------------------------------------------------*/
}
