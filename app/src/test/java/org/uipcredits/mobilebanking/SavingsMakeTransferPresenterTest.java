package org.uipcredits.mobilebanking;

import android.content.Context;

import com.uipcredits.mobilebanking.R;

import org.uipcredits.mobilebanking.api.DataManager;
import com.uipcredits.mobilebanking.models.templates.account.AccountOptionsTemplate;
import org.uipcredits.mobilebanking.presenters.SavingsMakeTransferPresenter;
import org.uipcredits.mobilebanking.ui.views.SavingsMakeTransferMvpView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.uipcredits.mobilebanking.api.DataManager;
import org.uipcredits.mobilebanking.ui.views.SavingsMakeTransferMvpView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;


import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dilpreet on 24/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SavingsMakeTransferPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    Context context;

    @Mock
    DataManager dataManager;

    @Mock
    SavingsMakeTransferMvpView view;

    private AccountOptionsTemplate accountOptionsTemplate;
    private SavingsMakeTransferPresenter presenter;

    @Before
    public void setUp() {
        presenter = new SavingsMakeTransferPresenter(dataManager, context);
        presenter.attachView(view);

        accountOptionsTemplate = FakeRemoteDataSource.getAccountOptionsTemplate();
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void testLoanAccountTransferTemplate() {
        when(dataManager.getAccountTransferTemplate()).thenReturn(Observable.
                just(accountOptionsTemplate));

        presenter.loanAccountTransferTemplate();

        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showSavingsAccountTemplate(accountOptionsTemplate);
        verify(view, never()).showError(context.getString(
                R.string.error_fetching_account_transfer_template));
    }

    @Test
    public void testLoanAccountTransferTemplateFails() {
        when(dataManager.getAccountTransferTemplate()).thenReturn(Observable.
                <AccountOptionsTemplate>error(new RuntimeException()));

        presenter.loanAccountTransferTemplate();

        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showError(context.getString(
                R.string.error_fetching_account_transfer_template));
        verify(view, never()).showSavingsAccountTemplate(accountOptionsTemplate);
    }
}
