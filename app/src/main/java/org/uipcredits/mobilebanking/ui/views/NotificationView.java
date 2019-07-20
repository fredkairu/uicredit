package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.models.notification.MifosNotification;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


import java.util.List;

/**
 * Created by dilpreet on 14/9/17.
 */

public interface NotificationView extends MVPView {

    void showNotifications(List<MifosNotification> notifications);

    void showError(String msg);
}
