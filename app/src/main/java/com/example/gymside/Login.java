package com.example.gymside;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityLoginBinding;
import com.example.gymside.db.MyDatabase;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Login extends AppCompatActivity {


    ActivityLoginBinding binding;
    /*EditText editUsername, editPassword;
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
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyDatabase db = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "database-name").build();

        //Initialize And Assign Variable


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.routines:
                        startActivity(new Intent(getApplicationContext(), Routines.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favourites:
                        startActivity(new Intent(getApplicationContext(), Favourites.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Login.this, profileButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Profile") || item.getTitle().equals("Perfil")) {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        if(item.getTitle().equals("Logout") || item.getTitle().equals("Salir")) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        return false;
                    }

                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method
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

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
            setHomeItem(Login.this);
        } else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}