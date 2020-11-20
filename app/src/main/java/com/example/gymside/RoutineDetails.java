package com.example.gymside;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymside.api.ApiRoutineService;
import com.example.gymside.api.model.Error;
import com.example.gymside.api.model.Routine;
import com.example.gymside.repository.Resource;
import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.ui.MainActivity;
import com.example.gymside.ui.RoutinesRVA;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RoutineDetails extends AppCompatActivity {


    private RoutineRepository api;
    TextView name;
    TextView rating;
    TextView detail;
    TextView category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApplication.getRoutineRepository();
        setContentView(R.layout.activity_routines_details);
        name = findViewById(R.id.name);
        rating = findViewById(R.id.rating);
        detail = findViewById(R.id.body);
        category = findViewById(R.id.category);
        Button start = findViewById(R.id.start_routine);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO agregar metodo para crear una execution y hacer las pantallas de ejecucion de rutina
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras.get("ROUTINE_NAME") != null) {
            name.setText(extras.get("ROUTINE_NAME").toString());
            rating.setText(extras.get("ROUTINE_RATING").toString());
            detail.setText(extras.get("ROUTINE_DETAIL").toString());
            category.setText(extras.get("ROUTINE_CATEGORY").toString());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize And Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.routines:
                        startActivity(new Intent(getApplicationContext(), Routines.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favourites:
                        startActivity(new Intent(getApplicationContext(), Favourites.class));
                        overridePendingTransition(0, 0);
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
                PopupMenu popup = new PopupMenu(RoutineDetails.this, profileButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Profile") || item.getTitle().equals("Perfil")) {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        if (item.getTitle().equals("Settings") || item.getTitle().equals("Configuración")) {
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        if (item.getTitle().equals("Logout") || item.getTitle().equals("Salir")) {
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
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        handleIntent(appLinkIntent);
    }


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            String recipeId = appLinkData.getLastPathSegment();
            Uri appData = Uri.parse("content://com.gymside/routines/").buildUpon()
                    .appendPath(recipeId).build();
            Log.d("UI",appData.getLastPathSegment());
            api.getRoutine(Integer.parseInt(appData.getLastPathSegment())).observeForever(r->{
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        assert r.getData() != null;
                        Routine rt = r.getData();
                        name.setText(rt.getName());
                        rating.setText(rt.getRating().toString());
                        detail.setText(rt.getDetail());
                        category.setText(rt.getCategory().getName());
                        break;
                    default:
                        Log.d("UI", "Error fetching routine!");
                        break;
                }

            });
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
