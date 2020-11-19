/*
package com.example.gymside.ui.Sport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.gymside.domain.Sport;
import com.example.gymside.ui.MainActivity;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    private final List<Sport> data;

    public SportAdapter(List<Sport> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SportAdapter.SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SportAdapter.SportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportAdapter.SportViewHolder holder, int position) {
        Sport sport = data.get(position);
        holder.bindTo(sport);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int id;
        private final TextView textView;

        public SportViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView)itemView;
            itemView.setOnClickListener(this);
        }

        public void bindTo(Sport sport) {
            id = sport.getId();
            textView.setText(sport.getName());
        }

        @Override
        public void onClick(View v) {
            Context context = textView.getContext();
            if (context instanceof MainActivity) {
                MainActivity activity = (MainActivity)context;
                activity.replaceFragment(SportFragment.create(id, false), true);
            }
        }
    }
}
*/
