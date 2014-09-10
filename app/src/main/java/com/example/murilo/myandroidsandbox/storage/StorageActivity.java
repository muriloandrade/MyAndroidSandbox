package com.example.murilo.myandroidsandbox.storage;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.murilo.myandroidsandbox.R;


public class StorageActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup radioGroup;
    FragmentManager manager;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        manager = getFragmentManager();
        fragment = getFragmentManager().findFragmentByTag("current_fragment");

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        nullifyFragment();
        Bundle bundle = new Bundle();

        switch (radioGroup.getCheckedRadioButtonId()) {

           case R.id.rdIntPref:
               fragment = new StorageIntPrefsFragment();
               break;
            case R.id.rdIntFile:
                bundle.putInt("where", StorageHelper.INTERNAL_FILE);
                makeGenericFragment(bundle);
                break;
            case R.id.rdIntCache:
                bundle.putInt("where", StorageHelper.INTERNAL_CACHE_FILE);
                makeGenericFragment(bundle);
                break;
            case R.id.rdExtPrivate:
                bundle.putInt("where", StorageHelper.EXTERNAL_PRIVATE_FILE);
                makeGenericFragment(bundle);
                break;
            case R.id.rdExtCache:
                bundle.putInt("where", StorageHelper.EXTERNAL_CACHE_FILE);
                makeGenericFragment(bundle);
                break;
            case R.id.rdExtPublic:
                bundle.putInt("where", StorageHelper.EXTERNAL_PUBLIC_DOWNLOAD_FILE);
                makeGenericFragment(bundle);
                break;
            case R.id.rdDatabase: fragment = new StorageDatabaseFragment();
                break;
        }

        openFragment(manager, fragment);
    }

    private void makeGenericFragment(Bundle bundle) {
        fragment = new StorageGenericFragment();
        fragment.setArguments(bundle);
    }

    private void openFragment(FragmentManager manager, Fragment fragment) {

        if (fragment != null && !fragment.isAdded()) {
            manager.beginTransaction().add(R.id.fragment_container, fragment, "current_fragment").commit();
        }
    }

    private void nullifyFragment() {

        if (fragment != null) {

            manager.beginTransaction().remove(fragment).commit();
            fragment = null;
        }

    }
}
