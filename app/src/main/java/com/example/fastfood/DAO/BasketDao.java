package com.example.fastfood.DAO;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fastfood.Model.Baskets;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BasketDao {
    FirebaseFirestore db;
    CollectionReference BasketCollection;
    Activity activity;

    public BasketDao(Activity activity) {
        db = FirebaseFirestore.getInstance();
        BasketCollection = db.collection("Basket");
        this.activity = activity;
    }

    public void addBasket(Baskets baskets, SharedPreferences.Editor editor) {
        Map<String, Object> Basket = new HashMap<>();
        Basket.put("table", baskets.getTable());
        Basket.put("date", baskets.getDate());
        BasketCollection.add(Basket)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "Thêm thành công giỏ hàng: " + documentReference.getId());
                        editor.putString("IDBASKET", documentReference.getId());
                        editor.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("TAG", "Lỗi rồi");
                        Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteBasket(String id) {
        BasketCollection.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Tag", "Xóa basket ok");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Tag", "Xóa basket lỗi" + e);
            }
        });
    }
}
