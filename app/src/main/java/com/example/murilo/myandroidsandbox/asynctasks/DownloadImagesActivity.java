package com.example.murilo.myandroidsandbox.asynctasks;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.murilo.myandroidsandbox.Msg;
import com.example.murilo.myandroidsandbox.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadImagesActivity extends Activity implements AdapterView.OnItemClickListener {

    EditText imageUrlEdit;
    ListView itemsList;
    ArrayAdapter<String> adapter;
    String[] imagesUrls;
    ProgressBar progressBar;
    DownloadImageTask myTask = null;
    File imageFile = null;

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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        imageUrlEdit.setText(imagesUrls[position]);
    }

    public void downloadImage(View view) {

        if (myTask == null) {
            myTask = new DownloadImageTask();
            myTask.execute(imageUrlEdit.getText().toString());
        } else {
            Msg.t(this, "Wait until download is finished");
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Void> {

        URL imageUrl;

        HttpURLConnection connection;
        InputStream is;
        FileOutputStream fos;
        File imagesDir;
        int fileTotalSize;
        int totalRead = 0;
        int ratio = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressBar.getVisibility() == View.GONE) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }


        @Override
        protected Void doInBackground(String... params) {

            try {
                imageUrl = new URL(params[0]);
                connection = (HttpURLConnection) imageUrl.openConnection();
                is = connection.getInputStream();

                if (!((fileTotalSize = connection.getContentLength()) > 0)) {
                    progressBar.setIndeterminate(true);
                } else {
                    progressBar.setIndeterminate(false);
                }

                imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_" + Uri.parse(params[0]).getLastPathSegment();
                imageFile = new File(imagesDir, imageFileName);

                fos = new FileOutputStream(imageFile);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    if(!myTask.isCancelled()) {
                        fos.write(buffer, 0, read);
                        totalRead += read;
                        ratio = (int) (((double) totalRead / fileTotalSize) * 100);
                        publishProgress(ratio);
                    } else {
                        break;
                    }
                }


            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) connection.disconnect();
                if (is != null) try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (fos != null) try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            if (fileTotalSize > 0 ) {
                progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressBar.isIndeterminate()) {
                progressBar.setIndeterminate(false);
            }
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);
            myTask = null;
            Msg.t(getBaseContext(), "Image successfully downloaded");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myTask != null && myTask.cancel(true)) {
            if (imageFile != null ) imageFile.delete();
            Msg.t(this, "Download was interrupted");
            myTask = null;
        }
    }
}
