package org.uip.mobilebanking.ui.views;

import org.uip.mobilebanking.models.templates.loans.LoanTemplate;
import org.uip.mobilebanking.ui.views.base.MVPView;


/**
 * Created by Rajan Maurya on 06/03/17.
 */

public interface LoanApplicationMvpView extends MVPView {

    void showUserInterface();

    void showLoanTemplate(LoanTemplate loanTemplate);

    void showUpdateLoanTemplate(LoanTemplate loanTemplate);

    void showLoanTemplateByProduct(LoanTemplate loanTemplate);

    void showUpdateLoanTemplateByProduct(LoanTemplate loanTemplate);

    void showLoanAccountCreatedSuccessfully();

    void showLoanAccountUpdatedSuccessfully();

    void showError(String message);
}
