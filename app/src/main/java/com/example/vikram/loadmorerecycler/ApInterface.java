package com.example.vikram.loadmorerecycler;

import com.example.vikram.loadmorerecycler.Model.Example;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Vikram on 04-May-17.
 */

public interface ApInterface {
        @GET("fetch_list.php")
        Call<Example> getTasks();

        @FormUrlEncoded
        @POST("fetch_list.php")
        Call<Example> calbk(
                @Field("id") long id,
                @Field("timestamp") long timestamp
        );
}
