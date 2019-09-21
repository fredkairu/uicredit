package org.uip.mobilebanking.ui.views;

import org.uip.mobilebanking.models.accounts.loan.LoanAccount;
import org.uip.mobilebanking.models.accounts.savings.SavingAccount;
import org.uip.mobilebanking.models.accounts.share.ShareAccount;
import org.uip.mobilebanking.ui.views.base.MVPView;


import java.util.List;

/**
 * Created by Rajan Maurya on 23/10/16.
 */

public interface AccountsView extends MVPView {

    void showLoanAccounts(List<LoanAccount> loanAccounts);

    void showSavingsAccounts(List<SavingAccount> savingAccounts);

    void showShareAccounts(List<ShareAccount> shareAccounts);

    void showError(String errorMessage);
}
