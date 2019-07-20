package org.uipcredits.mobilebanking.api.services;


import org.uipcredits.mobilebanking.api.PaymetResponse;

import org.uipcredits.mobilebanking.api.PaymetResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConfirmPayment {

    @FormUrlEncoded
    @POST("checkpayment.php")
    Call<PaymetResponse> sendCheckout(@Field("checkout") String checkout);
}
