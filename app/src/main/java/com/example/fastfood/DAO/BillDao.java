package com.example.fastfood.DAO;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fastfood.Adapter.AdapterBill;
import com.example.fastfood.Model.Bill;
import com.example.fastfood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BillDao {
    FirebaseFirestore db;
    CollectionReference BillReference;
    CollectionReference BasketReference;
    Activity activity;

    public BillDao(Activity activity) {
        db = FirebaseFirestore.getInstance();
        BillReference = db.collection("Bill");
        BasketReference = db.collection("Basket");
        this.activity = activity;
    }

    public void addBill(int price, String idban) {
        BasketReference.document(idban).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> Bill = documentSnapshot.getData();
                Bill.put("price", price);
                Bill.put("thanhtoan", false);
                BillReference.document(documentSnapshot.getId()).set(Bill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });

    }

    public void updateBill(String idbill, int price, boolean thanhtoan) {
        BillReference.document(idbill).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> Bill = documentSnapshot.getData();
                if (documentSnapshot.getData() == null) {
                    return;
                }
                Bill.put("price", price);
                Bill.put("thanhtoan", thanhtoan);
                BillReference.document(documentSnapshot.getId()).set(Bill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });
    }

    public void doanhthu(Date ngaybdau, Date ngaykthuc, TextView tvdoanhthu) {
        BillReference.whereEqualTo("thanhtoan", true).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocuxentSnapshots) {
                List<Bill> list = new ArrayList<>();
                for (QueryDocumentSnapshot x : queryDocuxentSnapshots) {
                    Bill bill = new Bill();
                    bill.setId(x.getId());
                    bill.setDate(x.getDate("date"));
                    bill.setPrice(Integer.parseInt(x.getData().get("price").toString()));
                    bill.setTable(x.getData().get("table").toString());
                    bill.setThanhtoan(true);
                    list.add(bill);
                }
                int tongdoanhthu = 0;
                ngaykthuc.setDate(ngaykthuc.getDate() + 1);
                for (Bill x : list) {
                    if (x.getDate().compareTo(ngaybdau) >= 0 && x.getDate().compareTo(ngaykthuc) <= 0) {
                        tongdoanhthu = tongdoanhthu + x.getPrice();
                    }
                }
                tvdoanhthu.setText("Tổng doanh thu: " + tongdoanhthu + " $");
            }
        });
    }

    public void getBill(ListView listView) {
        BillReference.whereEqualTo("thanhtoan", true).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocuxentSnapshots) {
                List<Bill> list = new ArrayList<>();
                for (QueryDocumentSnapshot x : queryDocuxentSnapshots) {
                    Bill bill = new Bill();
                    bill.setId(x.getId());
                    bill.setDate(x.getDate("date"));
                    bill.setPrice(Integer.parseInt(x.getData().get("price").toString()));
                    bill.setTable(x.getData().get("table").toString());
                    bill.setThanhtoan(true);
                    list.add(bill);
                }
                Collections.sort(list, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill t1, Bill t2) {
                        return t2.getDate().compareTo(t1.getDate());
                    }
                });
                AdapterBill adapterBill = new AdapterBill(activity, R.layout.item_lv_bill, list);
                listView.setAdapter(adapterBill);
            }
        });
    }
}
