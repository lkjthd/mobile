package com.auto;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InsuranceDb {
    private final CollectionReference coll;
    private final String uid;

    public InsuranceDb() {
        this.coll = FirebaseFirestore.getInstance().collection("insurances");
        this.uid = FirebaseAuth.getInstance().getUid();
    }

    public void create(InsuranceItem ins) {
        coll.document(uid).set(ins);
    }

    public List<InsuranceItem> read() {
        List<InsuranceItem> insurances = new ArrayList<>();
        coll.get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot ref : task.getResult())
                insurances.add(ref.toObject(InsuranceItem.class));
        });
        return insurances;
    }

    public void update(String field, String value) {
        coll.document(uid).update(field, value);
    }

    public void delete() {
        coll.document(uid).delete();
    }

    public long getInsuranceCount() {
        return read().size();
    }

    public InsuranceItem getLeastRecentInsurance() {
        AtomicReference<InsuranceItem> insurance = new AtomicReference<>();
        coll.orderBy("dateSubmitted", Query.Direction.DESCENDING).limit(1).get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot ref : task.getResult())
                        insurance.set(ref.toObject(InsuranceItem.class));
                });

        return insurance.get();
    }

    public List<InsuranceItem> getRecentInsurances() {
        List<InsuranceItem> insurances = new ArrayList<>();
        coll.orderBy("dateSubmitted", Query.Direction.ASCENDING).limit(10).get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot ref : task.getResult())
                        insurances.add(ref.toObject(InsuranceItem.class));
                });
        return insurances;
    }
}
