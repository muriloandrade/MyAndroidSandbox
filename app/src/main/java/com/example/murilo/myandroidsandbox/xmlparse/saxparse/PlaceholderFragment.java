package com.example.murilo.myandroidsandbox.xmlparse.saxparse;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PlaceholderFragment extends Fragment {

    ResultsCallBack callBack;
    MyTask myTask = null;

    public PlaceholderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        callBack = (ResultsCallBack) activity;

        if (myTask != null) {
            myTask.cancel(true);
            myTask.onAttach(callBack);
        } else {
            myTask = new MyTask(callBack);
        }

        myTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callBack = null;
        if (myTask != null) {
            myTask.onDetach();
        }
    }
}
