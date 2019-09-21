package org.uip.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.enums.LoanState;
import org.uip.mobilebanking.ui.fragments.LoanApplicationFragment;


public class LoanApplicationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        if (savedInstanceState == null) {
            replaceFragment(LoanApplicationFragment.newInstance(LoanState.CREATE), false,
                    R.id.container);
        }
        showBackButton();
    }
}
