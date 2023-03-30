package com.example.fastfood.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood.BanActivity;
import com.example.fastfood.DAO.BillFvsDDao;
import com.example.fastfood.DAO.ImageDao;
import com.example.fastfood.Model.BillDrink;
import com.example.fastfood.Model.BillFood;
import com.example.fastfood.Model.Drink;
import com.example.fastfood.R;

import java.util.List;

public class AdapterTop extends RecyclerView.Adapter<AdapterTop.TopViewHolder> {

    private Activity activity;
    private List<Drink> objects;
    private AlertDialog Alertdialog;

    int a = 0;

    public void setData(Activity activity, List<Drink> objects) {
        this.objects = objects;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_top, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Drink obj = objects.get(position);

        if (obj == null) {
            return;
        }

        a++;

        holder.tvName.setText(obj.getName());
        ImageDao imageDao = new ImageDao(activity);
        imageDao.getImage(obj.getId(), holder.img);

        SharedPreferences pref = activity.getSharedPreferences("USER_FILE_LOGIN", activity.MODE_PRIVATE);
        String userName = pref.getString("USERNAME_LOGIN", "");

        if (userName.equals("admin")) {
            holder.add.setVisibility(View.GONE);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obj.getFigure() == 0) {
                    Toast.makeText(activity, "Đồ uống tạm thời đang hết. Vui lòng order sau!", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences pref = activity.getSharedPreferences("TABLE", activity.MODE_PRIVATE);
                    String name = pref.getString("NAME", "");
                    if (name.equals("")) {
                        openAlertDialog();
                    } else {
                        BillFvsDDao billFvsDDao = new BillFvsDDao(activity);
                        if (obj.getFigure() > 0) {
                            billFvsDDao.addbillDrink(new BillDrink(pref.getString("IDBASKET", ""), obj.getId(), 1, obj.getPrice(), false));
                        } else {
                            billFvsDDao.addbillFood(new BillFood(pref.getString("IDBASKET", ""), obj.getId(), 1, obj.getPrice(), false));
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
    }

    @Override
    public int getItemCount() {
        if (objects != null) {
            return objects.size();
        }
        return 0;
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        Button add;
        ImageView img;
        CardView cardView;

        public TopViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_lv_top_tv_ten);
            add = itemView.findViewById(R.id.item_lv_top_btn);
            img = itemView.findViewById(R.id.item_lv_top_img);
            cardView = (CardView) itemView.findViewById(R.id.item_lv_top_bg);
        }
    }


    public void openAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                Intent intent = new Intent(activity, BanActivity.class);
                activity.startActivity(intent);
                Alertdialog.dismiss();
            }
        });
        Alertdialog = builder.create();
        Alertdialog.show();
    }
}
