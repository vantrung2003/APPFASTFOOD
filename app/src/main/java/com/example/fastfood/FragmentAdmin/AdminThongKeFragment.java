package com.example.fastfood.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fastfood.DAO.DrinkDao;
import com.example.fastfood.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminThongKeFragment extends Fragment {
    ListView lv;
    DrinkDao drinkDao;
    CollectionReference foodref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_thong_ke, container, false);
        lv = root.findViewById(R.id.thong_ke_lisview);
        drinkDao = new DrinkDao(getActivity());
        foodref = FirebaseFirestore.getInstance().collection("Drink");
        foodref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                drinkDao.getTop(lv, 0);
            }
        });


        return root;
    }
}