package com.auto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class MainActivity extends FragmentActivity {
    public static final String PREF_KEY = Objects.requireNonNull(MainActivity.class.getPackage()).toString();
    public static final InsuranceDb DAO = new InsuranceDb();
    private static final String OAUTH = "1065583486537-027p0jd8dj5eo2bs3mvim59ol20ubkum.apps.googleusercontent.com";
    private FirebaseAuth firebase;
    private EditText emailInput, passwordInput;
    private SharedPreferences prefs;
    private GoogleSignInClient googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebase = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        prefs = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        googleLogin = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(OAUTH).requestEmail().build());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this,
                        "Helytelen a Google belepes! Hiba uzenet: " + e, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebase.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                        startApplication();
                    else
                        Toast.makeText(MainActivity.this,
                                "Helytelen a Google belepes! Hiba uzenet: " + task.getException(), Toast.LENGTH_LONG).show();
                });
    }

    public void login(View view) {
        String email = emailInput.getText().toString(),
                password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    "Ures a(z) e-mail/jelszo!", Toast.LENGTH_LONG).show();
            return;
        }

        firebase.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful())
                startApplication();
            else
                Toast.makeText(MainActivity.this,
                        "Helytelen a(z) e-mail/jelszo!", Toast.LENGTH_LONG).show();
        });
    }

    public void googleLogin(View view) {
        Intent signInIntent = googleLogin.getSignInIntent();
        //noinspection deprecation
        startActivityForResult(signInIntent, 123);
    }

    private void startApplication() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", emailInput.getText().toString());
        editor.apply();
    }
}