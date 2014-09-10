package com.example.murilo.myandroidsandbox.xmlparse.saxparse;

import android.os.AsyncTask;

import com.example.murilo.myandroidsandbox.Msg;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
            Msg.l("ERRRRROOOOOOOOOO: " + e.toString());
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

        SaxHandler handler = new SaxHandler();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(is, handler);

        return handler.result;
    }
}
