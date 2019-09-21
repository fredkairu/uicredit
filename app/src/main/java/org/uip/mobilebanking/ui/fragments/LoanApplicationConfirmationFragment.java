package org.uip.mobilebanking.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.HomeActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanApplicationConfirmationFragment extends Fragment {

    @BindView(R.id.homebtn)
    Button  homebtn;;
    View rootView;
    public LoanApplicationConfirmationFragment() {
        // Required empty public constructor
    }

    public static LoanApplicationConfirmationFragment newInstance(){
        LoanApplicationConfirmationFragment transferFragment = new LoanApplicationConfirmationFragment();
        Bundle args = new Bundle();
        // args.putString(Constants.TRANSFER_TYPE, transferType);
        transferFragment.setArguments(args);
        return transferFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView= inflater.inflate(R.layout.fragment_loan_application_confirmation, container, false);
        ButterKnife.bind(this, rootView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        }, 2000);
        return rootView;
    }
    @OnClick(R.id.homebtn)
    void homebtn() {
        getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
    }
}