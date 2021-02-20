package com.example.jobtask.network;

import com.example.jobtask.model.DrinkResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/json/v1/1/search.php")
    Call<DrinkResponce> getByName(@Query("s") String drinkName);


}
