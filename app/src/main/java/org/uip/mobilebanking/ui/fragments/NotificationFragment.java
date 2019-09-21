package org.uip.mobilebanking.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.uip.mobilebanking.R;
import org.uip.mobilebanking.api.BaseURL;
import org.uip.mobilebanking.api.local.PreferencesHelper;
import org.uip.mobilebanking.models.notification.MifosNotification;
import org.uip.mobilebanking.presenters.NotificationPresenter;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.adapters.NotificationAdapter;
import org.uip.mobilebanking.ui.fragments.base.BaseFragment;
import org.uip.mobilebanking.utils.DividerItemDecoration;
import org.uip.mobilebanking.utils.Network;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dilpreet on 13/9/17.
 */

public class NotificationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_notifications)
    RecyclerView rvNotification;

    @BindView(R.id.swipe_notification_container)
    SwipeRefreshLayout swipeNotificationContainer;

    @BindView(R.id.layout_error)
    View layoutError;

    @Inject
    NotificationPresenter presenter;

    @Inject
    NotificationAdapter adapter;

    @Inject
    PreferencesHelper preferencesHelper;

    private View rootView;
    private SweetUIErrorHandler sweetUIErrorHandler;
    ArrayList<MifosNotification> notificationList;
    private String clientId;

    public static NotificationFragment newInstance(String clientID) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString("clientId",clientID);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.notification));
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            clientId = getArguments().getString("clientId");
        }
        sweetUIErrorHandler = new SweetUIErrorHandler(getActivity(), rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNotification.setLayoutManager(layoutManager);
        rvNotification.addItemDecoration(new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation()));
        rvNotification.setAdapter(adapter);

        swipeNotificationContainer.setColorSchemeResources(R.color.blue_light,
                R.color.green_light, R.color.orange_light, R.color.red_light);
        swipeNotificationContainer.setOnRefreshListener(this);

//        presenter.attachView(this);
//        presenter.loadNotifications();
        fetchAlerts();

        return rootView;
    }

//    @Override
//    public void showNotifications(List<MifosNotification> notifications) {
//        if (notifications.size() != 0) {
//            adapter.setNotificationList(notifications);
//        } else {
//            sweetUIErrorHandler.showSweetEmptyUI(getString(R.string.notification),
//                     R.drawable.ic_notifications, rvNotification, layoutError);
//        }
//    }

//    @Override
//    public void showError(String msg) {
//        if (!Network.isConnected(getActivity())) {
//            sweetUIErrorHandler.showSweetNoInternetUI(rvNotification, layoutError);
//        } else {
//            sweetUIErrorHandler.showSweetErrorUI(msg,
//                    rvNotification, layoutError);
//            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//        }
//    }

    @OnClick(R.id.btn_try_again)
    public void retryClicked() {
        if (Network.isConnected(getContext())) {
            sweetUIErrorHandler.hideSweetErrorLayoutUI(rvNotification, layoutError);
            presenter.loadNotifications();
        } else {
            Toast.makeText(getContext(), getString(R.string.internet_not_connected),
                    Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
    public void showProgress() {
        swipeNotificationContainer.setRefreshing(true);
    }

//    @Override
    public void hideProgress() {
        swipeNotificationContainer.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        sweetUIErrorHandler.hideSweetErrorLayoutUI(rvNotification, layoutError);
        fetchAlerts();

    }

    private void fetchAlerts() {
        showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseURL.NOTIFICATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("fetchAlerts", "fetchAlerts Response: " + response);
                        hideProgress();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("notifications");
                            if(jsonArray.length() == 0){
                                //no notifications
                            }else{
                                notificationList = new ArrayList<MifosNotification>();
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject notification = jsonArray.getJSONObject(i);
                                    MifosNotification mifosNotification = new MifosNotification();
                                    mifosNotification.setMsg(notification.getString("notification_content"));
                                    if(notification.getInt("is_read") == 0) {
                                        mifosNotification.setRead(false);
                                    }else{
                                        mifosNotification.setRead(true);
                                    }
                                    mifosNotification.setTimeStamp(notification.getString("made_on_date"));

                                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    long milliseconds1=0;
                                    try {
                                        Date d1 = f.parse(notification.getString("made_on_date"));
                                        milliseconds1 = d1.getTime();
                                        mifosNotification.setPk(milliseconds1);
                                    } catch (ParseException e) {
                                        mifosNotification.setPk(notification.getInt("id"));
                                        e.printStackTrace();
                                    }

                                    notificationList.add(mifosNotification);
                                }
                                adapter.setNotificationList(notificationList);
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("fetchAlerts", "ERROR-"+error.getMessage());
                        hideProgress();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // the GET parameters:
                params.put("client_id", ""+preferencesHelper.getClientId());
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //change the retry timeout
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 100,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
}
