package ewu.embroidit.parkc.io;

import java.util.List;
import javafx.scene.paint.Color;

/*-----------------------------------------------------------------------*/
/**
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class PECDecoder
{
    /*-----------------------------------------------------------------------*/
    
    private List<Color> COLOR_LIST;
    
    /*-----------------------------------------------------------------------*/
    private PECDecoder()
    {
        this.createColorlist();
    }
    
    public static PECDecoder getInstance()
    {
        return PECDecoderHolder.INSTANCE;
    }
    
    private static class PECDecoderHolder
    {
        private static final PECDecoder INSTANCE = new PECDecoder();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Generates the color list used for stitches.
     */
    private void createColorlist()
    {
        COLOR_LIST.add(Color.rgb(0, 0, 0));             //Unknown           (0)
        COLOR_LIST.add(Color.rgb(14, 31, 124));         //Prussian Blue     (1)
        COLOR_LIST.add(Color.rgb(10, 85, 163));         //Blue              (2)
        COLOR_LIST.add(Color.rgb(0, 135, 119));         //Teal Green        (3)
        COLOR_LIST.add(Color.rgb(75, 107, 175));        //Cornflower Blue   (4)
        COLOR_LIST.add(Color.rgb(237, 23, 31));         //Red               (5)
        COLOR_LIST.add(Color.rgb(209, 92, 0));          //Reddish Brown     (6)
        COLOR_LIST.add(Color.rgb(145, 54, 151));        //Magenta           (7)
        COLOR_LIST.add(Color.rgb(228, 154, 203));       //Light Lilac       (8)
        COLOR_LIST.add(Color.rgb(145, 95, 172));        //Lilac             (9)
        COLOR_LIST.add(Color.rgb(158, 214, 125));       //Mint Green        (10)
        COLOR_LIST.add(Color.rgb(232, 169,   0));       //Deep Gold         (11)
        COLOR_LIST.add(Color.rgb(254, 186,  53));       //Orange            (12)
        COLOR_LIST.add(Color.rgb(255, 255,   0));       //Yellow            (13)
        COLOR_LIST.add(Color.rgb(112, 188,  31));       //Lime Green        (14)
        COLOR_LIST.add(Color.rgb(186, 152,   0));       //Brass             (15)
        COLOR_LIST.add(Color.rgb(168, 168, 168));       //Silver            (16)
        COLOR_LIST.add(Color.rgb(125, 111,   0));       //Russet Brown      (17)
        COLOR_LIST.add(Color.rgb(255, 255, 179));       //Cream Brown       (18)
        COLOR_LIST.add(Color.rgb(79,  85,  86));        //Pewter            (19)
        COLOR_LIST.add(Color.rgb(0,   0,   0));         //Black             (20)
        COLOR_LIST.add(Color.rgb(11,  61, 145));        //Ultramarine       (21)
        COLOR_LIST.add(Color.rgb(119,   1, 118));       //Royal Purple      (22)
        COLOR_LIST.add(Color.rgb(41,  49,  51));        //Dark Gray         (23)
        COLOR_LIST.add(Color.rgb(42,  19,   1));        //Dark Brown        (24)
        COLOR_LIST.add(Color.rgb(246,  74, 138));       //Deep Rose         (25)
        COLOR_LIST.add(Color.rgb(178, 118,  36));       //Light Brown       (26)
        COLOR_LIST.add(Color.rgb(252, 187, 197));       //Salmon Pink       (27)
        COLOR_LIST.add(Color.rgb(254,  55,  15));       //Vermillion        (28)
        COLOR_LIST.add(Color.rgb(240, 240, 240));       //White             (29)
        COLOR_LIST.add(Color.rgb(106,  28, 138));       //Violet            (30)
        COLOR_LIST.add(Color.rgb(168, 221, 196));       //Seacrest          (31)
        COLOR_LIST.add(Color.rgb(37, 132, 187));        //Sky Blue          (32)
        COLOR_LIST.add(Color.rgb(254, 179,  67));       //Pumpkin           (33)
        COLOR_LIST.add(Color.rgb(255, 243, 107));       //Cream Yellow      (34)
        COLOR_LIST.add(Color.rgb(208, 166,  96));       //Khaki             (35)
        COLOR_LIST.add(Color.rgb(209,  84,   0));       //Clay Brown        (36)
        COLOR_LIST.add(Color.rgb(102, 186,  73));       //Leaf Green        (37)
        COLOR_LIST.add(Color.rgb(19,  74,  70));        //Peacock Blue      (38)
        COLOR_LIST.add(Color.rgb(135, 135, 135));       //Gray              (39)
        COLOR_LIST.add(Color.rgb(216, 204, 198));       //Warm Gray         (40)
        COLOR_LIST.add(Color.rgb(67,  86,   7));        //Dark Olive        (41)
        COLOR_LIST.add(Color.rgb(253, 217, 222));       //Flesh Pink        (42)
        COLOR_LIST.add(Color.rgb(249, 147, 188));       //Pink              (43)
        COLOR_LIST.add(Color.rgb(0,  56,  34));         //Deep Green        (44)
        COLOR_LIST.add(Color.rgb(178, 175, 212));       //Lavender          (45)
        COLOR_LIST.add(Color.rgb(104, 106, 176));       //Wisteria Violet   (46)
        COLOR_LIST.add(Color.rgb(239, 227, 185));       //Beige             (47)
        COLOR_LIST.add(Color.rgb(247,  56, 102));       //Carmine           (48)
        COLOR_LIST.add(Color.rgb(181,  75, 100));       //Amber Red         (49)
        COLOR_LIST.add(Color.rgb(19,  43,  26));        //Olive Green       (50)
        COLOR_LIST.add(Color.rgb(199,   1,  86));       //Dark Fuschia      (51)
        COLOR_LIST.add(Color.rgb(254, 158,  50));       //Tangerine         (52)
        COLOR_LIST.add(Color.rgb(168, 222, 235));       //Light Blue        (53)
        COLOR_LIST.add(Color.rgb(0, 103,  62));         //Emerald Green     (54)
        COLOR_LIST.add(Color.rgb(78,  41, 144));        //Purple            (55)
        COLOR_LIST.add(Color.rgb(47, 126,  32));        //Moss Green        (56)
        COLOR_LIST.add(Color.rgb(255, 204, 204));       //Flesh Pink        (57)
        COLOR_LIST.add(Color.rgb(255, 217,  17));       //Harvest Gold      (58)
        COLOR_LIST.add(Color.rgb(9,  91, 166));         //Electric Blue     (59)
        COLOR_LIST.add(Color.rgb(240, 249, 112));       //Lemon Yellow      (60)
        COLOR_LIST.add(Color.rgb(227, 243,  91));       //Fresh Green       (61)
        COLOR_LIST.add(Color.rgb(255, 153,   0));       //Orange            (62)
        COLOR_LIST.add(Color.rgb(255, 240, 141));       //Cream Yellow      (63)
        COLOR_LIST.add(Color.rgb(255, 200, 200));       //Applique          (64)
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the color in the color list that corresponds with the given
     * index
     * 
     * @param index int
     * @return Color
     */
    public Color getColorByIndex(int index)
    {
        return COLOR_LIST.get(index);
    }
    
    /*-----------------------------------------------------------------------*/
}
