package com.example.gymside.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiResponse;
import com.example.gymside.api.ApiSportService;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Sport;
import com.example.gymside.db.MyDatabase;
import com.example.gymside.db.dao.SportDao;
import com.example.gymside.db.entity.SportEntity;

import java.util.List;

public class SportRepository {
    private final ApiSportService apiService;
    private SportDao dao;
    private MyDatabase db;
    private LiveData<List<SportEntity>> sports;

    public SportRepository(Context context) {
        this.apiService = ApiClient.create(context, ApiSportService.class);
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


    public LiveData<Resource<PagedList<Sport>>> getSports() {
        return new NetworkBoundResource<PagedList<Sport>, PagedList<Sport>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Sport>>> createCall() {
                return apiService.getSports();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> getSport(int sportId) {
        return new NetworkBoundResource<Sport, Sport>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Sport>> createCall() {
                return apiService.getSport(sportId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> addSport(Sport sport) {
        return new NetworkBoundResource<Sport, Sport>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Sport>> createCall() {
                return apiService.addSport(sport);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> modifySport(Sport sport) {
        return new NetworkBoundResource<Sport, Sport>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Sport>> createCall() {
                return apiService.modifySport(sport.getId(), sport);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteSport(int sportId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteSport(sportId);
            }
        }.asLiveData();
    }
}
