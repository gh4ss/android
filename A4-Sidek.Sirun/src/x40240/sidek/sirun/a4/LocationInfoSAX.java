package x40240.sidek.sirun.a4;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public final class LocationInfoSAX extends DefaultHandler {
    private static final String LOGTAG = LocationInfoSAX.class.getSimpleName();
    private static final boolean DEBUG = false;
    
	private final LocationInfo  locationInfo = new LocationInfo();
	private final StringBuilder buffer = new StringBuilder();

	public LocationInfoSAX() {}
	
    public LocationInfo parse (byte[] bytes) throws Exception {
		final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		parse(is);
		is.close();
		return locationInfo;
	}
    
    public LocationInfo parse (InputStream is) throws Exception {
 		SAXParserFactory factory = SAXParserFactory.newInstance();
 		SAXParser saxParser = factory.newSAXParser();
 		saxParser.parse(is, this);
 		return locationInfo;
 	}
    
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
    public void startDocument () throws SAXException {
		super.startDocument();
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
    public void endDocument () throws SAXException {
		super.endDocument();
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement (String uri, String localName, String name, Attributes attributes)	throws SAXException {
		super.startElement (uri, localName, name, attributes);
	}
	
	/* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters (char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        buffer.append(ch, start, length);
    }
    
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement (String uri, String localName, String name) throws SAXException {
        if (DEBUG) Log.d (LOGTAG, "endElement="+localName);
        super.endElement(uri, localName, name);
        parseEndElement (uri, localName, name, buffer.toString());
        buffer.delete(0, buffer.length());
	}
    
	//  Encapsulate actual parsing specifics here.
    private void parseEndElement (String uri, String localName, String name, String value)
    {
        if (DEBUG) {
            Log.d (LOGTAG, "localName="+localName);
            Log.d (LOGTAG, "value="+value);
        }
        value = value.trim();  //  may not be appropriate for all parsing situations

        if (localName.equals("latitude")) {
            locationInfo.setLatitude(value);
            return;
        }
        if (localName.equals("longtitude")) {
            locationInfo.setLongitude(value);
            return;
        }
        if (localName.equals("altitude units")) {
        	locationInfo.setAltitudeUnit(value);
        }
        if (localName.equals("address")) {
        	locationInfo.setAddress(value);
        }
        if (localName.equals("description")) {
        	locationInfo.setDescription(value);
        }

    }
}
