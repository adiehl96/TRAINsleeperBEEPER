package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Hendrik Werner
 */
public class NsApiParser implements NamedLocationProvider {

    private static final String PROTOCOL = "http";
    private static final String HOST = "webservices.ns.nl";
    private static final String ENDPOINT = "/ns-api-stations-v2";
    private static final String ENCODED_AUTH = "YXJuZWRpZWhsOTZAZ21haWwuY29tOlFBY25RR2VBNGowSlNHVENWUmp6NF9BVUpOWkVKX2RsSW1mbGkzZ3FYYi14WlE5eVVBNFl2UQ==";

    private final Collection<NamedLocation> stations;

    private static URLConnection generateURLConnection() {
        try {
            return new URL(PROTOCOL, HOST, ENDPOINT).openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NamedLocation parse(Node n) {
        Element station = (Element) n;
        NamedLocation location = new NamedLocation(getContent(station, "Lang"));
        location.setLatitude(Double.parseDouble(getContent(station, "Lat")));
        location.setLongitude(Double.parseDouble(getContent(station, "Lon")));
        return location;
    }

    /**
     * @param e the element that contains the tag
     * @param tag the tag that contains the content
     * @return the text content of the tag within the element
     */
    private static String getContent(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }

    private static Document getDocument(URLConnection connection) {
        try {
            return new UrlBackgroundTask().execute(connection).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public NsApiParser() {
        stations = new LinkedList<>();
        URLConnection connection = generateURLConnection();
        connection.setRequestProperty("Authorization", "Basic " + ENCODED_AUTH);
        Document doc = getDocument(connection);
        System.out.println(doc);
        NodeList stationNodes = doc.getElementsByTagName("Station");
        System.out.println(stationNodes.getLength());
        for (int i = 0; i < stationNodes.getLength(); i++) {
            stations.add(parse(stationNodes.item(i)));
        }
    }

    @Override
    public Collection<NamedLocation> getLocations() {
        return stations;
    }

}
