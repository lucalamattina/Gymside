package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymside.api.model.Credentials;
import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityRegisterBinding;
import com.example.gymside.repository.Resource;
import com.example.gymside.ui.MainActivity;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_register);

        //Initialize And Assign Variable

        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Login.class));
            overridePendingTransition(0, 0);
        });

        Button verifyView = findViewById(R.id.verifyView);
        verifyView.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), VerifyAccount.class));
            overridePendingTransition(0, 0);
        });

        EditText editUsername, editPassword, editEmail;
        editUsername  = (EditText) findViewById(R.id.usernameRegister);
        editPassword = (EditText) findViewById(R.id.passwordRegister);
        editEmail = (EditText) findViewById(R.id.emailRegister);

        Button buttonCreateAccount = findViewById(R.id.createAccount);

        buttonCreateAccount.setOnClickListener(v->{
            String name = editUsername.getText().toString();
            String pass = editPassword.getText().toString();
            String email = editEmail.getText().toString();
            Credentials credentials = new Credentials(name, pass, "0", email, 0, "male");
            MyApplication app = ((MyApplication)getApplication());
            app.getUserRepository().createUser(credentials).observe(this, r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        Log.d("UI", "Success");
                        startActivity(new Intent(getApplicationContext(), VerifyAccount.class));
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
                Error error = resource.getError();
                //String message = getString(R.string.error, error.getDescription(), error.getCode());
                Log.d("UI", "Error");
                //binding.result.setText(message);
                break;
        }
    }

}
