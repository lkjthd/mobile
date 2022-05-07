package com.auto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;

import java.util.List;

public class ListActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView listText = findViewById(R.id.insuranceList);

        StringBuilder list = new StringBuilder();
        for (InsuranceItem ins : MainActivity.DAO.getRecentInsurances())
            list.append(ins.getName()).append(", kocsi tipusa: ").append(ins.getCarType()).append("\n");
        InsuranceItem leastRecentIns = MainActivity.DAO.getLeastRecentInsurance();
        if (leastRecentIns != null)
            list.append(leastRecentIns.getName()).append(", kocsi tipusa: ").append(leastRecentIns.getCarType());
        listText.setText(list.length() == 0 ? "Nem talalhato biztositas!" : list);
    }

    public void register(View view) {
        Intent intent = new Intent(this, InsuranceRegisterActivity.class);
        startActivity(intent);
    }
}