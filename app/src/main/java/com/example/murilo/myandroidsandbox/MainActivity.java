package com.example.murilo.myandroidsandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openProject(View view) {

        switch (view.getId()) {

            case R.id.btnCustomActionBarActivity: startProject("customactionbar.CustomActionBarActivity");
                break;
            case R.id.btnNinePatchButtonActivity: startProject("ninepatchbutton.NinePatchButtonActivity");
                break;
            case R.id.btnStorage: startProject("storage.StorageActivity");
                break;
            case R.id.btnIntents: startProject("intents.IntentsActivity");
                break;
            case R.id.btnAsyncTasks: startProject("asynctasks.AsyncTasksActivity");
                break;
            case R.id.btnLinkify: startProject("linkify.LinkifyActivity");
                break;
            case R.id.btnXmlParse: startProject("xmlparse.XmlParseActivity");
                break;
            case R.id.btnMultimedia: startProject("multimedia.MultimediaActivity");
                break;
        }
    }

    private void startProject(String cls) {
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName("com.example.murilo.myandroidsandbox." + cls));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
