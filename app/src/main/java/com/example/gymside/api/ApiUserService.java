package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Routine;
import com.example.gymside.api.model.Token;
import com.example.gymside.api.model.User;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @PUT("user/current")
    LiveData<ApiResponse<Void>> modifyUser(@Body Credentials credentials);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getFavourites();

    @POST("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> postFavourite(@Path("routineId") int routineId);

    @DELETE("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);
}
