package com.example.gymside;

import android.app.Application;

import com.example.gymside.repository.SportRepository;
import com.example.gymside.repository.UserRepository;

public class MyApplication extends Application {
    private UserRepository userRepository;
    private SportRepository sportRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        userRepository = new UserRepository(this);

        sportRepository =new SportRepository(this);
    }
}
