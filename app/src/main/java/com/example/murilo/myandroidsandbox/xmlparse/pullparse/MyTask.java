package com.example.murilo.myandroidsandbox.xmlparse.pullparse;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Murilo on 09/09/2014.
 */
public class MyTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>{

    ResultsCallBack callBack;

    public MyTask(ResultsCallBack callBack) {
        onAttach(callBack);
    }

    public void onAttach(ResultsCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (callBack != null) {
            callBack.onPreExecute();
        }
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            URL url = new URL("http://feeds.feedburner.com/techcrunch/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            is = connection.getInputStream();
            result = processXml(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        callBack.onProgressUpdate();
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
        super.onPostExecute(result);

        if (callBack != null) {
            callBack.onPostExecute(result);
        }
    }

    public void onDetach() {

        if (callBack != null) {
            callBack = null;
        }
    }

    private ArrayList<HashMap<String, String>> processXml(InputStream is) throws Exception{

        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> currentHashmap = null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(is, null);

        int eventType = parser.getEventType();
        String tagText = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {

                if (parser.getName().equals("item")) {
                    currentHashmap = new HashMap<String, String>();
                } else if (parser.getName().equals("media:thumbnail")) {
                    currentHashmap.put("imageURL", parser.getAttributeValue(null, "url"));
                }

            } else if (eventType == XmlPullParser.TEXT) {

                tagText = parser.getText();

            } else if (eventType == XmlPullParser.END_TAG) {

                if (parser.getName().equals("item")) {
                    if (currentHashmap != null) {
                        result.add(currentHashmap);
                    }
                } else if (parser.getName().equals("title")) {
                    if (currentHashmap != null) {
                        currentHashmap.put("title", tagText);
                    }
                } else if (parser.getName().equals("pubDate")) {
                    if (currentHashmap != null) {
                        currentHashmap.put("pubDate", tagText);
                    }
                }
            }
            eventType = parser.next();
        }

        return result;
    }
}
