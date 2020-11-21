package com.example.gymside;

import android.app.Application;

import com.example.gymside.repository.CycleRepository;
import com.example.gymside.repository.ExecutionRepository;
import com.example.gymside.repository.ExerciseRepository;
import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.repository.SportRepository;
import com.example.gymside.repository.UserRepository;

public class MyApplication extends Application {
    private static UserRepository userRepository;
    private SportRepository sportRepository;
    private static RoutineRepository routineRepository;
    private static ExerciseRepository exerciseRepository;
    private static CycleRepository cycleRepository;
    private static ExecutionRepository executionRepository;

    public static UserRepository getUserRepository() {
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

    public static ExecutionRepository getExecutionRepository() {return executionRepository;}

    @Override
    public void onCreate() {
        super.onCreate();

        userRepository = new UserRepository(this);

        cycleRepository = new CycleRepository(this);

        exerciseRepository = new ExerciseRepository(this);

        sportRepository =new SportRepository(this);

        routineRepository = new RoutineRepository(this);

        exerciseRepository = new ExerciseRepository(this);

        executionRepository = new ExecutionRepository(this);
    }
}
