package com.example.fastfood.DAO;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fastfood.Adapter.AdapterFood;
import com.example.fastfood.Model.Food;
import com.example.fastfood.Model.Image;
import com.example.fastfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDao {
    FirebaseFirestore db;
    CollectionReference Food;
    Activity activity;

    public FoodDao(Activity activity) {
        db = FirebaseFirestore.getInstance();
        Food = db.collection("Food");
        this.activity = activity;
    }

    public void addFood(Food food, ImageView img) {
        Map<String, Object> fod = new HashMap<>();
        fod.put("name", food.getName());
        fod.put("price", food.getPrice());
        fod.put("status", food.getStatus());
        fod.put("luotban", food.getLuotban());
        Food.add(fod)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ImageDao imageDao = new ImageDao(activity);
                        imageDao.addImage(new Image(documentReference.getId()), img);
                        Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getFood(ListView listView) {
        Food.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Food> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Food food = new Food();
                                food.setId(document.getId());
                                food.setName(document.getData().get("name").toString());
                                food.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                                food.setStatus(Integer.parseInt(document.getData().get("status").toString()));
                                food.setLuotban(Integer.parseInt(document.getData().get("luotban").toString()));
                                list.add(food);
                            }
                            Collections.sort(list, new Comparator<Food>() {
                                @Override
                                public int compare(Food o1, Food o2) {
                                    if (o1.getLuotban() < o2.getLuotban()) {
                                        return 1;
                                    }
                                    return -1;
                                }
                            });
                            AdapterFood adapterFood = new AdapterFood(activity, R.layout.item_lv_food, list);
                            listView.setAdapter(adapterFood);
                        } else {
                        }
                    }
                });
    }


    public void updateFood(Food food) {
        Map<String, Object> fod = new HashMap<>();
        fod.put("name", food.getName());
        fod.put("price", food.getPrice());
        fod.put("status", food.getStatus());
        fod.put("luotban", food.getLuotban());
        Food.document(food.getId()).set(fod).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }

    public void deleteFood(String idfood) {
        db.collection("BillFood").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                    if (x.getData().get("idFood").toString().equals(idfood)) {
                        Toast.makeText(activity, "Đồ ăn này đã có lịch sử mua. Chuyển về trạng thái ngưng kinh doanh", Toast.LENGTH_SHORT).show();
                        Food.document(idfood).update("status", 3).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "Cập nhật trang thái đồ ăn thành công");
                            }
                        });
                        return;
                    }
                }
                Food.document(idfood)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "Đã xóa đồ ăn", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void getNameFood(String idFood, TextView textView) {
        Food.document(idFood).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getData() == null) {
                    return;
                }
                textView.setText("" + documentSnapshot.getData().get("name").toString());
            }
        });
    }
}

