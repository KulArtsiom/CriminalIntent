package com.kulartyom.criminalintent.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.kulartyom.criminalintent.Crime;
import com.kulartyom.criminalintent.CrimeLab;
import com.kulartyom.criminalintent.R;
import com.kulartyom.criminalintent.constans.Constans;
import com.kulartyom.criminalintent.ui.activities.CrimePagerActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by KulArtyom on 27.07.2016.
 */
public class CrimeListFragment extends Fragment {
    private static final String SUBTITLE_VISIBILITY = "subtitle_visibility";
    private static final String ADAPTER_POSITION = "adapter_position";

    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    private int mAdapterClickPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        if (view != null) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycle_view);
            if (mRecyclerView != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            if (savedInstanceState != null) {
                mAdapterClickPosition = savedInstanceState.getInt(ADAPTER_POSITION,0);
            }
        }
        updateUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("onSaveInstanceState", "onSaveInstance cagirildi");
        super.onSaveInstanceState(outState);
        outState.putInt(ADAPTER_POSITION, mAdapterClickPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.notifyDataSetChanged();
            if(mAdapterClickPosition < 0) {
                mCrimeAdapter.notifyDataSetChanged();
            } else {
                mCrimeAdapter.notifyItemChanged(mAdapterClickPosition);
                mAdapterClickPosition = -1;
            }
        }

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        public TextView mTitleTextView;
        public TextView mDateTextView;
        public CheckBox mSolvedCheckBox;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_checkbox);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked", Toast.LENGTH_LONG).show();
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            SimpleDateFormat fmt = new SimpleDateFormat(Constans.DATE_FORMAT);
            mDateTextView.setText(fmt.format(crime.getDate()));
            mSolvedCheckBox.setChecked(crime.isSolved());
        }


    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.crime_list_item, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
