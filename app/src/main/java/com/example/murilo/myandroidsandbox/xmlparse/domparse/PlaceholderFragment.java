package com.example.murilo.myandroidsandbox.xmlparse.domparse;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PlaceholderFragment extends Fragment {

    MyTask myTask;
    ResultsCallBack callBack = null;

    public PlaceholderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    public void startTask() {

        if (myTask != null) {

            myTask.cancel(true);
        }

        myTask = new MyTask(callBack);
        myTask.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.callBack = (ResultsCallBack) activity;

        if (myTask != null) {
            myTask.onAttach(callBack);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.callBack = null;
        if(myTask != null) {
            myTask.onDetach();
        }
    }
}
