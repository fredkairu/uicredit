package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.models.accounts.loan.LoanWithAssociations;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


/**
 * Created by Rajan Maurya on 03/03/17.
 */

public interface LoanRepaymentScheduleMvpView extends MVPView {

    void showUserInterface();

    void showLoanRepaymentSchedule(LoanWithAssociations loanWithAssociations);

    void showEmptyRepaymentsSchedule(LoanWithAssociations loanWithAssociations);

    void showError(String message);
}