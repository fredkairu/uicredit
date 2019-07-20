package org.uipcredits.mobilebanking.ui.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.models.templates.loans.LoanTemplate;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.adapters.ImageAdapter;
import org.uipcredits.mobilebanking.ui.enums.AccountType;
import org.uipcredits.mobilebanking.ui.fragments.AccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.ClientAccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.HelpFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeOldFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountsDetailFragment;
import org.uipcredits.mobilebanking.ui.views.BaseActivityCallback;
import org.uipcredits.mobilebanking.ui.views.HelpView;
import org.uipcredits.mobilebanking.ui.views.LoanAccountsDetailView;
import org.uipcredits.mobilebanking.utils.Constants;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static java.lang.Long.getLong;

public class myloanlimit extends BaseActivity implements BaseActivityCallback {

    FabSpeedDial fabSpeedDial;
    Button btn_loan_submit;

    LinearLayout homebottom;
    LinearLayout helpbottom;
    LinearLayout accountsbottom;
    LinearLayout sharebottom;
    TextView LimitTops;

    private LoanTemplate loanTemplate;
    double accountId;
    public static final String MY_PREFS_LOANLIMIT = "Myloanlimit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myloanlimit);

        // transferType = getArguments().getString(Constants.TRANSFER_TYPE)

        SharedPreferences prefs2 = getSharedPreferences(MY_PREFS_LOANLIMIT, MODE_PRIVATE);
        String loanamoutLimit = prefs2.getString("loanlimitamount", null);



        ViewPager viewPager = findViewById(R.id.viewpager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);



        fabSpeedDial = findViewById(R.id.fabSpeedDial);
        LimitTops = findViewById(R.id.LimitTops);
        btn_loan_submit = findViewById(R.id.btn_loan_submit);


        LimitTops.setText(loanamoutLimit);

        /////////////////////ens
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
                Intent intent = new Intent(myloanlimit.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        accountsbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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



        btn_loan_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(myloanlimit.this, LoanApplicationActivity.class);
                startActivity(intent);
                finish();*/

                Intent intent = new Intent(myloanlimit.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                int id2 = menuItem.getGroupId();
             if (menuItem.getTitle().equals("Request Loan")) {

                    Intent intent = new Intent(myloanlimit.this, LoanApplicationActivity.class);
                    startActivity(intent);
                    finish();

                }else if (menuItem.getTitle().equals("Loan Limit")) {
                    Intent intent = new Intent(myloanlimit.this, myloanlimit.class);
                    startActivity(intent);
                    finish();

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

    @Override
    public void showProgressDialog(String message) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void setToolbarTitle(String title) {

    }
}
