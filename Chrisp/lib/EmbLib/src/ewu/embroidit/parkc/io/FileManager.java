package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Shape;


/*-----------------------------------------------------------------------*/
/**
 * Handles file and formatting operations: Open, Save, Import (More to come...)
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class FileManager
{
    
    /*-----------------------------------------------------------------------*/
    
    private FileManager()
    {}
    
    /**
     * Returns an instance of the file manager.
     * @return  FileManager
     */
    public static FileManager getInstance()
    {
        return FileManagerHolder.INSTANCE;
    }
    
    private static class FileManagerHolder
    { private static final FileManager INSTANCE = new FileManager(); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the pattern stored in the given file.
     * @param file File
     * @return EmbPattern
     */
    public EmbPattern openPattern(File file)
    {
        FileInputStream inFile;
        ObjectInputStream inObject;
        EmbPattern pattern = new EmbPattern();
        
        try
        {
            inFile = new FileInputStream(file);
            inObject = new ObjectInputStream(inFile);
            pattern = (EmbPattern) inObject.readObject();
            inObject.close();
        }
        catch(Exception e)
        { System.err.println("FileManager: openPattern: " + e); }
        
        return pattern;
    }
    
    /*-----------------------------------------------------------------------*/

    /**
     * Saves a pattern to the given file.
     * @param pattern EmbPattern
     * @param file File
     */
    public void savePattern(EmbPattern pattern, File file)
    {
        FileOutputStream outFile;
        ObjectOutputStream outObject;
        
        try
        {
            outFile = new FileOutputStream(file);
            outObject = new ObjectOutputStream(outFile);
            outObject.writeObject(pattern);
            outObject.close();
        }
        catch(Exception e)
        { System.err.println("FileManager: save Pattern: " + e); }
        
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns a pattern constructed from the given PES file.
     * @param file File
     * @return EmbPattern
     */
    public EmbPattern pesToPattern(File file)
    {
        FormatPES pesFormatter;
        
        pesFormatter = new FormatPES(file);
        return pesFormatter.getPattern();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Exports a pattern to PES file. Shapes are pre-sorted by color to reduce
     * unnecessary thread changes. (thrashing)
     * @param pattern EmbPattern
     */
    public void patternToPes(EmbPattern pattern)
    {
        List<A_EmbShapeWrapper> wrapperList;
        List<A_EmbShapeWrapper> sortedWrapperList;
        
        wrapperList = this.getWrapperList(pattern);
        sortedWrapperList = this.sortWrappersByColor(wrapperList);
        
        //NEXT:
        //Break down stitches
            //break down shapes into points (EmbStitch) (Done is Shape Wrappers)
        
        //for each shape
                //grab the last stitch
                //iterate to next shape
                //if that shapes color is the same and the distance
                //between the end and start points respectively
                //is greater than MM_TO_PXL * 12.00
                //duplicate the start stitch of the next shape
                //add that stitch as a jump stitch
                //(starting stitch remains, jump stitches do not
                //stitch, they move.)
                
                //if the shapes are not the same color, a stop stitch should
                //be encoded.
        //Set up bitmasked output encoding with PES and PEC.
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape wrapper list for the given pattern.
     * @param pattern EmbPattern
     * @return List&lt;EmbPattern&gt;
     */
    private List<A_EmbShapeWrapper> getWrapperList(EmbPattern pattern)
    {
        List<Shape> shapeList;
        List<A_EmbShapeWrapper> wrapperList;
        
        shapeList = pattern.getShapeList();
        wrapperList = new ArrayList<>();
        
        for(Shape shape: shapeList)
            wrapperList.add(pattern.getShapeWrapper(shape));
        
        return wrapperList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns a color sorted (with color group ordering arbitrary) version
     * of the given list.
     * @param wrapperList
     * @return List&lt;A_EmbShapeWrapper&gt;
     */
    public List<A_EmbShapeWrapper> sortWrappersByColor(List<A_EmbShapeWrapper> wrapperList)
    {
        A_EmbShapeWrapper coloredShape;
        List<A_EmbShapeWrapper> sortedWrapperList;
        List<A_EmbShapeWrapper> colorChunk;
        
        sortedWrapperList = new ArrayList<>();
        
        while(!wrapperList.isEmpty())
        {
            coloredShape = wrapperList.get(0);
            colorChunk = new ArrayList<>();
            
            for(A_EmbShapeWrapper wrapper : wrapperList)
                if(coloredShape.getThreadColor().equals(wrapper.getThreadColor()))
                    colorChunk.add(wrapper);
            
            for(A_EmbShapeWrapper wrapper : colorChunk)
                wrapperList.remove(wrapper);
            
            sortedWrapperList.addAll(colorChunk);
        }
        
        return sortedWrapperList;
    }
    
    /*-----------------------------------------------------------------------*/
}
