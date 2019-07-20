package org.uipcredits.mobilebanking.ui.views;

import android.graphics.Bitmap;

import org.uipcredits.mobilebanking.models.client.Client;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


/**
 * Created by naman on 07/04/17.
 */

public interface UserDetailsView extends MVPView {

    void showUserDetails(Client client);

    void showUserImage(Bitmap bitmap);

    void showError(String  message);
}
