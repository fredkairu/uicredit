package org.uip.mobilebanking.api;

import org.uip.mobilebanking.api.local.PreferencesHelper;
import org.uip.mobilebanking.api.services.AuthenticationService;
import org.uip.mobilebanking.api.services.BeneficiaryService;
import org.uip.mobilebanking.api.services.ClientChargeService;
import org.uip.mobilebanking.api.services.ClientService;
import org.uip.mobilebanking.api.services.GuarantorService;
import org.uip.mobilebanking.api.services.LoanAccountsListService;
import org.uip.mobilebanking.api.services.NotificationService;
import org.uip.mobilebanking.api.services.RecentTransactionsService;
import org.uip.mobilebanking.api.services.RegistrationService;
import org.uip.mobilebanking.api.services.SavingAccountsListService;
import org.uip.mobilebanking.api.services.ThirdPartyTransferService;


import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vishwajeet
 * @since 13/6/16
 */
public class BaseApiManager {

    private static Retrofit retrofit;
    private static AuthenticationService authenticationApi;
    private static ClientService clientsApi;
    private static SavingAccountsListService savingAccountsListApi;
    private static LoanAccountsListService loanAccountsListApi;
    private static RecentTransactionsService recentTransactionsApi;
    private static ClientChargeService clientChargeApi;
    private static BeneficiaryService beneficiaryApi;
    private static ThirdPartyTransferService thirdPartyTransferApi;
    private static RegistrationService registrationApi;
    private static NotificationService notificationApi;
    private static GuarantorService guarantorService;

    @Inject
    public BaseApiManager(PreferencesHelper preferencesHelper) {
        createService(preferencesHelper.getBaseUrl(), preferencesHelper.getTenant(),
                preferencesHelper.getToken());
    }

    private static void init() {
        authenticationApi = createApi(AuthenticationService.class);
        clientsApi = createApi(ClientService.class);
        savingAccountsListApi = createApi(SavingAccountsListService.class);
        loanAccountsListApi = createApi(LoanAccountsListService.class);
        recentTransactionsApi = createApi(RecentTransactionsService.class);
        clientChargeApi = createApi(ClientChargeService.class);
        beneficiaryApi = createApi(BeneficiaryService.class);
        thirdPartyTransferApi = createApi(ThirdPartyTransferService.class);
        registrationApi = createApi(RegistrationService.class);
        notificationApi = createApi(NotificationService.class);
        guarantorService = createApi(GuarantorService.class);
    }

    private static <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public static void createService(String endpoint, String tenant, String authToken) {
        retrofit = new Retrofit.Builder()
                .baseUrl(new BaseURL().getUrl(endpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new SelfServiceOkHttpClient(tenant, authToken).getMifosOkHttpClient())
                .build();
        init();
    }

    public AuthenticationService getAuthenticationApi() {
        return authenticationApi;
    }

    public ClientService getClientsApi() {
        return clientsApi;
    }

    public SavingAccountsListService getSavingAccountsListApi() {
        return savingAccountsListApi;
    }

    public LoanAccountsListService getLoanAccountsListApi() {
        return loanAccountsListApi;
    }

    public RecentTransactionsService getRecentTransactionsApi() {
        return recentTransactionsApi;
    }

    public ClientChargeService getClientChargeApi() {
        return clientChargeApi;
    }

    public BeneficiaryService getBeneficiaryApi() {
        return beneficiaryApi;
    }

    public ThirdPartyTransferService getThirdPartyTransferApi() {
        return thirdPartyTransferApi;
    }

    public RegistrationService getRegistrationApi() {
        return registrationApi;
    }

    public NotificationService getNotificationApi() {
        return notificationApi;
    }

    public GuarantorService getGuarantorApi() {
        return guarantorService;
    }
}
