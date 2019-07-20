package org.uipcredits.mobilebanking.api.services;



import org.uipcredits.mobilebanking.api.ApiEndPoints;
import org.uipcredits.mobilebanking.models.Page;
import org.uipcredits.mobilebanking.models.Transaction;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Vishwajeet
 * @since 10/08/2016
 */
public interface RecentTransactionsService {
    @GET(ApiEndPoints.CLIENTS + "/{clientId}/transactions")
    Observable<Page<Transaction>> getRecentTransactionsList(
            @Path("clientId") long clientId,
            @Query("offset") int offset,
            @Query("limit") int limit);
}