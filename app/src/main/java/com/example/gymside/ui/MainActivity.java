package com.example.gymside.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.gymside.AppPreferences;
import com.example.gymside.Favourites;
import com.example.gymside.Login;
import com.example.gymside.MyApplication;
import com.example.gymside.Profile;
import com.example.gymside.R;
import com.example.gymside.Register;
import com.example.gymside.Routines;
import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.api.model.Sport;
import com.example.gymside.databinding.ActivityLoginBinding;
import com.example.gymside.databinding.ActivityMainBinding;
import com.example.gymside.db.MyDatabase;
import com.example.gymside.repository.Resource;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    EditText editUsername, editPassword;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        editUsername  = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);

        Button signUpButton = findViewById(R.id.signupButtonn);
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
                        startActivity(new Intent(getApplicationContext(), Login.class));
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
                TextView textError = (TextView) findViewById(R.id.textShowError);
                if(resource.getError() != null){
                    textError.setText(R.string.invalidData);
                }
                Error error = resource.getError();
                //String message = getString(R.string.error, error.getDescription(), error.getCode());
                Log.d("UI", "Error");
                //binding.result.setText(message);
                break;
        }
    }
}