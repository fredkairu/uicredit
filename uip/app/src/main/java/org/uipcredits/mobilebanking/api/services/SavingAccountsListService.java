package org.uipcredits.mobilebanking.api.services;

import org.uipcredits.mobilebanking.api.ApiEndPoints;
import org.uipcredits.mobilebanking.models.accounts.savings.SavingsWithAssociations;
import org.uipcredits.mobilebanking.models.payload.TransferPayload;
import org.uipcredits.mobilebanking.models.templates.account.AccountOptionsTemplate;



import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Vishwajeet
 * @since 21/6/16.
 */
public interface SavingAccountsListService {

    @GET(ApiEndPoints.SAVINGS_ACCOUNTS + "/{accountId}")
    Observable<SavingsWithAssociations> getSavingsWithAssociations(
            @Path("accountId") long accountId,
            @Query("associations") String associationType);

    @GET(ApiEndPoints.ACCOUNT_TRANSFER + "/template")
    Observable<AccountOptionsTemplate> getAccountTransferTemplate();

    @POST(ApiEndPoints.ACCOUNT_TRANSFER)
    Observable<ResponseBody> makeTransfer(@Body TransferPayload transferPayload);
}