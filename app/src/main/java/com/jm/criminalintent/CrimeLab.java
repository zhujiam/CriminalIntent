package com.jm.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

  private static CrimeLab sCrimeLab;
  private List<Crime> mCrimes;
  private HashMap<UUID, Integer> mCrimeIndexMap;

  public static CrimeLab getInstance(Context context) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(context);
    }
    return sCrimeLab;
  }

  private CrimeLab(Context context) {
    mCrimes = new ArrayList<>();
    mCrimeIndexMap = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      Crime crime = new Crime();
      crime.setTitle("Crime #" + i);
      crime.setSolved(i % 2 == 0);
      crime.setRequiresPolice(false);
      mCrimes.add(crime);
      mCrimeIndexMap.put(crime.getId(), i);
    }
  }

  public List<Crime> getCrimes() {
    return mCrimes;
  }

  public Crime getCrime(UUID id) {
    if (mCrimeIndexMap != null && mCrimeIndexMap.get(id) != null && mCrimeIndexMap.get(id) < mCrimes.size()) {
      return mCrimes.get(mCrimeIndexMap.get(id));
    }
    return null;
  }

  public HashMap<UUID, Integer> getCrimeIndexMap() {
    return mCrimeIndexMap;
  }
}
