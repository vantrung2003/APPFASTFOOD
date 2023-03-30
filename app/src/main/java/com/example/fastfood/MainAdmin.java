package com.example.fastfood;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fastfood.databinding.ActivityMainAdminBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAdmin extends AppCompatActivity {

    private ActivityMainAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();

        BottomNavigationView navViewAdmin = findViewById( R.id.nav_view_admin);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.admin_home, R.id.admin_thong_ke, R.id.admin_doanh_thu, R.id.admin_ca_nhan)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewAdmin, navController);

    }

}