package com.example.fastfood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fastfood.databinding.ActivityMainAdminBinding;
import com.example.fastfood.databinding.ActivityMainKhachHangBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainKhachHang extends AppCompatActivity {

    private ActivityMainKhachHangBinding binding;
    private ActivityMainAdminBinding bindingAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();

        SharedPreferences pref = getSharedPreferences("USER_FILE_LOGIN", MODE_PRIVATE);
        String userName = pref.getString("USERNAME_LOGIN", "");
        if (userName.equals("admin")) {
            bindingAdmin = ActivityMainAdminBinding.inflate(getLayoutInflater());
            setContentView(bindingAdmin.getRoot());

            BottomNavigationView navViewAdmin = findViewById(R.id.nav_view_admin);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.khach_hang_home, R.id.admin_thong_ke, R.id.admin_doanh_thu, R.id.admin_ca_nhan)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_admin);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(bindingAdmin.navViewAdmin, navController);
        } else {
            binding = ActivityMainKhachHangBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.khach_hang_home, R.id.khach_hang_gio_hang, R.id.khach_hang_hoa_don)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_khach_hang);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
    }

}