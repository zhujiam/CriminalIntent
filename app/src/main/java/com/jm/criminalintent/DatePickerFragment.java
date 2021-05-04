package com.jm.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

  private static final String ARG_DATE = "date";
  public static final String EXTRA_DATE = "com.jm.criminalintent.date";
  private DatePicker mDatePicker;
  private static DatePickerFragment mDatePickerFragment;

  public static DatePickerFragment newInstance(Date date) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_DATE, date);
    mDatePickerFragment = new DatePickerFragment();
    mDatePickerFragment.setArguments(args);

    return mDatePickerFragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    Date date = (Date) getArguments().getSerializable(ARG_DATE);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    View view = inflater.inflate(R.layout.dialog_date, container);
    mDatePicker = view.findViewById(R.id.dialog_date_picker);
    mDatePicker.init(year, month, day, null);
    Button buttonCancel = view.findViewById(R.id.dialog_button_cancel);
    Button buttonOK = view.findViewById(R.id.dialog_button_ok);
    buttonCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mDatePickerFragment.dismiss();
      }
    });
    buttonOK.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        Date date = new GregorianCalendar(year, month, day).getTime();
        sendResult(Activity.RESULT_OK, date);
        mDatePickerFragment.dismiss();
      }
    });

    return view;
  }

//  @NonNull
//  @Override
//  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//    Date date = (Date) getArguments().getSerializable(ARG_DATE);
//    Calendar calendar = Calendar.getInstance();
//    calendar.setTime(date);
//    int year = calendar.get(Calendar.YEAR);
//    int month = calendar.get(Calendar.MONTH);
//    int day = calendar.get(Calendar.DAY_OF_MONTH);
//    View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null, false);
//    mDatePicker = view.findViewById(R.id.dialog_date_picker);
//    mDatePicker.init(year, month, day, null);
//
//    return new AlertDialog.Builder(getActivity())
//            .setTitle(R.string.data_picker_title)
//            .setView(view)
//            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//                int year = mDatePicker.getYear();
//                int month = mDatePicker.getMonth();
//                int day = mDatePicker.getDayOfMonth();
//                Date date = new GregorianCalendar(year, month, day).getTime();
//                sendResult(Activity.RESULT_OK, date);
//              }
//            })
//            .create();
//  }

  private void sendResult(int resultCode, Date date) {
    if (getTargetFragment() == null) {
      return;
    }
    Intent intent = new Intent();
    intent.putExtra(EXTRA_DATE, date);

    getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent


    );
  }
}
