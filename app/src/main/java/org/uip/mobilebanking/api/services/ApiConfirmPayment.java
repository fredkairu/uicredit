package org.uip.mobilebanking.api.services;


import org.uip.mobilebanking.api.PaymetResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConfirmPayment {

    @FormUrlEncoded
    @POST("checkpayment.php")
    Call<PaymetResponse> sendCheckout(@Field("checkout") String checkout);
}
