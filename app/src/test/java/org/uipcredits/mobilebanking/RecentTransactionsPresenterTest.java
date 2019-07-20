package org.uipcredits.mobilebanking;

import android.content.Context;

import org.uipcredits.mobilebanking.FakeRemoteDataSource;

import com.uipcredits.mobilebanking.R;

import org.uipcredits.mobilebanking.api.DataManager;
import com.uipcredits.mobilebanking.models.Page;
import com.uipcredits.mobilebanking.models.Transaction;
import org.uipcredits.mobilebanking.presenters.RecentTransactionsPresenter;
import org.uipcredits.mobilebanking.ui.views.RecentTransactionsView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.uipcredits.mobilebanking.api.DataManager;
import org.uipcredits.mobilebanking.models.Page;
import org.uipcredits.mobilebanking.models.Transaction;
import org.uipcredits.mobilebanking.ui.views.RecentTransactionsView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;


import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dilpreet on 24/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class RecentTransactionsPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    Context context;

    @Mock
    DataManager dataManager;

    @Mock
    RecentTransactionsView view;

    private RecentTransactionsPresenter presenter;
    private Page<Transaction> recentTransaction;
    private int offset, limit = 50;

    @Before
    public void setUp() {
        presenter = new RecentTransactionsPresenter(dataManager, context);
        presenter.attachView(view);

        recentTransaction = FakeRemoteDataSource.getTransactions();
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void testLoadRecentTransactionsZeroOffset() {
        offset = 0;
        when(dataManager.getRecentTransactions(offset, limit)).thenReturn(Observable.just(
                recentTransaction));

        presenter.loadRecentTransactions(false, offset);
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showRecentTransactions(recentTransaction.getPageItems());
        verify(view, never()).showErrorFetchingRecentTransactions(context.getString(R.string.
                error_recent_transactions_loading));
    }

    @Test
    public void testLoadRecentTransactionsWithOffset() {
        offset = 10;
        when(dataManager.getRecentTransactions(offset, limit)).thenReturn(Observable.just(
                recentTransaction));

        presenter.loadRecentTransactions(true, offset);
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showLoadMoreRecentTransactions(recentTransaction.getPageItems());
        verify(view, never()).showErrorFetchingRecentTransactions(context.getString(R.string.
                error_recent_transactions_loading));
    }

    @Test
    public void testLoadRecentTransactionsWithZeroTransactions() {
        offset = 0;
        when(dataManager.getRecentTransactions(offset, limit)).thenReturn(Observable.just(
                new Page<Transaction>()));

        presenter.loadRecentTransactions(false, offset);
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showEmptyTransaction();
        verify(view, never()).showErrorFetchingRecentTransactions(context.getString(R.string.
                error_recent_transactions_loading));
    }

    @Test
    public void testLoadRecentTransactionsFails() {
        offset = 0;
        when(dataManager.getRecentTransactions(offset, limit)).thenReturn(Observable.
                <Page<Transaction>>error(new RuntimeException()));

        presenter.loadRecentTransactions(false, offset);
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showErrorFetchingRecentTransactions(context.getString(R.string.
                error_recent_transactions_loading));
        verify(view, never()).showEmptyTransaction();
    }

}
