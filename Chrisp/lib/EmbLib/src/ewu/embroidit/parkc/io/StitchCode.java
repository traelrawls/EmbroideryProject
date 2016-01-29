package ewu.embroidit.parkc.io;

/*-----------------------------------------------------------------------*/
/**
 * Holds StitchCode values for use in pattern creation. 
 * Should not be instantiated!
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class StitchCode
{
    /*-----------------------------------------------------------------------*/
    
    public static final int NORMAL = 0;     //Stitch to (xx, yy)
    public static final int JUMP = 1;       //Move to (xx, yy)
    public static final int TRIM = 2;       //Trim + move to (xx, yy)
    public static final int STOP = 4;       //Pause machine for thread change
    public static final int SEQUIN = 8;     //Sequin
    public static final int END = 16;       //End of program
    
    /*-----------------------------------------------------------------------*/
    
    private StitchCode()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    public static StitchCode getInstance()
    {
        return StitchCodeHolder.INSTANCE;
    }
    
    /*-----------------------------------------------------------------------*/
    
    private static class StitchCodeHolder
    {
        private static final StitchCode INSTANCE = new StitchCode();
    }
    
    /*-----------------------------------------------------------------------*/
}
