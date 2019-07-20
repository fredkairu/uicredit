package org.uipcredits.mobilebanking.injection.component;

import org.uipcredits.mobilebanking.injection.PerActivity;
import org.uipcredits.mobilebanking.injection.module.ActivityModule;
import org.uipcredits.mobilebanking.ui.activities.HomeActivity;
import org.uipcredits.mobilebanking.ui.activities.LoginActivity;
import org.uipcredits.mobilebanking.ui.activities.PassCodeActivity;
import org.uipcredits.mobilebanking.ui.activities.SplashActivity;
import org.uipcredits.mobilebanking.ui.fragments.AccountOverviewFragment;
import org.uipcredits.mobilebanking.ui.fragments.AccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.AddGuarantorFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryAddOptionsFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryApplicationFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryListFragment;
import org.uipcredits.mobilebanking.ui.fragments.ClientAccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.ClientChargeFragment;
import org.uipcredits.mobilebanking.ui.fragments.GuarantorDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.GuarantorListFragment;
import org.uipcredits.mobilebanking.ui.fragments.HelpFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeOldFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountSummaryFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountTransactionFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountWithdrawFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountsDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanApplicationFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanRepaymentScheduleFragment;
import org.uipcredits.mobilebanking.ui.fragments.NotificationFragment;
import org.uipcredits.mobilebanking.ui.fragments.QrCodeImportFragment;
import org.uipcredits.mobilebanking.ui.fragments.RecentTransactionsFragment;
import org.uipcredits.mobilebanking.ui.fragments.RegistrationFragment;
import org.uipcredits.mobilebanking.ui.fragments.RegistrationFragment2;
import org.uipcredits.mobilebanking.ui.fragments.RegistrationVerificationFragment;
import org.uipcredits.mobilebanking.ui.fragments.SavingAccountsDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.SavingAccountsTransactionFragment;
import org.uipcredits.mobilebanking.ui.fragments.SavingsMakeTransferFragment;
import org.uipcredits.mobilebanking.ui.fragments.ThirdPartyTransferFragment;
import org.uipcredits.mobilebanking.ui.fragments.TransferProcessFragment;
import org.uipcredits.mobilebanking.ui.fragments.UserProfileFragment;

import org.uipcredits.mobilebanking.ui.activities.HomeActivity;
import org.uipcredits.mobilebanking.ui.activities.LoginActivity;
import org.uipcredits.mobilebanking.ui.activities.PassCodeActivity;
import org.uipcredits.mobilebanking.ui.activities.SplashActivity;
import org.uipcredits.mobilebanking.ui.fragments.AccountOverviewFragment;
import org.uipcredits.mobilebanking.ui.fragments.AccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.AddGuarantorFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryAddOptionsFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryApplicationFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.BeneficiaryListFragment;
import org.uipcredits.mobilebanking.ui.fragments.ClientAccountsFragment;
import org.uipcredits.mobilebanking.ui.fragments.ClientChargeFragment;
import org.uipcredits.mobilebanking.ui.fragments.GuarantorDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.GuarantorListFragment;
import org.uipcredits.mobilebanking.ui.fragments.HelpFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeFragment;
import org.uipcredits.mobilebanking.ui.fragments.HomeOldFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountSummaryFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountTransactionFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountWithdrawFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanAccountsDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanApplicationFragment;
import org.uipcredits.mobilebanking.ui.fragments.LoanRepaymentScheduleFragment;
import org.uipcredits.mobilebanking.ui.fragments.NotificationFragment;
import org.uipcredits.mobilebanking.ui.fragments.QrCodeImportFragment;
import org.uipcredits.mobilebanking.ui.fragments.RecentTransactionsFragment;
import org.uipcredits.mobilebanking.ui.fragments.RegistrationFragment;
import org.uipcredits.mobilebanking.ui.fragments.RegistrationVerificationFragment;
import org.uipcredits.mobilebanking.ui.fragments.SavingAccountsDetailFragment;
import org.uipcredits.mobilebanking.ui.fragments.SavingAccountsTransactionFragment;
import org.uipcredits.mobilebanking.ui.fragments.SavingsMakeTransferFragment;
import org.uipcredits.mobilebanking.ui.fragments.ThirdPartyTransferFragment;
import org.uipcredits.mobilebanking.ui.fragments.TransferProcessFragment;
import org.uipcredits.mobilebanking.ui.fragments.UserProfileFragment;

import dagger.Component;

/**
 * @author ishan
 * @since 08/07/16
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(PassCodeActivity passCodeActivity);

    void inject(HomeFragment homeFragment);

    void inject(ClientAccountsFragment clientAccountsFragment);

    void inject(RecentTransactionsFragment recentTransactionsFragment);

    void inject(ClientChargeFragment clientChargeFragment);

    void inject(SavingAccountsDetailFragment savingAccountsDetailActivity);

    void inject(LoanAccountsDetailFragment loanAccountsDetailActivity);

    void inject(AccountsFragment accountsFragment);

    void inject(LoanAccountSummaryFragment loanAccountSummaryFragment);

    void inject(LoanAccountTransactionFragment loanAccountTransactionFragment);

    void inject(LoanRepaymentScheduleFragment loanRepaymentScheduleFragment);

    void inject(LoanApplicationFragment loanApplicationFragment);

    void inject(LoanAccountWithdrawFragment loanAccountWithdrawFragment);

    void inject(SavingAccountsTransactionFragment savingAccountsTransactionFragment);

    void inject(SavingsMakeTransferFragment savingsMakeTransferFragment);

    void inject(BeneficiaryAddOptionsFragment beneficiaryAddOptionsFragment);

    void inject(BeneficiaryListFragment beneficiaryListFragment);

    void inject(BeneficiaryApplicationFragment beneficiaryApplicationFragment);

    void inject(BeneficiaryDetailFragment beneficiaryDetailFragment);

    void inject(ThirdPartyTransferFragment thirdPartyTransferFragment);

    void inject(TransferProcessFragment transferProcessFragment);

    void inject(UserProfileFragment userProfileFragment);

    void inject(HelpFragment helpFragment);

    void inject(RegistrationFragment registrationFragment);

    void inject(RegistrationFragment2 registrationFragment2);

    void inject(RegistrationVerificationFragment registrationVerificationFragment);

    void inject(AccountOverviewFragment accountOverviewFragment);

    void inject(HomeOldFragment homeOldFragment);

    void inject(NotificationFragment notificationFragment);
    
    void inject(QrCodeImportFragment qrCodeImportFragment);

    void inject(SplashActivity splashActivity);

    void inject(AddGuarantorFragment addGuarantorFragment);

    void inject(GuarantorListFragment guarantorListFragment);

    void inject(GuarantorDetailFragment guarantorDetailFragment);
}
