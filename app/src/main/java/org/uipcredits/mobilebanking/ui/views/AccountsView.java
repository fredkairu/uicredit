package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.models.accounts.loan.LoanAccount;
import org.uipcredits.mobilebanking.models.accounts.savings.SavingAccount;
import org.uipcredits.mobilebanking.models.accounts.share.ShareAccount;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


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
