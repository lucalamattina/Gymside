package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Cycle;
import com.example.gymside.api.model.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCycleService {
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getCycles(@Path("routineId") int routineId);
}
