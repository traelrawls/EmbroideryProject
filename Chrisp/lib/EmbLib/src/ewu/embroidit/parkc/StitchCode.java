package ewu.embroidit.parkc;

/**
 * This class is meant to hold StitchCode values and should not be instantiated.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class StitchCode
{
    
    public static final int NORMAL = 0;     //Stitch to (xx, yy)
    public static final int JUMP = 1;       //move to (xx, yy)
    public static final int TRIM = 2;       //trim + move to (xx, yy)
    public static final int STOP = 4;       //pause machine for thread change
    public static final int SEQUIN = 8;     //sequin
    public static final int END = 16;       //end of program
    
    private StitchCode()
    {}
    
    public static StitchCode getInstance()
    {
        return StitchCodeHolder.INSTANCE;
    }
    
    private static class StitchCodeHolder
    {
        private static final StitchCode INSTANCE = new StitchCode();
    }
}
