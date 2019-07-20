package org.uipcredits.mobilebanking.presenters;

import android.content.Context;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.injection.ApplicationContext;
import org.uipcredits.mobilebanking.models.FAQ;
import org.uipcredits.mobilebanking.presenters.base.BasePresenter;
import org.uipcredits.mobilebanking.ui.views.HelpView;



import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by dilpreet on 12/8/17.
 */

public class HelpPresenter extends BasePresenter<HelpView> {

    @Inject
    protected HelpPresenter(@ApplicationContext Context context) {
        super(context);
    }

    @Override
    public void attachView(HelpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadFaq() {

        String[] qs = context.getResources().getStringArray(R.array.faq_qs);
        String[] ans = context.getResources().getStringArray(R.array.faq_ans);
        ArrayList<FAQ> faqArrayList = new ArrayList<>();

        for (int i = 0; i < qs.length; i++) {
            faqArrayList.add(new FAQ(qs[i], ans[i]));
        }

        getMvpView().showFaq(faqArrayList);
    }

    public ArrayList<FAQ> filterList(ArrayList<FAQ> faqArrayList, String query) {
        ArrayList<FAQ> filteredList = new ArrayList<>();
        for (FAQ faq : faqArrayList) {
            if (faq.getQuestion().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(faq);
            }
        }
        return filteredList;
    }
}
