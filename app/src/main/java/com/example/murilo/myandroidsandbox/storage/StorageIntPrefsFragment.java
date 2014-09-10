package com.example.murilo.myandroidsandbox.storage;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.Msg;
import com.example.murilo.myandroidsandbox.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class StorageIntPrefsFragment extends Fragment implements View.OnClickListener {

    public static final String DEFAULT = "N/A";

    EditText nameEdit;
    EditText passwordEdit;
    TextView nameRetrTxt;
    TextView passRetrTxt;
    Button saveButton;
    Button readButton;
    SharedPreferences prefsFile;

    public StorageIntPrefsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage_int_prefs, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nameEdit = (EditText) getActivity().findViewById(R.id.nameEdit);
        passwordEdit = (EditText) getActivity().findViewById(R.id.passwordEdit);
        nameRetrTxt = (TextView) getActivity().findViewById(R.id.nameRetrTxt);
        passRetrTxt = (TextView) getActivity().findViewById(R.id.passRetrTxt);
        saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        readButton = (Button) getActivity().findViewById(R.id.readButton);
        saveButton.setOnClickListener(this);
        readButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == saveButton.getId()) {
            prefsFile = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String name = nameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if (name.isEmpty() || password.isEmpty()) {

                Msg.t(getActivity(), "No empty fields allowed.");
            } else {
                SharedPreferences.Editor editor = prefsFile.edit();
                editor.putString("name", name);
                editor.putString("password", password);
                if (editor.commit()) {

                    Msg.t(getActivity(), "Data successfully saved.");
                    nameEdit.setText("");
                    passwordEdit.setText("");
                    nameRetrTxt.setText("");
                    passRetrTxt.setText("");
                } else {

                    Msg.t(getActivity(), "Could not save data.");
                }
            }

        } else if (v.getId() == readButton.getId()) {
            prefsFile = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String name = prefsFile.getString("name", DEFAULT);
            String pass = prefsFile.getString("password", DEFAULT);
            if (name.equals(DEFAULT) || pass.equals(DEFAULT)) {
                Msg.t(getActivity(), "Could not retrieve user data.");
            } else {
                nameRetrTxt.setText(name);
                passRetrTxt.setText(pass);
            }
        }
    }
}
