package com.example.gymside.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiExecutionService;
import com.example.gymside.api.ApiResponse;
import com.example.gymside.api.model.Execution;
import com.example.gymside.api.model.PagedList;

import java.util.List;

public class ExecutionRepository {
    private final ApiExecutionService apiService;
    private LiveData<List<Execution>> execution;

    public ExecutionRepository(Context context){
        this.apiService = ApiClient.create(context, ApiExecutionService.class);
    }

    public LiveData<Resource<PagedList<Execution>>> getExecution(int routineId){
        return new NetworkBoundResource<PagedList<Execution>, PagedList<Execution>>(){
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Execution>>> createCall(){
                return apiService.getExecutions(routineId);
            }
        }.asLiveData();
    }
}
