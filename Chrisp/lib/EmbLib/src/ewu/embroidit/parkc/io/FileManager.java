package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbPattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


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
    
    //Export(EmbPattern pattern)
        //get wrapper list
        //sort wrapper list by color
        //figure out and mark jump and stop stitches
        //encode to output file
    /*-----------------------------------------------------------------------*/
    
    //get wrapper list(pattern)
        //for each shape in the shape list
        //grab its hashed wrapper and add it to a list
    //return the created wrapper list
    
    /*-----------------------------------------------------------------------*/
    //Sort Wrappers for Export (WrapperList)
        //while old list not empty
            //grab first element
            //add element to new list
            //remove that element from the old list
            //for each element in old list
            //if it color matches the first element
                    //add it to new list
                    //then remove it from old list
    
    /*-----------------------------------------------------------------------*/
}
