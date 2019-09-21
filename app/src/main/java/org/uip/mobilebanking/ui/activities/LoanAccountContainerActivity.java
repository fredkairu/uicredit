package org.uip.mobilebanking.ui.activities;
/*
~This project is licensed under the open source MPL V2.
~See https://github.com/openMF/self-service-app/blob/master/LICENSE.md
*/

import android.os.Bundle;


import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.fragments.LoanAccountsDetailFragment;
import org.uip.mobilebanking.utils.Constants;


public class LoanAccountContainerActivity extends BaseActivity {

    private long loanId;
    private String phoneNumber, client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        loanId = getIntent().getExtras().getLong(Constants.LOAN_ID);
        phoneNumber = getIntent().getExtras().getString("phone");
        client_id = getIntent().getExtras().getString("client_id");

        replaceFragment(LoanAccountsDetailFragment.newInstance(loanId, phoneNumber, client_id), false, R.id.container);
        showBackButton();
    }
}
