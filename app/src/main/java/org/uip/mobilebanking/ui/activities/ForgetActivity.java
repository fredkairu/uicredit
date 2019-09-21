package org.uip.mobilebanking.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.uip.mobilebanking.ApiConfirmClientClient;
import org.uip.mobilebanking.R;
import org.uip.mobilebanking.api.ApiConfirmClientService;
import org.uip.mobilebanking.utils.ApiResponseClientConfirmation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetActivity extends AppCompatActivity {
    EditText et_date_birth,et_phone_number;
    Button btn_client;
    SharedPreferences sharedpreferences;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        et_phone_number=findViewById(R.id.et_phone_number);
        et_date_birth=findViewById(R.id.et_date_birth);

        btn_client=findViewById(R.id.btn_client);

        btn_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkApp();
            }
        });
    }

    private void checkApp() {

        pDialog = new ProgressDialog(ForgetActivity.this,
                R.style.Theme_AppCompat_DayNight_DarkActionBar);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        showpDialog();

        String phone=et_phone_number.getText().toString();
        String idnumber=et_date_birth.getText().toString();
        ApiConfirmClientService service = ApiConfirmClientClient.getClient().create(ApiConfirmClientService.class);

        Call<ApiResponseClientConfirmation> userCall = service.checkClient(phone,idnumber);

        userCall.enqueue(new Callback<ApiResponseClientConfirmation>() {
            @Override
            public void onResponse(Call<ApiResponseClientConfirmation> call, Response<ApiResponseClientConfirmation> response) {
                hidepDialog();
                //onSignupSuccess();
                System.out.println("data out"+ call);
                Log.e("onResponse", "" + response.body().getMessage());


                if(response.body().getSuccess() == 1) {
                    String accountId=response.body().getAccount_no();
                    String firstname=response.body().getFirstname();
                    String lastname=response.body().getLastname();
                    String phonenumber=response.body().getMobile_no();

                    sharedpreferences = getSharedPreferences(CreateClientActivity.MyPREFERENCES, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    System.out.println("Account No"+accountId);
                    editor.putString(CreateClientActivity.firstnameacc, firstname);
                    editor.putString(CreateClientActivity.lastnameacc, lastname);
                    // editor.putString(middlenameacc, lastname);

                    editor.putString(CreateClientActivity.phonenumberacc,phonenumber);
                    editor.putString(CreateClientActivity.accountIdacc, accountId);
                    editor.commit();


                    Intent intent=new Intent(ForgetActivity.this,RegistrationActivity2.class);
                    startActivity(intent);
                    // dismiss();
                }else {
                    Toast.makeText(ForgetActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseClientConfirmation> call, Throwable t) {
                hidepDialog();
                Log.e("onFailure", t.toString());
                Toast.makeText(ForgetActivity.this,"A network error occurred. Please try again!", Toast.LENGTH_LONG).show();;
            }
        });
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
