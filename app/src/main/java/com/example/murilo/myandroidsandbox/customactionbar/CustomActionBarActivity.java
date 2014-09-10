package com.example.murilo.myandroidsandbox.customactionbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.R;


public class CustomActionBarActivity extends Activity {

    TextView txtHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_action_bar);

        txtHelloWorld = (TextView) findViewById(R.id.txtHelloWorld);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_action_bar, menu);

        MenuItem shareItem;
        ShareActionProvider shareActionProvider;
        Intent shareIntent;

        shareItem = menu.findItem(R.id.menu_item_share);
        shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        if (shareActionProvider != null){
            shareActionProvider.setShareIntent(shareIntent);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP) {

            int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
            int newUiOptions = uiOptions;

            if (getActionBar()!= null && getActionBar().isShowing()) {
                getActionBar().hide();
                txtHelloWorld.setPadding(0, 0, 0, 0);
                newUiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                newUiOptions += View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                newUiOptions += View.SYSTEM_UI_FLAG_FULLSCREEN;
                newUiOptions += View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                newUiOptions += View.SYSTEM_UI_FLAG_LOW_PROFILE;
//                newUiOptions += View.SYSTEM_UI_FLAG_IMMERSIVE;
//                newUiOptions += View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                newUiOptions += View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//                newUiOptions += View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//                newUiOptions += View.SYSTEM_UI_LAYOUT_FLAGS;
            } else {

                if (getActionBar() != null) {
                    getActionBar().show();
                }
                txtHelloWorld.setPadding(0, getActionBar().getHeight(), 0, 0);
            }
            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }

        return super.onTouchEvent(event);
    }
}
