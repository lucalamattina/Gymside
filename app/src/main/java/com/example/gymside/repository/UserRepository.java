package com.example.gymside.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiResponse;
import com.example.gymside.api.ApiUserService;
import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Routine;
import com.example.gymside.api.model.Token;
import com.example.gymside.api.model.User;

public class UserRepository {
    private final ApiUserService apiService;

    public UserRepository(Context context) {
        this.apiService = ApiClient.create(context, ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteUser() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> createUser(Credentials credentials) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.createUser(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> modifyUser(Credentials credentials) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.modifyUser(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> verifyUser(Credentials credentials) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.verifyUser(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Routine>>> getFavourites() {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getFavourites();
            }
        }.asLiveData();

    }

    public LiveData<Resource<Void>> postFavourite(int routine) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.postFavourite(routine);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteFavourite(int routine) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteFavourite(routine);
            }
        }.asLiveData();
    }
}
