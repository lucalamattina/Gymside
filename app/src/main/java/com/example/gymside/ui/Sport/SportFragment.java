/*
package com.example.gymside.ui.Sport;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymside.MyApplication;
import com.example.gymside.R;
import com.example.gymside.databinding.FragmentEditSportBinding;
import com.example.gymside.domain.Sport;
import com.example.gymside.repository.SportRepository;
import com.example.gymside.ui.MainActivity;
import com.example.gymside.viewmodel.RepositoryViewModelFactory;
import com.example.gymside.vo.Resource;

public class SportFragment extends Fragment {

    private static final String BUNDLE_SPORT_ID = "com.example.gymside.SPORT_ID";

    private MyApplication application;
    private MainActivity activity;
    private FragmentEditSportBinding binding;
    private SportViewModel sportViewModel;
    private MenuItem editMenuItem, deleteMenuItem;

    public static SportFragment create(int sportId, boolean edit) {
        SportFragment fragment = new SportFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_SPORT_ID, sportId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        binding = FragmentEditSportBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(SportRepository.class, application.getSportRepository());
        sportViewModel = new ViewModelProvider(this, viewModelFactory).get(SportViewModel.class);

        if (getArguments() != null) {
            toggleEditMode(false);
            binding.confirm.setText(R.string.confirm);

            sportViewModel.setSportId(getArguments().getInt(BUNDLE_SPORT_ID));
            sportViewModel.getSport().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        activity.showProgressBar();
                        binding.confirm.setEnabled(false);
                        break;
                    case SUCCESS:
                        activity.hideProgressBar();
                        binding.name.setText(resource.data.getName());
                        binding.detail.setText(resource.data.getDetail());
                        binding.confirm.setEnabled(true);
                        break;
                    case ERROR:
                        activity.hideProgressBar();
                        binding.confirm.setEnabled(true);
                        Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            });
        }

        binding.confirm.setOnClickListener(v -> {
            if (getArguments() == null) {
                Sport sport = new Sport(binding.name.getText().toString(), binding.name.getText().toString());
                if (!sportViewModel.addSport(sport).hasActiveObservers())
                    sportViewModel.addSport(sport).observe(getViewLifecycleOwner(), this::handleEditResponse);
            }
            else {
                Sport sport = sportViewModel.getSport().getValue().data;
                sport.setName(binding.name.getText().toString());
                sport.setDetail(binding.detail.getText().toString());
                if (!sportViewModel.modifySport(sport).hasActiveObservers())
                    sportViewModel.modifySport(sport).observe(getViewLifecycleOwner(), this::handleEditResponse);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_sport_menu, menu);
        editMenuItem = menu.findItem(R.id.menu_edit);
        deleteMenuItem = menu.findItem(R.id.menu_delete);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        boolean visible = (getArguments() != null) && !binding.name.isEnabled();
        menu.findItem(R.id.menu_edit).setVisible(visible);
        menu.findItem(R.id.menu_delete).setVisible(visible);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editMenuItem.setEnabled(false);
                toggleEditMode(true);
                return true;
            case R.id.menu_delete:
                deleteMenuItem.setEnabled(false);
                deleteSport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleEditResponse(Resource<Sport> resource) {

        switch (resource.status) {
            case LOADING:
                activity.showProgressBar();
                binding.confirm.setEnabled(false);
                break;
            case SUCCESS:
                activity.hideProgressBar();
                if (getArguments() != null) {
                    toggleEditMode(false);
                } else {
                    binding.name.getText().clear();
                    binding.detail.getText().clear();
                    binding.confirm.setEnabled(true);
                }
                Toast.makeText(activity, R.string.operation_success, Toast.LENGTH_SHORT).show();
                break;
            case ERROR:
                activity.hideProgressBar();
                binding.confirm.setEnabled(true);
                Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void toggleEditMode(boolean mode) {
        binding.name.setEnabled(mode);
        binding.detail.setEnabled(mode);
        binding.confirm.setVisibility(mode ? View.VISIBLE : View.GONE);
    }

    private void deleteSport() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.app_name);
        String message = getString(R.string.confirm_delete, binding.name.getText());
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, (dialog, id) -> {
            Sport sport = sportViewModel.getSport().getValue().data;
            sportViewModel.deleteSport(sport).observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        activity.showProgressBar();
                        break;
                    case SUCCESS:
                        activity.popBackStack();
                        Toast.makeText(activity, R.string.operation_success, Toast.LENGTH_SHORT).show();
                        break;
                    case ERROR:
                        activity.hideProgressBar();
                        Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            });
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }
}*/
