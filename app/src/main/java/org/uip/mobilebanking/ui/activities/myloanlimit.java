package org.uip.mobilebanking.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.uip.mobilebanking.R;
import org.uip.mobilebanking.api.BaseURL;
import org.uip.mobilebanking.models.templates.loans.LoanTemplate;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.adapters.ImageAdapter;
import org.uip.mobilebanking.ui.fragments.HelpFragment;
import org.uip.mobilebanking.ui.views.BaseActivityCallback;

import java.util.HashMap;
import java.util.Map;

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

    String client_id, idNumber;
    private LoanTemplate loanTemplate;
    double accountId;
    public static final String MY_PREFS_LOANLIMIT = "Myloanlimit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myloanlimit);
        client_id = getIntent().getExtras().getString("client_id");
        idNumber = getIntent().getExtras().getString("idNumber");

        // transferType = getArguments().getString(Constants.TRANSFER_TYPE)

        SharedPreferences prefs2 = getSharedPreferences(MY_PREFS_LOANLIMIT, MODE_PRIVATE);
        String loanamoutLimit = prefs2.getString("loanlimitamount", null);

        ViewPager viewPager = findViewById(R.id.viewpager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);



        fabSpeedDial = findViewById(R.id.fabSpeedDial);
        LimitTops = findViewById(R.id.LimitTops);
        btn_loan_submit = findViewById(R.id.btn_loan_submit);


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
//                    Intent intent = new Intent(myloanlimit.this, myloanlimit.class);
//                    startActivity(intent);
//                    finish();

                } else {
                    //
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }


        });

        updateCRB(idNumber);
        getCreditLimit(client_id);
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

    private void getCreditLimit(final String clientId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseURL.LIMIT_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("getCreditLimit", "getCreditLimit Response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String amount = jsonObject.getString("Limit");
                            if(!amount.equals("")){
                                LimitTops.setText(amount);
                            }else{
                                LimitTops.setText("0");
                            }
                        }catch (Exception expt){
                            expt.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("getCreditLimit", "ERROR-"+error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // the GET parameters:
                params.put("client_id", clientId);
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(myloanlimit.this);
        //change the retry timeout
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 100,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    private void updateCRB(final String id_number) {
        Log.e("Details", id_number+"---"+client_id);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseURL.CRB_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("updateCRB", "updateCRB Response: " + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("updateCRB", "ERROR-"+error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // the GET parameters:
                params.put("clientId", client_id);
                params.put("id", id_number);
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(myloanlimit.this);
        //change the retry timeout
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 100,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

}
