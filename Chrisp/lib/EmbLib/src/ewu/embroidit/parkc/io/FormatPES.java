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
    
    /**
     * Basic constructor that does not initialize any importing functionality.
     * Use for exporting functionality
     */
    public FormatPES()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a pattern from the imported file.
     * @param file 
     */
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
    
    public FormatPES(File file, EmbPattern pattern)
    {
        
    }
    
    /**
     * Creates a new RandomAccessFile with the given file.
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
    
/*
pesWriteSewSegSection

-calc bounding box


/*Stitch Blocks as far as I can tell are sections of stitches that share the same color 
(Count stitches and stitch blocks) (takes pattern and file pointer)
*/
    
/*
while there is a stitch
{

    get its flag
    get its color and count if not counted yet (if new)
    for(there is a stitch && it has the same flag)
    {
	count the stitches.
	get next stitch
    }

    count a stitch block
}

write blockcount to file as Short
write 0xFFFF to file as UShort
write 0x00 to file as Short
write 0x07 to file as Short
write string literal as it is

color count  =-1
block count = 0;

while (there is a stitch)
{
    get its flag
    get its thread color
    if a new color
    {
        add a increase colorinfoindex and write 
        blockcount to that position 
        repeat iwth new color code
    }

    count = 0;

    while(there is a stitch and the flag is the same)
    {
   	count stitches
    }

    if (flag is jump)
    {stitchtype = 1}
    else {stitchtype = 0}

    write stitchtype as short to file
    write colorcode as typecast (short) to file
    write count as short to file

    while there is a stitch && the flags match
    {
	write absX - left bounds as typecast short to file
	write absY - top bounds as typecast short to file
	//top left justify??
    }
    if(current stitch after while is not null)
    {
	write 0x8003 as short to file	
    }
    blockcount++
    reset stitch list to beginning
}

write colorCount to file as short
for each color in the color count
{
    binaryWriteShort(file, colorInfo[i * 2]);
    binaryWriteShort(file, colorInfo[i * 2 + 1])
}
write 0 int to file
if(colorInfo)
{
	set to 0;
}

//here is their code for both Short and UShort methods //(overloaded method) (file, short)(file, Ushort)
//embFile_putc(data & 0xFF, file);
//embFile_putc((data >> 8) & 0xFF, file); 
*/
    
    
    /*-----------------------------------------------------------------------*/
    /**
     * Returns the pattern containing the thread and stitch lists created from 
     * the imported PES file.
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

