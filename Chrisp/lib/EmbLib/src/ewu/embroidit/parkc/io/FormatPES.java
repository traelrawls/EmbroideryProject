package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbPattern;
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
    
    private final long PEC_OFFSET = 8;
    
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
        
        this.createThreads();
        this.readPEC();
        this.closeFile();
        
        
        //check for end stitch and add it if it's not there.
        //flip pattern verticle (are y values read backwards?)
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
            this.pecStart = this.inFile.readUnsignedByte();
            //this.pecStart = this.pecStart | this.pecStart << 8;
            //this.pecStart = this.pecStart | this.pecStart << 16;
            //this.pecStart = this.pecStart | this.pecStart << 24;
            System.err.println(" Start Location:" + this.pecStart);
        }
        catch(IOException e)
        { System.err.println("FormatPES: getPECStart: " + e); }   
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a thread object and adds it to the thread list of the pattern.
     */
    private void createThreads()
    {
        try
        {
            this.inFile.seek(this.pecStart + 48);
            this.threadCount = this.inFile.readByte() + 1;
            
            for(int i = 0; i < this.threadCount; i++)
            {
                this.pattern.addThread(this.inFile.readUnsignedByte());
                System.err.println("DEBUG: Thread Added.");
            }
        }
        catch(IOException e)
        { System.err.println("FormatPES: createThreads:" + e);
            System.err.println(this.pecStart);
        }
    }
    /*-----------------------------------------------------------------------*/
    
    private void readPEC()
    {
        
        try
        {
            this.inFile.seek(this.pecStart + 532);
            PECDecoder.getInstance().readStitches(this.pattern, this.inFile);
        }
        catch(IOException e)
        {
            System.err.println("FormatPES: Error in readPEC():" + e);
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
    public EmbPattern getPattern()
    {
        this.validateObject(this.pattern);
        return this.pattern;
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
    
}

