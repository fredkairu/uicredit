package org.uipcredits.mobilebanking.api.services;

import org.uipcredits.mobilebanking.api.ApiEndPoints;
import org.uipcredits.mobilebanking.models.User;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Vishwajeet
 * @since 09/06/16
 */

public interface AuthenticationService {

    @POST(ApiEndPoints.AUTHENTICATION)
    Observable<User> authenticate(@Query("username") String username,
                                  @Query("password") String password);
}
