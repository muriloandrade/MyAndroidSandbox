package com.example.murilo.myandroidsandbox.asynctasks;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.murilo.myandroidsandbox.Msg;
import com.example.murilo.myandroidsandbox.R;

public class AsyncTasksActivity extends ListActivity implements AdapterView.OnItemClickListener {

    String[] asyncTasks;
    String[] asyncActivities;
    Intent intent;
    Class mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asyncTasks = getResources().getStringArray(R.array.async_tasks);
        asyncActivities = getResources().getStringArray(R.array.async_activities);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, asyncTasks);
        getListView().setPadding(16,16,16,0);
        getListView().setAdapter(adapter);

        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            mClass = Class.forName(getPackageName() + asyncActivities[position]);
            intent = new Intent(this, mClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Msg.l(asyncActivities[position] + " class not found.");
            e.printStackTrace();
        }
    }
}
