package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.EmbPattern;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*-----------------------------------------------------------------------*/
/**
 * This Class opens a .PES file and interprets the stitch data it extracts
 * using the PECDecoder class. This data is used to create an embroidery
 * pattern.
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class FormatPES
{
    /*-----------------------------------------------------------------------*/
    
    public static final long PEC_OFFSET = 8;
    
    private int pecStart;
    private int threadCount;
    private RandomAccessFile inFile;
    private EmbPattern pattern;
    
    
    /*-----------------------------------------------------------------------*/
    
    public FormatPES(String filename)
    {
        this.validateObject(filename);
        this.openFile(filename);
        this.getPECStart();
        this.pattern = new EmbPattern();
        //add threads from file
        //seek to start of stitch data
        //pass off to pec for stitch creation
        this.closeFile();
        //check for stitch end and add if necessary
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a new RandomAccessFile with the given filename.
     * 
     * @param filename String
     */
    private void openFile(String filename)
    {   
        try
        { this.inFile = new RandomAccessFile(filename, "r"); }
        catch(FileNotFoundException e)
        { System.err.println("FormatPES openFile: " + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Closes the file being read by this object.
     */
    private void closeFile()
    {   
        try
        { this.inFile.close(); }
        catch(IOException e)
        { System.err.println("FormatPES closeFile: " + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the starting position of the PEC code block inside the open file
     */
    private void getPECStart()
    {   
        try
        {
            this.inFile.seek(PEC_OFFSET);
            this.pecStart = this.inFile.readInt();
        }
        catch(IOException e)
        { System.err.println("FormatPES: getPECStart: " + e); }   
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * 
     */
    private void createThreads()
    {
        try
        {
            this.inFile.seek(this.pecStart + 48);
            this.threadCount = this.inFile.readInt(); //embroidermodder equiv embfile_get_c() +1
            
            for(int i = 0; i < this.threadCount; i++)
            {
                //grab character (byte?) from file
                //use that value to add a thread of matching rgb value
                    //(create this list of colors)
                
            }
        }
        catch(IOException e)
        { System.err.println("FormatPES: sumColors:" + e); }
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     *
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("FormatPES: Null reference error");
    }
    
    /*-----------------------------------------------------------------------*/
    //get numcolors
    //for each color add a thread
    //use pec to read stitches
    /*-----------------------------------------------------------------------*/
    
}

