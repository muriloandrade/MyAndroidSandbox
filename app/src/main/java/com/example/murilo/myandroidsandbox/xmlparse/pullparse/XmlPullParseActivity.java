package com.example.murilo.myandroidsandbox.xmlparse.pullparse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.R;

import java.util.ArrayList;
import java.util.HashMap;

public class XmlPullParseActivity extends Activity implements ResultsCallBack {

    private static final String FRAGMENT_TAG = "PlaceholderFragment";
    PlaceholderFragment fragment;
    LinearLayout loadingLayout;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_generic_parse);

        loadingLayout = (LinearLayout) findViewById(R.id.xmlDomParseLoadingLayout);
        listView = (ListView) findViewById(R.id.xmlDomParseListview);

        if (savedInstanceState == null) {
            fragment = new PlaceholderFragment();
            getFragmentManager().beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        } else {
            fragment = (PlaceholderFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate() {

        
    }

    @Override
    public void onPostExecute(ArrayList<HashMap<String, String>> result) {

        loadingLayout.setVisibility(View.GONE);
        MyAdapter adapter = new MyAdapter(this, result);
        listView.setAdapter(adapter);
    }
}

class MyAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> dataSource = new ArrayList<HashMap<String, String>>();
    LayoutInflater layoutInflater;
    Context context;

    MyAdapter(Context context, ArrayList<HashMap<String, String>> dataSource) {
        this.dataSource = dataSource;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String title;
        String pubDate;
        String imageURL;
        HashMap<String, String> currentItem;

        View currentView = convertView;
        Holder holder;

        if (currentView == null) {
            currentView = layoutInflater.inflate(R.layout.xmlparse_custom_row, parent, false);
            holder = new Holder(currentView);
            currentView.setTag(holder);
        } else {
            holder = (Holder) currentView.getTag();
        }

        currentItem = dataSource.get(position);

        title = currentItem.get("title");
        pubDate =  currentItem.get("pubDate");
        imageURL = currentItem.get("imageURL");

        holder.articleTitleText.setText(title);
        holder.articlePublishedDate.setText(pubDate);
        holder.articleDescriptionText.setText(imageURL);
        return currentView;
    }
}

class Holder {

    TextView articleTitleText;
    TextView articlePublishedDate;
    TextView articleDescriptionText;

    Holder(View view) {
        articleTitleText = (TextView) view.findViewById(R.id.articleTitleText);
        articlePublishedDate = (TextView) view.findViewById(R.id.articlePublishedDate);
        articleDescriptionText = (TextView) view.findViewById(R.id.articleDescriptionText);
    }
}