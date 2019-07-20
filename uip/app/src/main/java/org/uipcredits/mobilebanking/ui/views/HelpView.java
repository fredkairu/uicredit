package org.uipcredits.mobilebanking.ui.views;

import org.uipcredits.mobilebanking.models.FAQ;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


import java.util.ArrayList;

/**
 * Created by dilpreet on 12/8/17.
 */

public interface HelpView extends MVPView {

    void showFaq(ArrayList<FAQ> faqArrayList);

}
