package com.example.fastfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfood.DAO.BasketDao;
import com.example.fastfood.DAO.TableDao;
import com.example.fastfood.MainKhachHang;
import com.example.fastfood.Model.Baskets;
import com.example.fastfood.Model.Table;
import com.example.fastfood.R;

import java.util.Calendar;
import java.util.List;

public class AdapterGridView extends BaseAdapter {

    private Activity activity;
    private List<Table> objects;
    private LayoutInflater inflater;

    int a = 0;

    public AdapterGridView(Activity activity, List<Table> objects) {
        this.activity = activity;
        this.objects = objects;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        a++;
        if (view == null) {
            view = inflater.inflate(R.layout.item_gridview, null);
            holder.ban_tv = view.findViewById(R.id.item_ban_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Table obj = objects.get(i);
        holder.ban_tv.setBackgroundColor(Color.parseColor("#A8A8A8"));
        if (obj.isDachon() == false) {
            if (a == 1) {
                holder.ban_tv.setBackgroundColor(Color.parseColor("#DDFCC9"));
            } else if (a == 2) {
                holder.ban_tv.setBackgroundColor(Color.parseColor("#FCF7C9"));
            } else if (a == 3) {
                holder.ban_tv.setBackgroundColor(Color.parseColor("#ECDAC5"));
                a = 0;
            }
        } else {
            if (a == 3) {
                a = 0;
            }
        }
        holder.ban_tv.setText(obj.getName());

        holder.ban_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obj.isDachon()) {
                    Toast.makeText(activity, "Bàn này đã được chọn", Toast.LENGTH_SHORT).show();
                } else {
                    TableDao tableDao = new TableDao(activity);
                    tableDao.chonBan(obj);

                    SharedPreferences pref = activity.getSharedPreferences("TABLE", activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    BasketDao basketDao = new BasketDao(activity);
                    basketDao.addBasket(new Baskets(obj.getName(), Calendar.getInstance().getTime()), editor);
                    editor.putString("NAME", obj.getName());
                    editor.putString("ID", obj.getId());
                    editor.commit();

                    activity.startActivity(new Intent(activity, MainKhachHang.class));
                }
            }
        });

        return view;
    }

    public class ViewHolder {
        public TextView ban_tv;
    }
}


