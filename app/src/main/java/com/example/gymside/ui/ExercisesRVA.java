package com.example.gymside.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymside.MyApplication;
import com.example.gymside.R;
import com.example.gymside.RoutineDetails;
import com.example.gymside.api.model.Exercise;
import com.example.gymside.api.model.Routine;
import com.example.gymside.repository.ExerciseRepository;
import com.example.gymside.repository.RoutineRepository;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class ExercisesRVA extends RecyclerView.Adapter<com.example.gymside.ui.ExercisesRVA.ExercisesViewHolder> {


    private static final String TAG = "ExercisesRVA";

    private Context mContext;
    private ExerciseRepository api;
    private List<Exercise> exercises;

    public ExercisesRVA(Context context, List<Exercise> exercises)
    {
        this.exercises = new ArrayList<>();
        mContext = context;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExercisesRVA.ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.exercise_card,parent, false);
        return new ExercisesRVA.ExercisesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ExercisesRVA.ExercisesViewHolder holder, int position) {
        api = MyApplication.getExerciseRepository();
        holder.exercise_name.setText(exercises.get(position).getName());
        holder.exercise_detail.setText(exercises.get(position).getDetail());
        holder.exercise_duration.setText(Integer.toString(exercises.get(position).getDuration()));
        holder.exercise_type.setText(exercises.get(position).getType());
//        holder.activateB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        Log.d("UI", "Exercises size: " + (this.exercises != null ? Integer.toString(this.exercises.size()) : "0"));
        return exercises != null ? this.exercises.size() : 0;
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder{

        TextView exercise_name;
        TextView exercise_detail;
        TextView exercise_duration;
        TextView exercise_type;
        RelativeLayout exercisesLayout;
        //Button activateB;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
//            routine_image = itemView.findViewById(R.id.routine_image);
            exercise_name = itemView.findViewById(R.id.exercise_name);
            exercise_detail = itemView.findViewById(R.id.exercise_detail);
            exercise_duration = itemView.findViewById(R.id.exercise_duration);
            exercise_type = itemView.findViewById(R.id.exercise_type);
            exercisesLayout = itemView.findViewById(R.id.exercise_layout);
//            activateB = itemView.findViewById(R.id.activateButton);

        }
    }
}
