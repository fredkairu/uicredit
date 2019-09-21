package org.uip.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.fragments.NotificationFragment;


public class NotificationActivity extends BaseActivity {

    private String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clientId = getIntent().getExtras().getString("clientId");
        replaceFragment(NotificationFragment.newInstance(clientId), false, R.id.container);
    }
}
