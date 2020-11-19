package com.example.gymside.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.example.gymside.db.MyDatabase;
import com.example.gymside.repository.Resource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Sport sport;
    EditText editUsername, editPassword;
    TextView result;
    Button buttonCheck;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyDatabase db = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "database-name").build();

        editUsername  = (EditText) findViewById(R.id.edituser);
        editPassword = (EditText) findViewById(R.id.editpass);
        result = (TextView) findViewById(R.id.tvShow);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editUsername.getText().toString();
                String pass = editPassword.getText().toString();
                savePass(name, pass);
                result.setText("Name: "+ name + "\npass: " + pass);
            }
        });

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        //Initialize And Assign Variable

/*        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener((view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            setContentView(R.layout.activity_login);
        }));*/

        Button loginView = findViewById(R.id.loginViewButton);
        loginView.setOnClickListener((view -> {
            setContentView(R.layout.activity_login);
        }));

        /*binding.loginViewButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            overridePendingTransition(0, 0);
        });*/

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

        binding.loginButton.setOnClickListener(v->{

            Credentials credentials = new Credentials(username, password);
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
        binding.getSportsButton.setOnClickListener(v->{
            MyApplication app = ((MyApplication)getApplication());
            app.getSportRepository().getSports().observe(this,r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        int count = r.getData().getResults().size();
                        //String message = getResources().getQuantityString(R.plurals.found, count, count);
                        //binding.result.setText(message);
                        break;
                    default:
                        defaultResourceHandler(r);
                        break;
                }
            });
        });

        binding.logoutButton.setOnClickListener(v->{
            MyApplication app = (MyApplication) getApplication();
            app.getUserRepository().logout().observe(this,r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        AppPreferences preferences = new AppPreferences(app);
                        break;
                    default:
                        defaultResourceHandler(r);
                        break;
                }
            });
        });

        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, profileButton);
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
                        if(item.getTitle().equals("Settings") || item.getTitle().equals("Configuración")) {
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        if(item.getTitle().equals("Logout") || item.getTitle().equals("Salir")) {
                            startActivity(new Intent(getApplicationContext(), Login.class));
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

    private void savePass(String user, String pass){
        this.username = user;
        this.password = pass;
    }
}