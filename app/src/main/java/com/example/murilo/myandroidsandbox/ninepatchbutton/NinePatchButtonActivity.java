package com.example.murilo.myandroidsandbox.ninepatchbutton;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.murilo.myandroidsandbox.R;


public class NinePatchButtonActivity extends Activity implements TextWatcher {

    EditText editButtonText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_patch_button);

        editButtonText = (EditText) findViewById(R.id.editButtonText);
        editButtonText.addTextChangedListener(this);
        button = (Button) findViewById(R.id.nine_patch_button);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button.setText(editButtonText.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
