package com.example.fastfood.DAO;

import android.app.Activity;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.fastfood.Adapter.AdapterGridView;
import com.example.fastfood.Model.Table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDao {
    FirebaseFirestore db;
    CollectionReference Table;
    Activity activity;

    public TableDao(Activity activity) {
        db = FirebaseFirestore.getInstance();
        Table = db.collection("Table");
        this.activity = activity;
    }

    public void addTable(Table table) {
        Map<String, Object> tab = new HashMap<>();
        tab.put("name", table.getName());
        tab.put("dachon", table.isDachon());

        Table.add(tab)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }

    public void chonBan(Table table) {
        Map<String, Object> tab = new HashMap<>();
        tab.put("name", table.getName());
        tab.put("dachon", true);
        Table.document(table.getId()).set(tab).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });
    }

    public void bochonBan(Table table) {
        Map<String, Object> tab = new HashMap<>();
        tab.put("name", table.getName());
        tab.put("dachon", false);
        Table.document(table.getId()).set(tab).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });
    }

    public void getBan(GridView gridView) {
        Table.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Table> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Table table = new Table();
                                table.setId(document.getId());
                                table.setName(document.getData().get("name").toString());
                                table.setDachon(document.getData().get("dachon").equals(true));
                                list.add(table);
                            }
                            Collections.sort(list, new Comparator<Table>() {
                                @Override
                                public int compare(Table o1, Table o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                            AdapterGridView adapterGridView = new AdapterGridView(activity, list);
                            gridView.setAdapter(adapterGridView);
                        } else {
                        }
                    }
                });
    }
}
