package com.example.fastfood.DAO;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fastfood.Adapter.AdapterBaskets;
import com.example.fastfood.Adapter.AdapterBillDvF;
import com.example.fastfood.Model.BillDrink;
import com.example.fastfood.Model.BillFood;
import com.example.fastfood.Model.DvsF;
import com.example.fastfood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillFvsDDao {
    FirebaseFirestore db;
    CollectionReference BillFoodDao;
    CollectionReference BillDrinkDao;
    Activity activity;

    public BillFvsDDao(Activity activity) {
        this.activity = activity;
        db = FirebaseFirestore.getInstance();
        BillFoodDao = db.collection("BillFood");
        BillDrinkDao = db.collection("BillDrink");
    }

    public void addbillFood(BillFood billFood) {
        Map<String, Object> bill = new HashMap<>();
        bill.put("idBill", billFood.getIdBill());
        bill.put("idFood", billFood.getIdFood());
        bill.put("figure", billFood.getFigure());
        bill.put("price", billFood.getPrice());
        bill.put("check", billFood.getCheck());
        BillFoodDao.whereEqualTo("idBill", billFood.getIdBill()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                    if (x.getData().get("idFood").toString().equals(billFood.getIdFood()) && x.getData().get("check").toString().equals("false")) {
                        BillFoodDao.document(x.getId()).update("figure", Integer.parseInt(x.getData().get("figure").toString()) + 1).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Thêm vào giỏ lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }
                BillFoodDao.add(bill)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(activity, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(activity, "Thêm vào giỏ lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void addbillDrink(BillDrink billDrink) {
        Map<String, Object> bill = new HashMap<>();
        bill.put("idBill", billDrink.getIdBill());
        bill.put("idDrink", billDrink.getIdDrink());
        bill.put("figure", billDrink.getFigure());
        bill.put("price", billDrink.getPrice());
        bill.put("check", billDrink.getCheck());
        BillDrinkDao.whereEqualTo("idBill", billDrink.getIdBill()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                    if (x.getData() == null) {
                        return;
                    }
                    if (x.getData().get("idDrink").toString().equals(billDrink.getIdDrink()) && x.getData().get("check").toString().equals("false")) {
                        db.collection("Drink").document(x.getData().get("idDrink").toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.getData() == null) {
                                    return;
                                }
                                if (Integer.parseInt(x.getData().get("figure").toString()) == Integer.parseInt(documentSnapshot.getData().get("Figure").toString())) {
                                    Toast.makeText(activity, "Đồ uống này hiện không thể đặt nhiều hơn. Vui lòng chọn đồ uống khác!", Toast.LENGTH_SHORT).show();
                                } else {
                                    BillDrinkDao.document(x.getId()).update("figure", Integer.parseInt(x.getData().get("figure").toString()) + 1).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(activity, "Thêm vào giỏ lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(activity, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });

                        return;
                    }
                }
                BillDrinkDao.add(bill)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(activity, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(activity, "Thêm vào giỏ lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void getBillBasket(ListView listView, String id) {
        BillFoodDao.whereEqualTo("idBill", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocuxentSnapshots) {
                List<DvsF> list = new ArrayList<>();
                for (QueryDocumentSnapshot x : queryDocuxentSnapshots) {
                    if (x.getData().get("check").toString().equals("false")) {
                        DvsF dvsF = new DvsF(x.getId(), x.getData().get("idBill").toString(), x.getData().get("idFood").toString(), Integer.parseInt(x.getData().get("figure").toString()), Integer.parseInt(x.getData().get("price").toString()), false, true);
                        int temp = 0;
                        for (DvsF obj : list) {
                            if (dvsF.idDrinkorFood().equals(obj.idDrinkorFood())) {
                                temp++;
                                obj.setFigure(obj.getFigure() + dvsF.getFigure());
                            }
                        }
                        if (temp == 0) {
                            list.add(dvsF);
                        }
                    }
                }
                BillDrinkDao.whereEqualTo("idBill", id).get().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshot) {
                        for (QueryDocumentSnapshot x : queryDocumentSnapshot) {
                            if (x.getData().get("check").toString().equals("false")) {
                                DvsF dvsF = new DvsF(x.getId(), x.getData().get("idBill").toString(), x.getData().get("idDrink").toString(), Integer.parseInt(x.getData().get("figure").toString()), Integer.parseInt(x.getData().get("price").toString()), false, false);
                                int temp = 0;
                                for (DvsF obj : list) {
                                    if (dvsF.idDrinkorFood().equals(obj.idDrinkorFood())) {
                                        temp++;
                                        obj.setFigure(obj.getFigure() + dvsF.getFigure());
                                    }
                                }
                                if (temp == 0) {
                                    list.add(dvsF);
                                }
                            }
                        }
                        AdapterBaskets adapterBaskets = new AdapterBaskets(activity, R.layout.item_lv_baskets, list);
                        listView.setAdapter(adapterBaskets);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void getHoaDon(ListView listView, String id, TextView tongtien) {
        BillFoodDao.whereEqualTo("idBill", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocuxentSnapshots) {
                List<DvsF> list = new ArrayList<>();
                for (QueryDocumentSnapshot x : queryDocuxentSnapshots) {
                    if (x.getData().get("check").toString().equals("true")) {
                        DvsF dvsF = new DvsF(x.getId(), x.getData().get("idBill").toString(), x.getData().get("idFood").toString(), Integer.parseInt(x.getData().get("figure").toString()), Integer.parseInt(x.getData().get("price").toString()), true, true);
                        int temp = 0;
                        for (DvsF obj : list) {
                            if (dvsF.idDrinkorFood().equals(obj.idDrinkorFood())) {
                                temp++;
                                obj.setFigure(obj.getFigure() + dvsF.getFigure());
                            }
                        }
                        if (temp == 0) {
                            list.add(dvsF);
                        }
                    }
                }
                BillDrinkDao.whereEqualTo("idBill", id).get().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshot) {
                        for (QueryDocumentSnapshot x : queryDocumentSnapshot) {
                            if (x.getData().get("check").toString().equals("true")) {
                                DvsF dvsF = new DvsF(x.getId(), x.getData().get("idBill").toString(), x.getData().get("idDrink").toString(), Integer.parseInt(x.getData().get("figure").toString()), Integer.parseInt(x.getData().get("price").toString()), true, false);
                                int temp = 0;
                                for (DvsF obj : list) {
                                    if (dvsF.idDrinkorFood().equals(obj.idDrinkorFood())) {
                                        temp++;
                                        obj.setFigure(obj.getFigure() + dvsF.getFigure());
                                    }
                                }
                                if (temp == 0) {
                                    list.add(dvsF);
                                }
                            }
                        }


                        AdapterBaskets adapterBaskets = new AdapterBaskets(activity, R.layout.item_lv_baskets, list);

                        listView.setAdapter(adapterBaskets);
                        int tongthanhtoan = 0;
                        for (int i = 0; i < list.size(); i++) {
                            tongthanhtoan = tongthanhtoan + list.get(i).getFigure() * list.get(i).getPrice();
                        }
                        tongtien.setText("" + tongthanhtoan);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void getbill(ListView listView, String id) {
        BillFoodDao.whereEqualTo("idBill", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocuxentSnapshots) {
                List<DvsF> list = new ArrayList<>();
                for (QueryDocumentSnapshot x : queryDocuxentSnapshots) {
                    if (x.getData().get("check").toString().equals("true")) {
                        DvsF dvsF = new DvsF(x.getId(), x.getData().get("idBill").toString(), x.getData().get("idFood").toString(), Integer.parseInt(x.getData().get("figure").toString()), Integer.parseInt(x.getData().get("price").toString()), true, true);
                        int temp = 0;
                        for (DvsF obj : list) {
                            if (dvsF.idDrinkorFood().equals(obj.idDrinkorFood())) {
                                temp++;
                                obj.setFigure(obj.getFigure() + dvsF.getFigure());
                            }
                        }
                        if (temp == 0) {
                            list.add(dvsF);
                        }
                    }
                }
                BillDrinkDao.whereEqualTo("idBill", id).get().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshot) {
                        for (QueryDocumentSnapshot x : queryDocumentSnapshot) {
                            if (x.getData().get("check").toString().equals("true")) {
                                DvsF dvsF = new DvsF(x.getId(), x.getData().get("idBill").toString(), x.getData().get("idDrink").toString(), Integer.parseInt(x.getData().get("figure").toString()), Integer.parseInt(x.getData().get("price").toString()), true, false);
                                int temp = 0;
                                for (DvsF obj : list) {
                                    if (dvsF.idDrinkorFood().equals(obj.idDrinkorFood())) {
                                        temp++;
                                        obj.setFigure(obj.getFigure() + dvsF.getFigure());
                                    }
                                }
                                if (temp == 0) {
                                    list.add(dvsF);
                                }
                            }
                        }
                        AdapterBillDvF adapter = new AdapterBillDvF(activity, 1, list);
                        listView.setAdapter(adapter);
                        listView.setLayoutParams(new LinearLayout.LayoutParams(1000, list.size() * 60));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void xacnhanorder(String id) {
        BillFoodDao.whereEqualTo("idBill", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                    BillFoodDao.document(x.getId()).update("check", true).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                    db.collection("Food").document(x.getData().get("idFood").toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.getData() == null) {
                                return;
                            }
                            db.collection("Food").document(x.getData().get("idFood").toString()).update("luotban", Integer.parseInt(documentSnapshot.getData().get("luotban").toString()) + Integer.parseInt(x.getData().get("figure").toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.i("TAG", "Cập nhật lượt bán đồ ăn thành công");
                                }
                            });
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        BillDrinkDao.whereEqualTo("idBill", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                    BillDrinkDao.document(x.getId()).update("check", true).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                    db.collection("Drink").document(x.getData().get("idDrink").toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.getData() == null) {
                                return;
                            }
                            db.collection("Drink").document(x.getData().get("idDrink").toString()).update("luotban", Integer.parseInt(documentSnapshot.getData().get("luotban").toString()) + Integer.parseInt(x.getData().get("figure").toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.i("TAG", "Cập nhật lượt bán đồ uống thành công");
                                }
                            });
                            db.collection("Drink").document(x.getData().get("idDrink").toString()).update("Figure", Integer.parseInt(documentSnapshot.getData().get("Figure").toString()) - Integer.parseInt(x.getData().get("figure").toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.i("TAG", "Cập nhật số lượng đồ uống thành công");
                                }
                            });
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void deletebill(String id, boolean FoodorDrink) {
        if (FoodorDrink) {
            BillFoodDao.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(activity, "Đã xóa món này", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity, "Xóa order lỗi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            BillDrinkDao.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(activity, "Đã xóa món này", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity, "Xóa order lỗi", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updatebill(String id, boolean FoodorDrink, int soluong) {
        if (FoodorDrink) {
            BillFoodDao.document(id).update("figure", soluong).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.i("TAG", "Thay đổi số lượng đồ ăn thành công: ");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("TAG", "Thay đổi số lượng đồ ăn thành công: ");
                }
            });
        } else {
            BillDrinkDao.document(id).get().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getData() == null) {
                        return;
                    }
                    db.collection("Drink").document(documentSnapshot.getData().get("idDrink").toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.getData() == null) {
                                return;
                            }
                            if (soluong > Integer.parseInt(documentSnapshot.getData().get("Figure").toString())) {
                                Toast.makeText(activity, "Đồ uống này hiện không thể đặt nhiều hơn. Vui lòng chọn đồ uống khác!", Toast.LENGTH_SHORT).show();
                            } else {
                                BillDrinkDao.document(id).update("figure", soluong).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("TAG", "Thay đổi số lượng đồ uống thành công: ");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("TAG", "Thay đổi số lượng đồ uống thành công: ");
                                    }
                                });
                            }
                        }
                    });
                }
            });


        }
    }
}
