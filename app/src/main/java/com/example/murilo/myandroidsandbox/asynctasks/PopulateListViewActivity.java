package com.example.murilo.myandroidsandbox.asynctasks;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.murilo.myandroidsandbox.Msg;

import java.util.ArrayList;

public class PopulateListViewActivity extends ListActivity {

    ArrayAdapter<String> adapter;
    PopulateListViewTask mTask;


    String[] texts = { "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipisicing", "elit",
            "sed", "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna",
            "aliqua", "Ut", "enim", "ad", "minim", "veniam", "quis", "nostrud", "exercitation", "ullamco",
            "laboris", "nisi", "ut", "aliquip", "ex", "ea", "commodo", "consequat", "Duis", "aute",
            "irure", "dolor", "in", "reprehenderit", "in", "voluptate", "velit", "esse", "cillum",
            "dolore", "eu", "fugiat", "nulla", "pariatur", "Excepteur", "sint", "occaecat", "cupidatat",
            "non", "proident", "sunt", "in", "culpa", "qui", "officia", "deserunt", "mollit", "anim",
            "id", "est", "laborum" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        getListView().setAdapter(adapter);
        getListView().setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        getListView().setStackFromBottom(true);

        mTask = new PopulateListViewTask();
        mTask.execute();
    }

    private class PopulateListViewTask extends AsyncTask <Void, String, Void> {

        int count = 0;

        private PopulateListViewTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressBarIndeterminate(false);
            setProgressBarVisibility(true);
            adapter.setNotifyOnChange(true);
        }

        @Override
        protected Void doInBackground(Void... params) {

                for (String item : texts) {

                    if (mTask.isCancelled()) {
                        break;
                    } else {
                        publishProgress(item);

                        // Simulate delay
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            adapter.add(values[0]);
            setProgress((int)(((double)count/texts.length)*10000));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setProgressBarVisibility(false);
            PopulateListViewActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            Msg.t(getBaseContext(), "All items were loaded sucessfully.");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTask.cancel(true)) {
            if (mTask.isCancelled()) {
                Msg.t(getBaseContext(), "Task was cancelled.");
            }
        }

    }
}
