package com.example.murilo.myandroidsandbox.xmlparse.domparse;

import android.os.AsyncTask;

import com.example.murilo.myandroidsandbox.Msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Murilo on 08/09/2014.
 */
public class MyTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

    ResultsCallBack callBack = null;

    public MyTask(ResultsCallBack callBack) {
        this.callBack = callBack;
    }

    public void onAttach(ResultsCallBack callBack) {
        this.callBack = callBack;
    }

    public void onDetach() {
        this.callBack = null;
    }

    @Override
    protected void onPreExecute() {
        if (callBack != null) {
            callBack.onPreExecute();
        }
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

        URL url;
        HttpURLConnection connection = null;
        InputStream is = null;
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

        try {

            String downloadUrlTC = "http://feeds.feedburner.com/techcrunch/";
            url = new URL(downloadUrlTC);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            is = connection.getInputStream();
            result = processXML(is);

        } catch (Exception e) {

            Msg.l(e.toString());
        } finally {

            if (connection != null) {

                connection.disconnect();
            }
            if (is != null) {

                try {

                    is.close();
                } catch (IOException e) {

                    Msg.l(e.toString());
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {

        if (callBack != null) {
            callBack.onPostExecute(result);
        }
    }

    private ArrayList<HashMap<String, String>> processXML(InputStream is) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(is);
        Element rootElement = document.getDocumentElement();

        NodeList itemsList = rootElement.getElementsByTagName("item");
        NodeList childList;
        Node currentItem;
        Node currentChild;
        int count = 0;
        HashMap<String, String> currentHashMap;
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < itemsList.getLength(); i++) {
            currentItem = itemsList.item(i);
            childList = currentItem.getChildNodes();

            currentHashMap = new HashMap<String, String>();

            for (int j = 0; j < childList.getLength(); j++) {
                currentChild = childList.item(j);

                if (currentChild.getNodeName().equalsIgnoreCase("title")) {

                    currentHashMap.put("title", currentChild.getTextContent());
                }
                if (currentChild.getNodeName().equalsIgnoreCase("pubDate")) {

                    currentHashMap.put("pubDate", currentChild.getTextContent());
                }
                if (currentChild.getNodeName().equalsIgnoreCase("description")) {

                    currentHashMap.put("description", currentChild.getTextContent());
                }

                if(currentChild.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                    count ++;
                    if (count == 2) {
                        currentHashMap.put("imageURL", currentChild.getAttributes().item(0).getTextContent());
                    }
                }
            }
            count = 0;
            if(!currentHashMap.isEmpty()) {
                result.add(currentHashMap);
            }
        }
        return result;
    }
}
