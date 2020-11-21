package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Category;
import com.example.gymside.api.model.Execution;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Rating;
import com.example.gymside.api.model.Routine;
import com.example.gymside.api.model.Sport;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {
    @POST("routines")
    LiveData<ApiResponse<Routine>> addRoutine(@Body Routine routine);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<Routine>> getRoutine(@Path("routineId") int routineId);

    @POST("routines/{routineId}/executions")
    LiveData<ApiResponse<Routine>> addExecution(@Path("routineId") int routineId, @Body Execution execution);

    @PUT("routines/{routineId}")
    LiveData<ApiResponse<Routine>> modifyRoutine(@Path("routineId") int routineId, @Body Routine routine);

    @DELETE("routines/{routineId}")
    LiveData<ApiResponse<Void>> deleteRoutine(@Path("routineId") int routineId);

    @GET("user/current/routines/?page=0&size=99999&orderBy=dateCreated&direction=desc")
    LiveData<ApiResponse<PagedList<Routine>>> getRoutines();

    @GET("user/current/routines/?page=0&size=99999&direction=desc")
    LiveData<ApiResponse<PagedList<Routine>>> getRoutinesOrderedBy(@Query("orderBy") String order);

    @POST("routines/{routineId}/ratings")
    LiveData<ApiResponse<Void>> setRoutineRating(@Path("routineId") int routineId, @Body Rating rating);
}
