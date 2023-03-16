package com.example.fastfood.FragmentAdmin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fastfood.DAO.BillDao;
import com.example.fastfood.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AdminDoanhThuFragment extends Fragment {
    TextView tv_tuNgay, tv_denNgay, tv_doanhThu;
    ImageView img_tuNgay, img_denNgay;
    Button btn_doanhThu;
    int mYear, mMonth, mDay;
    Date ngayBatDau, ngayKetThuc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_doanh_thu, container, false);
        tv_denNgay = root.findViewById(R.id.doanhthu_edt_denngay);
        tv_tuNgay = root.findViewById(R.id.doanhthu_edt_tungay);
        tv_doanhThu = root.findViewById(R.id.doanhthu_tv);
        img_tuNgay = root.findViewById(R.id.doanhthu_img_tungay);
        img_denNgay = root.findViewById(R.id.doanhthu_img_denngay);
        btn_doanhThu = root.findViewById(R.id.doanhthu_btn_tinh);

        tv_tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DATE);
                DatePickerDialog d = new DatePickerDialog(getContext(), 0, tuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        tv_denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DATE);
                DatePickerDialog d = new DatePickerDialog(getContext(), 0, denNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        btn_doanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_tuNgay.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa chọn ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                } else if (tv_denNgay.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa chọn ngày kết thúc!", Toast.LENGTH_SHORT).show();
                } else {
                    int check = ngayBatDau.compareTo(ngayKetThuc);
                    if (check > 0) {
                        Toast.makeText(getContext(), "Ngày bắt đầu phải nhỏ hơn ngày kết thúc! ", Toast.LENGTH_SHORT).show();
                    } else {
                        tv_doanhThu.setText("");
                        BillDao billDao = new BillDao(getActivity());
                        billDao.doanhthu(ngayBatDau, ngayKetThuc, tv_doanhThu);
                    }

                }

            }
        });


        return root;
    }

    DatePickerDialog.OnDateSetListener tuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            mDay = d;
            mMonth = m;
            mYear = y;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            ngayBatDau = c.getTime();
            tv_tuNgay.setText("Ngày " + d + "/" + (m + 1) + "/" + y);

        }
    };

    DatePickerDialog.OnDateSetListener denNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            mDay = d;
            mMonth = m;
            mYear = y;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            ngayKetThuc = c.getTime();
            tv_denNgay.setText("Ngày " + d + "/" + (m + 1) + "/" + y);
        }
    };
}