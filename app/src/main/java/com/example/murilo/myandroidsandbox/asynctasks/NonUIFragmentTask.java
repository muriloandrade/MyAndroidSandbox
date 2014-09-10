package com.example.murilo.myandroidsandbox.asynctasks;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.murilo.myandroidsandbox.Msg;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class NonUIFragmentTask extends Fragment {

    DownloadImageTask myTask = null;
    private Activity activity;

    public NonUIFragmentTask() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
        this.activity = activity;
        if (myTask != null) {
            myTask.onAttach(activity);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (myTask != null) {

            myTask.onDetach();
        }
    }

    public void beginTask(String... params) {
        if (myTask != null && myTask.cancel(true)) {
            Msg.t(activity, "Download cancelled");
        }
        myTask = new DownloadImageTask(activity);
        myTask.execute(params);
    }
}
