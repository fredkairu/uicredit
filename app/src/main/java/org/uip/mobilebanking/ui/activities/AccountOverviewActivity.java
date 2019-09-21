package org.uip.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.fragments.AccountOverviewFragment;


/**
 * @author Rajan Maurya
 *         On 16/10/17.
 */
public class AccountOverviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        replaceFragment(AccountOverviewFragment.newInstance(), false, R.id.container);
        showBackButton();
        hideToolbarElevation();
    }
}
