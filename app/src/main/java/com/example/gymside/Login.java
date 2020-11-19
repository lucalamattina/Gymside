package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityLoginBinding;
import com.example.gymside.databinding.ActivityMainBinding;
import com.example.gymside.databinding.ActivityProfileBinding;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gymside.AppPreferences;
import com.example.gymside.Favourites;
import com.example.gymside.Login;
import com.example.gymside.MyApplication;
import com.example.gymside.Profile;
import com.example.gymside.R;
import com.example.gymside.Register;
import com.example.gymside.Routines;
import com.example.gymside.Settings;
import com.example.gymside.api.ApiClient;
import com.example.gymside.api.ApiSportService;
import com.example.gymside.api.ApiUserService;
import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Sport;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityMainBinding;
import com.example.gymside.repository.Resource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
/*    EditText editUsername, editPassword;
    TextView result;
    Button buttonCheck;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*setContentView(R.layout.activity_login);*/

/*        Button buttonRegister = findViewById(R.id.signupButton);
        buttonRegister.setOnClickListener((view -> {
            startActivity(new Intent(getApplicationContext(), Register.class));
            overridePendingTransition(0,0);
            setContentView(R.layout.activity_register);
        }));*/

        /*binding.signupButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Register.class));
            overridePendingTransition(0,0);
            setContentView(R.layout.activity_register);
        });*/


        binding.signupButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(0, 0);
        });


        /*editUsername  = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);
        result = (TextView) findViewById(R.id.tvShow);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editUsername.getText().toString();
                String pass = editPassword.getText().toString();
                result.setText("Name: "+ name);
            }
        });*/



        binding.loginButton.setOnClickListener(v->{
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