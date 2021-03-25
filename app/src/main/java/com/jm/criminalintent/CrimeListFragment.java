package com.jm.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {

  private RecyclerView mCrimeRecyclerView;
  private CrimeAdapter mCrimeAdapter;

  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
    mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
    mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUI();

    return view;
  }

  private void updateUI() {
    CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
    List<Crime> crimes = crimeLab.getCrimes();
    mCrimeAdapter = new CrimeAdapter(crimes);
    mCrimeRecyclerView.setAdapter(mCrimeAdapter);
  }

  private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTitleTv;
    private TextView mDateTv;
    private Crime mCrime;

    public CrimeHolder(@NonNull View itemView) {
      super(itemView);
    }

    public CrimeHolder(LayoutInflater layoutInflater, ViewGroup parent) {
      super(layoutInflater.inflate(R.layout.list_item_crime, parent, false));
      itemView.setOnClickListener(this);
      mTitleTv = itemView.findViewById(R.id.crime_title);
      mDateTv = itemView.findViewById(R.id.crime_date);
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

  private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

    private List<Crime> mCrimes;

    public CrimeAdapter(List<Crime> crimes) {
      mCrimes = crimes;
    }

    @NonNull
    @Override
    public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new CrimeHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
      Crime crime = mCrimes.get(position);
      holder.bind(crime);
    }

    // !!!!!!!! 返回值错误将导致显示错误！！！！！
    @Override
    public int getItemCount() {
      return mCrimes.size();
    }
  }
}
