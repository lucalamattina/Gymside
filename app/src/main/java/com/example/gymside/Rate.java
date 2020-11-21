package com.example.gymside;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.gymside.api.model.Error;
import com.example.gymside.api.model.Rating;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rate extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

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


        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Rate.this, profileButton);
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
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

        Bundle extras = getIntent().getExtras();
        TextView rating;
        rating = findViewById(R.id.textView5);

        if(extras.get("ROUTINE_NAME") != null){
            rating.setText(extras.get("ROUTINE_RATING").toString());
        }

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v->{
            finish();
        });

        Button buttonSubmit = findViewById(R.id.submitButton);
        buttonSubmit.setOnClickListener(v->{
            MyApplication app = ((MyApplication)getApplication());
            if(extras.get("ROUTINE_ID") != null){
                app.getRoutineRepository().setRoutineRating((Integer) extras.get("ROUTINE_ID"), new Rating(2, "0")).observe(this, r -> {
                    switch (r.getStatus()) {
                        case SUCCESS:
                            Log.d("UI", "Success");
                            finish();
                            //int count = r.getData().getResults().size();
                            //String message = getResources().getQuantityString(R.plurals.found, count, count);
                            //binding.result.setText(message);
                            break;
                        default:
                            defaultResourceHandler(r);
                            break;
                    }
                });
            }
        });

        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        if(text.equals("r")) {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
            bottomNavigationView.getMenu().getItem(2).setChecked(false);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        if(text.equals("f")){
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
            bottomNavigationView.getMenu().getItem(1).setChecked(false);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);


        }
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
