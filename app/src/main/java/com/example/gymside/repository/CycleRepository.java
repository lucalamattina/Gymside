package com.example.gymside.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiCycleService;
import com.example.gymside.api.ApiExerciseService;
import com.example.gymside.api.ApiResponse;
import com.example.gymside.api.model.Cycle;
import com.example.gymside.api.model.Exercise;
import com.example.gymside.api.model.PagedList;

import java.util.List;

public class CycleRepository {
    private final ApiCycleService apiService;
    private LiveData<List<Cycle>> cycles;

    public CycleRepository(Context context){
        this.apiService = ApiClient.create(context, ApiCycleService.class);
    }

    public LiveData<Resource<PagedList<Cycle>>> getCycles(int routineId) {
        return new NetworkBoundResource<PagedList<Cycle>, PagedList<Cycle>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return apiService.getCycles(routineId);
            }
        }.asLiveData();
    }
}
