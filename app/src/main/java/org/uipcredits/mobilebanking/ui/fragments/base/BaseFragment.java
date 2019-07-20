package org.uipcredits.mobilebanking.ui.fragments.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.views.BaseActivityCallback;
import org.uipcredits.mobilebanking.utils.LanguageHelper;
import org.uipcredits.mobilebanking.utils.ProgressBarHandler;
import org.uipcredits.mobilebanking.ui.views.BaseActivityCallback;


public class BaseFragment extends Fragment {

    private BaseActivityCallback callback;
    private ProgressBarHandler progressBarHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarHandler = new ProgressBarHandler(getActivity());
    }

    /**
     * Used to show Progress bar. {@code callback} is implemented in
     * {@link BaseActivity}
     * @param message Message you want to display
     */
    protected void showMifosProgressDialog(String message) {
        if (callback != null)
            callback.showProgressDialog(message);
    }

    /**
     * Used for hiding the progressbar.
     */
    protected void hideMifosProgressDialog() {
        if (callback != null)
            callback.hideProgressDialog();
    }

    /**
     * Used for setting title of Toolbar
     * @param title String you want to display as title
     */
    protected void setToolbarTitle(String title) {
        callback.setToolbarTitle(title);
    }

    /**
     * Displays {@link ProgressBarHandler}
     */
    protected void showProgressBar() {
        progressBarHandler.show();
    }

    /**
     * Hides {@link ProgressBarHandler}
     */
    protected void hideProgressBar() {
        progressBarHandler.hide();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(LanguageHelper.onAttach(context));
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            callback = (BaseActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseActivityCallback methods");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

}
