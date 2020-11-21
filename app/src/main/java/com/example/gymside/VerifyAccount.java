package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityRegisterBinding;
import com.example.gymside.databinding.ActivityVerifyBinding;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VerifyAccount extends AppCompatActivity {

    ActivityVerifyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_verify);

        //Initialize And Assign Variable

        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(v->{
            finish();
        });

        EditText editEmail, editCode;
        editEmail = (EditText) findViewById(R.id.emailRegister);
        editCode = (EditText) findViewById(R.id.codeRegister);

        Button verifyButton = findViewById(R.id.buttonVerify);

        verifyButton.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String code = editCode.getText().toString();
            Credentials credentials = new Credentials(email, code, "verify");
            MyApplication app = ((MyApplication)getApplication());
            app.getUserRepository().verifyUser(credentials).observe(this, r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        //int count = r.getData().getResults().size();
                        //String message = getResources().getQuantityString(R.plurals.found, count, count);
                        //binding.result.setText(message);
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
                TextView textError = (TextView) findViewById(R.id.showErrorTextv);
                if(resource.getError() != null){
                    textError.setText(R.string.invalidData);
                }
                Error error = resource.getError();
                //String message = getString(R.string.error, error.getDescription(), error.getCode());
                Log.d("UI", "Error");
                //binding.result.setText(message);
                break;
        }
    }
}
