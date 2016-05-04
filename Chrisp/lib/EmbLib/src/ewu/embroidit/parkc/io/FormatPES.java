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
        this.openFile(file, "r");
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
     * Creates a new RandomAccessFile with the given file pointer.
     * @param file File
     */
    private void openFile(File file, String mode)
    {   
        try
        { this.inFile = new RandomAccessFile(file, mode); }
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
        { System.err.println("FormatPES: Error in readPEC():" + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /* pesWrite(pattern, pointer to file name
    
    make sure pattern and filename pointer exist
    open file for writing
    
    if pattern does not exist or contains no stitches, error
    
    check for end stitch, add if not present. // This is ensure in our design and likely unecessary.
    
    flip the pattern vertically
    
    scale pattern by 10
    
    //add scale method to math package
    //it will look like this
    //for each stitch in the pattern
    //{
    //    stitchx = stitchx * scale factor
    //    stitchy = stitchy * scale factor
    //}
    
    write #PES0001 as string literal to file
    
    //Write PECPointer
    write 0x00 to file as int
    
    write 0x01 to file as short
    write 0x01 to file as short
    
    //Write Object count
    write 0x01 to file as short
    write 0xffff to file as short //Labeled as command?
    write 0x00 to file as short //Labeled as unknown?
    
    call PesWriteEmbOneSection(pattern, file)
    call pesWriteSewSegSection(pattern, file)
    
    pecLocation = current file position
    move from beginning of file to 0x08 in the file (position where pec location is recorded)
    
    write (pecLocation & 0xFF) to file as unsigned char
    write (pecLocation >> 8) & 0xFF to file as unsigned char
    write (pecLocation >> 16) & 0xFF to file as unsigned char
    
    move from end of file to 0x00 in the file
    
    call writePecStitches(pattern, file, filename)
    
    close the file
    
    */
    
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
  
    /*  pesWriteEmbOneSection(pattern, file pointer)
    
    write 0x07 to file as short
    write "CEmbOne" to file as literal
    
    calculate the bounding box of the pattern
    
    write 0 to file as short
    write 0 to file as short
    write 0 to file as short
    write 0 to file as short
    
    write 0 to file as short
    write 0 to file as short
    write 0 to file as short
    write 0 to file as short
    
    //Write AffineTransform (not sure how this is used yet) From research
    //an affine transform is used to preserve line symetry when scaling. Perhaps
    //this will be used for displaying the image to a screen on the machine.
    
    write 1.0 as float to file
    write 0.0 as float to file
    write 0.0 as float to file
    write 1.0 as float to file
    write (bounding box width - hoopWidth) / 2 to file
    write (bounding box height + hoopHeight) / 2 to file //Notice addition here not subtraction
    
    write 1 as short to file
    write 0 as short to file //Translate X
    write 0 as short to file //Translate Y
    write bounding rect width as short to file
    write bounding rect height as short to file
    
    for(i from = to 8)
    {write 0 as byte to file}
    
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

