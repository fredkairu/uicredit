package org.uip.mobilebanking.ui.fragments;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.uip.mobilebanking.R;
import org.uip.mobilebanking.api.BaseURL;
import org.uip.mobilebanking.api.local.PreferencesHelper;
import org.uip.mobilebanking.models.accounts.loan.LoanAccount;
import org.uip.mobilebanking.models.accounts.loan.LoanWithAssociations;
import org.uip.mobilebanking.models.accounts.loan.TitleDeed;
import org.uip.mobilebanking.models.payload.LoansPayload;
import org.uip.mobilebanking.models.templates.loans.LoanTemplate;
import org.uip.mobilebanking.models.templates.loans.ProductOptions;
import org.uip.mobilebanking.presenters.LoanApplicationPresenter;
import org.uip.mobilebanking.ui.activities.base.BaseActivity;
import org.uip.mobilebanking.ui.enums.LoanState;
import org.uip.mobilebanking.ui.fragments.base.BaseFragment;
import org.uip.mobilebanking.ui.views.LoanApplicationMvpView;
import org.uip.mobilebanking.utils.Constants;
import org.uip.mobilebanking.utils.DateHelper;
import org.uip.mobilebanking.utils.MFDatePicker;
import org.uip.mobilebanking.utils.Network;
import org.uip.mobilebanking.utils.Toaster;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import static java.lang.Double.parseDouble;

/**
 * Created by Rajan Maurya on 06/03/17.
 */
