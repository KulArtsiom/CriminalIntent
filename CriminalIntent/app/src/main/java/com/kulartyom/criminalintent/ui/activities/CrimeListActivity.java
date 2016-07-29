package com.kulartyom.criminalintent.ui.activities;

import android.support.v4.app.Fragment;

import com.kulartyom.criminalintent.ui.fragments.CrimeFragment;
import com.kulartyom.criminalintent.ui.fragments.CrimeListFragment;

/**
 * Created by KulArtyom on 27.07.2016.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
