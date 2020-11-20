package com.example.gymside.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiExerciseService;
import com.example.gymside.api.ApiResponse;
import com.example.gymside.api.model.Exercise;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Routine;
import com.example.gymside.db.MyDatabase;
import com.example.gymside.db.dao.RoutineDao;

import java.util.List;

public class ExerciseRepository {
    private final ApiExerciseService apiService;
    private RoutineDao dao;
    private MyDatabase db;
    private LiveData<List<Exercise>> exercises;

    public ExerciseRepository(Context context){
        this.apiService = ApiClient.create(context, ApiExerciseService.class);
    }

    public LiveData<Resource<PagedList<Exercise>>> getExercises(int routineId) {
        return new NetworkBoundResource<PagedList<Exercise>, PagedList<Exercise>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Exercise>>> createCall() {
                return apiService.getExercises(routineId);
            }
        }.asLiveData();
    }
}
