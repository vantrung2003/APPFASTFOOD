package com.example.fastfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.fastfood.DAO.DrinkDao;
import com.example.fastfood.DAO.FoodDao;
import com.example.fastfood.Model.DvsF;
import com.example.fastfood.R;

import java.util.List;

public class AdapterBillDvF extends ArrayAdapter<DvsF> {

    private Activity activity;
    private int resource;
    private List<DvsF> objects;
    private LayoutInflater inflater;
    private AlertDialog Alertdialog;

    int a = 0;

    public AdapterBillDvF(Activity activity, int resource, List objects) {
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
            convertView = inflater.inflate(R.layout.item_lv_bill_fd, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_lv_bill_fd_ten);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.item_lv_bill_fd_gia);
            holder.tvnSl = (TextView) convertView.findViewById(R.id.item_lv_bill_fd_sl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DvsF obj = objects.get(position);

        if (obj.isDrinkorfood()) {
            FoodDao foodDao = new FoodDao(activity);
            foodDao.getNameFood(obj.idDrinkorFood(), holder.tvName);
        } else {
            DrinkDao drinkDao = new DrinkDao(activity);
            drinkDao.getNameDrink(obj.idDrinkorFood(), holder.tvName);
        }

        holder.tvPrice.setText(String.valueOf("Giá: " + obj.getPrice() + " $"));
        holder.tvnSl.setText("SL: " + obj.getFigure());


        return convertView;
    }

    public class ViewHolder {
        TextView tvName, tvPrice, tvnSl;
    }

}
