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
    {
        private static final FileManager INSTANCE = new FileManager();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the pattern stored in the given file.
     * @param filepath String
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
     * @param name String
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
    
    
}
