package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Token;
import com.example.gymside.api.model.User;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("user/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @POST("user")
    LiveData<ApiResponse<Void>> createUser(@Body Credentials credentials);

    @POST("user/verify_email")
    LiveData<ApiResponse<Void>> verifyUser(@Body Credentials credentials);

    @DELETE("user/current")
    LiveData<ApiResponse<Void>> deleteUser();

    @GET("user/current")
    LiveData<ApiResponse<User>> getCurrentUser();
}
