package com.example.murilo.myandroidsandbox.storage;


import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
public class StorageDatabaseFragment extends Fragment implements View.OnClickListener {

    EditText nameEdit;
    EditText passwordEdit;
    EditText deleteEdit;
    TextView databaseRetrTxt;
    Button saveButton;
    Button readButton;
    Button deleteButton;
    DbHelper dbHelper;


    public StorageDatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage_database, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nameEdit = (EditText) getActivity().findViewById(R.id.nameEdit);
        passwordEdit = (EditText) getActivity().findViewById(R.id.passwordEdit);
        deleteEdit = (EditText) getActivity().findViewById(R.id.deleteEdit);
        databaseRetrTxt = (TextView) getActivity().findViewById(R.id.databaseRetrText);
        saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        readButton = (Button) getActivity().findViewById(R.id.readButton);
        deleteButton = (Button) getActivity().findViewById(R.id.deleteButton);
        saveButton.setOnClickListener(this);
        readButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == saveButton.getId()) {

            saveOrUpdate();

        } else if (v.getId() == readButton.getId()) {

            retrieveData();

        } else if (v.getId() == deleteButton.getId()) {

            delete(deleteEdit.getText().toString());
        }

    }

    private void delete(String username) {

        String[] user = {username};

        dbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rows_affected = db.delete(DbContract.UsersTable.TABLE_NAME, DbContract.UsersTable.NAME + " = ?", user);

        if (rows_affected != 0) {
            Msg.t(getActivity(), "User sucessfully deleted.");
            deleteEdit.setText("");
            databaseRetrTxt.setText("");
        } else {
            Msg.t(getActivity(), "User not found.");
        }
    }

    private void retrieveData() {
        dbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {DbContract.UsersTable._ID,
                DbContract.UsersTable.NAME,
                DbContract.UsersTable.PASSWORD};

        StringBuilder result = new StringBuilder();

        Cursor cursor = db.query(DbContract.UsersTable.TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                result.append("User ");
                result.append(cursor.getColumnName(i).toLowerCase() + ": ");
                result.append(cursor.getString(i));
                result.append("\n");
            }
            result.append("---------------\n");
        }
        if (result.toString().isEmpty()) {
            Msg.t(getActivity(), "Database is empty.");
        } else {
            databaseRetrTxt.setText(result.toString());
        }
    }

    private void saveOrUpdate() {

        String name = nameEdit.getText().toString();
        String[] username = {name};
        String pass = passwordEdit.getText().toString();

        if (name.isEmpty() || pass.isEmpty()) {

            Msg.t(getActivity(), "No empty fields allowed.");
        } else {

            ContentValues values = new ContentValues();

            values.put(DbContract.UsersTable.NAME, name);
            values.put(DbContract.UsersTable.PASSWORD, pass);

            dbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // try to Update based on username
            int upload = db.update(DbContract.UsersTable.TABLE_NAME, values, DbContract.UsersTable.NAME + " = ?", username);
            if (upload == 0) {

                // if cannot Update, then Insert new
                long row_id = db.insert(DbContract.UsersTable.TABLE_NAME, null, values);

                if (row_id != -1) {
                    Msg.t(getActivity(), "User successfully saved.");
                    nameEdit.setText("");
                    passwordEdit.setText("");
                    databaseRetrTxt.setText("");
                } else {
                    Msg.t(getActivity(), "Could not save user.");
                }
            } else {
                Msg.t(getActivity(), "User successfully uploaded.");
                nameEdit.setText("");
                passwordEdit.setText("");
                databaseRetrTxt.setText("");
            }
        }
    }
}
