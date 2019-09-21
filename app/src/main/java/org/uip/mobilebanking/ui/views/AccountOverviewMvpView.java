package org.uip.mobilebanking.ui.views;

import org.uip.mobilebanking.ui.views.base.MVPView;


/**
 * @author Rajan Maurya
 *         On 16/10/17.
 */
public interface AccountOverviewMvpView extends MVPView {

    void showTotalLoanSavings(double totalLoan, double totalSavings);

    void showError(String message);
}
