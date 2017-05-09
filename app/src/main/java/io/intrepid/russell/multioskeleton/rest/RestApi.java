package io.intrepid.russell.multioskeleton.rest;

import io.intrepid.russell.multioskeleton.models.IpModel;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestApi {
    @GET("/?format=json")
    Observable<IpModel> getMyIp();
}
