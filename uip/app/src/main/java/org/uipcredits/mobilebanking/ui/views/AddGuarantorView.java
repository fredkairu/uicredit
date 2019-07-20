package org.uipcredits.mobilebanking.ui.views;

/*
 * Created by saksham on 23/July/2018
 */
import org.uipcredits.mobilebanking.models.guarantor.GuarantorApplicationPayload;
import org.uipcredits.mobilebanking.models.guarantor.GuarantorTemplatePayload;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


public interface AddGuarantorView extends MVPView {

    void updatedSuccessfully(String message);
    void submittedSuccessfully(String message, GuarantorApplicationPayload payload);
    void showGuarantorUpdation(GuarantorTemplatePayload template);
    void showGuarantorApplication(GuarantorTemplatePayload template);
    void showError(String message);

}
