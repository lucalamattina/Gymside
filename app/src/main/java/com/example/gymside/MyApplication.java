package com.example.gymside;

import android.app.Application;

import com.example.gymside.repository.CycleRepository;
import com.example.gymside.repository.ExerciseRepository;
import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.repository.SportRepository;
import com.example.gymside.repository.UserRepository;

public class MyApplication extends Application {
    private UserRepository userRepository;
    private SportRepository sportRepository;
    private static RoutineRepository routineRepository;
    private static ExerciseRepository exerciseRepository;
    private static CycleRepository cycleRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public static CycleRepository getCycleRepository() {
        return cycleRepository;
    }

    public static ExerciseRepository getExerciseRepository() {
        return exerciseRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    public static RoutineRepository getRoutineRepository() {return routineRepository;}

    @Override
    public void onCreate() {
        super.onCreate();

        userRepository = new UserRepository(this);

        cycleRepository = new CycleRepository(this);

        exerciseRepository = new ExerciseRepository(this);

        sportRepository =new SportRepository(this);

        routineRepository = new RoutineRepository(this);

        exerciseRepository = new ExerciseRepository(this);
    }
}
