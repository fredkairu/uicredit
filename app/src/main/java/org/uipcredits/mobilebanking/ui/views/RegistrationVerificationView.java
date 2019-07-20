package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.ui.views.base.MVPView;


/**
 * Created by dilpreet on 31/7/17.
 */

public interface RegistrationVerificationView extends MVPView {

    void showVerifiedSuccessfully();

    void showError(String msg);
}
