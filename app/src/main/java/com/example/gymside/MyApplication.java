package com.example.gymside;

import android.app.Application;

import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.repository.SportRepository;
import com.example.gymside.repository.UserRepository;

public class MyApplication extends Application {
    private UserRepository userRepository;
    private SportRepository sportRepository;
    private static RoutineRepository routineRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    public static RoutineRepository getRoutineRepository() {return routineRepository;}

    @Override
    public void onCreate() {
        super.onCreate();

        userRepository = new UserRepository(this);

        sportRepository =new SportRepository(this);

        routineRepository = new RoutineRepository(this);
    }
}
