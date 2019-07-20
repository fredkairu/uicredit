package org.uipcredits.mobilebanking.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.SSLCertificateSocketFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.models.register.RegisterPayload;
import org.uipcredits.mobilebanking.presenters.RegistrationPresenter;
import org.uipcredits.mobilebanking.ui.activities.CreateClientActivity;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.fragments.base.BaseFragment;
import org.uipcredits.mobilebanking.ui.views.RegistrationView;
import org.uipcredits.mobilebanking.utils.Network;
import org.uipcredits.mobilebanking.utils.Toaster;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment2 extends BaseFragment implements RegistrationView {
    String url="https://techsavanna.net:8181/uip/api/authenticate.php";
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_first_name)
    EditText etFirstName;

    @BindView(R.id.et_last_name)
    EditText etLastName;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.rg_verification_mode)
    RadioGroup rgVerificationMode;

    @Inject
    RegistrationPresenter presenter;

    private View rootView;

    public static RegistrationFragment2 newInstance() {
        RegistrationFragment2 fragment = new RegistrationFragment2();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_registration_fragment2, container, false);

        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);

        return rootView;
    }
    @OnClick(R.id.btn_register)
    public void registerClicked() {
        if (areFieldsValidated()) {

            SharedPreferences settings =  getActivity().getSharedPreferences(CreateClientActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String firstname = settings.getString(CreateClientActivity.firstnameacc, "");
            String lastname = settings.getString(CreateClientActivity.lastnameacc, "");
            // String middlename = settings.getString(middlenameacc, "");
            String phonenumber = settings.getString(CreateClientActivity.phonenumberacc, "");
            final String accountid = settings.getString(CreateClientActivity.accountIdacc, "");

            RadioButton radioButton =  rootView.findViewById(rgVerificationMode.
                    getCheckedRadioButtonId());

            final RegisterPayload payload = new RegisterPayload();
            // payload.setAccountNumber(etAccountNumber.getText().toString());
            payload.setAccountNumber(accountid);
            System.out.println("account2"+firstname);
            //payload.setAuthenticationMode(radioButton.getText().toString());
            payload.setAuthenticationMode("email");
            payload.setEmail(etEmail.getText().toString());
            //  payload.setFirstName(etFirstName.getText().toString());
            payload.setFirstName(firstname);
            //  payload.setLastName(etLastName.getText().toString());
            // payload.setMobileNumber(etPhoneNumber.getText().toString());
            System.out.println("Print Data"+firstname);
            payload.setLastName(lastname);
            payload.setMobileNumber(phonenumber);
            if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                Toaster.show(rootView, getString(R.string.error_password_not_match));
                return;
            } else {
                payload.setPassword(etPassword.getText().toString());
            }
            payload.setPassword(etPassword.getText().toString());
            payload.setUsername(etUsername.getText().toString().replace(" ", ""));
            System.out.println("Payload :"+payload);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        URL urls = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

                        if (conn instanceof HttpsURLConnection) {
                            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                            httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                            httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                        }
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);


                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(accountid);

                        os.flush();
                        os.close();
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        System.out.println("Output from Server .... \n");

                        String accountId=br.readLine();



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            thread.start();
            if (Network.isConnected(getContext())) {
                presenter.registerUser(payload);
            } else {
                Toaster.show(rootView, getString(R.string.no_internet_connection));
            }
        }

    }

    private boolean areFieldsValidated() {

        SharedPreferences settings =  getActivity().getSharedPreferences(CreateClientActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String firstname = settings.getString(CreateClientActivity.firstnameacc, "");
        String lastname = settings.getString(CreateClientActivity.lastnameacc, "");
        // String middlename = settings.getString(middlenameacc, "");
        String phonenumber = settings.getString(CreateClientActivity.phonenumberacc, "");
        String accountid = settings.getString(CreateClientActivity.accountIdacc, "");


        if (accountid.trim().length() == 0) {
            Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.
                    account_number)));
            return false;
        } else
        if (etUsername.getText().toString().trim().length() == 0) {
            Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.
                    username)));
            return false;
        } else if (etUsername.getText().toString().trim().length() < 5) {
            Toaster.show(rootView, getString(R.string.error_validation_minimum_chars,
                    getString(R.string.username),
                    getResources().getInteger(R.integer.username_minimum_length)));
            return false;
        } else if (etUsername.getText().toString().trim().contains(" ")) {
            Toaster.show(rootView, getString(R.string.error_validation_cannot_contain_spaces,
                    getString(R.string.username), getString(R.string.not_contain_username)));
            return false;
        } else
        if (firstname.length() == 0) {
            Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.
                    first_name)));
            return false;
        } else if (lastname.trim().length() == 0) {
            Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.
                    last_name)));
            return false;
        } else
        if (etEmail.getText().toString().trim().length() == 0) {
            Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.
                    email)));
            return false;
        } else if (etPassword.getText().toString().trim().length() == 0) {
            Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.
                    password)));
            return false;
        } else if (etPassword.getText().toString().trim().length()
                < etPassword.getText().toString().length()) {
            Toaster.show(rootView,
                    getString(R.string.error_validation_cannot_contain_leading_or_trailing_spaces,
                            getString(R.string.password)));
            return false;
        } else if (etUsername.getText().toString().trim().length() < 6) {
            Toaster.show(rootView, getString(R.string.error_username_greater_than_six));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( etEmail.getText().toString().trim())
                .matches()) {
            Toaster.show(rootView, getString(R.string.error_invalid_email));
            return false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            Toaster.show(rootView, getString(R.string.error_validation_minimum_chars,
                    getString(R.string.password), getResources().
                            getInteger(R.integer.password_minimum_length)));
            return false;
        }

        return true;
    }

    @Override
    public void showRegisteredSuccessfully() {
        ((BaseActivity) getActivity()).replaceFragment(RegistrationVerificationFragment.
                newInstance(), true, R.id.container);
    }

    @Override
    public void showError(String msg) {
        Toaster.show(rootView, msg);
    }

    @Override
    public void showProgress() {
        showMifosProgressDialog(getString(R.string.sign_up));
    }

    @Override
    public void hideProgress() {
        hideMifosProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
