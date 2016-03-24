package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*-----------------------------------------------------------------------*/
/**
 * This Class opens a .PES file and interprets the stitch data extracted
 * by the PECDecoder class. This data is used to create the stitches for
 * an embroidery pattern.
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class FormatPES
{
    /*-----------------------------------------------------------------------*/
    
    private final long PEC_OFFSET = 8;  //Location in the PES file that contains
                                        //the starting address of the PEC code block.
    
    private int pecStart;               //Starting location of PEC code block.
    private int threadCount;            //Number of threads used in this PES file.
    private RandomAccessFile inFile;    //The input stream.
    private EmbPattern pattern;         //Pattern used to hold PES data.
    
    /*-----------------------------------------------------------------------*/
    
    public FormatPES(File file)
    {
        this.validateObject(file);
        this.openFile(file);
        this.getPECStart();
        this.pattern = new EmbPattern();
        
        this.createThreads();
        this.readPEC();
        this.closeFile();
        this.validateLastStitch();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a new RandomAccessFile with the given file.
     * 
     * @param file File
     */
    private void openFile(File file)
    {   
        try
        { this.inFile = new RandomAccessFile(file, "r"); }
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
            this.pecStart = this.pecStart | this.inFile.readUnsignedByte() << 8;
            this.pecStart = this.pecStart | this.inFile.readUnsignedByte() << 16;
            this.pecStart = this.pecStart | this.inFile.readUnsignedByte() << 24;
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
        { System.err.println("FormatPES: createThreads:" + e); }
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Uses PECDecoder to read stitch information into the EmbPattern held by 
     * this class.
     */
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
    
    /**
     * Returns the pattern containing the thread and stitch lists created from 
     * the imported PES file.
     * 
     * @return EmbPattern
     */
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
    
    /**
     * Ensures that the last stitch in the pattern is labeled as an END stitch.
     */
    private void validateLastStitch()
    {
        int listSize = this.pattern.getStitchList().size();
        EmbStitch lastStitch = this.pattern.getStitchList().get(listSize - 1);
        
        if(lastStitch.getFlag() != StitchCode.END)
        {
            this.pattern.addStitchRel(0.0, 0.0, StitchCode.END, 1);
            System.err.println("End Stitch not present in data, Added manually.");
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
}

