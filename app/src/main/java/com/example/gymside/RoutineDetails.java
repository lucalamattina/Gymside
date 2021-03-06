package com.example.gymside;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;
import com.example.gymside.api.model.Error;
import com.example.gymside.api.model.Exercise;
import com.example.gymside.api.model.PagedList;
import com.example.gymside.api.model.Routine;

import com.example.gymside.repository.CycleRepository;
import com.example.gymside.repository.ExerciseRepository;
import com.example.gymside.repository.Resource;
import com.example.gymside.repository.RoutineRepository;
import com.example.gymside.repository.UserRepository;
import com.example.gymside.ui.ExercisesRVA;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class RoutineDetails extends AppCompatActivity {


    Button rateButton;
    ToggleButton toggleButton;


    private RoutineRepository api;
    TextView name;
    TextView rating;
    TextView detail;
    TextView category;
    Button share;
    int routineId;
    int cycleId;
    String cycleName;
    private ExerciseRepository exerciseApi;
    private UserRepository userApi;
    private CycleRepository cycleApi;
    public List<Exercise> exercises = new ArrayList<>();
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApplication.getRoutineRepository();
        exerciseApi = MyApplication.getExerciseRepository();
        userApi = MyApplication.getUserRepository();
        cycleApi = MyApplication.getCycleRepository();
        setContentView(R.layout.activity_routines_details);
        name = findViewById(R.id.name);
        rating = findViewById(R.id.rating);
        detail = findViewById(R.id.body);
        category = findViewById(R.id.category);
        share = findViewById(R.id.share_button);
        Button start = findViewById(R.id.playButton);


        /*start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO agregar metodo para crear una execution y hacer las pantallas de ejecucion de rutina
            }
        });*/

        Bundle extras = getIntent().getExtras();
        if(extras.get("ROUTINE_NAME") != null) {
            name.setText(extras.get("ROUTINE_NAME").toString());
            rating.setText(extras.get("ROUTINE_RATING").toString());
            detail.setText(extras.get("ROUTINE_DETAIL").toString());
            category.setText(extras.get("ROUTINE_CATEGORY").toString());
            routineId = (Integer)extras.get("ROUTINE_ID");
            cycleApi.getCycles(routineId).observeForever( r->{
                switch (r.getStatus()){
                    case SUCCESS:
                        if(r.getData().getResults().size() != 0){
                            cycleId = r.getData().getResults().get(0).getId();
                            if(extras.get("ROUTINE_ID") != null) {
                                exerciseApi.getExercises(routineId, cycleId).observeForever(resp -> {
                                    switch (resp.getStatus()) {
                                        case SUCCESS:
                                            Log.d("UI", "Success");
                                            assert resp.getData() != null;
                                            if(resp.getData().getResults() != null && !resp.getData().getResults().isEmpty()) {
                                                category.setText(resp.getData().getResults().get(0).getName());
                                            }
                                            break;
                                        default:
                                            defaultResourceHandler(resp);
                                            break;
                                    }
                                });
                            }
                        }
                        break;
                    default:
                        defaultResourceHandler(r);
                        break;
                }
            });
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RoutineExecution.class);
                intent.putExtra("ROUTINE_ID", routineId);
                startActivity(intent, new Bundle());
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://gymside.com/routines/" + extras.get("ROUTINE_ID"));
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        name.setText(extras.get("ROUTINE_NAME").toString());
        rating.setText(extras.get("ROUTINE_RATING").toString());
        detail.setText(extras.get("ROUTINE_DETAIL").toString());
        category.setText(extras.get("ROUTINE_CATEGORY").toString());
        routineId = (Integer)extras.get("ROUTINE_ID");
        cycleApi.getCycles(routineId).observeForever( r->{
            switch (r.getStatus()){
                case SUCCESS:
                    if(r.getData().getResults().size() != 0){
                        cycleId = r.getData().getResults().get(0).getId();
                        exerciseApi.getExercises(routineId, cycleId).observeForever(resp->{
                            switch (resp.getStatus()) {
                                case SUCCESS:
                                    Log.d("UI", "Success");
                                    assert resp.getData() != null;
                                    this.exercises.addAll(resp.getData().getResults());
                                    RecyclerView recyclerView = findViewById(R.id.recycler_view2);
                                    ExercisesRVA adapter = new ExercisesRVA(this, this.exercises);
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                                    Log.d("UI", "adentro de la api: "+Integer.toString(exercises.size()));
                                    break;
                                default:
                                    defaultResourceHandler(resp);
                                    break;
                            }
                        });
                    }
                    break;
                default:
                    defaultResourceHandler(r);
                    break;
            }
        });

        rateButton = (Button) findViewById(R.id.rate);


        LiveData<Resource<PagedList<Routine>>> favs = this.userApi.getFavourites();
        LiveData<com.example.gymside.repository.Resource<com.example.gymside.api.model.Routine>> currRoutine = this.api.getRoutine((Integer)extras.get("ROUTINE_ID"));

        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);

        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorites));



        userApi.getFavourites().observeForever(r->{
            switch (r.getStatus()) {
                case SUCCESS:
                    Log.d("EntroSuccess", "EntroSuccess");
                    Boolean belongs = false;
                    assert r.getData() != null;
                    for (Routine i : r.getData().getResults()) {
                        if (i.getId().equals((Integer) extras.get("ROUTINE_ID"))) {
                            belongs = true;
                            toggleButton.setChecked(true);
                            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_red_full));

                        }
                    }
                    if (!belongs) {
                        toggleButton.setChecked(false);
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_red_empty));
                    }
                    break;
                default:
                    defaultResourceHandler(r);
                    break;
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_red_full));
                    userApi.postFavourite((Integer) extras.get("ROUTINE_ID")).observeForever(r->{

                    });
                    Log.d("UI","agregado paaaaaaaa");
                }
                else{
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_red_empty));
                    userApi.deleteFavourite((Integer) extras.get("ROUTINE_ID")).observeForever(r->{

                    });
                    Log.d("UI","removido paaaaaaa");
                }
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
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Login.class));
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

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        Intent intent = getIntent();
        text = intent.getStringExtra(Intent.EXTRA_TEXT);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.equals("r")) {
                    String textToPass = "r";

                    Intent intent = new Intent(getBaseContext(), Rate.class);
                    intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                    intent.putExtra("ROUTINE_RATING", extras.get("ROUTINE_RATING").toString());
                    intent.putExtra("ROUTINE_ID", extras.get("ROUTINE_ID").toString());

                    startActivityForResult(intent,1);

                    //startActivity(new Intent(RoutineDetails.this, Rate.class));
                }
                if(text.equals("f")){
                    String textToPass = "f";

                    Intent intent = new Intent(getBaseContext(), Rate.class);
                    intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                    intent.putExtra("ROUTINE_RATING", extras.get("ROUTINE_RATING").toString());
                    intent.putExtra("ROUTINE_ID", extras.get("ROUTINE_ID").toString());
                    startActivityForResult(intent,1);

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
                        if (item.getTitle().equals("Profile") || item.getTitle().equals("Perfil")) {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        if(item.getTitle().equals("Edit Profile") || item.getTitle().equals("Editar perfil")) {
                            startActivity(new Intent(getApplicationContext(), EditProfile.class));
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
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        handleIntent(appLinkIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, text.equals("r") ? Routines.class : Favourites.class));
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
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
