package com.example.gymside;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.api.model.User;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView showProfileName = findViewById(R.id.showProfileName);
        TextView showFullname = findViewById(R.id.showFullName);
        TextView showProfileEmail = findViewById(R.id.showEmailProfile);

        MyApplication app = (MyApplication) getApplication();
        app.getUserRepository().getCurrentUser().observe(this,r -> {
            switch (r.getStatus()) {
                case SUCCESS:
                    Log.d("UI", "Success");
                    assert r.getData() != null;
                    this.user = r.getData();
                    showFullname.setText(user.getFullName());
                    showProfileName.setText(user.getUsername());
                    showProfileEmail.setText(user.getEmail());
                    break;
                default:
                    defaultResourceHandler(r);
                    break;
            }
        });

        ImageButton buttonModify = findViewById(R.id.imageView2);

        buttonModify.setOnClickListener(v->{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Modify your username");
            EditText username = (EditText) findViewById(R.id.username);

            builder.setPositiveButton("Accept", null);
            builder.setNegativeButton("Cancel", null);
            AlertDialog dialog = builder.create();
            dialog.show();
//            String name = editUsername.getText().toString();
//            String pass = editPassword.getText().toString();
//            String email = editEmail.getText().toString();
//            Credentials credentials = new Credentials(name, pass, "0", email, 0, "male");
//            MyApplication app = ((MyApplication)getApplication());
//            app.getUserRepository().createUser(credentials).observe(this, r -> {
//                switch (r.getStatus()) {
//                    case SUCCESS:
//                        Log.d("UI", "Success");
//                        startActivity(new Intent(getApplicationContext(), VerifyAccount.class));
//                        overridePendingTransition(0,0);
//                        //int count = r.getData().getResults().size();
//                        //String message = getResources().getQuantityString(R.plurals.found, count, count);
//                        //binding.result.setText(message);
//                        break;
//                    default:
//                        defaultResourceHandler(r);
//                        break;
//                }
//            });
        });

        Button deleteUserButton = findViewById(R.id.deleteUserButton);

        deleteUserButton.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Estás seguro que quieres borrar tu perfil?");
            EditText username = (EditText) findViewById(R.id.username);

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MyApplication app = (MyApplication) getApplication();
                    app.getUserRepository().deleteUser().observeForever(r -> {
                        switch (r.getStatus()) {
                            case SUCCESS:
                                Log.d("UI", "Success");
                                AppPreferences preferences = new AppPreferences(app);
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                overridePendingTransition(0,0);
                                break;
                            default:
                                defaultResourceHandler(r);
                                break;
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

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
                    default:
                        return false;
                }
                //return false;
            }
        });



        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Profile.this, profileButton);
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



        changeMenuItemCheckedStateColor(bottomNavigationView, "#FFFFFF", "#FFFFFF");

    }

    private void changeMenuItemCheckedStateColor(BottomNavigationView bottomNavigationView, String checkedColorHex, String uncheckedColorHex) {
        int checkedColor = Color.parseColor(checkedColorHex);
        int uncheckedColor = Color.parseColor(uncheckedColorHex);

        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] {android.R.attr.state_checked}, // checked

        };

        int[] colors = new int[] {
                uncheckedColor,
                checkedColor
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        bottomNavigationView.setItemTextColor(colorStateList);
        bottomNavigationView.setItemIconTintList(colorStateList);

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
