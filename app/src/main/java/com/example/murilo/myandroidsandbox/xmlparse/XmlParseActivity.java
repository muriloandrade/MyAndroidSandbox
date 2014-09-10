package com.example.murilo.myandroidsandbox.xmlparse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.murilo.myandroidsandbox.R;

public class XmlParseActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView xmlListView;
    Intent intent;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);

        xmlListView = (ListView) findViewById(R.id.xmlParseListview);
        xmlListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0: className = ".xmlparse.domparse.XmlDomParseActivity";
                break;
            case 1: className = ".xmlparse.saxparse.XmlSaxParseActivity";
                break;
            case 2: className = ".xmlparse.staxparse.XmlStaxParseActivity";
                break;
        }

        try {
            intent = new Intent(this, Class.forName(getPackageName() + className));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
