package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Execution;
import com.example.gymside.api.model.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExecutionService {
    @GET("routines/{routineId}/executions?page=0&size=10&orderBy=date&direction=asc")
    LiveData<ApiResponse<PagedList<Execution>>> getExecutions(@Path("routineId") int routineId);
}
