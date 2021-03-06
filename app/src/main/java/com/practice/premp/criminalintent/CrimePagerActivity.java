package com.practice.premp.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.CallBacks {

    // CONSTANTS
    private static final String EXTRA_CRIME_ID =
            "com.practice.premp.criminalintent.crime_id";

    // Declarations
    private ViewPager mViewPager;
    private List<Crime> mCrimes;


    // Creating new intent
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();


        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        setCrime(crimeId);

    } // onCreate() end.

    private void setCrime(UUID crimeId) {
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    } // setCrime() end.

    @Override
    public void onCrimeUpdated(Crime crime) {
        /**
         *  It will remain empty. We don't need to update here in phone mode.
         */
    } // onCrimeUpdated() end.

    @Override
    public void onCrimeDeleted() {
        /**
         *  We don't need to delete fragment in phone mode,
         *  We'll remove activity and return to list activity.
         */
        finish();
    } // onCrimeDeleted();
}
