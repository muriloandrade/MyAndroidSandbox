package com.example.murilo.myandroidsandbox.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.murilo.myandroidsandbox.Msg;
import com.example.murilo.myandroidsandbox.R;

public class DownloadImagesWithFragmentActivity extends Activity implements AdapterView.OnItemClickListener {

    EditText imageUrlEdit;
    ListView itemsList;
    ArrayAdapter<String> adapter;
    String[] imagesUrls;
    ProgressBar progressBar;
    NonUIFragmentTask fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_images);

        imageUrlEdit = (EditText) findViewById(R.id.imageUrlEdit);
        itemsList = (ListView) findViewById(R.id.itemsList);
        itemsList.setOnItemClickListener(this);
        imagesUrls = getResources().getStringArray(R.array.async_imagesUrls);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String[] items = getResources().getStringArray(R.array.async_items);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        itemsList.setAdapter(adapter);

        if (savedInstanceState == null) {
            fragment = new NonUIFragmentTask();
            getFragmentManager().beginTransaction().add(fragment, "Fragment Task").commit();
        } else {
            fragment = (NonUIFragmentTask) getFragmentManager().findFragmentByTag("Fragment Task");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        imageUrlEdit.setText(imagesUrls[position]);
    }

    public void downloadImage(View view) {
        String url = imageUrlEdit.getText().toString();
        if (!url.isEmpty()) {
            fragment.beginTask(imageUrlEdit.getText().toString());
            setProgressBarIndeterminate(true);
            showProgressBar();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void showProgressBar() {

        if (fragment.myTask != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {

        if (fragment.myTask != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void updateProgressBar(Integer value) {

        if (progressBar.getVisibility() == View.GONE) {
            showProgressBar();
        }
        if (fragment.myTask != null) {
            if (fragment.myTask.isIndeterminate()) {
                if (!progressBar.isIndeterminate()) {
                    progressBar.setIndeterminate(true);
                }
            } else {
                if (progressBar.isIndeterminate()) {
                    progressBar.setIndeterminate(false);
                }
                progressBar.setProgress(value);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (fragment.myTask != null && fragment.myTask.getStatus() == AsyncTask.Status.RUNNING) {
            fragment.myTask.cancel(true);
            Msg.t(this, "Download cancelled");
        }
        super.onDestroy();
    }
}

