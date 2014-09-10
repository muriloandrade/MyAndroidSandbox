package com.example.murilo.myandroidsandbox.xmlparse.domparse;

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

public class XmlDomParseActivity extends Activity implements ResultsCallBack {

    PlaceholderFragment taskFragment;
    ListView articlesListView;
    LinearLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_dom_parse);

        articlesListView = (ListView) findViewById(R.id.xmlDomParseListview);
        loadingLayout = (LinearLayout) findViewById(R.id.xmlDomParseLoadingLayout);

        if (savedInstanceState == null) {

            taskFragment = new PlaceholderFragment();
            getFragmentManager().beginTransaction().add(taskFragment, "MyTaskFragment").commit();
        } else {

            taskFragment = (PlaceholderFragment) getFragmentManager().findFragmentByTag("MyTaskFragment");
        }

        taskFragment.startTask();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(ArrayList<HashMap<String, String>> result) {

        loadingLayout.setVisibility(View.GONE);
        articlesListView.setAdapter(new MyAdapter(this, result));
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
