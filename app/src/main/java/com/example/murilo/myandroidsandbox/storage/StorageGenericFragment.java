package com.example.murilo.myandroidsandbox.storage;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class StorageGenericFragment extends Fragment implements View.OnClickListener {

    private static final String INTERNAL_FILENAME = "mInternalFile.txt";
    private static final String INTERNAL_CACHE_FILENAME = "mInternalCacheFile.txt";
    private static final String EXTERNAL_CACHE_FILENAME = "mExternalCacheFile.txt";
    private static final String EXTERNAL_PRIVATE_FILENAME = "mExternalPrivateFile.txt";
    private static final String EXTERNAL_PUBLIC_DOC_FILENAME = "mExternalPublicDocFile.txt";
    EditText typeTextEdit;
    TextView readText;
    Button saveButton;
    Button readButton;
    StorageHelper storageHelper;

    int where;
    String filename;


    public StorageGenericFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage_generic, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        typeTextEdit = (EditText) getActivity().findViewById(R.id.typeTextEdit);
        readText = (TextView) getActivity().findViewById(R.id.readTxt);
        saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        readButton = (Button) getActivity().findViewById(R.id.readButton);

        saveButton.setOnClickListener(this);
        readButton.setOnClickListener(this);
        storageHelper = new StorageHelper(getActivity());

        where = getArguments().getInt("where");

        switch (where) {
            case StorageHelper.INTERNAL_FILE:
                filename = INTERNAL_FILENAME;
                readButton.setText("Read Internal File");
                break;
            case StorageHelper.INTERNAL_CACHE_FILE:
                filename = INTERNAL_CACHE_FILENAME;
                readButton.setText("Read Internal Cache File");
                break;
            case StorageHelper.EXTERNAL_CACHE_FILE:
                filename = EXTERNAL_CACHE_FILENAME;
                readButton.setText("Read External Cache File");
                break;
            case StorageHelper.EXTERNAL_PRIVATE_FILE:
                filename = EXTERNAL_PRIVATE_FILENAME;
                readButton.setText("Read External Private File");
                break;
            case StorageHelper.EXTERNAL_PUBLIC_DOWNLOAD_FILE:
                filename = EXTERNAL_PUBLIC_DOC_FILENAME;
                readButton.setText("Read Ext Public Doc File");
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == saveButton.getId()) {

            if (storageHelper.writeToFile(typeTextEdit, filename, where)) {
                readText.setText("");
            }

        } else if (v.getId() == readButton.getId()) {

            storageHelper.readFromFile(readText, filename, where);
        }
    }

}
