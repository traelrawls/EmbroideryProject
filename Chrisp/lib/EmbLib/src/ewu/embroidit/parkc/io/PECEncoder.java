package ewu.embroidit.parkc.io;

/**
 * Singleton class used to write stitch information to the PEC section
 * of a .PES file.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class PECEncoder
{
    /*-----------------------------------------------------------------------*/
    
    /*-----------------------------------------------------------------------*/
    
    private PECEncoder()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    private static PECEncoder getInstance()
    { return PECEncoderHolder.INSTANCE; }
    
    /*-----------------------------------------------------------------------*/
    
    private static class PECEncoderHolder
    { private static final PECEncoder INSTANCE = new PECEncoder(); }
    
    /*-----------------------------------------------------------------------*/
    
    /* writePecStitches
    
    //Embroidermodder spends the first several lines of this method
    //stripping the file name out of a file path.
    
    write "LA:" as literal to file
    write filename as literal to file (excluding extension)
    
    if file length is less than 16 pad up to 16 with binary writes of 0x20
    write 0x0D as byte to file
    pad write 12 more 0x20 as byte
    4 more byte writes to file as follows:
    0xFF
    0x00
    0x06
    0x26
    pat write 12 more 0x20 as byte
    
    get the current thread count
    write threadcount-1 to file as binary
    
    for each thread in the thread count
    {
        write that thread color as byte to file
    }
    
    for (i < 0x1CF - thread count)
    {
        write 0x20 as byte (more padding)
    }
    
    write 0x0000 as short
    
    get current file pointer position (graphics offset location)
    
    //Placeholder bytes
    write bytes:
    0x00
    0x00
    0x00
    
    write bytes:
    0x31
    0xFF
    0xF0
    
    bounds = calculate bounding rect
    
    get height and width of bounds
    
    write width as short //X size
    write height as short //Y size
    
    write 0x1E0 as short
    write 0x1B0 as short
    
    write 0x9000 | -left bounds as unsigned short eitshifted eight //Rounded
    write 0x9000 | -top bounds as unsigned short bitshifted eight //Rounded
    
    
    ////////////////////////////////////////
    //USHORTBE example code  
    void binaryWriteUShortBE(EmbFile* file, unsigned short data)
    {
        embFile_putc((data >> 8) & 0xFF, file);
        embFile_putc(data & 0xFF, file);
    }
    ///////////////////////////////////////
    
    call pecEncode(file, Pattern)
    
    graphics offset value = current file position - graphics offsetlocation + 2
    
    move to graphics offset location from the beginning of the file
    
    write graphics offset value & 0xFF to file as byte
    write (graphics offset value >> 8) & 0xFF to file as byte
    write (graphics offset value >> 16 ) & 0xFF to file as byte
    
    move to position 0x00 from the end of the file
    
    
    //Writing all colors
    
    clear image (38 x 48 array of unsigned chars) //TO PORT
    
    yFactor = 32.0 / height;
    xFactor = 42.0 / width
    
    while(there is a stitch)
    {
        x = ((stitch x - left bounds) * xFactor) + 3 //Rounded
        y = ((stitch y - top bounds) * yFactor) + 3 //Rounded
        image[y][x] = 1;
    }
    
    write image(file pointer, image array) //TO PORT
    
    
    //Writing each individual color
    
    for(each thread in thread count)
    {
        clear image(image array) //TO PORT
    
        while(there is a stitch)
        {
            x = ((stitch x - left bounds) * xFactor) + 3 //Rounded
            y = ((stitch y - top bounds) * yFactor) + 3 //Rounded
            if(current stitches flag & STOP
            {
                move to next stitch
                break;
            }
            image[y][x] = 1
        }
    
    writeImage(file pointer, image array) //TO PORT
    
    }
    
    */
    
    /*-----------------------------------------------------------------------*/
    
    /* pecEncode
    
    while(there is a stitch)
    {
        stopCode = 2
        thisX = thisY = 0.0;
    
        deltaX = stitchX - thisX
        deltaY = stitchY - thisY
        thisX += (double)deltaX;
        thisY += (double)deltaY;
    
        if(stitch flag & STOP)
        {
            pecEncodeStop(file pointer, stopCode) //ACCOUNT FOR THIS
            if stop code is 2 make it 1
            else make it 2
        }
        else if(stitch & END)
        {
            write 0xFF to file as byte
            break;
        }
    else if(deltaX < 63 && deltaX > -64 &&
            deltaY < 63 && deltaY > -64 &&
            (!(s.flags & (JUMP | TRIM))))
        { 
    
            //NOTE: Ternary Operators inside these file writes
            binaryWriteByte(file, (deltaX < 0) ?
                (unsigned char)(deltaX + 0x80) : (unsigned char)deltaX);
            binaryWriteByte(file, (deltaY < 0) ?
                (unsigned char)(deltaY + 0x80) : (unsigned char)deltaY);
        }
        else
        {
        pecEncodeJump(file pointer, deltaX, stitch flag);
        pecEncodeJump(file pointer, deltay, stitch flag);
        }
    }
    
    */
    
    /*-----------------------------------------------------------------------*/
    
    /* encode stop(file pointer, unsigned char stopCode)
    
    check to make sure file pointer is valid
    write bytes to file:
    0xFE
    0xB0
    stopCode
    
    */
    
    /*-----------------------------------------------------------------------*/
    
    /* encode jump(file pointer, int delta, int stitchCode
    
    outputval = abs(delta) & 0x7FF
    UInt orpart = 0x80
    
    validate file pointer
    if(stitchCode & TRIM)
    {
        orpart |= 0x20
    }
    else if(stitchCode & Jump)
    {
        orPart |= 0x10
    }
    
    
    if(delta < 0)
    {
        outputval = delta + 0x1000 & 0x7FF
        outputval |= 0x800
    }
    */
    
    /*-----------------------------------------------------------------------*/
}
