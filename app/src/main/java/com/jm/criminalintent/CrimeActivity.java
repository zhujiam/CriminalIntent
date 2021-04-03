package com.jm.criminalintent;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

  private static final String EXTRA_CRIME_ID = "com.jm.criminalIntent.crime_id";

  @Override
  protected Fragment createFragment() {

    UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
    return CrimeFragment.newInstance(crimeId);
  }

  public static Intent newIntent(Context context, UUID crimeId) {
    Intent intent = new Intent(context, CrimeActivity.class);
    intent.putExtra(EXTRA_CRIME_ID, crimeId);
    return intent;
  }

}
