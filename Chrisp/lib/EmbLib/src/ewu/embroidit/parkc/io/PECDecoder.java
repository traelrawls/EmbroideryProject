package ewu.embroidit.parkc.io;

/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class PECDecoder
{
    
    private PECDecoder()
    {
    }
    
    public static PECDecoder getInstance()
    {
        return PECDecoderHolder.INSTANCE;
    }
    
    private static class PECDecoderHolder
    {

        private static final PECDecoder INSTANCE = new PECDecoder();
    }
}
