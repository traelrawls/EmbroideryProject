package emblibtest;

import ewu.embroidit.parkc.io.*;
import ewu.embroidit.parkc.pattern.*;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/*-----------------------------------------------------------------------*/
/**
 * This driver file tests the functionality of PES reading for the
 * EmbroidIt Library
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbLibTest 
{

    /*-----------------------------------------------------------------------*/
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        String filename =  "test3.pes";
        
        FormatPES pesInput = new FormatPES(filename);
        EmbPattern pattern = pesInput.getPattern();
        printStitches(pattern);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Prints out each thread color, and each stitches: 
     * location, color, type.
     * 
     * @param pattern EmbPattern
     */
    public static void printStitches(EmbPattern pattern)
    {
        int i, tempFlag, tempColorIndex;
        Color tempColor;
        Point2D tempPoint;
        List<String> printQueue;
        List<EmbThread> threadList;
        List<EmbStitch> stitchList;
        
        printQueue = new ArrayList<>();
        threadList = pattern.getThreadList();
        stitchList = pattern.getStitchList();
        

        printQueue.add("Colors:\n\n");
        
        i = 0;
        for(EmbThread thread : threadList)
        {
           tempColor = thread.getThreadColor(); 
           printQueue.add(tempColor.toString() + " Color #(" + i + ")\n");
           i++;
        }
        
        printQueue.add("\n\n");
        printQueue.add("Stitch List:\n\n");
        
        for(EmbStitch stitch : stitchList)
        {
            tempPoint = stitch.getSitchPosition();
            tempFlag = stitch.getFlag();
            tempColorIndex = stitch.getColorIndex();
            
            printQueue.add(tempPoint.toString() + " Flag: " + tempFlag 
                    + " Color Index: " + tempColorIndex + "\n");
        }
         
        for(String str : printQueue)
            System.err.print(str);
    }
}
