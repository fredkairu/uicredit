package org.uipcredits.mobilebanking.presenters.base;

import org.uipcredits.mobilebanking.ui.views.base.MVPView;
import org.uipcredits.mobilebanking.ui.views.base.MVPView;


/**
 * @author ishan
 * @since 19/05/16
 */
public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();

}
