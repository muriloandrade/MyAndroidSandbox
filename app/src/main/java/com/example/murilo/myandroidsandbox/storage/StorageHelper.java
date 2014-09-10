package com.example.murilo.myandroidsandbox.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.murilo.myandroidsandbox.Msg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Murilo on 02/09/2014.
 */
public class StorageHelper {

    Context context;
    public static final int INTERNAL_FILE = 1;
    public static final int INTERNAL_CACHE_FILE = 2;
    public static final int EXTERNAL_CACHE_FILE = 3;
    public static final int EXTERNAL_PRIVATE_FILE = 4;
    public static final int EXTERNAL_PUBLIC_DOWNLOAD_FILE = 5;

    public static final String PRIVATE_EXT_FOLDER = "mPrivateExtFolder";



    public StorageHelper(Context context) {
        this.context = context;
    }

    public boolean writeToFile(EditText input, String filename, int where) {

        String text = input.getText().toString();

        if (!text.isEmpty()) {

            File file = getFile(filename, where);
            FileOutputStream fos = null;
            byte[] buffer = text.getBytes();

            try {

                fos = new FileOutputStream(file);
                fos.write(buffer);
                Msg.t(context, "File saved successfully.");
                input.setText("");
            } catch (IOException e) {

                e.printStackTrace();
                Log.e("Storage", e.toString());
            } finally {

                if (fos != null) {
                    try {

                        fos.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                        Log.e("Storage", e.toString() );
                    }
                }
            }
        } else {

            Msg.t(context, "No empty fields allowed.");
            return false;
        }
        return true;
    }

    public void readFromFile(TextView output, String filename, int where) {

        File file = getFile(filename, where);

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(file);
            StringBuilder stringBuilder = new StringBuilder();
            int read;
            while ((read = fis.read()) != -1) {

                stringBuilder.append((char)read);
            }
            output.setText(stringBuilder.toString());

        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getFile(String filename, int where) {

        File directory = null;
        File file;

        switch (where) {
            case INTERNAL_FILE:
                directory = context.getFilesDir();
                break;
            case INTERNAL_CACHE_FILE:
                directory = context.getCacheDir();
                break;
            case EXTERNAL_CACHE_FILE:
                directory = context.getExternalCacheDir();
                break;
            case EXTERNAL_PRIVATE_FILE:
                directory = context.getExternalFilesDir(PRIVATE_EXT_FOLDER);
                break;
            case EXTERNAL_PUBLIC_DOWNLOAD_FILE:
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                break;
        }

        file = new File(directory, filename);
        return file;
    }
}
