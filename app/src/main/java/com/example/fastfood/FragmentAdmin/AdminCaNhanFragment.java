package com.example.fastfood.FragmentAdmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fastfood.DanhSachHoaDonActivity;
import com.example.fastfood.MainKhachHang;
import com.example.fastfood.PasswordActivity;
import com.example.fastfood.R;

public class AdminCaNhanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_ca_nhan, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("USER_FILE_LOGIN", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        root.findViewById(R.id.ca_nhan_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), MainKhachHang.class));
            }
        });

        root.findViewById(R.id.ca_nhan_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PasswordActivity.class));
            }
        });
        root.findViewById(R.id.hoadon_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DanhSachHoaDonActivity.class));
            }
        });

        return root;
    }
}