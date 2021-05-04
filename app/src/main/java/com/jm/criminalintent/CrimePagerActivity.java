package com.jm.criminalintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

  private static final String EXTRA_CRIME_ID = "com.jm.CrimePagerActivity.crimeId";

  private ViewPager mViewPager;
  private List<Crime> mCrimes;

  public static Intent newIntent(Context packageContext, UUID crimeId) {
    Intent intent = new Intent(packageContext, CrimePagerActivity.class);
    intent.putExtra(EXTRA_CRIME_ID, crimeId);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crime_pager);
    initViews();
  }

  private void initViews() {
    UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
    mViewPager = findViewById(R.id.crime_view_pager);
    mCrimes = CrimeLab.getInstance(this).getCrimes();
    final ImageButton imageButtonGoTop = findViewById(R.id.crime_go_top);
    final ImageButton imageButtonGoBottom = findViewById(R.id.crime_go_bottom);
    imageButtonGoTop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setCurrentItem(0, imageButtonGoTop,imageButtonGoBottom);
      }
    });
    imageButtonGoBottom.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setCurrentItem(mCrimes.size() - 1, imageButtonGoTop,imageButtonGoBottom);
      }
    });
    FragmentManager fragmentManager = getSupportFragmentManager();
    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @NonNull
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
    for (int i = 0; i < mCrimes.size(); i++) {
      if (mCrimes.get(i).getId().equals(crimeId)) {
        setCurrentItem(i, imageButtonGoTop, imageButtonGoBottom);
        break;
      }
    }
  }

  private void setCurrentItem(int index, ImageButton goTop, ImageButton goBottom) {

    mViewPager.setCurrentItem(index);
    if (index == 0) {
      goTop.setClickable(false);
      goTop.setAlpha(0.3f);
      goBottom.setClickable(true);
      goBottom.setAlpha(1.0f);
    } else if (index == mCrimes.size() - 1) {
      goTop.setClickable(true);
      goTop.setAlpha(1.0f);
      goBottom.setClickable(false);
      goBottom.setAlpha(0.3f);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    setResult(mViewPager.getCurrentItem());
  }
}
