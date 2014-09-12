package com.example.murilo.myandroidsandbox.xmlparse.pullparse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Murilo on 08/09/2014.
 */
interface ResultsCallBack {

    public void onPreExecute();

    public void onProgressUpdate();

    public void onPostExecute(ArrayList<HashMap<String, String>> result);
}
