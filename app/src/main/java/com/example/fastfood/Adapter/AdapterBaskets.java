package com.example.fastfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.fastfood.DAO.BillFvsDDao;
import com.example.fastfood.DAO.DrinkDao;
import com.example.fastfood.DAO.FoodDao;
import com.example.fastfood.DAO.ImageDao;
import com.example.fastfood.Model.DvsF;
import com.example.fastfood.R;

import java.util.List;

public class AdapterBaskets extends ArrayAdapter<DvsF> {

    private Activity activity;
    private int resource;
    private List<DvsF> objects;
    private LayoutInflater inflater;

    int a = 0;

    public AdapterBaskets(Activity activity, int resource, List objects) {
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
            convertView = inflater.inflate(R.layout.item_lv_baskets, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_lv_baskets_tv_name);
            holder.tvnSl = (TextView) convertView.findViewById(R.id.item_lv_baskets_edt_sl);
            holder.img = (ImageView) convertView.findViewById(R.id.item_lv_baskets_img);
            holder.cardView = (CardView) convertView.findViewById(R.id.item_lv_baskets_bg);
            holder.tvXoa = (ImageView) convertView.findViewById(R.id.item_lv_baskets_tv_xoa);
            holder.tvCong = (TextView) convertView.findViewById(R.id.item_lv_baskets_tv_cong);
            holder.tvtru = (TextView) convertView.findViewById(R.id.item_lv_baskets_tv_tru);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DvsF obj = objects.get(position);
        if (obj.isCheck()) {
            holder.tvXoa.setVisibility(View.GONE);
            holder.tvtru.setVisibility(View.GONE);
            holder.tvCong.setVisibility(View.GONE);
            holder.tvnSl.setEnabled(false);
            holder.tvnSl.setLayoutParams(new LinearLayout.LayoutParams(700, 60));
            holder.tvnSl.setText("Số Lương: " + obj.getFigure());
        } else {
            holder.tvnSl.setText("" + obj.getFigure());
        }
        if (obj.isDrinkorfood()) {
            FoodDao foodDao = new FoodDao(activity);
            foodDao.getNameFood(obj.idDrinkorFood(), holder.tvName);
        } else {
            DrinkDao drinkDao = new DrinkDao(activity);
            drinkDao.getNameDrink(obj.idDrinkorFood(), holder.tvName);
        }
        BillFvsDDao billFvsDDao = new BillFvsDDao(activity);
        ImageDao imageDao = new ImageDao(activity);
        imageDao.getImage(obj.idDrinkorFood(), holder.img);

        holder.tvnSl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                billFvsDDao.updatebill(obj.getId(), obj.isDrinkorfood(), Integer.parseInt(s.toString()));
                Toast.makeText(activity, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.tvCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billFvsDDao.updatebill(obj.getId(), obj.isDrinkorfood(), obj.getFigure() + 1);
            }
        });
        holder.tvtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj.getFigure() == 1) {
                    Toast.makeText(activity, "Số lượng tối thiểu là 1. Nếu bạn không muốn order hãy nhấn Xóa!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billFvsDDao.updatebill(obj.getId(), obj.isDrinkorfood(), obj.getFigure() - 1);
                }
            }
        });
        holder.tvXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billFvsDDao.deletebill(obj.getId(), obj.isDrinkorfood());
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
        TextView tvName, tvnSl, tvCong, tvtru;
        ImageView img, tvXoa;
        CardView cardView;
    }
}
