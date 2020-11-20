package com.example.gymside;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gymside.api.ApiRoutineService;
import com.example.gymside.api.model.Error;
import com.example.gymside.repository.Resource;
import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RoutineDetails extends AppCompatActivity {


    Button rateButton;
    ToggleButton toggleButton;


    private RoutineRepository api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApplication.getRoutineRepository();
        setContentView(R.layout.activity_routines_details);
        TextView name = findViewById(R.id.name);
        TextView rating = findViewById(R.id.rating);
        TextView detail = findViewById(R.id.body);
        TextView category = findViewById(R.id.category);
        Button start = findViewById(R.id.start_routine);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO agregar metodo para crear una execution y hacer las pantallas de ejecucion de rutina
            }
        });

        Bundle extras = getIntent().getExtras();

        name.setText(extras.get("ROUTINE_NAME").toString());
        rating.setText(extras.get("ROUTINE_RATING").toString());
        detail.setText(extras.get("ROUTINE_DETAIL").toString());
        category.setText(extras.get("ROUTINE_CATEGORY").toString());

        rateButton = (Button) findViewById(R.id.rate);



        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorites));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_fav_red_full));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_red_empty));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize And Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.routines:
                        startActivity(new Intent(getApplicationContext(),Routines.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favourites:
                        startActivity(new Intent(getApplicationContext(),Favourites.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.equals("r")) {
                    String textToPass = "r";

                    Intent intent = new Intent(getBaseContext(), Rate.class);
                    intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                    startActivity(intent);

                    //startActivity(new Intent(RoutineDetails.this, Rate.class));
                }
                if(text.equals("f")){
                    String textToPass = "f";

                    Intent intent = new Intent(getBaseContext(), Rate.class);
                    intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                    startActivity(intent);

                    //startActivity(new Intent(RoutineDetails.this, Rate.class));
                }

            }
        });


        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(RoutineDetails.this, profileButton);
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
                            MyApplication app = (MyApplication) getApplication();
                            app.getUserRepository().logout().observeForever(r -> {
                                switch (r.getStatus()) {
                                    case SUCCESS:
                                        Log.d("UI", "Success");
                                        AppPreferences preferences = new AppPreferences(app);
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        overridePendingTransition(0, 0);
                                        break;
                                    default:
                                        defaultResourceHandler(r);
                                        break;
                                }
                            });
                            return true;
                        }
                        return false;
                    }

                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method


        if(text.equals("r")) {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
            bottomNavigationView.getMenu().getItem(2).setChecked(false);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }

        if(text.equals("f")) {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
            bottomNavigationView.getMenu().getItem(1).setChecked(false);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }

        //if(getCallingActivity().getClassName().equals("com.example.gymside.Routines")) {
            //bottomNavigationView.getMenu().getItem(0).setChecked(false);
            //bottomNavigationView.getMenu().getItem(2).setChecked(false);
            //bottomNavigationView.getMenu().getItem(1).setChecked(true);
        //}
        //if(getCallingActivity().getClassName().equals("com.example.gymside.Favourites")){
            //bottomNavigationView.getMenu().getItem(0).setChecked(false);
            //bottomNavigationView.getMenu().getItem(1).setChecked(false);
            //bottomNavigationView.getMenu().getItem(2).setChecked(true);
        //}
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
