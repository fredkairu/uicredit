package org.uip.mobilebanking.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.adapter.MyAdapter;
import org.uip.mobilebanking.models.accounts.loan.TitleDeed;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Oclemmy on 4/23/2016 for ProgrammingWizards Channel.
 */
public class TitleDeedFragment extends DialogFragment {

    String[] tvshows={"Farm Input Shop","Farm Produce Shop"};
    RecyclerView rv;
    MyAdapter adapter;
    private ArrayList<TitleDeed> titleDeeds;

    public static TitleDeedFragment newInstance(Serializable titles){
        TitleDeedFragment titleDeedFragment = new TitleDeedFragment();
        Bundle args = new Bundle();
        args.putSerializable("titles", titles);
        titleDeedFragment.setArguments(args);
        return titleDeedFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.fraglayout,container);

        if (getArguments() != null) {
            titleDeeds = (ArrayList<TitleDeed>) getArguments().getSerializable("titles");
        }
        //RECYCER
        rv= (RecyclerView) rootView.findViewById(R.id.mRecyerID);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //ADAPTER
        adapter=new MyAdapter(this.getActivity(),titleDeeds);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TitleDeed obj, int position) {
                LoanApplicationFragment.setData(""+obj.loanAmount, obj.titleNumber, obj.unitPrice);
                dismiss();
            }
        });


        return rootView;
    }
}
