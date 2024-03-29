package org.uip.mobilebanking.ui.views;

/*
 * Created by saksham on 24/July/2018
 */

import org.uip.mobilebanking.models.guarantor.GuarantorPayload;
import org.uip.mobilebanking.ui.views.base.MVPView;


import java.util.List;

public interface GuarantorListView extends MVPView {

    void showGuarantorListSuccessfully(List<GuarantorPayload> list);
    void showError(String message);
}
