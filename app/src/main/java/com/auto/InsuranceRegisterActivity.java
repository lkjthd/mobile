package com.auto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import java.util.Date;

public class InsuranceRegisterActivity extends FragmentActivity {
    private Notifications notifications;
    private EditText firstNameInput, lastNameInput, carTypeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        notifications = new Notifications(this);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        carTypeInput = findViewById(R.id.carTypeInput);
    }

    public void register(View view) {
        notifications.sendNotif("Sikeres biztositas-regisztracio");

        InsuranceItem ins = new InsuranceItem(firstNameInput.getText().toString(),
                lastNameInput.getText().toString(), carTypeInput.getText().toString(),
                new Date().getTime());

        if (MainActivity.DAO.getInsuranceCount() > 20)
            MainActivity.DAO.delete();

        MainActivity.DAO.create(ins);

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void list(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}