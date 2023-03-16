package com.example.fastfood.DAO;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood.Adapter.AdapterDrink;
import com.example.fastfood.Adapter.AdapterTop;
import com.example.fastfood.Model.Drink;
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

public class DrinkDao {
    FirebaseFirestore db;
    CollectionReference DrinkCollection;
    CollectionReference Food;

    Activity activity;

    public DrinkDao(Activity activity) {
        db = FirebaseFirestore.getInstance();
        DrinkCollection = db.collection("Drink");

        Food = db.collection("Food");

        this.activity = activity;
    }

    public void addDrink(Drink food, ImageView img) {
        Map<String, Object> fod = new HashMap<>();
        fod.put("name", food.getName());
        fod.put("price", food.getPrice());
        fod.put("Figure", food.getFigure());
        fod.put("status", food.getStatus());
        fod.put("luotban", food.getLuotban());
        DrinkCollection.add(fod)
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

    public void getDrink(ListView listView) {
        DrinkCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Drink> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Drink drink = new Drink();
                                drink.setId(document.getId());
                                drink.setName(document.getData().get("name").toString());
                                drink.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                                drink.setFigure(Integer.parseInt(document.getData().get("Figure").toString()));
                                drink.setStatus(Integer.parseInt(document.getData().get("status").toString()));
                                drink.setLuotban(Integer.parseInt(document.getData().get("luotban").toString()));
                                list.add(drink);
                                Log.d("TAG", "Food " + list.size() + "" + document.getData());
                            }
                            Collections.sort(list, new Comparator<Drink>() {
                                @Override
                                public int compare(Drink o1, Drink o2) {
                                    if (o1.getLuotban() < o2.getLuotban()) {
                                        return 1;
                                    }
                                    return -1;
                                }
                            });
                            AdapterDrink adapterDrink = new AdapterDrink(activity, R.layout.item_lv_food, list);
                            listView.setAdapter(adapterDrink);
                        } else {
                        }
                    }
                });
    }


    public void getNameDrink(String idDrink, TextView textView) {
        DrinkCollection.document(idDrink).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getData() == null) {
                    return;
                }
                textView.setText(documentSnapshot.getData().get("name").toString());
            }
        });
    }

    public void updateDrink(Drink drink) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("name", drink.getName());
        obj.put("price", drink.getPrice());
        obj.put("Figure", drink.getFigure());
        obj.put("status", drink.getStatus());
        obj.put("luotban", drink.getLuotban());
        DrinkCollection.document(drink.getId()).set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void deleteDrink(String idDrink) {
        db.collection("BillDrink").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                    if (x.getData().get("idDrink").toString().equals(idDrink)) {
                        Toast.makeText(activity, "Đồ uống này đã có lịch sử mua. Chuyển về trạng thái ngưng kinh doanh", Toast.LENGTH_SHORT).show();
                        DrinkCollection.document(idDrink).update("status", 3).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "Cập nhật trang thái đồ uống thành công");
                            }
                        });
                        return;
                    }
                }
                DrinkCollection.document(idDrink)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "Đã xóa đồ uống", Toast.LENGTH_SHORT).show();
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

    public void getTop(ListView listView, int top) {
        DrinkCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Drink drink;
                            List<Drink> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                drink = new Drink();
                                drink.setId(document.getId());
                                drink.setName(document.getData().get("name").toString());
                                drink.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                                drink.setFigure(Integer.parseInt(document.getData().get("Figure").toString()));
                                drink.setStatus(Integer.parseInt(document.getData().get("status").toString()));
                                drink.setLuotban(Integer.parseInt(document.getData().get("luotban").toString()));

                                list.add(drink);
                            }
                            db.collection("Food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Drink drink = new Drink();
                                            drink.setId(document.getId());
                                            drink.setName(document.getData().get("name").toString());
                                            drink.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                                            drink.setStatus(Integer.parseInt(document.getData().get("status").toString()));
                                            drink.setLuotban(Integer.parseInt(document.getData().get("luotban").toString()));
                                            drink.setFigure(-1);
                                            list.add(drink);
                                        }
                                        Collections.sort(list, new Comparator<Drink>() {
                                            @Override
                                            public int compare(Drink drink, Drink t1) {
                                                return t1.getLuotban() - drink.getLuotban();
                                            }
                                        });
                                        if (top == 0) {
                                            AdapterDrink adapterDrink = new AdapterDrink(activity, R.layout.item_lv_food, list);
                                            listView.setAdapter(adapterDrink);
                                        } else {
                                            AdapterDrink adapterDrink = new AdapterDrink(activity, R.layout.item_lv_food, list.subList(0, top));
                                            listView.setAdapter(adapterDrink);
                                        }
                                    }

                                }
                            });
                        } else {
                        }
                    }
                });
    }

    public void getTopHome(RecyclerView recyclerView, int top) {
        DrinkCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Drink drink;
                            List<Drink> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                drink = new Drink();
                                drink.setId(document.getId());
                                drink.setName(document.getData().get("name").toString());
                                drink.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                                drink.setFigure(Integer.parseInt(document.getData().get("Figure").toString()));
                                drink.setStatus(Integer.parseInt(document.getData().get("status").toString()));
                                drink.setLuotban(Integer.parseInt(document.getData().get("luotban").toString()));

                                list.add(drink);
                            }
                            db.collection("Food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Drink drink = new Drink();
                                            drink.setId(document.getId());
                                            drink.setName(document.getData().get("name").toString());
                                            drink.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                                            drink.setStatus(Integer.parseInt(document.getData().get("status").toString()));
                                            drink.setLuotban(Integer.parseInt(document.getData().get("luotban").toString()));
                                            drink.setFigure(-1);
                                            list.add(drink);
                                        }
                                        Collections.sort(list, new Comparator<Drink>() {
                                            @Override
                                            public int compare(Drink drink, Drink t1) {
                                                return t1.getLuotban() - drink.getLuotban();
                                            }
                                        });
                                        if (top == 0) {
                                            AdapterTop adapterTop = new AdapterTop();
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
                                            recyclerView.setLayoutManager(linearLayoutManager);
                                            adapterTop.setData(activity, list);
                                            recyclerView.setAdapter(adapterTop);
                                        } else {
                                            AdapterTop adapterTop = new AdapterTop();
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
                                            recyclerView.setLayoutManager(linearLayoutManager);
                                            adapterTop.setData(activity, list.subList(0, top));
                                            recyclerView.setAdapter(adapterTop);
                                        }
                                    }

                                }
                            });
                        } else {
                        }
                    }
                });
    }
}

