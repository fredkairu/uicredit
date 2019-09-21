package org.uip.mobilebanking.ui.views;

import org.uip.mobilebanking.models.FAQ;
import org.uip.mobilebanking.ui.views.base.MVPView;


import java.util.ArrayList;

/**
 * Created by dilpreet on 12/8/17.
 */

public interface HelpView extends MVPView {

    void showFaq(ArrayList<FAQ> faqArrayList);

}
