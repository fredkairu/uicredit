package org.uipcredits.mobilebanking.ui.fragments;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.api.local.PreferencesHelper;
import org.uipcredits.mobilebanking.models.accounts.loan.LoanAccount;
import org.uipcredits.mobilebanking.models.client.Client;
import org.uipcredits.mobilebanking.presenters.HomeOldPresenter;
import org.uipcredits.mobilebanking.ui.activities.HomeActivity;
import org.uipcredits.mobilebanking.ui.activities.LoanApplicationActivity;
import org.uipcredits.mobilebanking.ui.activities.LoginActivity;
import org.uipcredits.mobilebanking.ui.activities.NotificationActivity;
import org.uipcredits.mobilebanking.ui.activities.UserProfileActivity;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.activities.myloanlimit;
import org.uipcredits.mobilebanking.ui.enums.AccountType;
import org.uipcredits.mobilebanking.ui.enums.ChargeType;
import org.uipcredits.mobilebanking.ui.fragments.base.BaseFragment;
import org.uipcredits.mobilebanking.ui.views.HomeOldView;
import org.uipcredits.mobilebanking.ui.views.LoanAccountsDetailView;
import org.uipcredits.mobilebanking.ui.views.LoanApplicationMvpView;
import org.uipcredits.mobilebanking.utils.CircularImageView;
import org.uipcredits.mobilebanking.utils.Constants;
import org.uipcredits.mobilebanking.utils.CurrencyUtil;
import org.uipcredits.mobilebanking.utils.MaterialDialog;
import org.uipcredits.mobilebanking.utils.TextDrawable;
import org.uipcredits.mobilebanking.utils.Toaster;

import org.uipcredits.mobilebanking.ui.enums.AccountType;
import org.uipcredits.mobilebanking.ui.enums.ChargeType;
import org.uipcredits.mobilebanking.ui.views.HomeOldView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by michaelsosnick on 1/1/17.
 */

