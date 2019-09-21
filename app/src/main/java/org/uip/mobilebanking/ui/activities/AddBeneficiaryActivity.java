package org.uip.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.fragments.BeneficiaryAddOptionsFragment;


/**
 * @author Rajan Maurya
 * On 04/06/18.
 */
public class AddBeneficiaryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        showBackButton();
        replaceFragment(BeneficiaryAddOptionsFragment.newInstance(), false, R.id.container);
    }
}
