package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.EmbUtil;
import ewu.embroidit.parkc.util.math.EmbMath;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;

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
    
    public static PECEncoder getInstance()
    { return PECEncoderHolder.INSTANCE; }
    
    /*-----------------------------------------------------------------------*/
    
    private static class PECEncoderHolder
    { private static final PECEncoder INSTANCE = new PECEncoder(); }
    
    /*-----------------------------------------------------------------------*/
    public void writeStitches(RandomAccessFile fileStream, String fileName,
            List<A_EmbShapeWrapper> wrapperListList, 
            List<EmbStitch> stitchList) throws IOException
    {
        int trimPoint, padSize, numColors, colorIndex;
        int height, width;
        long graphicsOffsetLocation;
        List<Color> colorList;
        BoundingBox bounds;
        
        bounds = EmbMath.calcBoundingRect(stitchList);
        
        //Trim off file extension
        trimPoint = fileName.lastIndexOf(".");
        if(trimPoint > 0)
            fileName = fileName.substring(0, trimPoint);
        
    
        fileStream.writeBytes("LA:");
        fileStream.writeBytes(fileName);
        
        padSize = 16 - fileName.length();
        if(padSize > 0)
            this.padBytes(fileStream, 0x20, padSize);
            
        fileStream.writeByte(0x0D);
        this.padBytes(fileStream, 0x20, 12);
        fileStream.writeByte(0xFF);
        fileStream.writeByte(0x00);
        fileStream.writeByte(0x06);
        fileStream.writeByte(0x26);
        this.padBytes(fileStream, 0x20, 12);
        
        numColors = EmbMath.colorCount(wrapperListList);
        fileStream.writeByte(numColors - 1);
        colorList = EmbUtil.getExportColorList(wrapperListList);
        
        for(int i = 0; i < numColors; i++)
        {
            colorIndex = EmbMath.approximateColorIndex(colorList.get(i));
            fileStream.writeByte(colorIndex);
        }
        
        this.padBytes(fileStream, 0x20, (0x1CF - numColors));
        fileStream.writeShort(0x0000);
        
        graphicsOffsetLocation = fileStream.getFilePointer();
        fileStream.writeByte(0x00);
        fileStream.writeByte(0x00);
        fileStream.writeByte(0x00);
        fileStream.write(0x31);
        fileStream.write(0xFF);
        fileStream.write(0xF0);
        
        height = EmbMath.roundDouble(bounds.getHeight());
        width = EmbMath.roundDouble(bounds.getWidth());
        fileStream.writeShort(width);
        fileStream.writeShort(height);
        fileStream.writeShort(0x1E0);
        fileStream.writeShort(0x1B0);
        fileStream.writeShort(0x9000 | -EmbMath.roundDouble(bounds.getMinX()));
        fileStream.writeShort(0x9000 | -EmbMath.roundDouble(bounds.getMinY()));
        
        //pec encode (Method)
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes the value to the file stream the given number of times.
     * @param fileStream RandomAccessFile
     * @param value int
     * @param amount int
     * @throws IOException 
     */
    private void padBytes(RandomAccessFile fileStream, int value, 
            int amount) throws IOException
    {
        for(int i = 0; i < value; i++)
            fileStream.writeByte(value);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /*    
    
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
