package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VerifyAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        //Initialize And Assign Variable

        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Register.class));
            overridePendingTransition(0, 0);
        });

        Button verifyButton = findViewById(R.id.buttonVerify);
        verifyButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Login.class));
            overridePendingTransition(0, 0);
        });
    }
}
