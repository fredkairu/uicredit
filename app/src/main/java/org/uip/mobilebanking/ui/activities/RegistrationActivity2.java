package org.uip.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.fragments.RegistrationFragment2;

public class RegistrationActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        replaceFragment(RegistrationFragment2.newInstance(), false, R.id.container);
    }
}
