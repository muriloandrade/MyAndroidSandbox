package com.example.murilo.myandroidsandbox.xmlparse.pullparse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Murilo on 09/09/2014.
 */
public class SaxHandler extends DefaultHandler {

    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> currentHashmap = null;
    String content = null;
    String imageUrl= null;
    Attributes currentAttributes = null;


    @Override
    // Triggered when the start of tag is found
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (qName.equals("item")) {
            currentHashmap = new HashMap<String, String>();
        }
        if (qName.equals("media:thumbnail")) {
            currentAttributes = attributes;
            imageUrl = currentAttributes.getValue("url");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (currentHashmap != null) {
            if (qName.equals("item")) {
                result.add(currentHashmap);
                currentHashmap = null;
            }
            if (content != null && !content.isEmpty()) {
                if (qName.equals("title")) {
                    currentHashmap.put("title", content);
                }
                if (qName.equals("pubDate")) {
                    currentHashmap.put("pubDate", content);
                }
            }
            if (imageUrl != null && !imageUrl.isEmpty()) {
                if (qName.equals("media:thumbnail")) {
                    currentHashmap.put("imageURL", imageUrl);
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        content = String.copyValueOf(ch, start, length).trim();
    }


}
