package com.auto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends FragmentActivity {
    private EditText emailInput, passwordInput, passwordConfirmInput;
    private FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebase = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput);

        SharedPreferences preferences = getSharedPreferences(MainActivity.PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");

        emailInput.setText(email);
    }

    public void register(View view) {
        String email = emailInput.getText().toString(),
                password = passwordInput.getText().toString();

        if (!password.equals(passwordConfirmInput.getText().toString())) {
            Toast.makeText(RegisterActivity.this,
                    "A jelszavak nem egyeznek!", Toast.LENGTH_LONG).show();
            return;
        }

        firebase.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startApplication();
                Toast.makeText(RegisterActivity.this,
                        "Sikeres regisztracio!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(RegisterActivity.this,
                        "Hiba tortent:" + task.getException(), Toast.LENGTH_LONG).show();
        });
    }

    public void cancel(View view) {
        finish();
    }

    private void startApplication() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
