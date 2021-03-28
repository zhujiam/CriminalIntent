package com.jm.criminalintent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Crime {

  private UUID mId;
  private String mTitle;
  private String mDate;
  private boolean mSolved;
  private boolean mRequiresPolice;

  public Crime() {
    mId = UUID.randomUUID();
    Date date = new Date();
    //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MM dd, yyyy", Locale.ENGLISH);
    mDate = simpleDateFormat.format(date);
  }

  public UUID getId() {
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getDate() {
    return mDate;
  }

  public void setDate(String date) {
    mDate = date;
  }

  public boolean isSolved() {
    return mSolved;
  }

  public void setSolved(boolean solved) {
    mSolved = solved;
  }

  public boolean isRequiresPolice() {
    return mRequiresPolice;
  }

  public void setRequiresPolice(boolean requiresPolice) {
    mRequiresPolice = requiresPolice;
  }
}
