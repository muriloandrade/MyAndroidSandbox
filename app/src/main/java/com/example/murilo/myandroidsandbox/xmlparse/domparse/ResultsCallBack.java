package com.example.murilo.myandroidsandbox.xmlparse.domparse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Murilo on 08/09/2014.
 */
interface ResultsCallBack {

    public void onPreExecute();

    public void onPostExecute(ArrayList<HashMap<String, String>> result);
}
