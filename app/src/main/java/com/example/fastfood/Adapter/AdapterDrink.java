package com.example.fastfood.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.example.fastfood.BanActivity;
import com.example.fastfood.DAO.BillFvsDDao;
import com.example.fastfood.DAO.DrinkDao;
import com.example.fastfood.DAO.ImageDao;
import com.example.fastfood.Model.BillDrink;
import com.example.fastfood.Model.Drink;
import com.example.fastfood.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AdapterDrink extends ArrayAdapter<Drink> {

    private Activity activity;
    private int resource;
    private List<Drink> objects;
    private LayoutInflater inflater;
    private AlertDialog Alertdialog;

    int a = 0;

    public AdapterDrink(Activity activity, int resource, List objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        a++;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lv_drink, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_lv_drink_tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.item_lv_drink_tv_price);
            holder.tvnSl = (TextView) convertView.findViewById(R.id.item_lv_drink_tv_db);
            holder.tvSLH = (TextView) convertView.findViewById(R.id.item_lv_drink_tv_sl);

            holder.add = (Button) convertView.findViewById(R.id.item_lv_drink_btn_add);
            holder.img = (ImageView) convertView.findViewById(R.id.item_lv_drink_img);
            holder.cardView = (CardView) convertView.findViewById(R.id.item_lv_drink_bg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Drink obj = objects.get(position);
        if (obj.getStatus() == 1) {
            holder.tvName.setText(obj.getName());
        } else if (obj.getStatus() == 3) {
            holder.tvName.setText(obj.getName() + " (Ngừng bán)");
        } else if (obj.getFigure() == 0) {
            holder.tvName.setText(obj.getName() + " (Hết hàng)");
        }
        holder.tvPrice.setText(String.valueOf(obj.getPrice()));
        holder.tvnSl.setText("Lượt bán: " + obj.getLuotban());
        ImageDao imageDao = new ImageDao(activity);
        imageDao.getImage(obj.getId(), holder.img);

        SharedPreferences pref = activity.getSharedPreferences("USER_FILE_LOGIN", activity.MODE_PRIVATE);
        String userName = pref.getString("USERNAME_LOGIN", "");

        if (userName.equals("admin")) {
            holder.add.setVisibility(View.GONE);
            holder.tvSLH.setVisibility(View.VISIBLE);

            holder.tvSLH.setText("SL: " + obj.getFigure());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialog(Gravity.CENTER, obj);
                }
            });
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obj.getFigure() == 0) {
                    Toast.makeText(activity, "Đồ uống tạm thời đang hết. Vui lòng order sau!", Toast.LENGTH_SHORT).show();
                } else {
                    if (obj.getStatus() == 3) {
                        Toast.makeText(activity, "Đồ uống tạm thời ngưng bán. Vui lòng order sau!", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences pref = activity.getSharedPreferences("TABLE", activity.MODE_PRIVATE);
                        String name = pref.getString("NAME", "");
                        if (name.equals("")) {
                            openAlertDialog();
                        } else {
                            BillFvsDDao billFvsDDao = new BillFvsDDao(activity);
                            billFvsDDao.addbillDrink(new BillDrink(pref.getString("IDBASKET", ""), obj.getId(), 1, obj.getPrice(), false));
                        }
                    }
                }
            }
        });

        if (a == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#DDFCC9"));
        } else if (a == 2) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FCF7C9"));
        } else if (a == 3) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ECDAC5"));
            a = 0;
        }

        return convertView;
    }

    public class ViewHolder {
        TextView tvName, tvPrice, tvnSl, tvSLH;
        Button add;
        ImageView img;
        CardView cardView;
    }

    private void openDialog(int gravity, Drink drink) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_drink);
        // thiết lập giao diện
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        //thiết lập kích thước
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

        TextView tvTitle = dialog.findViewById(R.id.dialog_drink_tv_title);

        EditText edtName = dialog.findViewById(R.id.dialog_drink_tv_name);
        EditText edtPrice = dialog.findViewById(R.id.dialog_drink_tv_price);
        EditText edtsl = dialog.findViewById(R.id.dialog_drink_tv_sl);

        TextInputLayout tilName = dialog.findViewById(R.id.dialog_drink_til_name);
        TextInputLayout tilPrice = dialog.findViewById(R.id.dialog_drink_til_price);
        TextInputLayout tilsl = dialog.findViewById(R.id.dialog_drink_til_sl);

        ImageView img = dialog.findViewById(R.id.dialog_drink_img);

        Button btnadd = dialog.findViewById(R.id.dialog_drink_btn_add);
        Button btncancel = dialog.findViewById(R.id.dialog_drink_btn_cancel);

        // set data dialog
        tvTitle.setText("SỬA/XÓA Drink");

        edtName.setText(drink.getName());
        edtPrice.setText("" + drink.getPrice());
        edtsl.setText("" + drink.getFigure());

        ImageDao imageDao = new ImageDao(activity);
        imageDao.getImage(drink.getId(), img);

        btnadd.setText("SỬA");
        btncancel.setText("XÓA");

        DrinkDao drinkDao = new DrinkDao(activity);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = 0;
                if (edtName.getText().toString().isEmpty()) {
                    tilName.setError("Tên Không Được Để Trống");
                    temp++;
                } else {
                    tilName.setError("");
                }
                if (edtPrice.getText().toString().isEmpty()) {
                    tilPrice.setError("Giá Không Được Để Trống");
                    temp++;
                } else {
                    tilPrice.setError("");
                }
                if (edtsl.getText().toString().isEmpty()) {
                    tilsl.setError("Số Lượng Không Được Để Trống");
                    temp++;
                } else {
                    tilsl.setError("");
                }

                if (temp == 0) {
                    drink.setName(edtName.getText().toString());
                    drink.setPrice(Integer.parseInt(edtPrice.getText().toString()));
                    //update
                    drinkDao.updateDrink(drink);
                    dialog.dismiss();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkDao.deleteDrink(drink.getId());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void openAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn Chưa Chọn Bàn");
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Alertdialog.dismiss();
            }
        });
        builder.setNegativeButton("Xác Nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getContext(), BanActivity.class);
                activity.startActivity(intent);
                Alertdialog.dismiss();
            }
        });
        Alertdialog = builder.create();
        Alertdialog.show();
    }
}
