package model;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * @author Hendrik Werner
 * @author Jasper Haasdijk
 * @author Janne van Den Hout
 * @author Arne Diehl
 */
public class UrlBackgroundTask extends AsyncTask<URLConnection, Void, Document> {

    private static DocumentBuilder getBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Document parse(URLConnection connection) {
        try {
            return getBuilder().parse(connection.getInputStream());
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Document doInBackground(URLConnection... params) {
        return parse(params[0]);
    }
}
