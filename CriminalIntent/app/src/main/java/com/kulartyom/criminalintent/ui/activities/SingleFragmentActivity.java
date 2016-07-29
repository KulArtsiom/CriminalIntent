package com.kulartyom.criminalintent.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.kulartyom.criminalintent.R;
import com.kulartyom.criminalintent.ui.fragments.CrimeFragment;

/**
 * Created by KulArtyom on 27.07.2016.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    private static final String TAG = SingleFragmentActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        } else {
            Log.e(TAG, "fragment = null");
        }
    }
}