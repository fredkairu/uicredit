package org.uip.mobilebanking.ui.views;

import com.google.zxing.Result;
import org.uip.mobilebanking.ui.views.base.MVPView;


/**
 * Created by manishkumar on 19/05/18.
 */

public interface QrCodeImportView extends MVPView {


    void showErrorReadingQr(String message);

    void handleDecodedResult (Result result);

}
