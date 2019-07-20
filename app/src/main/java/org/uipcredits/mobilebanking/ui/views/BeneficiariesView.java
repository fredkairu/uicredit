package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.models.beneficiary.Beneficiary;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;

import java.util.List;


/**
 * Created by dilpreet on 14/6/17.
 */

public interface BeneficiariesView extends MVPView {

    void showUserInterface();

    void showError(String msg);

    void showBeneficiaryList(List<Beneficiary> beneficiaryList);
}
