package com.example.gymside.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymside.MyApplication;
import com.example.gymside.R;
import com.example.gymside.api.model.Routine;
import com.example.gymside.repository.RoutineRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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
        Log.d("UI", "holisssssss");
        return new RoutinesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RoutinesViewHolder holder, int position) {
        api = MyApplication.getRoutineRepository();
        Log.d("UI", "holis");
        holder.routine_name.setText(routines.get(position).getName());
        holder.routinesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, routines.get(position).getName(), Toast.LENGTH_LONG).show();
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
        RelativeLayout routinesLayout;
        //Button activateB;

        public RoutinesViewHolder(@NonNull View itemView) {
            super(itemView);
//            routine_image = itemView.findViewById(R.id.routine_image);
            routine_name = itemView.findViewById(R.id.routine_name);
            routinesLayout = itemView.findViewById(R.id.routine_layout);
//            activateB = itemView.findViewById(R.id.activateButton);

        }
    }


}