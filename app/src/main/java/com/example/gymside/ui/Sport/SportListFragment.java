/*
package com.example.gymside.ui.Sport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.gymside.MyApplication;
import com.example.gymside.databinding.FragmentListSportBinding;
import com.example.gymside.domain.Sport;
import com.example.gymside.repository.SportRepository;
import com.example.gymside.ui.MainActivity;
import com.example.gymside.viewmodel.RepositoryViewModelFactory;

public class SportListFragment  extends Fragment {

    FragmentListSportBinding binding;
    private SportViewModel sportViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListSportBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyApplication application = (MyApplication)getActivity().getApplication();
        MainActivity activity = (MainActivity)getActivity();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(SportRepository.class, application.getSportRepository());
        sportViewModel = new ViewModelProvider(this, viewModelFactory).get(SportViewModel.class);

        List<Sport> sports = new ArrayList<>();
        SportAdapter adapter = new SportAdapter(sports);
        sportViewModel.getSports().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    activity.showProgressBar();
                    break;
                case SUCCESS:
                    activity.hideProgressBar();
                    sports.clear();
                    sports.addAll(resource.data);
                    adapter.notifyDataSetChanged();
                    binding.list.scrollToPosition(sports.size() - 1);
                    break;
            }
        });

        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(activity));
        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!binding.list.canScrollVertically(1)) {
                    sportViewModel.getMoreSports();
                }
            }
        });
        binding.list.setAdapter(adapter);
    }
}
*/
