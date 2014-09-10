package com.example.murilo.myandroidsandbox.asynctasks;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.murilo.myandroidsandbox.Msg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Murilo on 08/09/2014.
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Void> {

    URL imageUrl;
    File imageFile = null;

    HttpURLConnection connection;
    InputStream is;
    FileOutputStream fos;
    File imagesDir;
    int fileTotalSize;
    int totalRead = 0;
    int ratio = 0;
    private Activity activity;

    public DownloadImageTask(Activity activity) {
        onAttach(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //nothing to do here
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            imageUrl = new URL(params[0]);
            connection = (HttpURLConnection) imageUrl.openConnection();
            fileTotalSize = connection.getContentLength();

            is = connection.getInputStream();

            imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_" + Uri.parse(params[0]).getLastPathSegment();
            imageFile = new File(imagesDir, imageFileName);

            fos = new FileOutputStream(imageFile);

            byte[] buffer = new byte[1024];
            int read;
            while (((read = is.read(buffer)) != -1) && !this.isCancelled()) {
                fos.write(buffer, 0, read);
                totalRead += read;

                ratio = (int) (((double) totalRead / fileTotalSize) * 100);
                publishProgress(ratio);
            }


        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
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

        if (activity != null) {
            ((DownloadImagesWithFragmentActivity)activity).updateProgressBar(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (activity != null) {
            ((DownloadImagesWithFragmentActivity)activity).updateProgressBar(0);
            ((DownloadImagesWithFragmentActivity)activity).hideProgressBar();
            Msg.t(activity, "Download complete");
        }
    }

    public void onAttach(Activity activity) {

        this.activity = activity;
    }

    public void onDetach() {
        this.activity = null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (imageFile != null) {
            imageFile.delete();
        }
        if (activity != null) {
            ((DownloadImagesWithFragmentActivity)activity).updateProgressBar(0);
            ((DownloadImagesWithFragmentActivity)activity).hideProgressBar();
        }
    }

    public boolean isIndeterminate() {
        return fileTotalSize == -1;
    }
}
