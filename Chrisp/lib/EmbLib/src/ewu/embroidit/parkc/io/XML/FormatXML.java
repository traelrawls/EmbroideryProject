package ewu.embroidit.parkc.io.XML;

import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.pattern.EmbThread;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Shape;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/*-----------------------------------------------------------------------*/
/**
 * Formats the information contained in a pattern file to and from XML.
 * @author Chris Park  (christopherpark@eagles.ewu.edu)
 */
public class FormatXML
{
    
    /*-----------------------------------------------------------------------*/
    
    private EmbPattern pattern;
    
    /*-----------------------------------------------------------------------*/
    
    private FormatXML()
    {}
    
    public static FormatXML getInstance()
    { return FormatXMLHolder.INSTANCE; }
    
    private static class FormatXMLHolder
    { private static final FormatXML INSTANCE = new FormatXML(); }
    
    /*-----------------------------------------------------------------------*/
    
    public EmbPattern loadFile(File file)
    {
        XMLPatternAdapter patternAdapter = new XMLPatternAdapter();
        List<XMLStitchAdapter> stitchAdapterList = new ArrayList<>(); 
        List<XMLThreadAdapter> threadAdapterList = new ArrayList<>(); 
        List<XMLShapeAdapter> shapeAdapterList = new ArrayList<>(); 
        List<EmbStitch> stitchList = new ArrayList<>();
        List<EmbThread> threadList = new ArrayList<>();
        List<Shape> shapeList = new ArrayList<>();
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(XMLPatternAdapter.class,
                    XMLStitchAdapter.class, XMLThreadAdapter.class, XMLShapeAdapter.class);
            Unmarshaller um = context.createUnmarshaller();
            
            patternAdapter = (XMLPatternAdapter) um.unmarshal(file);
        }
        catch(Exception e)
        {
            System.err.println(e);
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from xml file:\n"
                                + file.getPath());
            
            alert.showAndWait();
        }
        
        this.pattern = new EmbPattern();
        this.pattern.setColorIndex(patternAdapter.getColorIndex());
        this.pattern.setLastX(patternAdapter.getLastX());
        this.pattern.setLastY(patternAdapter.getLastY());
        this.pattern.setHomePoint(new Point2D(patternAdapter.getHomePointX(),
                                              patternAdapter.getHomePointY()));
        this.pattern.setName(patternAdapter.getName());
        
        stitchAdapterList = patternAdapter.getStitchAdapterList();
        threadAdapterList = patternAdapter.getThreadAdapterList();
        shapeAdapterList = patternAdapter.getShapeAdapterList();
        
        //get stitch adapter list
            //for each, recreate a stitch and add it to the list
        for(XMLStitchAdapter stitch : stitchAdapterList)
        {
            
        }
        
            
        //get thread adapter list
            //for each, reacreate thread and add to list
        
        
        //get the shape adapter list
            //for each, recreate the shape
            //recreate its wrapper
            //add both to their respective lists
            
        //After pattern reconstruction, we'll need to re apply fill
        //algorithms and redraw the pattern.
        
        
        return this.pattern;
            
    }

    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates an XML bound pattern adapter that contains pattern information
     * necessary for saving a file to XML.
     * @param pattern EmbPattern - The pattern to save
     * @param file File - The file to write the data to.
     */
    public void saveFile(EmbPattern pattern, File file)
    {
        XMLPatternAdapter patternAdapter = new XMLPatternAdapter(pattern);
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(XMLPatternAdapter.class,
                    XMLStitchAdapter.class, XMLThreadAdapter.class, XMLShapeAdapter.class);
            Marshaller m = context.createMarshaller();
            
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(patternAdapter, file);
        }
        catch(Exception e)
        {
            System.err.println(e);
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to xml file:\n"
                                + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
