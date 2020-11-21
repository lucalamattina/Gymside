package com.example.gymside;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymside.api.model.Error;
import com.example.gymside.api.model.Execution;
import com.example.gymside.api.model.ExecutionCreate;
import com.example.gymside.databinding.ActivityRoutineExecutionBinding;
import com.example.gymside.repository.ExecutionRepository;
import com.example.gymside.repository.Resource;
import com.example.gymside.repository.RoutineRepository;


public class RoutineExecution extends AppCompatActivity {
    ActivityRoutineExecutionBinding binding;
    private ExecutionRepository executionApi;
    private RoutineRepository routineApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executionApi = MyApplication.getExecutionRepository();
        routineApi = MyApplication.getRoutineRepository();

        binding = ActivityRoutineExecutionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        boolean started = viewModel.getCountDownTimer().isStarted();
        boolean paused = viewModel.getCountDownTimer().isPaused();

        binding.start.setEnabled(!started);
        binding.stop.setEnabled(started);
        binding.pause.setEnabled(started && !paused);
        binding.resume.setEnabled(started && paused);
        binding.addTime.setEnabled(started && !paused);

        viewModel.getCountDownTimer().getStatus().observe(this, countDownTimerStatus -> {
            if (countDownTimerStatus.isFinished()) {
                binding.countdown.setText(R.string.done);
                binding.start.setEnabled(true);
                binding.stop.setEnabled(false);
                binding.pause.setEnabled(false);
                binding.addTime.setEnabled(false);
            } else {
                binding.countdown.setText(String.valueOf(countDownTimerStatus.getRemainingTime()));
            }
        });

        Bundle extras = getIntent().getExtras();

        /*viewModel.getCountDownTimer().start(30*1000, 1000);
        binding.start.setEnabled(false);
        binding.stop.setEnabled(true);
        binding.pause.setEnabled(true);
        binding.addTime.setEnabled(true);*/

        binding.start.setOnClickListener(v -> {
            if(viewModel.getCountDownTimer().getStatus().getValue().toString().equals(' ') || Integer.parseInt(viewModel.getCountDownTimer().getStatus().getValue().toString()) <= 0) {
                routineApi.createExecution((Integer) extras.get("ROUTINE_ID"), new ExecutionCreate(Integer.parseInt(viewModel.getCountDownTimer().getStatus().getValue().toString()), false)).observeForever(r -> {
                    switch (r.getStatus()) {
                        case SUCCESS:

                            break;
                        default:
                            defaultResourceHandler(r);
                            break;
                    }
                });
            }
            //long time = Integer.parseInt(binding.time.getText().toString()) * 1000;
            //long interval = Integer.parseInt(binding.interval.getText().toString()) * 1000;

            /*viewModel.getCountDownTimer().start(30*1000, 1000);
            binding.start.setEnabled(false);
            binding.stop.setEnabled(true);
            binding.pause.setEnabled(true);
            binding.addTime.setEnabled(true);*/
        });
        if (viewModel.getCountDownTimer().getStatus().getValue() != null) {
            viewModel.getCountDownTimer().start(Integer.parseInt(viewModel.getCountDownTimer().getStatus().getValue().toString()) * 1000, 1000);
        }
        binding.start.setEnabled(false);
        binding.stop.setEnabled(true);
        binding.pause.setEnabled(true);
        binding.addTime.setEnabled(true);

        binding.stop.setOnClickListener(v -> {
            if(viewModel.getCountDownTimer() != null) {
                viewModel.getCountDownTimer().stop();
                binding.start.setEnabled(true);
                binding.stop.setEnabled(false);
                binding.pause.setEnabled(false);
                binding.resume.setEnabled(false);
                binding.addTime.setEnabled(false);
                binding.countdown.setText("");
            }
        });

        binding.pause.setOnClickListener(v -> {
            if(viewModel.getCountDownTimer() != null) {
                viewModel.getCountDownTimer().pause();
                binding.pause.setEnabled(false);
                binding.resume.setEnabled(true);
                binding.addTime.setEnabled(false);
            }
        });

        binding.resume.setOnClickListener(v -> {
            if(viewModel.getCountDownTimer() != null) {
                viewModel.getCountDownTimer().resume();
                binding.pause.setEnabled(true);
                binding.resume.setEnabled(false);
                binding.addTime.setEnabled(true);
            }
        });

        binding.addTime.setOnClickListener(v -> {
            if(!viewModel.getCountDownTimer().isPaused()) {
                viewModel.getCountDownTimer().stop();
                long time = (viewModel.getCountDownTimer().getStatus().getValue().getRemainingTime() + 10) * 1000;
                viewModel.getCountDownTimer().start(time, 1000);
            } else {
                long time = (viewModel.getCountDownTimer().getStatus().getValue().getRemainingTime() + 10) * 1000;
                viewModel.getCountDownTimer().start(time, 1000);
            }
        });
    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                Log.d("UI", "Success");
                //binding.result.setText(R.string.loading);
                break;
            case ERROR:
                Error error = resource.getError();
                //String message = getString(R.string.error, error.getDescription(), error.getCode());
                Log.d("UI", "Error");
                //binding.result.setText(message);
                break;
        }
    }
}
