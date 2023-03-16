package com.example.fastfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.example.fastfood.DAO.BillFvsDDao;
import com.example.fastfood.Model.Bill;
import com.example.fastfood.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterBill extends ArrayAdapter<Bill> {

    private Activity activity;
    private int resource;
    private List<Bill> objects;
    private LayoutInflater inflater;
    private AlertDialog Alertdialog;

    int a = 0;

    public AdapterBill(Activity activity, int resource, List objects) {
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
            convertView = inflater.inflate(R.layout.item_lv_bill, null);
            holder.tvBan = (TextView) convertView.findViewById(R.id.item_lv_bill_soBan);
            holder.tvNgay = (TextView) convertView.findViewById(R.id.item_lv_bill_ngay);
            holder.tvTong = (TextView) convertView.findViewById(R.id.item_lv_bill_tong);

            holder.listView = (ListView) convertView.findViewById(R.id.item_lv_bill_lv);
            holder.cardView = (CardView) convertView.findViewById(R.id.item_lv_bill_bg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#EFEABE"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#EFC5C5"));
        }
        Bill obj = objects.get(position);

        holder.tvBan.setText(obj.getTable());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        holder.tvNgay.setText(sdf.format(obj.getDate()));
        holder.tvTong.setText("" + obj.getPrice() + " $");
        BillFvsDDao billFvsDDao = new BillFvsDDao(activity);
        billFvsDDao.getbill(holder.listView, obj.getId());
        holder.listView.setEnabled(false);
        return convertView;
    }

    public class ViewHolder {
        TextView tvBan, tvNgay, tvTong;
        ListView listView;
        CardView cardView;
    }

}