public class LoanApplicationFragment extends BaseFragment implements LoanApplicationMvpView,
        MFDatePicker.OnDatePickListener, AdapterView.OnItemSelectedListener {

    static TextInputLayout tilPrincipalAmount, title_number, title_value;
    static TextView tvlimittops;

    @BindView(R.id.tv_new_loan_application)
    TextView tvNewLoanApplication;

    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;

    @BindView(R.id.tv_submission_date)
    TextView tvSubmissionDate;

    @BindView(R.id.sp_loan_products)
    Spinner spLoanProducts;

    @BindView(R.id.sp_loan_purpose)
    Spinner spLoanPurpose;

    @BindView(R.id.tv_currency)
    TextView tvCurrency;

    @BindView(R.id.tv_expected_disbursement_date)
    TextView tvExpectedDisbursementDate;

    @BindView(R.id.ll_add_loan)
    LinearLayout llAddLoan;

    @BindView(R.id.ll_error)
    RelativeLayout llError;

    @BindView(R.id.tv_status)
    TextView tvErrorStatus;

    @BindView(R.id.iv_status)
    ImageView ivReload;

    @BindView(R.id.fabSpeedDial)
    FabSpeedDial fabSpeedDial;

    @BindView(R.id.accountsbottom)
    LinearLayout accountsbottom;

    @BindView(R.id.btn_loan_submit)
    AppCompatButton btn_loan_submit;


    @Inject
    LoanApplicationPresenter loanApplicationPresenter;

    @Inject
    PreferencesHelper preferencesHelper;
    View rootView;

    NumberFormat formatter = NumberFormat.getNumberInstance();
    private List<String> listLoanProducts = new ArrayList<>();
    private List<String> listLoanPurpose = new ArrayList<>();

    private ArrayAdapter<String> loanProductAdapter;
    private ArrayAdapter<String> loanPurposeAdapter;

    private LoanTemplate loanTemplate;
    private DialogFragment mfDatePicker;
    private LoanState loanState;
    private LoanWithAssociations loanWithAssociations;
    private int productId;
    private int purposeId;
    private String disbursementDate, phoneNumber, client_id;
    private String submittedDate;
    private boolean isDisbursementDate = false;
    private boolean isLoanUpdatePurposesInitialization = true;
    private Snackbar snackbar;
    private ArrayList<TitleDeed> titles;
    private boolean isSecure = false;

    /**
     * Used when we want to apply for a Loan
     * @param loanState {@link LoanState} is set to {@code LoanState.CREATE}
     * @return Instance of {@link LoanApplicationFragment}
     */
    public static LoanApplicationFragment newInstance(LoanState loanState, String phone, String client_id) {
        LoanApplicationFragment fragment = new LoanApplicationFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.LOAN_STATE, loanState);
        args.putString("phone", phone);
        args.putString("client_id", client_id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Used when we want to update a Loan Application
     * @param loanState {@link LoanState} is set to {@code LoanState.UPDATE}
     * @param loanWithAssociations {@link LoanAccount} to modify
     * @return Instance of {@link LoanApplicationFragment}
     */
    public static LoanApplicationFragment newInstance(LoanState loanState,
                                                      LoanWithAssociations loanWithAssociations,
                                                      String phone, String clientID) {
        LoanApplicationFragment fragment = new LoanApplicationFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.LOAN_STATE, loanState);
        args.putParcelable(Constants.LOAN_ACCOUNT, loanWithAssociations);
        args.putString("phone", phone);
        args.putString("client_id",clientID);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        if (getArguments() != null) {
            loanState = (LoanState) getArguments().getSerializable(Constants.LOAN_STATE);
            phoneNumber = getArguments().getString("phone");
            client_id = getArguments().getString("client_id");

            if (loanState == LoanState.CREATE) {
                setToolbarTitle(getString(R.string.apply_for_loan));
            } else {
                setToolbarTitle(getString(R.string.update_loan));
                loanWithAssociations = getArguments().getParcelable(Constants.LOAN_ACCOUNT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_loan_application, container, false);

        tilPrincipalAmount = (TextInputLayout) rootView.findViewById(R.id.til_principal_amount);
        title_number = (TextInputLayout) rootView.findViewById(R.id.title_number);
        title_value = (TextInputLayout) rootView.findViewById(R.id.title_value);
        tvlimittops = (TextView) rootView.findViewById(R.id.LimitTops);
        ButterKnife.bind(this, rootView);
        loanApplicationPresenter.attachView(this);

        showUserInterface();
        if (savedInstanceState == null) {
            loadLoanTemplate();
            getCreditLimit();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.TEMPLATE, loanTemplate);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            LoanTemplate template = savedInstanceState.getParcelable(Constants.TEMPLATE);
            if (loanState == LoanState.CREATE) {
                showLoanTemplate(template);
            } else {
                showUpdateLoanTemplate(template);
            }
        }
    }

    /**
     * Loads {@link LoanTemplate} according to the {@code loanState}
     */
    private void loadLoanTemplate() {
        if (loanState == LoanState.CREATE) {
            loanApplicationPresenter.loadLoanApplicationTemplate(LoanState.CREATE);
        } else {
            loanApplicationPresenter.loadLoanApplicationTemplate(LoanState.UPDATE);
        }
    }

    /**
     * Calls function which applies for a new Loan Application or updates a Loan Application
     * according to {@code loanState}
     */
    @OnClick(R.id.btn_loan_submit)
    void onSubmitLoanApplication() {
        if (tilPrincipalAmount.getEditText().getText().toString().equals("")) {
            tilPrincipalAmount.setError(getString(R.string.enter_amount));
            return;
        }

        if (tilPrincipalAmount.getEditText().getText().toString().equals(".")) {
            tilPrincipalAmount.setError(getString(R.string.invalid_amount));
            return;
        }

        if (tilPrincipalAmount.getEditText().getText().toString().matches("^0*")) {
            tilPrincipalAmount.setError(getString(R.string.amount_greater_than_zero));
            return;
        }
        if (Double.parseDouble(tilPrincipalAmount.getEditText().getText().toString()) < 250) {
            tilPrincipalAmount.setError(getString(R.string.amount_less_than_min));
            return;
        }
        if (Double.parseDouble(tilPrincipalAmount.getEditText().getText().toString()) > Double.parseDouble(tvlimittops.getText().toString())) {
            tilPrincipalAmount.setError("You cannot borrow more than KSh "+Double.parseDouble(tvlimittops.getText().toString()));
            return;
        }
        if (loanState == LoanState.CREATE) {
            submitNewLoanApplication();
        } else {
            submitUpdateLoanApplication();
        }
    }



    /**
     * Submits a New Loan Application to the server
     */
    private void submitNewLoanApplication() {
        LoansPayload loansPayload = new LoansPayload();
        loansPayload.setClientId(loanTemplate.getClientId());
        //loansPayload.setLoanPurposeId(purposeId);

/*
        double  amountobepaid = parseDouble(tilPrincipalAmount.getEditText().getText().toString());

         double actualamt = amountobepaid - (amountobepaid * 0.15);
         double amount2 = actualamt -25;

         System.out.print("REAL AMOUNT TO BE "+actualamt);*/

//
//      productId=1;
      //  System.out.println("Product Idd"+productId);
        loansPayload.setProductId(productId);
        loansPayload.setPrincipal(parseDouble(tilPrincipalAmount.getEditText().getText().toString()));//////////ENS ADDED
        loansPayload.setLoanTermFrequency(loanTemplate.getTermFrequency());
        loansPayload.setLoanTermFrequencyType(loanTemplate.getInterestRateFrequencyType().getId());
        loansPayload.setLoanType("individual");
        loansPayload.setNumberOfRepayments(loanTemplate.getNumberOfRepayments());
        loansPayload.setRepaymentEvery(loanTemplate.getRepaymentEvery());
        loansPayload.setRepaymentFrequencyType(loanTemplate.getInterestRateFrequencyType().getId());
        loansPayload.setInterestRatePerPeriod(loanTemplate.getInterestRatePerPeriod());
        loansPayload.setExpectedDisbursementDate(disbursementDate);
        loansPayload.setSubmittedOnDate(submittedDate);

        loansPayload.setTransactionProcessingStrategyId(
                loanTemplate.getTransactionProcessingStrategyId());
        loansPayload.setAmortizationType(loanTemplate.getAmortizationType().getId());
        loansPayload.setInterestCalculationPeriodType(
                loanTemplate.getInterestCalculationPeriodType().getId());
        loansPayload.setInterestType(loanTemplate.getInterestType().getId());
        //secure loans
        if(isSecure) {
            try {
                loansPayload.setSecure("1");
                JsonArray datatables = new JsonArray();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("registeredTableName", "more_loan_details");
                JsonObject data = new JsonObject();
                data.addProperty("title_deed", title_number.getEditText().getText().toString());
                data.addProperty("locale", "en");
                data.addProperty("value", ""+formatter.parse(title_value.getEditText().getText().toString()).intValue());
                jsonObject.add("data", data);
                datatables.add(jsonObject);
                loansPayload.setDatatables(datatables);
            } catch (Exception ext) {
                Log.e("LOAN ERROR", ext.getMessage());
            }
        }
        loanApplicationPresenter.createLoansAccount(loansPayload);
    }

    /**
     * Requests server to update the Loan Application with new values
     */
    private void submitUpdateLoanApplication() {

/*
        double  amountobepaid = parseDouble(tilPrincipalAmount.getEditText().getText().toString());

        double actualamt = amountobepaid - (amountobepaid * 0.15);
        double amount2 = actualamt -25;

        System.out.print("REAL AMOUNT TO BE 2 "+actualamt);*/

        LoansPayload loansPayload = new LoansPayload();
        loansPayload.setPrincipal(parseDouble(tilPrincipalAmount.getEditText().getText().toString())); //////ENS ADDED
        loansPayload.setProductId(productId);
        //loansPayload.setLoanPurposeId(purposeId);
        loansPayload.setLoanTermFrequency(loanTemplate.getTermFrequency());
        loansPayload.setLoanTermFrequencyType(loanTemplate.getInterestRateFrequencyType().getId());
        loansPayload.setNumberOfRepayments(loanTemplate.getNumberOfRepayments());
        loansPayload.setRepaymentEvery(loanTemplate.getRepaymentEvery());
        loansPayload.setRepaymentFrequencyType(loanTemplate.getInterestRateFrequencyType().getId());
        loansPayload.setInterestRatePerPeriod(loanTemplate.getInterestRatePerPeriod());
        loansPayload.setInterestType(loanTemplate.getInterestType().getId());
        loansPayload.setInterestCalculationPeriodType(
                loanTemplate.getInterestCalculationPeriodType().getId());
        loansPayload.setAmortizationType(loanTemplate.getAmortizationType().getId());
        loansPayload.setTransactionProcessingStrategyId(
                loanTemplate.getTransactionProcessingStrategyId());
        loansPayload.setExpectedDisbursementDate(disbursementDate);

        //secure loans
        if(isSecure) {
            try {
                loansPayload.setSecure("1");
                JsonArray datatables = new JsonArray();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("registeredTableName", "more_loan_details");
                JsonObject data = new JsonObject();
                data.addProperty("title_deed", title_number.getEditText().getText().toString());
                data.addProperty("locale", "en");
                data.addProperty("value", ""+formatter.parse(title_value.getEditText().getText().toString()).intValue());
                jsonObject.add("data", data);
                datatables.add(jsonObject);
                loansPayload.setDatatables(datatables);

            } catch (Exception ext) {
                Log.e("LOAN ERROR", ext.getMessage());
            }
        }
        loanApplicationPresenter.updateLoanAccount(loanWithAssociations.getId(), loansPayload);
    }

    /**
     * Retries to fetch {@link LoanTemplate} by calling {@code loadLoanTemplate()}
     */
    @OnClick(R.id.iv_status)
    void onRetry() {
        llError.setVisibility(View.GONE);
        llAddLoan.setVisibility(View.VISIBLE);
        loadLoanTemplate();
    }



    /**
     * Initializes {@code tvSubmissionDate} with current Date
     */
    public void inflateSubmissionDate() {
        tvSubmissionDate.setText(MFDatePicker.getDatePickedAsString());
    }

    /**
     * Initializes {@code tvExpectedDisbursementDate} with current Date
     */
    public void inflateDisbursementDate() {
        mfDatePicker = MFDatePicker.newInstance(this, MFDatePicker.FUTURE_DAYS);
        tvExpectedDisbursementDate.setText(MFDatePicker.getDatePickedAsString());
    }

    /**
     * Sets {@code submittedDate} and {@code disbursementDate} in a specific format
     */
    public void setSubmissionDisburseDate() {
        disbursementDate = tvExpectedDisbursementDate.getText().toString();
        submittedDate = tvSubmissionDate.getText().toString();
        submittedDate = DateHelper.getSpecificFormat(DateHelper.FORMAT_dd_MMMM_yyyy, submittedDate);
        disbursementDate = DateHelper.getSpecificFormat(
                DateHelper.FORMAT_dd_MMMM_yyyy, disbursementDate);
    }


    /**
     * Shows a {@link DialogFragment} for selecting a Date for Disbursement
     */
    @OnClick(R.id.ll_expected_disbursement_date_edit)
    public void setTvDisbursementOnDate() {
        isDisbursementDate = true;
        mfDatePicker.show(getActivity().getSupportFragmentManager(), Constants
                .DFRAG_DATE_PICKER);
    }

    /**
     * A CallBack for {@link MFDatePicker} which provides us with the date selected from the
     * {@link android.app.DatePickerDialog}
     * @param date Date selected by user in {@link }
     */
    @Override
    public void onDatePicked(String date) {
        if (isDisbursementDate) {
            tvExpectedDisbursementDate.setText(date);
            disbursementDate = date;
            isDisbursementDate = false;
        }
        setSubmissionDisburseDate();
    }


    /**
     * Initializes the layout
     */
    @Override
    public void showUserInterface() {
        loanProductAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                listLoanProducts);
        loanProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoanProducts.setAdapter(loanProductAdapter);
        spLoanProducts.setOnItemSelectedListener(this);

        loanPurposeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                listLoanPurpose);
        loanPurposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoanPurpose.setAdapter(loanPurposeAdapter);
        spLoanPurpose.setOnItemSelectedListener(this);

        inflateSubmissionDate();
        inflateDisbursementDate();
        setSubmissionDisburseDate();
    }

    /**
     * Fetches the {@link LoanTemplate} from server for {@code loanState} as CREATE
     * @param loanTemplate Template for Loan Application
     */
    @Override
    public void showLoanTemplate(LoanTemplate loanTemplate) {
        this.loanTemplate = loanTemplate;
        for (ProductOptions productOption : loanTemplate.getProductOptions()) {

            String nn=loanTemplate.getProductOptions().toString();

            System.out.println("Name loan "+nn);

           if(productOption.getId()==1) {
                listLoanProducts.add(productOption.getName());
           }else{
               verifyTitle(productOption);
           }
//            listLoanProducts.add(productOption.getName());
        }
        loanProductAdapter.notifyDataSetChanged();
    }

    /**
     * Fetches the {@link LoanTemplate} from server for {@code loanState} as UPDATE
     * @param loanTemplate Template for Loan Application
     */
    @Override
    public void showUpdateLoanTemplate(LoanTemplate loanTemplate) {
        this.loanTemplate = loanTemplate;

        for (ProductOptions productOption : loanTemplate.getProductOptions()) {
            listLoanProducts.add(productOption.getName());
        }
        loanProductAdapter.notifyDataSetChanged();

        spLoanProducts.setSelection(loanProductAdapter
                .getPosition(loanWithAssociations.getLoanProductName()));
        tvAccountNumber.setText(getString(R.string.string_and_string,
                getString(R.string.account_number) + " ", loanWithAssociations.getAccountNo()));
        tvNewLoanApplication.setText(getString(R.string.string_and_string,
                getString(R.string.update_loan_application) + " ",
                loanWithAssociations.getClientName()));
        tilPrincipalAmount.getEditText().setText(String.valueOf(loanWithAssociations.
                getPrincipal()));
        tvCurrency.setText(loanWithAssociations.getCurrency().getDisplayLabel());

        tvSubmissionDate.setText(DateHelper.getDateAsString(loanWithAssociations.
                getTimeline().getSubmittedOnDate(), "dd-MM-yyyy"));
        tvExpectedDisbursementDate.setText(DateHelper.getDateAsString(loanWithAssociations.
                getTimeline().getExpectedDisbursementDate(), "dd-MM-yyyy"));
        setSubmissionDisburseDate();


    }

    /**
     * Fetches the {@link LoanTemplate} according to product from server for {@code loanState} as
     * CREATE
     * @param loanTemplate Template for Loan Application
     */
    @Override
    public void showLoanTemplateByProduct(LoanTemplate loanTemplate) {
        this.loanTemplate = loanTemplate;
        /*tvAccountNumber.setText(getString(R.string.string_and_string,
                getString(R.string.account_number) + " ", loanTemplate.getClientAccountNo()));*/


        /*  tvNewLoanApplication.setText(getString(R.string.string_and_string,
                getString(R.string.new_loan_application) + "\n\t\t", loanTemplate.getClientName()));*/
        tvCurrency.setText(loanTemplate.getCurrency().getDisplayLabel());

        tilPrincipalAmount.getEditText().setText(String.valueOf(loanTemplate.getPrincipal()));
        ///ENS added
        String vals = String.valueOf(loanTemplate.getPrincipal());
        tvlimittops.setText(vals);

        if(spLoanProducts.getSelectedItemPosition()>0) {
            calculateLoanAmount();
        }
//        listLoanPurpose.clear();
//        for (LoanPurposeOptions loanPurposeOptions : loanTemplate.getLoanPurposeOptions()) {
//            listLoanPurpose.add(loanPurposeOptions.getName());
//        }
//        loanPurposeAdapter.notifyDataSetChanged();
    }

    /**
     * Fetches the {@link LoanTemplate} according to product from server for {@code loanState} as
     * UPDATE
     * @param loanTemplate Template for Loan Application
     */
    @Override
    public void showUpdateLoanTemplateByProduct(LoanTemplate loanTemplate) {
        this.loanTemplate = loanTemplate;
//        listLoanPurpose.clear();
//        for (LoanPurposeOptions loanPurposeOptions : loanTemplate.getLoanPurposeOptions()) {
//            listLoanPurpose.add(loanPurposeOptions.getName());
//        }
//        loanPurposeAdapter.notifyDataSetChanged();
//
//        if (isLoanUpdatePurposesInitialization &&
//                loanWithAssociations.getLoanPurposeName() != null) {
//            spLoanPurpose.setSelection(loanPurposeAdapter
//                    .getPosition(loanWithAssociations.getLoanPurposeName()));
//            isLoanUpdatePurposesInitialization = false;
//        } else
// {
            tvAccountNumber.setText(getString(R.string.string_and_string,
                    getString(R.string.account_number) + " ", loanTemplate.getClientAccountNo()));
            tvNewLoanApplication.setText(getString(R.string.string_and_string,
                    getString(R.string.new_loan_application) + " ", loanTemplate.getClientName()));
            tilPrincipalAmount.getEditText().setText(String.valueOf(loanTemplate.getPrincipal()));
            tvCurrency.setText(loanTemplate.getCurrency().getDisplayLabel());
            String vals = String.valueOf(loanTemplate.getPrincipal());
            tvlimittops.setText(vals);
            if(spLoanProducts.getSelectedItemPosition()>0) {
                calculateLoanAmount();
            }
       // }
    }

    /**
     * Shows a {@link android.support.design.widget.Snackbar} after Loan Application is created
     * successfully
     */
    @Override
    public void showLoanAccountCreatedSuccessfully() {
        Toaster.show(rootView, R.string.loan_application_submitted_successfully);
        getActivity().getSupportFragmentManager().popBackStack();
        //getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));

        ((BaseActivity) getActivity()).replaceFragment(LoanApplicationConfirmationFragment
                .newInstance(), false, R.id.container);
    }

    /**
     * Shows a {@link android.support.design.widget.Snackbar} after Loan Application is updated
     * successfully
     */
    @Override
    public void showLoanAccountUpdatedSuccessfully() {
        Toaster.show(rootView, R.string.loan_application_updated_successfully);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * It is called whenever any error occurs while executing a request
     * @param message Error message that tells the user about the problem.
     */
    @Override
    public void showError(String message) {
        if (!Network.isConnected(getActivity())) {
            ivReload.setImageResource(R.drawable.ic_error_black_24dp);
            tvErrorStatus.setText(getString(R.string.internet_not_connected));
            llAddLoan.setVisibility(View.GONE);
            llError.setVisibility(View.VISIBLE);
        } else {
            Toaster.show(rootView, message);
        }
    }

    @Override
    public void showProgress() {
        llAddLoan.setVisibility(View.GONE);
        showProgressBar();
    }

    @Override
    public void hideProgress() {
        llAddLoan.setVisibility(View.VISIBLE);
        hideProgressBar();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_loan_products:
                productId = loanTemplate.getProductOptions().get(position).getId();

//                Toast.makeText(getActivity(),""+loanTemplate.getProductOptions().get(position).isSecure(),Toast.LENGTH_LONG).show();
                    btn_loan_submit.setText(getResources().getString(R.string.applyloan));
                    btn_loan_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onSubmitLoanApplication();
                        }
                    });
                    tilPrincipalAmount.setVisibility(View.VISIBLE);
                    tvExpectedDisbursementDate.setVisibility(View.VISIBLE);
                    tvSubmissionDate.setVisibility(View.VISIBLE);

                    if (loanState == LoanState.CREATE) {
                        loanApplicationPresenter.loadLoanApplicationTemplateByProduct(productId,
                                LoanState.CREATE);
                    } else {
                        loanApplicationPresenter.loadLoanApplicationTemplateByProduct(productId,
                                LoanState.UPDATE);
                    }
                break;
