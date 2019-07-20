package org.uipcredits.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.fragments.RegistrationFragment;


public class RegistrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        replaceFragment(RegistrationFragment.newInstance(), false, R.id.container);
    }
}
