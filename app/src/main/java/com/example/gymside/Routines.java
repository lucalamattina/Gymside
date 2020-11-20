package com.example.gymside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.gymside.api.model.Routine;
import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.ui.MainActivity;
import com.example.gymside.ui.RoutinesRVA;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Routines extends AppCompatActivity {
    private RoutineRepository api;
    public List<Routine> routines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApplication.getRoutineRepository();
        setContentView(R.layout.activity_routines);

        //get the spinner from the xml.
        //Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
       // String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);

        //Initialize And Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.routines);

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
                PopupMenu popup = new PopupMenu(Routines.this, profileButton);
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
        Log.d("UI","previo a entrar: " + Integer.toString(routines.size()));
        api.getRoutines().observeForever(r -> {
            switch (r.getStatus()) {
                case SUCCESS:
                    Log.d("UI", "Success");
                    assert r.getData() != null;
                    this.routines.addAll(r.getData().getResults());
                    RecyclerView recyclerView = findViewById(R.id.recycle_view);
                    RoutinesRVA adapter = new RoutinesRVA(this, this.routines);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    Log.d("UI", "adentro de la api: "+Integer.toString(routines.size()));
                    break;
                default:
                    Log.d("UI", "Error fetching routines!");
                    break;
            }
        });

//        Button detailsButton = (Button) findViewById(R.id.details_button);

//        api.getRoutines(new Callback<Result<List<Routine>>>() {
//            @Override
//            public void onResponse(Call<Result<List<Routine>>> call, Response<Result<List<Routine>>> response) {
//                if(response.isSuccessful()) {
//                    Result<List<Routine>> result = response.body();
//                    if (result != null) {
//                        List<Routine> routines = result.getResult();
//                        for (Routine routine : routines) {
//                            mRoutinesIds.add(routine.getId());
//                            mRoutinesNames.add(routine.getName());
//                            mRoutinesImages.add(R.drawable.routine);
//                            adapter.notifyItemInserted(routines.size() - 1);
//                        }
//                    }
//                }
//                return;
//            }
//        });

//        detailsButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), RoutineDetails.class));
//                overridePendingTransition(0,0);
//            }
//        });
    }
}