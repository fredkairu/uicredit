package org.uipcredits.mobilebanking.ui.fragments;


import android.app.ProgressDialog;
import android.net.SSLCertificateSocketFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONException;
import org.json.JSONObject;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.utils.Constants;
import org.uipcredits.mobilebanking.utils.FcmVolley;
import org.uipcredits.mobilebanking.utils.gcm.Constant;
import org.uipcredits.mobilebanking.utils.gcm.SharedPreference;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class StkPushFragment extends Fragment {
    private long accountId;
    View rootView;
    private  static final String urlAdress="https://techsavanna.net:3000/UIPSTKPush/webresources/uipstkpush/mpesa/push";

    TextView accountidtxt;
    EditText amountedt,phoneedt;
    Button makepaymentbtn;
    private ProgressDialog progressDialog;
    public StkPushFragment() {
        // Required empty public constructor
    }

public static StkPushFragment newInstance(Long accountId){
    StkPushFragment transferFragment = new StkPushFragment();
    Bundle args = new Bundle();
    args.putLong(Constants.ACCOUNT_ID, accountId);
   // args.putString(Constants.TRANSFER_TYPE, transferType);
    transferFragment.setArguments(args);
    return transferFragment;
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountId = getArguments().getLong(Constants.ACCOUNT_ID);
           // transferType = getArguments().getString(Constants.TRANSFER_TYPE);
        }
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_stk_push, container, false);
        amountedt=rootView.findViewById(R.id.amount);
        phoneedt=rootView.findViewById(R.id.phoneno);
        accountidtxt=rootView.findViewById(R.id.accounttxt);
        makepaymentbtn=rootView.findViewById(R.id.paymentbtn);

       // accountidtxt.setText((int) accountId);
        accountidtxt.setText(String.valueOf(accountId));

        makepaymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payLoan(accountId);

            }
        });
        return rootView;
    }

    private void payLoan(final long accountId) {

        final String amount=amountedt.getText().toString();
        final String phone=phoneedt.getText().toString();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if (conn instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                        httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                        httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                    }
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    String strLastFourDi = phone.length() >= 9 ? phone.substring(phone.length() - 9): "";
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("phone", "254"+strLastFourDi);
                    jsonParam.put("amount", amount);
                    jsonParam.put("accountReference", "M#"+accountId);
                    jsonParam.put("transactionDesc", "Subscription");

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("Output from Server .... \n");
                    //   System.out.println("jsonnn"+br.readLine());

                    // String json = null;

                    //System.out.println("CheckoutRequestID :"+ br.readLine());
                    try {
                        JSONObject ns = new JSONObject(br.readLine());
                        String checkout=ns.getString("CheckoutRequestID");

                        sendToken(checkout);
//                        ApiConfirmPayment apiConfirmPayment= ApiConfirmPaymentClient.getClient().create(ApiConfirmPayment.class);
//                        Call<PaymetResponse> userCall = apiConfirmPayment.sendCheckout(checkout);
//
//                        userCall.enqueue(new Callback<PaymetResponse>() {
//                            @Override
//                            public void onResponse(Call<PaymetResponse> call, retrofit2.Response<PaymetResponse> response) {
//                                // hidepDialog();
//                                //onSignupSuccess();
//
//                                Log.d("onResponse", "" + response.body().getMessage());
//
//                                String res = response.body().getMessage();
//
//                                System.out.println("data result" + response.body().getSuccess());
//
//                                if (response.body().getSuccess() == 1) {
//                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                                } else {
//                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<PaymetResponse> call, Throwable t) {
//
//                                Log.d("onFailure", t.toString());
//                            }
//                        });


                        System.out.println("CheckoutRequestID :"+ checkout);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("MSG new" , conn.getInputStream().toString());


                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    public void sendToken(final String view) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Registering Device...");
                progressDialog.show();
//

                final String token = SharedPreference.getInstance(getContext()).getDeviceToken();
                final String email = view;

                System.out.println("Token update" + token + " " + email);
//
                if (token == null) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Token not generated", Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGISTER_DEVICE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("token", token);
                        return params;
                    }
                };
                FcmVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);
            }

        });
    }
}
