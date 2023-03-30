package com.example.fastfood.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfood.DAO.DrinkDao;
import com.example.fastfood.DAO.ImageDao;
import com.example.fastfood.Model.Drink;
import com.example.fastfood.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DrinkActivity extends AppCompatActivity {

    Button add;

    int a, checkImg = 0, REQUEST_CODE_FOLDER = 123;

    ImageView img;

    Bitmap bitmap;

    DrinkDao drinkDao;

    ImageDao imageDao;

    ListView lv;

    CollectionReference foodref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_toolbar)));
        getSupportActionBar().setTitle("DRINK");

        add = findViewById(R.id.drink_btn_add);
        lv = findViewById(R.id.drink_lv);

        SharedPreferences pref = getSharedPreferences("USER_FILE_LOGIN", MODE_PRIVATE);
        String userName = pref.getString("USERNAME_LOGIN", "");

        drinkDao = new DrinkDao(this);
        imageDao = new ImageDao(this);
  // kiểm tra xem giá trị userName có bằng admin không.Nếu là admin thì sẽ hiện đối tượng add
        if (!userName.equals("admin")) {
            add.setVisibility(View.GONE);
        }
//Xử lý sự kiện onclick()
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });
//Lấy đối tượng đại diện cho tệp dữ liệu
        foodref = FirebaseFirestore.getInstance().collection("Drink");
        foodref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //cập nhật dữ liệu vào danh sách
                drinkDao.getDrink(lv);
            }
        });
    }

    private void openDialog(int gravity) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_drink);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
  //ánh xạ
        EditText edtName = dialog.findViewById(R.id.dialog_drink_tv_name);
        EditText edtPrice = dialog.findViewById(R.id.dialog_drink_tv_price);
        EditText edtSl = dialog.findViewById(R.id.dialog_drink_tv_sl);

        TextInputLayout tilName = dialog.findViewById(R.id.dialog_drink_til_name);
        TextInputLayout tilPrice = dialog.findViewById(R.id.dialog_drink_til_price);
        TextInputLayout tilSl = dialog.findViewById(R.id.dialog_drink_til_sl);

        img = dialog.findViewById(R.id.dialog_drink_img);

        Button btnadd = dialog.findViewById(R.id.dialog_drink_btn_add);
        Button btncancel = dialog.findViewById(R.id.dialog_drink_btn_cancel);
          // gắn 1 sự kiện lắng nghe
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHọn nguồn thư viện ảnh
                Intent intent = new Intent(Intent.ACTION_PICK);
                //chỉ định dữ liệu sẽ được chọn, ở đây là dạng image
                intent.setType("image/*");
                //hoạt động trả về từ việc chọn hình ảnh
                startActivityForResult(intent, REQUEST_CODE_FOLDER); //  mở 1 hoạt động mới trả về kết quả REQUEST_CODE_FOLDER
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chekc validate
                int temp = 0;
                if (edtName.getText().toString().isEmpty()) {
                    tilName.setError("Chưa nhập tên đồ uống");
                    temp++;
                } else {
                    tilName.setErrorEnabled(false);
                }
                if (edtPrice.getText().toString().isEmpty()) {
                    tilPrice.setError("Chưa nhập giá đồ uống");
                    temp++;
                } else {
                    tilPrice.setErrorEnabled(false);
                }
                if (edtSl.getText().toString().isEmpty()) {
                    tilSl.setError("Chưa nhập số lượng");
                    temp++;
                } else {
                    tilSl.setErrorEnabled(false);
                }
                //check giá trị của ảnh
                if (checkImg == 1) {

                    if (temp == 0) {
                        //thêm 1 đối tượng mới vào cơ sở dữ liệu
                        drinkDao.addDrink(new Drink(edtName.getText().toString(), Integer.parseInt(edtPrice.getText().toString()), Integer.parseInt(edtSl.getText().toString()), 1, 0), img);
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(DrinkActivity.this, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DrinkActivity.this, "Huỷ thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    // được gọi tự động sau khi một hoạt động con (activity) hoàn thành và trả về kết quả.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            //Nếu điều kiện trong bước 2 được đáp ứng, tiếp tục thực hiện các hành động
            Uri uri = data.getData();
            try {
                //đọc dữ liệu uri
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                checkImg = 1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}