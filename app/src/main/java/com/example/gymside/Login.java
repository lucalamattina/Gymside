package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityLoginBinding;
import com.example.gymside.repository.Resource;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        Button signUpButton = findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Register.class));
            overridePendingTransition(0, 0);
        });


       /* binding.loginButton.setOnClickListener(v->{
            Credentials credentials = new Credentials("johndoe", "1234567890");
            MyApplication app = (MyApplication) getApplication();
            app.getUserRepository().login(credentials).observe(this,r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        AppPreferences preferences = new AppPreferences(app);
                        preferences.setAuthToken(r.getData().getToken());
                        break;
                    default:
                        defaultResourceHandler(r);
                        break;
                }
            });
        });*/
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