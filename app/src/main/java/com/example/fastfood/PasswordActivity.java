package com.example.fastfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class PasswordActivity extends AppCompatActivity {

    TextInputLayout tilmk, tilmkm, tilrmk;
    EditText edtmk, edtmkm, edtrmk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_toolbar)));
        getSupportActionBar().setTitle("ĐỔI MẬT KHẨU");

        tilmk = findViewById(R.id.password_til_mk);
        tilmkm = findViewById(R.id.password_til_mkm);
        tilrmk = findViewById(R.id.password_til_rmk);

        edtmk = findViewById(R.id.password_edt_mk);
        edtmkm = findViewById(R.id.password_edt_mkm);
        edtrmk = findViewById(R.id.password_edt_rmkm);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String pass = pref.getString("USERPASS", "");

        findViewById(R.id.password_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtmk.getText().toString().equals(pass)) {
                    tilmk.setError("");
                    int temp = 0;

                    if (edtmkm.getText().toString().isEmpty()) {
                        tilmkm.setError("Mật Khẩu Không Được Để Trống");
                        temp++;
                    } else {
                        tilmkm.setError("");
                    }

                    if (edtrmk.getText().toString().isEmpty()) {
                        tilrmk.setError("Mật Khẩu Không Được Để Trống");
                        temp++;
                    } else {
                        tilrmk.setError("");
                    }

                    if (temp == 0) {
                        if (edtmkm.getText().toString().equals(edtrmk.getText().toString())) {
                            tilmkm.setError("");
                            tilrmk.setError("");
                            Toast.makeText(PasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = pref.edit();
                            editor.clear();
                            editor.putString("USERNAME", "admin");
                            editor.putString("USERPASS", edtrmk.getText().toString());
                            editor.commit();
                            startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                        } else {
                            tilmkm.setError("Mật Khẩu Không Trùng Khớp");
                            tilrmk.setError("Mật Khẩu Không Trùng Khớp");
                        }
                    } else {
                        temp = 0;
                    }

                } else {
                    tilmk.setError("Mật Khẩu Cũ Không Đúng");
                }
            }
        });
    }
}