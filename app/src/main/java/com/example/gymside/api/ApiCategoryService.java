package com.example.gymside.api;

import androidx.lifecycle.LiveData;

import com.example.gymside.api.model.Category;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Sport;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCategoryService {
    @POST("categories")
    LiveData<ApiResponse<Category>> addSport(@Body Category category);

    @GET("categories")
    LiveData<ApiResponse<PagedList<Sport>>> getCategories();
}
