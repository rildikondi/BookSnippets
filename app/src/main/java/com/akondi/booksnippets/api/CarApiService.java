package com.akondi.booksnippets.api;

import com.akondi.booksnippets.mvp.model.CarsResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CarApiService {

    @GET("/car2go/vehicles.json")
    Observable<CarsResponse> getCars();

    @GET("/car2go/vehicles.json")
    Call<CarsResponse> getTheCars();
}
