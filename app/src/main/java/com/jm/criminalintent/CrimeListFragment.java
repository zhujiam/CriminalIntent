package com.jm.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CrimeListFragment extends Fragment {

  private static final String TAG = "CrimeListFragment";
  private static final int CRIME_DETAILS = 0;
  private static final boolean DBG = true;
  private RecyclerView mCrimeRecyclerView;
  private CrimeAdapter mCrimeAdapter;
  private int mUpdateId;

  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
    mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
    mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUI();

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume: executed.");
    updateUI();
  }

  private void updateUI() {

    CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
    List<Crime> crimes = crimeLab.getCrimes();
    if (DBG) {
      Log.d(TAG, "updateUI: mCrimeAdapter=null? " + (mCrimeAdapter == null) + ", mUpdateId=" + mUpdateId);
    }
    if (mCrimeAdapter == null) {
      mCrimeAdapter = new CrimeAdapter(crimes);
      mCrimeRecyclerView.setAdapter(mCrimeAdapter);
    } else {
      mCrimeAdapter.notifyDataSetChanged();
    }
  }

  private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTitleTv;
    private TextView mDateTv;
    private Crime mCrime;
    private ImageView mSolvedImg;

    public CrimeHolder(LayoutInflater layoutInflater, ViewGroup parent) {

      super(layoutInflater.inflate(R.layout.list_item_crime, parent, false));
      itemView.setOnClickListener(this);
      mTitleTv = itemView.findViewById(R.id.crime_title);
      mDateTv = itemView.findViewById(R.id.crime_date);
      mSolvedImg = itemView.findViewById(R.id.crime_solved);
    }

    private void bind(Crime crime) {
      mCrime = crime;
      mTitleTv.setText(mCrime.getTitle());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MM dd, yyyy", Locale.getDefault());
      mDateTv.setText(simpleDateFormat.format(mCrime.getDate()));
      mSolvedImg.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
      if (DBG) Log.d(TAG, "CrimeHolder: getItemViewType()=" + getItemViewType());
    }

    @Override
    public void onClick(View v) {
      Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
      startActivityForResult(intent, CRIME_DETAILS);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (DBG) Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
    if (requestCode == CRIME_DETAILS) {
      mUpdateId = resultCode;
    }
  }

  private class CrimePoliceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTitleTv;
    private TextView mDateTv;
    private Crime mCrime;
    private Button mCallPolice;

    public CrimePoliceHolder(LayoutInflater layoutInflater, ViewGroup parent) {

      super(layoutInflater.inflate(R.layout.list_item_crime_police, parent, false));
      itemView.setOnClickListener(this);
      mTitleTv = itemView.findViewById(R.id.crime_title);
      mDateTv = itemView.findViewById(R.id.crime_date);
      mCallPolice = itemView.findViewById(R.id.crime_call_police);
      mCallPolice.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(getActivity(), "Call the police", Toast.LENGTH_SHORT).show();
        }
      });
    }

    private void bind(Crime crime) {
      mCrime = crime;
      mTitleTv.setText(mCrime.getTitle());
      mDateTv.setText(mCrime.getDate().toString());
    }

    @Override
    public void onClick(View v) {
      Toast.makeText(getActivity(), mCrime.getTitle() + "clicked!", Toast.LENGTH_SHORT).show();
    }
  }

  private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Crime> mCrimes;

    public CrimeAdapter(List<Crime> crimes) {
      mCrimes = crimes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      RecyclerView.ViewHolder holder;
      if (viewType == 1) {
        holder = new CrimePoliceHolder(layoutInflater, parent);
      } else {
        holder = new CrimeHolder(layoutInflater, parent);
      }
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      Crime crime = mCrimes.get(position);
      if (getItemViewType(position) == 1) {
        CrimePoliceHolder crimePoliceHolder = (CrimePoliceHolder) holder;
        crimePoliceHolder.bind(crime);
      } else {
        CrimeHolder crimeHolder = (CrimeHolder) holder;
        crimeHolder.bind(crime);
      }
    }

    @Override
    public int getItemViewType(int position) {
      return mCrimes.get(position).isRequiresPolice() ? 1 : 0;
    }

    // !!!!!!!! 返回值错误将导致显示错误！！！！！
    @Override
    public int getItemCount() {
      return mCrimes.size();
    }
  }
}
