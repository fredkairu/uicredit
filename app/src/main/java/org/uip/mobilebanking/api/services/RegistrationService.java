package org.uip.mobilebanking.api.services;

import org.uip.mobilebanking.api.ApiEndPoints;
import org.uip.mobilebanking.models.register.RegisterPayload;
import org.uip.mobilebanking.models.register.UserVerify;



import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dilpreet on 31/7/17.
 */

public interface RegistrationService {

    @POST(ApiEndPoints.REGISTRATION)
    Observable<ResponseBody> registerUser(@Body RegisterPayload registerPayload);

    @POST(ApiEndPoints.REGISTRATION + "/user")
    Observable<ResponseBody> verifyUser(@Body UserVerify userVerify);
}
