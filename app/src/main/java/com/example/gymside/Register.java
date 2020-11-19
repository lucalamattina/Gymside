package com.example.gymside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymside.api.model.Error;
import com.example.gymside.databinding.ActivityRegisterBinding;
import com.example.gymside.repository.Resource;

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

        Button buttonCreateAccount = findViewById(R.id.createAccount);
        buttonCreateAccount.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), VerifyAccount.class));
            overridePendingTransition(0, 0);
        });

        /*binding.buttonRegister.setOnClickListener(v->{
            Credentials credentials = new Credentials("usuario2", "contra", "nombrecompleto", "hola@gmail.com", "masculino");
            MyApplication app = (MyApplication) getApplication();
            app.getUserRepository().createUser(credentials).observe(this,r -> {
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
*/

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
