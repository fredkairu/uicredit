package org.uip.mobilebanking.ui.views;

/*
 * Created by saksham on 23/July/2018
 */
import org.uip.mobilebanking.models.guarantor.GuarantorApplicationPayload;
import org.uip.mobilebanking.models.guarantor.GuarantorTemplatePayload;
import org.uip.mobilebanking.ui.views.base.MVPView;


public interface AddGuarantorView extends MVPView {

    void updatedSuccessfully(String message);
    void submittedSuccessfully(String message, GuarantorApplicationPayload payload);
    void showGuarantorUpdation(GuarantorTemplatePayload template);
    void showGuarantorApplication(GuarantorTemplatePayload template);
    void showError(String message);

}
