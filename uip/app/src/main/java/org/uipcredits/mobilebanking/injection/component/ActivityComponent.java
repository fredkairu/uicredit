package org.uip.mobilebanking.injection.component;

import org.uip.mobilebanking.injection.PerActivity;
import org.uip.mobilebanking.injection.module.ActivityModule;
import org.uip.mobilebanking.ui.activities.PassCodeActivity;
import org.uip.mobilebanking.ui.activities.SplashActivity;
import org.uip.mobilebanking.ui.fragments.ClientAccountsFragment;
import org.uip.mobilebanking.ui.fragments.ClientChargeFragment;
import org.uip.mobilebanking.ui.fragments.GuarantorDetailFragment;
import org.uip.mobilebanking.ui.fragments.HelpFragment;
import org.uip.mobilebanking.ui.fragments.HomeFragment;
import org.uip.mobilebanking.ui.fragments.HomeOldFragment;
import org.uip.mobilebanking.ui.fragments.LoanAccountTransactionFragment;
import org.uip.mobilebanking.ui.fragments.QrCodeImportFragment;
import org.uip.mobilebanking.ui.fragments.RecentTransactionsFragment;
import org.uip.mobilebanking.ui.fragments.RegistrationFragment;
import org.uip.mobilebanking.ui.fragments.RegistrationFragment2;
import org.uip.mobilebanking.ui.fragments.ThirdPartyTransferFragment;
import org.uip.mobilebanking.ui.fragments.UserProfileFragment;

import org.uip.mobilebanking.ui.activities.HomeActivity;
import org.uip.mobilebanking.ui.activities.LoginActivity;
import org.uip.mobilebanking.ui.fragments.AccountOverviewFragment;
import org.uip.mobilebanking.ui.fragments.AccountsFragment;
import org.uip.mobilebanking.ui.fragments.AddGuarantorFragment;
import org.uip.mobilebanking.ui.fragments.BeneficiaryAddOptionsFragment;
import org.uip.mobilebanking.ui.fragments.BeneficiaryApplicationFragment;
import org.uip.mobilebanking.ui.fragments.BeneficiaryDetailFragment;
import org.uip.mobilebanking.ui.fragments.BeneficiaryListFragment;
import org.uip.mobilebanking.ui.fragments.GuarantorListFragment;
import org.uip.mobilebanking.ui.fragments.LoanAccountSummaryFragment;
import org.uip.mobilebanking.ui.fragments.LoanAccountWithdrawFragment;
import org.uip.mobilebanking.ui.fragments.LoanAccountsDetailFragment;
import org.uip.mobilebanking.ui.fragments.LoanApplicationFragment;
import org.uip.mobilebanking.ui.fragments.LoanRepaymentScheduleFragment;
import org.uip.mobilebanking.ui.fragments.NotificationFragment;
import org.uip.mobilebanking.ui.fragments.RegistrationVerificationFragment;
import org.uip.mobilebanking.ui.fragments.SavingAccountsDetailFragment;
import org.uip.mobilebanking.ui.fragments.SavingAccountsTransactionFragment;
import org.uip.mobilebanking.ui.fragments.SavingsMakeTransferFragment;
import org.uip.mobilebanking.ui.fragments.TransferProcessFragment;

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
