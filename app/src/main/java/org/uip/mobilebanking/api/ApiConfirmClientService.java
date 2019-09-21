package org.uip.mobilebanking.api;

import org.uip.mobilebanking.utils.ApiResponseClientConfirmation;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConfirmClientService {
    @FormUrlEncoded
    @POST("checkClient.php")
    Call<ApiResponseClientConfirmation> checkClient(
            @Field("phone") String phone,
            @Field("idnumber") String idnumber);
}