//
//            case R.id.sp_loan_purpose:
//                purposeId = loanTemplate.getLoanPurposeOptions().get(position).getId();
//                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgressBar();
        loanApplicationPresenter.detachView();
    }

    private void getCreditLimit() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseURL.LIMIT_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("getCreditLimit", "getCreditLimit Response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String amount = jsonObject.getString("Limit");
                            if(!amount.equals("")){
                                setData(amount,"","");
                            }else{
                                setData("0","","");
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
                params.put("client_id", client_id);
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

    private void verifyTitle(final ProductOptions productOption) {
        tilPrincipalAmount.setError(null);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseURL.TITLE_API+phoneNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("verifyTitle", "verifyTitle Response: " + response);
                        progressDialog.dismiss();
                        try{
                            JSONObject respObj = new JSONObject(response);
                            JSONArray records = respObj.getJSONArray("records");
                            if(records.length() > 0) {
                                if (records.getJSONObject(0).getString("unit_price").length() == 0) {

                                } else {
                                    isSecure = true;
                                    if (!listLoanProducts.contains(productOption.getName())) {
                                        listLoanProducts.add(productOption.getName());
                                    }
                                }
                            }
                        }catch (Exception expt){
                            expt.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("verifyTitle", "ERROR-"+error.getMessage());
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // the GET parameters:
//                params.put("phone_no", "0712789789");
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

    private void calculateLoanAmount() {
        tilPrincipalAmount.setError(null);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseURL.TITLE_API+phoneNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("calculateLoanAmount", "calculateLoanAmount Response: " + response);
                        progressDialog.dismiss();
                        try{
//                            Toast.makeText(getActivity(),phoneNumber,Toast.LENGTH_LONG).show();
                            JSONObject respObj = new JSONObject(response);
                            JSONArray jsonArray = respObj.getJSONArray("records");
                            if(jsonArray.length() == 0){
                                tvlimittops.setText(0);
                                tilPrincipalAmount.getEditText().setText(0);
                            }else{
                                titles = new ArrayList<>();
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if(jsonObject.getString("unit_price").length() > 0){
                                        double amount = Double.valueOf(jsonObject.getString("unit_price").replace(",",""));
                                        //give 40% of title value
                                        double newAmount = amount * 0.4;
                                        titles.add(new TitleDeed(jsonObject.getString("title_number"),jsonObject.getString("productname"),
                                                ""+amount, newAmount));
                                    }else{
                                        tvlimittops.setText(0);
                                        tilPrincipalAmount.getEditText().setText(0);
                                    }
                                }

                                final FragmentManager fm= getActivity().getFragmentManager();
                                final  TitleDeedFragment shopsFragment = TitleDeedFragment.newInstance(titles);
                                shopsFragment.show(fm,"Title Deed");
                            }
                        }catch (Exception expt){
                            expt.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("verifyTitle", "ERROR-"+error.getMessage());
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // the GET parameters:
//                params.put("phone_no", phoneNumber);
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

    public static void setData(String amount, String titleNumber, String unitPrice){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        double amountVal = 0;
        try {
            amountVal = Double.parseDouble(""+formatter.parse(amount).intValue());
        }catch (Exception exp){
            exp.printStackTrace();
        }
        tilPrincipalAmount.getEditText().setText(""+amountVal);
        tvlimittops.setText(""+amountVal);
        title_number.getEditText().setText(titleNumber);
        title_value.getEditText().setText(unitPrice);
    }
}
