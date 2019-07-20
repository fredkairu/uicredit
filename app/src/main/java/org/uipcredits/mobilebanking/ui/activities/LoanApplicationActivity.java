package org.uipcredits.mobilebanking.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.enums.AccountType;
import org.uipcredits.mobilebanking.ui.enums.LoanState;
import org.uipcredits.mobilebanking.ui.fragments.ClientAccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.HelpFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeOldFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanApplicationFragment;

import butterknife.BindView;
import io.github.yavski.fabspeeddial.FabSpeedDial;


public class LoanApplicationActivity extends BaseActivity {
    @BindView(R.id.fabSpeedDial)
    FabSpeedDial fabSpeedDial;

    LinearLayout homebottom;
    LinearLayout helpbottom;
    LinearLayout accountsbottom;
    LinearLayout sharebottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        if (savedInstanceState == null) {
            replaceFragment(LoanApplicationFragment.newInstance(LoanState.CREATE), false,
                    R.id.container);
        }
        showBackButton();

        homebottom = findViewById(R.id.homebottom);
        helpbottom = findViewById(R.id.helpbottom);
        accountsbottom = findViewById(R.id.accountsbottom);
        sharebottom = findViewById(R.id.sharebottom);


        sharebottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Intent.ACTION_SEND);
                ii.setType("text/plain");
                ii.putExtra(Intent.EXTRA_TEXT, getString(R.string.string_and_string,
                        getString(R.string.share_msg), getApplication().getPackageName()));
                startActivity(Intent.createChooser(ii, getString(R.string.choose)));
            }
        });

        homebottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        accountsbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* replaceFragment(ClientAccountsFragment.newInstance(AccountType.SAVINGS),
                        true, R.id.container);*/

                Intent intent = new Intent(getApplicationContext(), AccountOverviewActivity.class);
                startActivity(intent);
            }
        });

        helpbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*replaceFragment(HelpFragment.getInstance(), true, R.id.container)*/;
                FragmentManager fragmentManager = getSupportFragmentManager();
                HelpFragment helpFragment = new HelpFragment();
                fragmentManager.beginTransaction().replace(R.id.container,helpFragment)
                        .commit();
            }
        });


        fabSpeedDial = findViewById(R.id.fabSpeedDial);

        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                int id2 = menuItem.getGroupId();
                if (menuItem.getTitle().equals("Pay Loan")) {

                    replaceFragment(ClientAccountsFragment.newInstance(AccountType.SAVINGS),
                            true, R.id.container);

                } else if (menuItem.getTitle().equals("Request Loan")) {
                    startActivity(new Intent(LoanApplicationActivity.this, LoanApplicationActivity.class));

                }else if (menuItem.getTitle().equals("View your status")) {
                    replaceFragment(ClientAccountsFragment.newInstance(AccountType.SAVINGS),
                            true, R.id.container);

                }else if (menuItem.getTitle().equals("Home")) {
                    startActivity(new Intent(LoanApplicationActivity.this,HomeActivity.class));

                }else if (menuItem.getTitle().equals("Loan Limit")) {

                 Intent intent = new Intent(getApplicationContext(),myloanlimit.class);
                    startActivity(intent);



                } else {
                    //
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }


        });

    }
}
