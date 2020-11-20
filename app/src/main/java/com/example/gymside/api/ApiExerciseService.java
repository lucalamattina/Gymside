package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Exercise;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseService {

    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getExercises(@Path("routineId") int routineId, @Path("cycleId") int cycleId);
}