public class HomeOldFragment extends BaseFragment implements HomeOldView,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.tv_saving_total_amount)
    TextView tvSavingTotalAmount;

    @BindView(R.id.tv_loan_total_amount)
    TextView tvLoanTotalAmount;

    @BindView(R.id.ll_account_detail)
    LinearLayout llAccountDetail;

    @BindView(R.id.iv_visibility)
    ImageView ivVisibility;

    @BindView(R.id.iv_user_image)
    ImageView ivUserImage;

    @BindView(R.id.iv_circular_user_image)
    CircularImageView ivCircularUserImage;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.swipe_home_container)
    SwipeRefreshLayout slHomeContainer;

    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    @BindView(R.id.ll_repay_loan)
    LinearLayout repayll;

    @BindView(R.id.viewStatus)
    CardView viewStatus;



    @BindView(R.id.ll_loan_limit)
    LinearLayout ll_loan_limit;




    @Inject
    HomeOldPresenter presenter;

    @Inject
    PreferencesHelper preferencesHelper;

    LinearLayout ll_apply_for_loan;
    TextView warningTxt;
    String loanlimiter;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String MY_PREFS_LOANLIMIT = "Myloanlimit";

    View rootView;
    private double totalLoanAmount, totalSavingAmount;
    private Client client;
    private long clientId;
    private View toolbarView;
    private boolean isDetailVisible;
    private boolean isReceiverRegistered = false;
    private TextView tvNotificationCount;

    private LoanAccount loanAccount;

    public static HomeOldFragment newInstance() {
        HomeOldFragment fragment = new HomeOldFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_old, container, false);
        ((HomeActivity) getActivity()).getActivityComponent().inject(this);
        ButterKnife.bind(this, rootView);
        TextView tv_loan_total_amount=rootView.findViewById(R.id.tv_loan_total_amount);
        ll_apply_for_loan =rootView.findViewById(R.id.ll_apply_for_loan);
        viewStatus =rootView.findViewById(R.id.viewStatus);


       warningTxt=rootView.findViewById(R.id.warningTxt);
        String tv_loan_total_amountt=tv_loan_total_amount.getText().toString();

        clientId = preferencesHelper.getClientId();

        presenter.attachView(this);
        setHasOptionsMenu(true);

        slHomeContainer.setColorSchemeResources(R.color.blue_light, R.color.green_light, R
                .color.orange_light, R.color.red_light);
        slHomeContainer.setOnRefreshListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            llContainer.getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }
        if (savedInstanceState == null) {
            loadClientData();
        }

        setToolbarTitle(getString(R.string.home));
        showUserInterface();
        return rootView;
    }

    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().invalidateOptionsMenu();
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_notifications);
        View count = menuItem.getActionView();
        tvNotificationCount = count.findViewById(R.id.tv_notification_indicator);
        presenter.getUnreadNotificationsCount();
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), NotificationActivity.class));
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

   @Override
    public void onResume() {
        super.onResume();

        registerReceiver();
        getActivity().invalidateOptionsMenu();////////////////////////////////////////////////////////

       //startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(notificationReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(notificationReceiver,
                    new IntentFilter(Constants.NOTIFY_HOME_FRAGMENT));
            isReceiverRegistered = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(Constants.TOTAL_LOAN, totalLoanAmount);
        outState.putDouble(Constants.TOTAL_SAVINGS, totalSavingAmount);
        outState.putParcelable(Constants.USER_DETAILS, client);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            showUserDetails((Client) savedInstanceState.getParcelable(Constants.USER_DETAILS));
            presenter.setUserProfile(preferencesHelper.getUserProfileImage());
            showLoanAccountDetails(savedInstanceState.getDouble(Constants.TOTAL_LOAN));
            showSavingAccountDetails(savedInstanceState.getDouble(Constants.TOTAL_SAVINGS));


        }
    }

    @Override
    public void onRefresh() {
        loadClientData();
    }

    private void loadClientData() {
        presenter.loadClientAccountDetails();
        presenter.getUserDetails();
        presenter.getUserImage();



    }

    @Override
    public void showUserInterface() {
        toolbarView = ((HomeActivity) getActivity()).getToolbar().getRootView();
        isDetailVisible = preferencesHelper.overviewState();
        if (isDetailVisible) {
            showOverviewState();
        } else {
            hideOverviewState();
        }
    }

    /**
     * Opens {@link ClientAccountsFragment} according to the {@code accountType} provided
     *
     * @param accountType Enum of {@link AccountType}
     */
    public void openAccount(AccountType accountType) {
        ((BaseActivity) getActivity()).replaceFragment(
                ClientAccountsFragment.newInstance(accountType), true, R.id.container);
    }

    /**
     * Provides {@code totalLoanAmount} fetched from server
     *
     * @param totalLoanAmount Total Loan amount
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void showLoanAccountDetails(double totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
        System.out.println("Total Loan"+totalLoanAmount);
        //warningTxt.setText((int) totalLoanAmount);
        if(totalLoanAmount>0) {
            ll_apply_for_loan.setEnabled(false);
           // warningTxt.setText("Please clear your loan");


            warningTxt.setTextColor(R.color.colorAccent);
        }
            tvLoanTotalAmount.setText(CurrencyUtil.formatCurrency(getContext(), totalLoanAmount));

        double amntLoan = totalLoanAmount;
        SharedPreferences.Editor editor2 = getActivity().getSharedPreferences(MY_PREFS_LOANLIMIT, MODE_PRIVATE).edit();
        editor2.putString("loanlimitamount", String.valueOf(amntLoan));
        editor2.apply();

    }


    /**
     * Open LOAN tab under ClientAccountsFragment
     */
    @OnClick(R.id.ll_total_loan)
    public void onClickLoan() {
        openAccount(AccountType.LOAN);
        ((HomeActivity) getActivity()).setNavigationViewSelectedItem(R.id.item_accounts);
    }
    @OnClick(R.id.ll_repay_loan)
    public void onClickRepay() {
        ((HomeActivity) getActivity()).replaceFragment(
                ClientAccountsFragment.newInstance(AccountType.LOAN), true,
                R.id.container);
    }

    @OnClick(R.id.ll_loan_limit)////addition
    public void onClickLoanLimit() {

        //System.out.print("LIMITED LOAN "+warningTxt.getText().toString());
        startActivity(new Intent(getContext(), myloanlimit.class));
    }

    @OnClick(R.id.viewStatus)
    public void onClickViewStatus() {
        ((HomeActivity) getActivity()).replaceFragment(
                ClientAccountsFragment.newInstance(AccountType.LOAN), true,
                R.id.container);
    }



    /**
     * Provides {@code totalSavingAmount} fetched from server
     *
     * @param totalSavingAmount Total Saving amount
     */
    @Override
    public void showSavingAccountDetails(double totalSavingAmount) {
        this.totalSavingAmount = totalSavingAmount;
        tvSavingTotalAmount.setText(CurrencyUtil.formatCurrency(getContext(), totalSavingAmount));


        //getting Loan limit number and passing storing it for late use



    }

    /**
     * Open SAVINGS tab under ClientAccountsFragment
     */
    @OnClick(R.id.ll_total_savings)
    public void onClickSavings() {
        openAccount(AccountType.SAVINGS);
        ((HomeActivity) getActivity()).setNavigationViewSelectedItem(R.id.item_accounts);
    }


    /**
     * Fetches Client details and display clientName
     *
     * @param client Details about client
     */
    @SuppressLint("StringFormatInvalid")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showUserDetails(Client client) {
        this.client = client;


         //getting phone number and passing storing it for late use
        String phon2 = client.getMobileNo();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("phonenoIndividual", phon2);
        editor.apply();

       //getting the hour time for displaying the right greeting on the dashboard based on time
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("H");
        String hrs = currentTime.format(cal.getTime());

        System.out.print("TIME NOW "+hrs);

        //Getting first letter of the name and making it capital
        String fname = client.getDisplayName();
        String lastName = "";
        String firstName= "";
        if(fname.split("\\w+").length>1){

            lastName = fname.substring(fname.lastIndexOf(" ")+1);
            firstName = fname.substring(0, fname.lastIndexOf(' '));
        }
        else{
            firstName = fname;
        }
        //Capitalization of the first letter
        String capitalfirstletter = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        System.out.print("Name "+capitalfirstletter);

        if (hrs.equals("17")||hrs.equals("18")||hrs.equals("19")||hrs.equals("20")||hrs.equals("21")||hrs.equals("22")||hrs.equals("23")){
            tvUserName.setText(getString(R.string.goodevening1)+" "+capitalfirstletter+",");
        }else if (hrs.equals("12")||hrs.equals("13")||hrs.equals("14")||hrs.equals("15")||hrs.equals("16")){
            tvUserName.setText(getString(R.string.goodafternoon1)+" "+capitalfirstletter+",");
        }else if (hrs.equals("05")||hrs.equals("06")||hrs.equals("07")||hrs.equals("08")||hrs.equals("09")||hrs.equals("10")||hrs.equals("11")){
            tvUserName.setText(getString(R.string.goodmorning1)+" "+capitalfirstletter+",");
        }else if (hrs.equals("5")||hrs.equals("6")||hrs.equals("7")||hrs.equals("8")||hrs.equals("9")||hrs.equals("10")||hrs.equals("11")){
            tvUserName.setText(getString(R.string.goodmorning1)+" "+capitalfirstletter+",");
        }else {
            tvUserName.setText(getString(R.string.hello_client)+" "+capitalfirstletter+",");
        }

    }

    /**
     * Provides with Client image fetched from server
     *
     * @param bitmap Client Image
     */
    @Override
    public void showUserImage(final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {

                    ivUserImage.setVisibility(View.GONE);
                    ivCircularUserImage.setVisibility(View.VISIBLE);
                    ivCircularUserImage.setImageBitmap(bitmap);

                } else {

                    String userName;
                    if (!preferencesHelper.getUserName().isEmpty()) {

                        userName = preferencesHelper.getUserName();
                    } else {

                        userName = getString(R.string.app_name);
                    }
                    TextDrawable drawable = TextDrawable.builder()
                            .beginConfig()
                            .toUpperCase()
                            .endConfig()
                            .buildRound(userName.substring(0, 1),
                                    ContextCompat.getColor(
                                            getContext(), R.color.txtcolors));
                    ivUserImage.setVisibility(View.VISIBLE);
                    ivUserImage.setImageDrawable(drawable);
                    ivCircularUserImage.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void showNotificationCount(int count) {

        if (count > 0) {
            tvNotificationCount.setVisibility(View.VISIBLE);
            tvNotificationCount.setText(String.valueOf(count));
        } else {
            tvNotificationCount.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_user_image, R.id.iv_circular_user_image})
    public void userImageClicked() {
        startActivity(new Intent(getActivity(), UserProfileActivity.class));
    }

    /**
     * Reverses the state of Account Overview section i.e. visible to hidden or vice a versa
     */
    @OnClick(R.id.iv_visibility)
    public void reverseDetailState() {
        if (isDetailVisible) {
            isDetailVisible = false;
            preferencesHelper.setOverviewState(false);
            hideOverviewState();
        } else {
            isDetailVisible = true;
            preferencesHelper.setOverviewState(true);
            showOverviewState();
        }
    }

    /**
     * Makes Overview state visible
     */
    private void showOverviewState() {
        ivVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_24px));
        ivVisibility.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray_dark));
        llAccountDetail.setVisibility(View.VISIBLE);
    }

    /**
     * Hides Overview state
     */
    private void hideOverviewState() {
        ivVisibility.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_visibility_off_24px));
        ivVisibility.setColorFilter(ContextCompat.getColor(getActivity(), R.color.light_grey));
        llAccountDetail.setVisibility(View.GONE);
    }


    /**
     * Calls {@code openAccount()} for opening {@link ClientAccountsFragment}
     */
    @OnClick(R.id.ll_accounts)
    public void accountsClicked() {
        ((HomeActivity) getActivity()).replaceFragment(
                AccountOverviewFragment.newInstance(), true,
                R.id.container);
    }



    /**
     * Shows a dialog with options: Normal Transfer and Third Party Transfer
     */
    @OnClick(R.id.ll_transfer)
    public void transferClicked() {
        String[] transferTypes = {getString(R.string.transfer), getString(R.string.
                third_party_transfer)};
        new MaterialDialog.Builder().init(getActivity())
                .setTitle(R.string.choose_transfer_type)
                .setItems(transferTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ((HomeActivity) getActivity()).replaceFragment(
                                    SavingsMakeTransferFragment.newInstance(1, ""), true,
                                    R.id.container);
                        } else {
                            ((HomeActivity) getActivity()).replaceFragment(
                                    ThirdPartyTransferFragment.newInstance(), true, R.id.container);
                        }
                    }
                })
                .createMaterialDialog()
                .show();
    }

    /**
     * Opens {@link ClientChargeFragment} to display all Charges associated with client's account
     */
    @OnClick(R.id.ll_charges)
    public void chargesClicked() {
        ((HomeActivity) getActivity()).replaceFragment(ClientChargeFragment.newInstance(clientId,
                ChargeType.CLIENT), true, R.id.container);
    }

    /**
     * Opens {@link LoanApplicationFragment} to apply for a loan
     */
    @OnClick(R.id.ll_apply_for_loan)
    public void applyForLoan() {
        startActivity(new Intent(getActivity(), LoanApplicationActivity.class));  ///////////////////////////
    }

   /* *//**
     * Opens {@link LoanApplicationFragment} to viewStatus for a loan
     *//*
    @OnClick(R.id.viewStatus)
    public void viewStatus() {
        startActivity(new Intent(getActivity(), LoanAccountsDetailView.class));
    }
*/


    /**
     * Opens {@link BeneficiaryListFragment} which contains list of Beneficiaries associated with
     * Client's account
     */
    @OnClick(R.id.ll_beneficiaries)
    public void beneficiaries() {
        ((HomeActivity) getActivity()).replaceFragment(BeneficiaryListFragment.
                newInstance(), true, R.id.container);
    }

    @OnClick(R.id.ll_surveys)
    public void surveys() {

    }

    /**
     * It is called whenever any error occurs while executing a request
     * @param errorMessage Error message that tells the user about the problem.
     */
    @Override
    public void showError(String errorMessage) {
        int checkedItem = ((HomeActivity) getActivity()).getCheckedItem();
        if (checkedItem == R.id.item_about_us || checkedItem == R.id.item_help ||
                checkedItem == R.id.item_settings) {
            return;
        }
        Toaster.show(rootView, errorMessage);
    }

    /**
     * Shows {@link SwipeRefreshLayout}
     */
    @Override
    public void showProgress() {
        slHomeContainer.setRefreshing(true);
    }

    /**
     * Hides {@link SwipeRefreshLayout}
     */
    @Override
    public void hideProgress() {
        slHomeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (slHomeContainer.isRefreshing()) {
            slHomeContainer.setRefreshing(false);
            slHomeContainer.removeAllViews();
        }
        presenter.detachView();
    }

}

