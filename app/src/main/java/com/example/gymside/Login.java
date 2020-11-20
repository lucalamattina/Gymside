package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityLoginBinding;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    EditText editUsername, editPassword;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        editUsername  = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);

        Button signUpButton = findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Register.class));
            overridePendingTransition(0, 0);
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v->{
            String name = editUsername.getText().toString();
            String pass = editPassword.getText().toString();
            Credentials credentials = new Credentials(name, pass);
            MyApplication app = (MyApplication) getApplication();
            app.getUserRepository().login(credentials).observe(this,r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        AppPreferences preferences = new AppPreferences(app);
                        preferences.setAuthToken(r.getData().getToken());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    default:
                        defaultResourceHandler(r);
                        break;
                }
            });
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