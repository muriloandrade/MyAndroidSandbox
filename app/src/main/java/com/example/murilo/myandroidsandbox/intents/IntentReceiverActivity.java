package com.example.murilo.myandroidsandbox.intents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.murilo.myandroidsandbox.R;

import java.io.InputStream;

public class IntentReceiverActivity extends Activity {

    Bitmap bitmap = null;
    Intent intent;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_receiver);

        // get Intent that started this activity
        intent = getIntent();
        imageView =  (ImageView) findViewById(R.id.imageView);

//        final MyImage imageView = (MyImage) findViewById(R.id.imageView);

//        imageView.setOnImageViewSizeChanged(new MyImage.OnImageViewSizeChanged() {
//
//            @Override
//            public void invoke(ImageView v, final int w, final int h) {
//                if (intent.getType() != null && intent.getType().contains("image/")) {
//
//
//                    if (bitmap != null) {
//                        bitmap.recycle();
//                    }
//
//                    Uri image_uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
//                    try {
//                        InputStream is = getContentResolver().openInputStream(image_uri);
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inJustDecodeBounds = true;
//                        bitmap = BitmapFactory.decodeStream(is, null, options);
//                        int outWidth = options.outWidth;
//                        int outHeight = options.outHeight;
//                        int scale = Math.min((h / outHeight), (w / outWidth));
//                        options.inJustDecodeBounds = false;
//                        options.inSampleSize = scale;
//                        is.reset();
//                        bitmap = BitmapFactory.decodeStream(is, null, options);
//                        is.close();
//                        imageView.setImageBitmap(bitmap);
//                    } catch (java.io.IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (intent.getType() != null && intent.getType().contains("image/")) {


            if (bitmap != null) {
                bitmap.recycle();
            }

            Uri image_uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            try {
                InputStream is = getContentResolver().openInputStream(image_uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                bitmap = BitmapFactory.decodeStream(is, null, options);
                int outWidth = options.outWidth;
                int outHeight = options.outHeight;
                int scale = Math.min((imageView.getHeight() / outHeight), (imageView.getWidth() / outWidth));
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;
                is.reset();
                bitmap = BitmapFactory.decodeStream(is, null, options);
                is.close();
                imageView.setImageBitmap(bitmap);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}
