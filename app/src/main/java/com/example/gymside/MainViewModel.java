package com.example.gymside;


import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final CountDownTimer countDownTimer = new CountDownTimer();

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }
}
