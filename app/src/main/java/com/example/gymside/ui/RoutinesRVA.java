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
import com.example.gymside.api.model.Routine;
import com.example.gymside.repository.RoutineRepository;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class RoutinesRVA extends RecyclerView.Adapter<com.example.gymside.ui.RoutinesRVA.RoutinesViewHolder>{

    private static final String TAG = "RoutinesRVA";

    private Context mContext;
    private RoutineRepository api;
    private List<Routine> routines;

    public RoutinesRVA(Context context, List<Routine> routines)
    {
        this.routines = new ArrayList<>();
        mContext = context;
        this.routines = routines;
    }

    @NonNull
    @Override
    public RoutinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.routine_card,parent, false);
        return new RoutinesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RoutinesViewHolder holder, int position) {
        api = MyApplication.getRoutineRepository();
        holder.routine_name.setText(routines.get(position).getName());
        holder.routine_difficulty.setText(routines.get(position).getDifficulty());
        holder.routine_category.setText(routines.get(position).getCategory().getName());
        holder.routine_rating.setText(Float.toString(routines.get(position).getRating()));
        holder.routinesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, routines.get(position).getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, RoutineDetails.class);
                intent.putExtra("ROUTINE_NAME", routines.get(position).getName());
                intent.putExtra("ROUTINE_DETAIL", routines.get(position).getDetail());
                intent.putExtra("ROUTINE_RATING", routines.get(position).getRating());
                intent.putExtra("ROUTINE_DIFFICULTY", routines.get(position).getDifficulty());
                intent.putExtra("ROUTINE_CATEGORY", routines.get(position).getCategory().getName());
                String textToPass = "r";
                intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                intent.putExtra("ROUTINE_ID", routines.get(position).getId());
                startActivity(mContext, intent ,new Bundle());
            }
        });
//        holder.activateB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        Log.d("UI", "Routines size: " + (this.routines != null ? Integer.toString(this.routines.size()) : "0"));
        return routines != null ? this.routines.size() : 0;
    }

    public class RoutinesViewHolder extends RecyclerView.ViewHolder{

        TextView routine_name;
        TextView routine_difficulty;
        TextView routine_category;
        TextView routine_rating;
        RelativeLayout routinesLayout;
        //Button activateB;

        public RoutinesViewHolder(@NonNull View itemView) {
            super(itemView);
//            routine_image = itemView.findViewById(R.id.routine_image);
            routine_name = itemView.findViewById(R.id.exercise_name);
            routine_difficulty = itemView.findViewById(R.id.exercise_duration);
            routine_category = itemView.findViewById(R.id.exercise_detail);
            routine_rating = itemView.findViewById(R.id.exercise_type);
            routinesLayout = itemView.findViewById(R.id.routine_layout);
//            activateB = itemView.findViewById(R.id.activateButton);

        }
    }


}