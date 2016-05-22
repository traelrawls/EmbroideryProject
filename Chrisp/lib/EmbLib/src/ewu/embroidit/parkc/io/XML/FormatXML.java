package ewu.embroidit.parkc.io.XML;

import ewu.embroidit.parkc.pattern.EmbPattern;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park  (christopherpark@eagles.ewu.edu)
 */
public class FormatXML //implements ValidationEventHandler
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
        this.pattern = new EmbPattern();
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(EmbPattern.class);
            Unmarshaller um = context.createUnmarshaller();
            
            this.pattern = (EmbPattern) um.unmarshal(file);
        }
        catch(Exception e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("could not load data from xml file:\n"
                                + file.getPath());
            
            alert.showAndWait();
        }
        
        return this.pattern;
    }
    
    /*-----------------------------------------------------------------------*/
    
    
    /*-----------------------------------------------------------------------*/
    
    public void saveFile(EmbPattern pattern, File file)
    {
        
        XMLPatternAdapter patternAdapter = new XMLPatternAdapter(pattern);
        
        
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(XMLPatternAdapter.class,
                    XMLStitchAdapter.class, XMLThreadAdapter.class, XMLShapeAdapter.class);
            
            System.err.println("Initialized context.");
            
            Marshaller m = context.createMarshaller();
            
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(patternAdapter, file);
            m.marshal(patternAdapter, System.err);
        }
        catch(Exception e)
        {
            System.err.println(e);
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("could not save data to xml file:\n"
                                + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
