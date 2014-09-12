package com.example.murilo.myandroidsandbox.intents;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.Msg;
import com.example.murilo.myandroidsandbox.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class IntentsActivity extends Activity {

    public static final int PICK_CONTACT_REQUEST = 1;
    public static final int TAKE_PICTURE_REQUEST = 2;

    TextView contactText;
    EditText phoneNumbEdit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);

        contactText = (TextView) findViewById(R.id.contactText);
        phoneNumbEdit = (EditText) findViewById(R.id.phoneNumbEdit);

    }


    public void openMap(View view) {

        Uri location = Uri.parse("geo:-20.54373,-47.39295?z=15");
        intent = new Intent(Intent.ACTION_VIEW, location);

        // Verify it resolves and start
        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            startActivity(intent);
        } else {
            Msg.t(this, "No apps can perform this action");
        }
    }

    public void sendEmail(View view) {

        intent = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");

        String[] to = {"muriloap@live.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);

        intent.putExtra(Intent.EXTRA_SUBJECT, "Email sent from intent");
        intent.putExtra(Intent.EXTRA_TEXT, "Sending email from Android App");

        Intent chooser = Intent.createChooser(intent, "Send email");
        startActivity(chooser);
    }

    public void sendImage(View view) {

        intent = new Intent(Intent.ACTION_SEND);

        intent.setType("image/png");

        String[] to = {"muriloap@live.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "My image");
        intent.putExtra(Intent.EXTRA_TEXT, "Sending image from Android App");

        Uri image_uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.building);
        intent.putExtra(Intent.EXTRA_STREAM, image_uri);

        Intent chooser = Intent.createChooser(intent, "Send image");
        startActivity(chooser);
    }

    public void call(View view) {

        String phoneNumber = phoneNumbEdit.getText().toString();

        if (phoneNumber.isEmpty()) {
            Msg.t(this, "Type a phone number to call");
        } else {
            Uri phone_uri = Uri.parse("tel:" + phoneNumber);
            intent = new Intent(Intent.ACTION_CALL, phone_uri);

            List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
            if (activities.size() > 0) {
                startActivity(intent);
            } else {
                Msg.t(this, "No apps can perform this action");
            }
        }
    }

    public void openWeb(View view) {

        Uri web_uri = Uri.parse("http://www.google.com");
        intent = new Intent(Intent.ACTION_VIEW, web_uri);

        Intent chooser = Intent.createChooser(intent, "Open Webpage");
        startActivity(chooser);

    }

    public void pickContact(View view) {

        intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        Intent chooser = Intent.createChooser(intent, "Pick contact");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, PICK_CONTACT_REQUEST);
        }
    }

    public void wifiSettings (View view) {

        intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    public void gotoReceiver(View view) {
        try {

            intent = new Intent(this, Class.forName(getPackageName() + ".intents.IntentReceiverActivity"));
            startActivity(intent);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void takePicture(View view) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, TAKE_PICTURE_REQUEST);

        } else {

            Msg.t(this, "No app to perform this action");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_REQUEST) {

            if (resultCode == RESULT_OK) {

                Uri contactUri = data.getData();

                String[] projection = { Phone.DISPLAY_NAME,
                        Phone.NUMBER };

                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                StringBuilder stringBuilder = new StringBuilder();
                if (cursor.moveToFirst()) {

                    stringBuilder.append("Name: ");
                    stringBuilder.append(cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME)));
                    stringBuilder.append("\n");
                    stringBuilder.append("Phone: ");
                    stringBuilder.append(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                }
                contactText.setText(stringBuilder.toString());

            } else {
                Msg.t(this, "Could not get contact");
            }
        } else if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {

                Msg.t(this, "Picture saved to Gallery");
        }
    }

}
