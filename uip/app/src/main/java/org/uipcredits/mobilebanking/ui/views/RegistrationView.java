package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.ui.views.base.MVPView;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


/**
 * Created by dilpreet on 31/7/17.
 */

public interface RegistrationView extends MVPView {

    void showRegisteredSuccessfully();

    void showError(String msg);
}
