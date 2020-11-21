package com.example.gymside;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymside.databinding.ActivityRoutineExecutionBinding;
import com.example.gymside.repository.ExecutionRepository;


public class RoutineExecution extends AppCompatActivity {
    ActivityRoutineExecutionBinding binding;
    private ExecutionRepository executionApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executionApi = MyApplication.getExecutionRepository();

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

        executionApi.getExecution((Integer)extras.get("ROUTINE_ID")).observe(this, r->{
            viewModel.getCountDownTimer().start(r.getData().getResults().get(0).getDuration() * 1000, 1000);
            binding.start.setEnabled(false);
            binding.stop.setEnabled(true);
            binding.pause.setEnabled(true);
            binding.addTime.setEnabled(true);
        });

        /*viewModel.getCountDownTimer().start(30*1000, 1000);
        binding.start.setEnabled(false);
        binding.stop.setEnabled(true);
        binding.pause.setEnabled(true);
        binding.addTime.setEnabled(true);*/

        binding.start.setOnClickListener(v -> {
            //long time = Integer.parseInt(binding.time.getText().toString()) * 1000;
            //long interval = Integer.parseInt(binding.interval.getText().toString()) * 1000;

            /*viewModel.getCountDownTimer().start(30*1000, 1000);
            binding.start.setEnabled(false);
            binding.stop.setEnabled(true);
            binding.pause.setEnabled(true);
            binding.addTime.setEnabled(true);*/
        });

        binding.stop.setOnClickListener(v -> {
            viewModel.getCountDownTimer().stop();
            binding.start.setEnabled(true);
            binding.stop.setEnabled(false);
            binding.pause.setEnabled(false);
            binding.resume.setEnabled(false);
            binding.addTime.setEnabled(false);
            binding.countdown.setText("");
        });

        binding.pause.setOnClickListener(v -> {
            viewModel.getCountDownTimer().pause();
            binding.pause.setEnabled(false);
            binding.resume.setEnabled(true);
            binding.addTime.setEnabled(false);
        });

        binding.resume.setOnClickListener(v -> {
            viewModel.getCountDownTimer().resume();
            binding.pause.setEnabled(true);
            binding.resume.setEnabled(false);
            binding.addTime.setEnabled(true);
        });

        binding.addTime.setOnClickListener(v -> {
            viewModel.getCountDownTimer().stop();
            long time = (viewModel.getCountDownTimer().getStatus().getValue().getRemainingTime() + 10) * 1000;
            viewModel.getCountDownTimer().start(time, 1000);
        });
    }
}
