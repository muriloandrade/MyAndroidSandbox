package com.example.murilo.myandroidsandbox;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Murilo on 02/09/2014.
 */
public final class Msg {

    public static void t(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void l(String message) {
        Log.d("sandbox", message);
    }
}
