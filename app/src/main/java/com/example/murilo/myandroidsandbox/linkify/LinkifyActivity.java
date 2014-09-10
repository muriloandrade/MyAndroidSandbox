package com.example.murilo.myandroidsandbox.linkify;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.R;

import java.util.regex.Pattern;

public class LinkifyActivity extends Activity {

    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkify);

        txtView = (TextView) findViewById(R.id.txtView1);

        //Pattern and action for CamelCase
        Pattern matcher = Pattern.compile("\\b[A-Z]+[a-z0-9]+[A-Z]+[A-za-z0-9]+\\b");
        String viewURL = "http://en.wikipedia.org/wiki/CamelCase";

        Linkify.addLinks(txtView, Linkify.ALL);
        Linkify.addLinks(txtView, matcher, viewURL);
    }
}
