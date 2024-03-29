package org.uip.mobilebanking.api.services;

import org.uip.mobilebanking.api.ApiEndPoints;
import org.uip.mobilebanking.models.beneficiary.Beneficiary;
import org.uip.mobilebanking.models.beneficiary.BeneficiaryPayload;
import org.uip.mobilebanking.models.beneficiary.BeneficiaryUpdatePayload;
import org.uip.mobilebanking.models.templates.beneficiary.BeneficiaryTemplate;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dilpreet on 14/6/17.
 */

public interface BeneficiaryService {

    @GET(ApiEndPoints.BENEFICIARIES + "/tpt")
    Observable<List<Beneficiary>> getBeneficiaryList();

    @GET(ApiEndPoints.BENEFICIARIES + "/tpt/template")
    Observable<BeneficiaryTemplate> getBeneficiaryTemplate();

    @POST(ApiEndPoints.BENEFICIARIES + "/tpt")
    Observable<ResponseBody> createBeneficiary(@Body BeneficiaryPayload beneficiaryPayload);

    @PUT(ApiEndPoints.BENEFICIARIES + "/tpt/{beneficiaryId}")
    Observable<ResponseBody> updateBeneficiary(@Path("beneficiaryId") long beneficiaryId,
                                               @Body BeneficiaryUpdatePayload payload);

    @DELETE(ApiEndPoints.BENEFICIARIES + "/tpt/{beneficiaryId}")
    Observable<ResponseBody> deleteBeneficiary(@Path("beneficiaryId") long beneficiaryId);
}
