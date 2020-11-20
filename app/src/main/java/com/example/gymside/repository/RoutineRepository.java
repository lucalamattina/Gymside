package com.example.gymside.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiResponse;
import com.example.gymside.api.ApiRoutineService;
import com.example.gymside.api.model.Execution;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Routine;
import com.example.gymside.db.MyDatabase;
import com.example.gymside.db.dao.RoutineDao;
import com.example.gymside.db.entity.RoutineEntity;

import java.util.List;

public class RoutineRepository {
    private final ApiRoutineService apiService;
    private RoutineDao dao;
    private MyDatabase db;
    private LiveData<List<RoutineEntity>> routines;

    public RoutineRepository(Context context) {
        this.apiService = ApiClient.create(context, ApiRoutineService.class);
        /*db = Room.databaseBuilder(application, MyDatabase.class, "database").build();
        dao = db.sportDao();
        sports = dao.findAll();*/
    }

    /*//agregado
    private LiveData<List<SportEntity>> findAll(){
        return sports;
    }
    //agregado
    void insert(SportEntity sport){
        db.getQueryExecutor().execute(
                ()->dao.insert(sport)
        );
    }
    //agregado
    void update(SportEntity sport){
        db.getQueryExecutor().execute(
                ()->dao.update(sport)
        );
    }*/


    public LiveData<Resource<PagedList<Routine>>> getRoutines() {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Routine>>> getRoutinesOrderedBy(String order) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getRoutinesOrderedBy(order);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(int routineId) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> addRoutine(Routine routine) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.addRoutine(routine);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> createExecution(int routineId, Execution execution) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.addExecution(routineId, execution);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> modifyRoutine(Routine routine) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.modifyRoutine(routine.getId(), routine);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteRoutine(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteRoutine(routineId);
            }
        }.asLiveData();
    }


}
